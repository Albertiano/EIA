package br.net.eia.ui.certificado;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.commons.httpclient.protocol.Protocol;
import org.controlsfx.dialog.Dialogs;

import br.net.eia.ui.MainApp;
import br.net.eia.ui.produto.CadastroProdutoController;
import br.net.eia.util.Config;
import br.net.eia.util.SocketFactoryDinamico;
import br.net.eia.util.certificado.PegarKs;

@SuppressWarnings("restriction")
public class CarregarCertificadoController implements Initializable {
	private KeyStore ks;
	private String alias;
	private String tipoCertificado = MainApp.getProps().getProperty("tipoCert");
	private String senhaCertificado = MainApp.getProps().getProperty("senhaCert");
	
	private Stage dialogStage;
	private MainApp mainApp;
	@FXML
	private ComboBox<String> cbAlias;
	@FXML
    private TextArea taDet;
		
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        cbAlias.getItems().clear();
        listarCertificados();
			
	}
	
	@FXML
    public void show(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/certificado/CarregarCertificado.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Lista de Certificados");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(overviewPage);
            dialogStage.setScene(scene);

            // Give the controller access to the main app
            CarregarCertificadoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);

            dialogStage.showAndWait();

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(
                    Level.SEVERE, e.getLocalizedMessage(), e);
        }

    }
	
	@FXML
	public void handleOk(ActionEvent event){
        alias = cbAlias.getValue();
        PegarKs.setKS(ks, senhaCertificado, alias);
        SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(PegarKs.getX509Certificate(), PegarKs.getpKey());
        socketFactoryDinamico.setFileCacerts(Config.getCofiguracoes().getProperty("nfe-cacerts"));
        Protocol protocol = new Protocol("https", socketFactoryDinamico, 443);
        Protocol.registerProtocol("https", protocol);
        
		dialogStage.close();
	}
	@FXML
	public void handleCancela(ActionEvent event){
		dialogStage.close();
	}
	
	@FXML
	private void handleSeleciona(ActionEvent event){
		try {
			alias = cbAlias.getValue();
			X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
			StringBuilder sb = new StringBuilder();		
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			sb.append("SubjectDN...........: " + cert.getSubjectDN().toString());
			sb.append("\n\r");
			sb.append("Version.............: " + cert.getVersion());
			sb.append("\n\r");
			sb.append("SerialNumber........: " + cert.getSerialNumber());
			sb.append("\n\r");
			sb.append("SigAlgName..........: " + cert.getSigAlgName());
			sb.append("\n\r");
			sb.append("Válido a partir de..: " + dateFormat.format(cert.getNotBefore()));
			sb.append("\n\r");
			sb.append("Válido até..........: " + dateFormat.format(cert.getNotAfter()));
			taDet.setText(sb.toString());
		} catch (Exception e) {
			Logger.getLogger(CarregarCertificadoController.class.getName()).log(
					Level.SEVERE, null, e);
			e.printStackTrace();
		}
		
	}

	public void listarCertificados() {			
		switch (tipoCertificado) {
		case "WIN":
			try {
				ks = KeyStore.getInstance("Windows-MY","SunMSCAPI");
				ks.load(null, null);				
			} catch (Exception e) {
				Logger.getLogger(CarregarCertificadoController.class.getName()).log(
						Level.SEVERE, null, e);
				e.printStackTrace();
			}
			break;
		case "A3":
			try {
				String configName = MainApp.getProps().getProperty("A3Config");
				Provider p = new sun.security.pkcs11.SunPKCS11(configName);
				ks = KeyStore.getInstance("pkcs11", p);
				char[] pin = senhaCertificado.toCharArray();
				ks.load(null, pin);
			} catch (Exception ex) {
				Logger.getLogger(CarregarCertificadoController.class.getName()).log(
						Level.SEVERE, null, ex);
				ex.printStackTrace();
			}
		case "A1":
			String caminhoCertificadoCliente = MainApp.getProps().getProperty(
					"A1Local");
			try {
				ks = KeyStore.getInstance("pkcs12");
				char[] pin = senhaCertificado.toCharArray();
				ks.load(new FileInputStream(caminhoCertificadoCliente), pin);
			} catch (Exception ex) {
				Logger.getLogger(CarregarCertificadoController.class.getName()).log(
						Level.SEVERE, null, ex);
				ex.printStackTrace();
			}
			break;
		default:
			break;
		}
		try {
			Enumeration<String> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) {
				String alias = (String) aliasEnum.nextElement();
				if (ks.isKeyEntry(alias)) {					
					cbAlias.getItems().add(alias);					
					cbAlias.setValue(alias);
					handleSeleciona(null);
				}
			}
		} catch (Exception e) {
			Logger.getLogger(CarregarCertificadoController.class.getName()).log(
				Level.SEVERE, null, e);
			e.printStackTrace();
		}
	}
}
