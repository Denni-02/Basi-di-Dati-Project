package bd.model.dao;
import bd.exception.DAOException;
import bd.model.domain.Prodotto;
import bd.model.domain.ProdottoDisponibile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class StampaCatalogoDAO implements ProceduraGenericaDAO <List<Prodotto>>{

    @Override
    public List<Prodotto> execute(Object... params) throws DAOException {

        Connection conn = (Connection) params[0];
        List<Prodotto> prodotti = new ArrayList<>();
        String query = "{CALL stampaCatalogoProdotti()}";

        try {

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery() ;

            while (rs.next()) {
                Prodotto prodotto = new Prodotto(
                        rs.getInt("idProdotto"),
                        rs.getString("Nome"),
                        rs.getBigDecimal("Prezzo"),
                        Prodotto.Categoria.valueOf(rs.getString("Categoria").toUpperCase())
                );
                prodotti.add(prodotto);
            }

        } catch(SQLException e) {
            throw new DAOException("StampaCatalogoDAO error: " + e.getMessage());
        }

        return prodotti;
    }
}
