package bd.model.domain;
import java.math.BigDecimal;
import java.util.Date;

public class GiacenzaMagazzino {
    private final Date dataScadenza;
    private final int idProdotto;
    private final int quantità;
    private final String nome;
    private final BigDecimal prezzo;
    private final String categoria;

    public GiacenzaMagazzino(Date dataScadenza, int idProdotto, int quantità, String nome,  BigDecimal prezzo, String categoria){
        this.dataScadenza = dataScadenza;
        this.idProdotto = idProdotto;
        this.quantità = quantità;
        this.nome = nome;
        this.prezzo = prezzo;
        this.categoria = categoria;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }
    public int getIdProdotto() {
        return idProdotto;
    }

    public String getNome() {
        return nome;
    }
    public int getQuantità() {
        return quantità;
    }
    public BigDecimal getPrezzo() {
        return prezzo;
    }
    public String getCategoria() {
        return categoria;
    }
}
