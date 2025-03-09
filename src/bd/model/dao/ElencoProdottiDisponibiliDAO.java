package bd.model.dao;
import bd.exception.DAOException;
import bd.model.domain.ProdottoDisponibile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElencoProdottiDisponibiliDAO implements ProceduraGenericaDAO <List<ProdottoDisponibile>>{

    @Override
    public List<ProdottoDisponibile> execute(Object... params) throws DAOException {
        Connection conn = (Connection) params[0];
        List<ProdottoDisponibile> prodotti = new ArrayList<>();
        String query = "{CALL elencoProdottiDisponibili()}";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery() ;

            while (rs.next()) {
                ProdottoDisponibile prodotto = new ProdottoDisponibile(
                        rs.getInt("idProdotto"),
                        rs.getString("Nome"),
                        rs.getBigDecimal("Prezzo"),
                        rs.getInt("QuantitàDisponibile"),
                        rs.getDate("DataScadenza")
                );
                prodotti.add(prodotto);
            }
        } catch(SQLException e) {
            throw new DAOException("ElencoProdottiDisponibiliDAO error: " + e.getMessage());
        }

        return prodotti;
    }
}
