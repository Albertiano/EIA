package br.net.eia.ui.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.net.eia.ui.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Login Controller.
 */
public class LoginController extends AnchorPane implements Initializable {

    @FXML
    TextField tfUserId;
    @FXML
    PasswordField pfPassword;
    @FXML
    TextField tftoken;
    @FXML
    Button btLogin;
    @FXML
    Label errorMessage;

    private MainApp application;
    
    public LoginController(){
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login/Login.fxml"));
    	fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public void setApp(MainApp application){
        this.application = application;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        
    }
    
    
    public void processLogin(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            errorMessage.setText("Olá " + tfUserId.getText());
        } else {
            if (!application.userLogging(Integer.parseInt(tftoken.getText()), tfUserId.getText(), pfPassword.getText())){
                errorMessage.setText("Usuário/Senha Incorreto(a)");
            }
        }
    }
}
