package bd.model.domain;
import java.math.BigDecimal;

public class RicevutaRifornimento {
    private final int idRifornimento;
    private final BigDecimal costo;
    private final String nomeFornitore;
    private final String cognomeFornitore;
    private final String codiceFiscale;

    public RicevutaRifornimento(int idRifornimento, BigDecimal costo, String nomeFornitore, String cognomeFornitore, String codiceFiscale) {
        this.idRifornimento = idRifornimento;
        this.costo = costo;
        this.nomeFornitore = nomeFornitore;
        this.cognomeFornitore = cognomeFornitore;
        this.codiceFiscale = codiceFiscale;
    }

    public int getIdRifornimento() {
        return idRifornimento;
    }
    public BigDecimal getCosto() {
        return costo;
    }
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    public String getCognomeFornitore() {
        return cognomeFornitore;
    }
    public String getNomeFornitore() {
        return nomeFornitore;
    }
}
