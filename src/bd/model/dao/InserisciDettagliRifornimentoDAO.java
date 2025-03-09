package bd.model.dao;
import bd.model.domain.Fornisce;
import bd.exception.DAOException;
import java.sql.*;

public class InserisciDettagliRifornimentoDAO implements  ProceduraGenericaDAO<Fornisce>{

    @Override
    public Fornisce execute(Object ... params) throws DAOException {

        Connection conn = (Connection) params[0];
        int idRifornimento = (int) params[1];
        int idProdotto = (int) params[2];
        int quantitaRifornita = (int) params[3];

        java.util.Date dataScadenza = (java.util.Date) params[4];
        java.sql.Date sqlDataScadenza = new java.sql.Date(dataScadenza.getTime());

        try {

            CallableStatement cs = conn.prepareCall("{CALL inserisciDettaglioRifornimento(?, ?, ?, ?)}");

            cs.setInt(1, idRifornimento);
            cs.setInt(2, idProdotto);
            cs.setInt(3, quantitaRifornita);
            cs.setDate(4, sqlDataScadenza);
            cs.execute();

        } catch (SQLException e) {
            throw new DAOException("InserisciDettagliRifornimentiDAO error: " + e.getMessage());
        }

        Fornisce fornisce = new Fornisce(idRifornimento, idProdotto, quantitaRifornita, dataScadenza);
        return fornisce;
    }
}
