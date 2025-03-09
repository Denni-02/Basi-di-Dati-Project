package bd.model.dao;
import bd.model.domain.Contiene;
import bd.exception.DAOException;
import java.sql.*;
import java.util.Date;

public class InserisciDettagliOrdineDAO implements  ProceduraGenericaDAO<Contiene>{

    @Override
    public Contiene execute(Object ... params) throws DAOException {

        Connection conn = (Connection) params[0];
        int idOrdine = (int) params[1];
        int idProdotto = (int) params[2];
        int quantitaOrdinata = (int) params[3];
        Date dataScadenza = (Date) params[4];
        java.sql.Date sqlDataScadenza = new java.sql.Date(dataScadenza.getTime());

        try {
            CallableStatement cs = conn.prepareCall("{CALL InserisciDettaglioOrdine(?, ?, ?, ?)}");

            cs.setInt(1, idOrdine);
            cs.setInt(2, idProdotto);
            cs.setInt(3, quantitaOrdinata);
            cs.setDate(4, sqlDataScadenza);
            cs.execute();

        } catch (SQLException e) {
            throw new DAOException("InserisciDettaglioOrdineDAO error: " + e.getMessage());
        }

        Contiene contiene = new Contiene(idOrdine, idProdotto, quantitaOrdinata, dataScadenza);
        return contiene;
    }
}
