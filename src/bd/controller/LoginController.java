package bd.controller;
import bd.exception.DAOException;
import bd.model.dao.LoginProcedureDAO;
import bd.model.domain.Credentials;
import bd.view.LoginView;
import java.io.IOException;
public class LoginController implements Controller{
    Credentials cred = null;

    @Override
    public void start() {
        try {
            cred = LoginView.authenticate();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        try {
            cred = new LoginProcedureDAO().execute(cred.getUsername(), cred.getPassword());
        } catch(DAOException e) {
            throw new RuntimeException(e);
        }
    }

    public Credentials getCred() {
        return cred;
    }
}
