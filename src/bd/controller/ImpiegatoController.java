package bd.controller;
import bd.exception.DAOException;
import bd.model.dao.*;
import bd.model.domain.*;
import bd.view.ImpiegatoView;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImpiegatoController implements Controller {

    @Override
    public void start() {
        try {
            ConnectionFactory.changeRole(Role.IMPIEGATO);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        while(true) {
            int choice;
            try {
                choice = ImpiegatoView.showMenu();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }

            switch(choice) {
                case 1 -> registraOrdine();
                case 2 -> stampaRicevutaOrdine();
                case 3 -> reportOrdini();
                case 4 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
    }

    public void reportOrdini() {

        List<RicevutaOrdine> ordini = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Inserisci la data dell'ordine (YYYY-MM-DD): ");
            String dataOrdineStr = scanner.nextLine();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date dataOrdine = sdf.parse(dataOrdineStr);

            ordini = new ReportOrdiniGiornalieriDAO().execute(dataOrdine);

            for (RicevutaOrdine ordine : ordini) {

                System.out.println(ordine.getIdOrdine() + " - Data: " + ordine.getDataOrdine() + " - Contatto emergenza: " + ordine.getContattoEmergenza() + " - Indirizzo spedizione: " +
                        ordine.getIndirizzoSpedizione() + " - Importo: " + ordine.getImportoTotale() + " - P.IVA Cliente: " + ordine.getPIvaCliente());
            }

            System.out.println("\n");

        } catch (DAOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void stampaRicevutaOrdine() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("ID Ordine: ");
        int idOrdine = scanner.nextInt();

        try {
            RicevutaOrdine ordine = new StampaRicevutaOrdineDAO().execute(idOrdine);

            System.out.println(ordine.getIdOrdine() + " - Data: " + ordine.getDataOrdine() + " - Contatto emergenza: " + ordine.getContattoEmergenza() + " - Indirizzo spedizione: " +
                    ordine.getIndirizzoSpedizione() + " - Importo: " + ordine.getImportoTotale() + " - P.IVA Cliente: " + ordine.getPIvaCliente() + " \n");

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }

    public void registraOrdine()  {

        List<ProdottoDisponibile> selezionati = new ArrayList<>();
        BigDecimal importoTotale = BigDecimal.ZERO;
        boolean disponibilita = true;

        try {
            Connection conn = ConnectionFactory.getConnection();

            try {

                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                conn.setAutoCommit(false);

                Scanner scanner = new Scanner(System.in);
                List<ProdottoDisponibile> prodotti = new ElencoProdottiDisponibiliDAO().execute(conn);

                for (ProdottoDisponibile prodotto : prodotti) {
                    System.out.println(prodotto.getIdProdotto() + " - Nome: " + prodotto.getNome() + " - Prezzo: " + prodotto.getPrezzo() + " - Scadenza: " +
                            prodotto.getDataScadenza() + " - Quantità disponibile: " + prodotto.getQuantitàDisponibile());
                }

                System.out.println("Inserisci l'ID del prodotto, la data di scadenza e la quantità desiderata (0 per terminare):");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                while (true) {
                    System.out.print("ID Prodotto: ");
                    int idProdotto = scanner.nextInt();
                    if (idProdotto == 0) break;

                    System.out.print("Data di scadenza (YYYY-MM-DD): ");
                    String dataScadenzaStr = scanner.next();
                    Date dataScadenza = sdf.parse(dataScadenzaStr);

                    System.out.print("Quantità: ");
                    int quantita = scanner.nextInt();
                    BigDecimal quantitaBD = new BigDecimal(quantita);

                    for (ProdottoDisponibile prodotto : prodotti) {
                        if (prodotto.getIdProdotto() == idProdotto && prodotto.getDataScadenza().equals(dataScadenza)) {
                            if (quantita <= prodotto.getQuantitàDisponibile()) {
                                System.out.println("Hai aggiunto " + quantita + " di " + prodotto.getNome());
                                ProdottoDisponibile sel = new ProdottoDisponibile(prodotto.getIdProdotto(), prodotto.getNome(), prodotto.getPrezzo(), quantita, prodotto.getDataScadenza());
                                selezionati.add(sel);
                            } else {
                                disponibilita = false;
                                break;
                            }
                        }
                    }
                }

                if (disponibilita) {
                    System.out.print("\nOK, hai selezionato i seguenti prodotti:\n");

                    for (ProdottoDisponibile sel : selezionati) {
                        BigDecimal quantitaBD = new BigDecimal(sel.getQuantitàDisponibile());
                        importoTotale = importoTotale.add(sel.getPrezzo().multiply(quantitaBD));
                        sel.stampaProdotto();
                    }

                    System.out.print("Inserisci dati dell'ordine:\n");

                    System.out.print("Inserisci P.IVA Cliente: ");
                    String pIvaCliente = scanner.next();

                    System.out.print("Inserisci il contatto di emergenza: ");
                    String contattoEmergenza = scanner.next();

                    System.out.print("Inserisci l'indirizzo di spedizione: ");
                    scanner.nextLine();
                    String indirizzoSpedizione = scanner.nextLine();

                    int idOrdine = new RegistraOrdineDAO().inserisciOrdine(contattoEmergenza, indirizzoSpedizione, importoTotale, pIvaCliente, conn);

                    for (ProdottoDisponibile sel : selezionati) {
                        Contiene contiene = new InserisciDettagliOrdineDAO().execute(conn, idOrdine, sel.getIdProdotto(), sel.getQuantitàDisponibile(), sel.getDataScadenza());
                    }

                } else {
                    System.out.print("\nERRORE\n");
                }

                conn.commit();

            } catch (DAOException | ParseException | SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                conn.setAutoCommit(true);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
