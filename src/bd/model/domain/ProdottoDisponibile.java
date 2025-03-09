package bd.model.domain;
import java.util.Date;
import java.math.BigDecimal;

public class ProdottoDisponibile {
    private final int idProdotto;
    private final String nome;
    private final BigDecimal prezzo;
    private final Date dataScadenza;
    private final int quantitàDisponibile;

    public ProdottoDisponibile(int idProdotto, String nome, BigDecimal prezzo, int quantitàDisponibile, Date dataScadenza) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.prezzo = prezzo;
        this.dataScadenza = dataScadenza;
        this.quantitàDisponibile = quantitàDisponibile;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public int getQuantitàDisponibile() {
        return quantitàDisponibile;
    }

    public void stampaProdotto(){
        System.out.println(this.idProdotto + " - Nome: " + this.nome + " - Quantità: " + this.quantitàDisponibile);
    }

}
