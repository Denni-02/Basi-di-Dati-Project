package bd.model.dao;
import bd.model.domain.RicevutaOrdine;
import bd.exception.DAOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportOrdiniGiornalieriDAO implements ProceduraGenericaDAO<List<RicevutaOrdine>>{
    public List<RicevutaOrdine> execute(Object... params) throws DAOException {

        List<RicevutaOrdine> ordini = new ArrayList<>();
        Date dataOrdine = (Date) params[0];
        java.sql.Date sqlDataOrdine = new java.sql.Date(dataOrdine.getTime());

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{CALL stampaOrdiniGiornalieri(?)}");
            cs.setDate(1, sqlDataOrdine);
            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {
                while(rs.next()) {
                    RicevutaOrdine ordine = new RicevutaOrdine(
                            rs.getInt("idOrdine"),
                            rs.getDate("Data"),
                            rs.getString("ContattoEmergenza"),
                            rs.getString("IndirizzoSpedizione"),
                            rs.getBigDecimal("Importo"),
                            rs.getString("Cliente")
                    );
                    ordini.add(ordine);
                }
            }

        } catch(SQLException e) {
            throw new DAOException("ReportOrdiniGiornalieriDAO error: " + e.getMessage());
        }

        return ordini;
    }

}