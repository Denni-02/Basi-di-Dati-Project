package bd.controller;
import bd.model.domain.Credentials;
public class ApplicationController implements Controller{

    Credentials cred;

    @Override
    public void start() {
        LoginController loginController = new LoginController();
        loginController.start();
        cred = loginController.getCred();

        if(cred.getRole() == null) {
            throw new RuntimeException("Invalid credentials");
        }

        switch(cred.getRole()) {
            case IMPIEGATO -> new ImpiegatoController().start();
            case MAGAZZINIERE -> new MagazziniereController().start();
            default -> throw new RuntimeException("Invalid credentials");
        }
    }
}
