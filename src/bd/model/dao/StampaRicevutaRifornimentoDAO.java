package bd.model.dao;
import bd.exception.DAOException;
import bd.model.domain.RicevutaRifornimento;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StampaRicevutaRifornimentoDAO implements ProceduraGenericaDAO<RicevutaRifornimento>{

    public RicevutaRifornimento execute(Object... params) throws DAOException {

        int idRif = (int) params[0];

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{CALL stampaRicevutaRifornimento(?)}");
            cs.setInt(1, idRif);
            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {
                if(rs.next()) {
                    return new RicevutaRifornimento(
                            rs.getInt("idRifornimento"),
                            rs.getBigDecimal("Costo"),
                            rs.getString("Nome"),
                            rs.getString("Cognome"),
                            rs.getString("CodiceFiscale")
                    );
                }
            }

        } catch(SQLException e) {
            throw new DAOException("StampaRicevutaRifornimentoDAO error: " + e.getMessage());
        }
        return null;
    }
}
