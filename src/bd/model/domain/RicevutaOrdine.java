package bd.model.domain;
import java.math.BigDecimal;
import java.util.Date;

public class RicevutaOrdine {
    private final int idOrdine;
    private final Date dataOrdine;
    private final String contattoEmergenza;
    private final String indirizzoSpedizione;
    private final BigDecimal importoTotale;
    private final String pIvaCliente;

    public RicevutaOrdine(int idOrdine, Date dataOrdine, String contattoEmergenza, String indirizzoSpedizione, BigDecimal importoTotale, String pIvaCliente) {
        this.idOrdine = idOrdine;
        this.dataOrdine = dataOrdine;
        this.contattoEmergenza = contattoEmergenza;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.importoTotale = importoTotale;
        this.pIvaCliente = pIvaCliente;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public String getContattoEmergenza() {
        return contattoEmergenza;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    public String getPIvaCliente() {
        return pIvaCliente;
    }
}