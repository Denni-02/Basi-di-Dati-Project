package bd.model.domain;

import java.util.Date;

public class Contiene {
    private final int ordine;
    private final int prodotto;
    private final int quantitaOrdinata;
    private final Date dataScadenza;

    public Contiene(int ordine, int prodotto, int quantitaOrdinata, Date dataScadenza) {
        this.ordine = ordine;
        this.prodotto = prodotto;
        this.quantitaOrdinata = quantitaOrdinata;
        this.dataScadenza = dataScadenza;
    }

    public int getOrdine() {
        return ordine;
    }
    public int getProdotto() {
        return prodotto;
    }
    public int getQuantitaOrdinata() {
        return quantitaOrdinata;
    }
    public Date getDataScadenza(){return  dataScadenza;}
}
