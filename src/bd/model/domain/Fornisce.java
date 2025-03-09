package bd.model.domain;
import java.util.Date;

public class Fornisce {
    private final int idRifornimento;
    private final int idProdotto;
    private final int quantitaRifornita;
    private final Date dataScadenza;

    public Fornisce(int idRifornimento, int idProdotto, int quantitaRifornita, Date dataScadenza) {
        this.idProdotto = idProdotto;
        this.idRifornimento = idRifornimento;
        this.quantitaRifornita = quantitaRifornita;
        this.dataScadenza = dataScadenza;
    }

    public int getIdRifornimento() {
        return idRifornimento;
    }
    public int getIdProdotto() {
        return idProdotto;
    }
    public int getQuantitaRifornita() {
        return quantitaRifornita;
    }
    public Date getDataScadenza() {
        return dataScadenza;
    }
}
