package br.net.eia.ui.contato;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.ui.MainApp;

@SuppressWarnings("restriction")
public class ContatoManager {
	
	private Contato contato;

	public boolean showPesquisaContatoDialog(MainApp mainApp, TpContato tpContato, Stage dialogStageMain) {
    	try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/contato/CadastroContato.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Contatos");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.initOwner(dialogStageMain);
            Scene scene = new Scene(overviewPage);
            dialogStage.setScene(scene);

            // Give the controller access to the main app
            CadastroContatoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);  
            controller.setcbTpContato(tpContato);
            dialogStage.showAndWait();
            setContato(controller.getContato());
            return controller.isOkClicked();

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(
                    Level.ERROR, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    	return false;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}
}
