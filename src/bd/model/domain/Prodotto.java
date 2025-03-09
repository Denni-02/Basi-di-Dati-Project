package bd.model.domain;
import java.math.BigDecimal;

public class Prodotto {
    private final int idProdotto;
    private final String nome;
    private final BigDecimal prezzo;
    public enum Categoria {
        FRUTTA,
        VERDURA
    }
    private final Categoria categoria;

    public Prodotto(int idProdotto, String nome, BigDecimal prezzo, Categoria categoria) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.prezzo = prezzo;
        this.categoria = categoria;
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

    public Categoria getCategoria() {
        return categoria;
    }
}
