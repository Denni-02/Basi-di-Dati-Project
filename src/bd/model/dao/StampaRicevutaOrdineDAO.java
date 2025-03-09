package bd.model.dao;
import bd.model.domain.RicevutaOrdine;
import bd.exception.DAOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StampaRicevutaOrdineDAO implements ProceduraGenericaDAO<RicevutaOrdine>{
    public RicevutaOrdine execute(Object... params) throws DAOException {

        int idOrdine = (int) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{CALL stampaRicevutaOrdine(?)}");
            cs.setInt(1, idOrdine);
            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {
                if(rs.next()) {
                    return new RicevutaOrdine(
                            rs.getInt("idOrdine"),
                            rs.getDate("Data"),
                            rs.getString("ContattoEmergenza"),
                            rs.getString("IndirizzoSpedizione"),
                            rs.getBigDecimal("Importo"),
                            rs.getString("Cliente")
                    );
                }
            }

        } catch(SQLException e) {
            throw new DAOException("StampaRicevutaOrdineDAO error: " + e.getMessage());
        }

        return null;
    }

}
