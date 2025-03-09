package bd.model.dao;
import bd.exception.DAOException;
import bd.model.domain.GiacenzaMagazzino;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResocontoGiacenzeDAO implements ProceduraGenericaDAO<List<GiacenzaMagazzino>>{

    public List<GiacenzaMagazzino> execute(Object... params) throws DAOException {

        List<GiacenzaMagazzino> giacenze = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{CALL resocontoGiacenze()}");
            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {
                while(rs.next()) {
                    GiacenzaMagazzino giacenza =  new GiacenzaMagazzino(
                            rs.getDate("DataScadenza"),
                            rs.getInt("Prodotto"),
                            rs.getInt("QuantitàDisponibile"),
                            rs.getString("Nome"),
                            rs.getBigDecimal("Prezzo"),
                            rs.getString("Categoria")
                    );
                    giacenze.add(giacenza);
                }
            }

        } catch(SQLException e) {
            throw new DAOException("RescontoGiacenzeDAO error: " + e.getMessage());
        }

        return giacenze;
    }

}
