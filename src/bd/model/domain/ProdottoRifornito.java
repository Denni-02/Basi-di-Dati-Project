package bd.model.domain;
import java.math.BigDecimal;
import java.util.Date;

public class ProdottoRifornito {
    private final int idProdotto;
    private final String nome;
    private final BigDecimal prezzo;
    private final Date dataScadenza;
    private final int quantitàRifornita;


    public ProdottoRifornito(int idProdotto, String nome, BigDecimal prezzo, Date dataScadenza, int quantitàRifornita) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.prezzo = prezzo;
        this.dataScadenza = dataScadenza;
        this.quantitàRifornita = quantitàRifornita;
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
    public int getQuantitàRifornita() {
        return quantitàRifornita;
    }
    public void stampaProdotto(){
        System.out.println(this.idProdotto + " - Nome: " + this.nome + " - Quantità: " + this.quantitàRifornita);
    }

}
