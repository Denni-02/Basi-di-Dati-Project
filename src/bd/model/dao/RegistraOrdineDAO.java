package bd.model.dao;
import bd.exception.DAOException;
import java.math.BigDecimal;
import java.sql.*;

public class RegistraOrdineDAO {

    public int inserisciOrdine(String contattoEmergenza, String indirizzoSpedizione, BigDecimal importo, String cliente, Connection conn) throws DAOException {

        try{

            CallableStatement cs = conn.prepareCall("{CALL inserisciOrdine(?, ?, ?, ?, ?, ?)}");

            cs.setDate(1,new java.sql.Date(new java.util.Date().getTime()));
            cs.setString(2, contattoEmergenza);
            cs.setString(3, indirizzoSpedizione);
            cs.setBigDecimal(4, importo);
            cs.setString(5, cliente);
            cs.registerOutParameter(6, Types.INTEGER);

            cs.execute();

            int idOrdine = cs.getInt(6);
            return idOrdine;

        } catch (SQLException e) {
            throw new DAOException("RegistraOrdineDAO error: " + e.getMessage());
        }
    }
}
