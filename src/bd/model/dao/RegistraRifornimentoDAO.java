package bd.model.dao;
import bd.exception.DAOException;
import java.math.BigDecimal;
import java.sql.*;

public class RegistraRifornimentoDAO {

    public int inserisciRifornimento(BigDecimal costo, int idFornitore, Connection conn) throws DAOException {

        try{

            CallableStatement cs = conn.prepareCall("{CALL registraRifornimento(?, ?, ?)}");

            cs.setBigDecimal(1, costo);
            cs.setInt(2, idFornitore);
            cs.registerOutParameter(3, Types.INTEGER);

            cs.execute();

            int idRifornimento = cs.getInt(3);
            return idRifornimento;

        } catch (SQLException e) {
            throw new DAOException("RegistraRifornimentoDAO error: " + e.getMessage());
        }
    }
}
