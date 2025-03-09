package bd.controller;
import bd.exception.DAOException;
import bd.model.dao.*;
import bd.model.domain.*;
import bd.view.MagazziniereView;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MagazziniereController implements Controller {

    @Override
    public void start() {
        try {
            ConnectionFactory.changeRole(Role.MAGAZZINIERE);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        while(true) {
            int choice;
            try {
                choice = MagazziniereView.showMenu();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }

            switch(choice) {
                case 1 -> gestisciProdotti();
                case 2 -> registraRifornimento();
                case 3 -> stampaRicevutaRifornimento();
                case 4 -> resocontoGiacenze();
                case 5 -> reportScadenze();
                case 6 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
    }

    public void registraRifornimento() {

        Scanner scanner = new Scanner(System.in);
        BigDecimal costo = BigDecimal.ZERO;

        try {
            Connection conn = ConnectionFactory.getConnection();

            try {

                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                conn.setAutoCommit(false);

                List<Prodotto> prodotti = new StampaCatalogoDAO().execute(conn);
                List<ProdottoRifornito> prodottiRiforniti = new ArrayList<>();

                for (Prodotto prodotto : prodotti) {
                    System.out.println(prodotto.getIdProdotto() + " - Nome: " + prodotto.getNome() + " - Prezzo: " + prodotto.getPrezzo());
                }

                System.out.println("Scegliere prodotti da rifornire, per farlo inserisci l'ID del prodotto, la data di scadenza e la quantità desiderata (0 per terminare):");
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

                    for (Prodotto prodotto : prodotti) {
                        if (prodotto.getIdProdotto() == idProdotto) {
                            System.out.println("Hai aggiunto " + quantita + " di " + prodotto.getNome() + " con scadenza " + dataScadenzaStr);
                            ProdottoRifornito prodottoRifornito = new ProdottoRifornito(idProdotto, prodotto.getNome(), prodotto.getPrezzo(), dataScadenza, quantita);
                            prodottiRiforniti.add(prodottoRifornito);
                        }
                    }
                }

                System.out.print("\nOK, hai selezionato i seguenti prodotti da rifornire:\n");

                for (ProdottoRifornito prodottoRifornito : prodottiRiforniti) {
                    BigDecimal quantitaBD = new BigDecimal(prodottoRifornito.getQuantitàRifornita());
                    costo = costo.add(prodottoRifornito.getPrezzo().multiply(quantitaBD));
                    prodottoRifornito.stampaProdotto();
                }

                System.out.print("Inserisci id del fornitore:");
                int idFornitore = scanner.nextInt();

                int idRifornimento = new RegistraRifornimentoDAO().inserisciRifornimento(costo, idFornitore, conn);
                System.out.print("Inserito rifornimento: " + idRifornimento + "\n");


                for (ProdottoRifornito rif : prodottiRiforniti) {
                    Fornisce fornisce = new InserisciDettagliRifornimentoDAO().execute(conn, idRifornimento, rif.getIdProdotto(), rif.getQuantitàRifornita(), rif.getDataScadenza());
                }

                conn.commit();

            } catch (DAOException | ParseException | SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void gestisciProdotti() {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci ID del prodotto: ");
            int idProdotto = scanner.nextInt();
            System.out.print("Inserisci il nuovo prezzo: ");

            BigDecimal nuovoPrezzo = new BigDecimal(scanner.next());

            GestioneProdottiDAO gestioneProdottiDAO = new GestioneProdottiDAO();
            gestioneProdottiDAO.aggiornaPrezzo(idProdotto, nuovoPrezzo);

            System.out.println("Prezzo aggiornato con successo.");
        } catch (DAOException e){
            throw new RuntimeException(e);
        }
    }

    public void reportScadenze() {

        Date dataCorrente = null;
        Date dataScadenza;

        try{
            List<ProdottoInScadenza> prodotti = new ReportScadenzeDAO().execute();

            for (ProdottoInScadenza prodotto : prodotti) {

                dataScadenza = prodotto.getDataScadenza();

                if (!dataScadenza.equals(dataCorrente)) {
                    System.out.println("Data scadenza: " + dataScadenza);
                    dataCorrente = dataScadenza;
                }

                System.out.println("Nome: " + prodotto.getNome() + " - Quantità: " + prodotto.getQuantità());
            }

        }catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stampaRicevutaRifornimento() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID Rifornimento: ");
        int idRif = scanner.nextInt();

        try {
            RicevutaRifornimento rif = new StampaRicevutaRifornimentoDAO().execute(idRif);

            System.out.println(rif.getIdRifornimento() + " - Costo: " + rif.getCosto() + " - Nome: " + rif.getNomeFornitore() + " - Cognome: " +
                    rif.getCognomeFornitore() + " - CF: " + rif.getCodiceFiscale() + " \n");

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resocontoGiacenze() {
        List<GiacenzaMagazzino> giacenze = new ArrayList<>();

        try {
            giacenze = new ResocontoGiacenzeDAO().execute();

            for (GiacenzaMagazzino giacenza : giacenze) {
                System.out.println(giacenza.getIdProdotto() + " - Nome: " + giacenza.getNome() + " - Categoria: " + giacenza.getCategoria() + " - Prezzo: " +
                        giacenza.getPrezzo() + " - Quantità: " + giacenza.getQuantità() + " - Data scadenza: " + giacenza.getDataScadenza());
            }
            System.out.println("\n");

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
}

