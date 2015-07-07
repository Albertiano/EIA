/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package br.net.eia.ui;

import java.io.InputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import br.net.eia.emitente.Emitente;
import br.net.eia.logger.GeradorLog;
import br.net.eia.login.Authenticator;
import br.net.eia.login.User;
import br.net.eia.ui.RootLayoutController;
import br.net.eia.ui.certificado.CarregarCertificadoController;
import br.net.eia.ui.emitente.RestEmitenteManager;
import br.net.eia.ui.login.LoginController;
import br.net.eia.util.Config;

import java.io.IOException;
import java.util.Properties;

/**
 * Main Application. This class handles navigation and user session.
 */
@SuppressWarnings("restriction")
public class MainApp extends Application {

    private Stage stage;
    private User loggedUser;
    private static Emitente emitente;
    private static Properties props;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
    	GeradorLog.inicialize();
        Application.launch(MainApp.class, (java.lang.String[])null);
    }
    
    public void iniciar(){
        GeradorLog.inicialize();
        Application.launch(MainApp.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.stage = primaryStage;
            primaryStage.setTitle("FXML Login Sample");
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
        	Logger.getLogger(RestEmitenteManager.class.getName()).log(
					Level.ERROR, ex.getLocalizedMessage(), ex);
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }
        
    public boolean userLogging(Integer token, String userId, String password){
        if (Authenticator.validate(userId, password)) {
            loggedUser = User.of(userId);
            RestEmitenteManager rest = new RestEmitenteManager();
            setEmitente(rest.carregar(token));
            gotoProfile();
            return true;
        } else {
            return false;
        }
    }
    
    public void userLogout(){
        loggedUser = null;
        gotoLogin();
    }
    
    
    private void gotoProfile() {
        try {
        	String fxml = "/fxml/RootLayout.fxml";
        	FXMLLoader loader = new FXMLLoader();
            InputStream in = MainApp.class.getResourceAsStream(fxml);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(MainApp.class.getResource(fxml));
            BorderPane page;
            try {
                page = (BorderPane) loader.load(in);
            } finally {
                in.close();
            } 
            stage.setScene(getSceneProperties(page));
            //stage.sizeToScene();
            stage.centerOnScreen();
            
        	RootLayoutController profile = (RootLayoutController) loader.getController();
            profile.setApp(this);
        } catch (IOException ex) {
        	Logger.getLogger(RestEmitenteManager.class.getName()).log(
					Level.ERROR, ex.getLocalizedMessage(), ex);
                ex.printStackTrace();
        	Dialogs.create()
            .title("Aviso")
            .message("Erro").showException(ex);
        }
    }

    private void gotoLogin() {
        try {
            LoginController page = new LoginController();
            page.setApp(this);
            Scene scene = new Scene(page);
            stage.setTitle("EIA");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (Exception ex) {
        	Logger.getLogger(RestEmitenteManager.class.getName()).log(
					Level.ERROR, ex.getLocalizedMessage(), ex);
        }
    }
    
    
    private Scene getSceneProperties(BorderPane rootLayout)
    {
        //The percentage values are used as multipliers for screen width/height.
        double percentageWidth = 0.99;
        double percentageHeight = 0.90;
        
        //Calculate the width / height of screen.
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        percentageWidth *= screenSize.getWidth();
        percentageHeight *= screenSize.getHeight();
                   
        //Create a scene object. Pass in the layout and set
        //the dimensions to 98% of screen width & 90% screen height.
        Scene scene = new Scene(rootLayout, percentageWidth, percentageHeight);
        
        //Add CSS Style Sheet (located in same package as this class).
        //String AppCss = this.getClass().getResource("/styles/App.css").toExternalForm();
        
        //scene.getStylesheets().add(AppCss);

        return scene;
    }
    
    public static Emitente getEmitente(){
		return emitente;
	}
	
	public void setEmitente(Emitente emitente){
		MainApp.emitente = emitente;
	}
	
	public static Properties getProps(){
		if(props==null){
			props = Config.getCofiguracoes();
		}
		return props;
	}

	public Stage getPrimaryStage() {
		return stage;
	}

}
