package bd.model.dao;
import bd.exception.DAOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class GestioneProdottiDAO{

    public void aggiornaPrezzo(int idProdotto, BigDecimal nuovoPrezzo) throws DAOException {

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{CALL gestioneProdotti(?, ?)}");
            cs.setInt(1, idProdotto);
            cs.setBigDecimal(2, nuovoPrezzo);
            cs.execute();

        } catch(SQLException e) {
            throw new DAOException("GestioneProdottiDAO error: " + e.getMessage());
        }

    }

}
