package br.net.eia.ui.produto.unidade;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.ui.MainApp;

public class EditUnidadeController implements Initializable {

	@FXML
	private TextField tfSigla;
	@FXML
	private TextField tfDescricao;

	private Stage dialogStage;
	private Unidade produto = new Unidade();

	private boolean okClicked = false;

	// Reference to the main application
	@SuppressWarnings("unused")
	private MainApp mainApp;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setProduto(Unidade produto) {
		this.produto = produto;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
			
	}
	
	
	private boolean isInputValid() {
		String errorMessage = "";

		if (tfSigla.getText() == null || tfDescricao.getText().length() == 0) {
			errorMessage += "Sigla inválida!\n";
		}
		if (tfDescricao.getText() == null || tfDescricao.getText().length() == 0) {
			errorMessage += "Descrição inválida!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Dialogs.create()
			.owner(mainApp.getPrimaryStage())
			.title("Aviso")
			.masthead(errorMessage)
			.message("Por Favor Corrija os Campos Inválidos")
			.showInformation();
			return false;
		}
	}
	
    
	@FXML
	public void handleBtSalvar(ActionEvent event) {
		if (isInputValid()) {
			produto.setSigla(tfSigla.getText());       
	        produto.setDescricao(tfDescricao.getText());
	        
	        produto.setEmitente(mainApp.getEmitente());

			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}
	
	public void exibir(){
		tfSigla.setText(produto.getSigla());
        tfDescricao.setText(produto.getDescricao());
	}

}
