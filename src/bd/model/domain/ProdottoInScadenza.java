package bd.model.domain;
import java.util.Date;

public class ProdottoInScadenza {
    private final String nome;
    private final Date dataScadenza;
    private final int quantità;

    public ProdottoInScadenza(String nome, Date dataScadenza, int quantità){
        this.nome = nome;
        this.dataScadenza = dataScadenza;
        this.quantità = quantità;
    }

    public int getQuantità() {
        return quantità;
    }

    public String getNome() {
        return nome;
    }
    public Date getDataScadenza() {
        return dataScadenza;
    }
}
