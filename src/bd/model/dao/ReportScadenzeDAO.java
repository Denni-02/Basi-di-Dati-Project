package bd.model.dao;
import bd.exception.DAOException;
import bd.model.domain.ProdottoInScadenza;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportScadenzeDAO implements ProceduraGenericaDAO<List<ProdottoInScadenza>>{

    @Override
    public List<ProdottoInScadenza> execute(Object... params) throws DAOException{

        List<ProdottoInScadenza> prodotti = new ArrayList<>();

        String sql = "{CALL reportScadenze(?)}";

        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall(sql);
            cs.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            cs.execute();

            try(ResultSet rs = cs.getResultSet()){

                while (rs.next()) {
                    ProdottoInScadenza prodotto = new ProdottoInScadenza(
                            rs.getString("Nome"),
                            rs.getDate("DataScadenza"),
                            rs.getInt("QuantitàDisponibile")
                    );
                    prodotti.add(prodotto);
                }
            }

        } catch(SQLException e) {
            throw new DAOException("ReportScadenzeDAO error: " + e.getMessage());
        }

        return prodotti;
    }
}
