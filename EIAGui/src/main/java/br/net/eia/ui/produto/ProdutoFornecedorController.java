package br.net.eia.ui.produto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.produto.FornecedorProduto;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.contato.ContatoManager;
import br.net.eia.ui.produto.unidade.RestUnidadeManager;

@SuppressWarnings("restriction")
public class ProdutoFornecedorController implements Initializable {

	@FXML
	private TextField tfFornecedor;
	@FXML
	private TextField tfCodFornecedor;
	@FXML
	private ComboBox<Unidade> cbUnidFornecedor;
	@FXML
	private TextField tfFatorConversao;
	
	private Stage dialogStage;
	private FornecedorProduto produtoFornecedor = new FornecedorProduto();
	private Contato fornecedor;

	private boolean okClicked = false;

	// Reference to the main application
	private MainApp mainApp;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setProduto(FornecedorProduto produto) {
		this.produtoFornecedor = produto;
		if(produto!=null){
			fornecedor = produto.getFornecedor();
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Unidade> paises = new RestUnidadeManager().getAll(mainApp.getEmitente());
        cbUnidFornecedor.getItems().clear();
        cbUnidFornecedor.getItems().addAll(paises);
        cbUnidFornecedor.setPromptText("-- Selecione --");
			
	}
	
	
	private boolean isInputValid() {
		String errorMessage = "";

		if (tfFornecedor.getText() == null || tfFornecedor.getText().length() == 0) {
			errorMessage += "Fornecedor inválida!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setHeaderText("Por Favor Corrija os Campos Inválidos");
            dialog.setContentText(errorMessage);
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
            return false;
		}
	}
	
    
	@FXML
	public void handleBtSalvar(ActionEvent event) {
		if (isInputValid()) {
			produtoFornecedor.setFornecedor(fornecedor);     
	        produtoFornecedor.setCodFornecedor(tfCodFornecedor.getText());
	        produtoFornecedor.setUnidFornecedor(cbUnidFornecedor.getValue());
	        produtoFornecedor.setFatorConversao(ConversorBigDecimal.paraBigDecimal(tfFatorConversao.getText()));
	        
			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}
	
	public void exibir(){
		if(produtoFornecedor.getFornecedor()!=null){
			tfFornecedor.setText(produtoFornecedor.getFornecedor().getNome());
		}		     
        tfCodFornecedor.setText(produtoFornecedor.getCodFornecedor());
        cbUnidFornecedor.setValue(produtoFornecedor.getUnidFornecedor());
        tfFatorConversao.setText(ConversorBigDecimal.paraString(produtoFornecedor.getFatorConversao()));
	}
	
	@FXML
	private void handlePesquisa() {
		ContatoManager cM = new ContatoManager(); 
		boolean okClicked = cM.showPesquisaContatoDialog(mainApp, TpContato.FORNECEDOR, dialogStage);
		fornecedor = cM.getContato();
		if (fornecedor != null) {			
			if (okClicked) {
				tfFornecedor.setText(fornecedor.getNome());
			}

		} else {
			Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setHeaderText("Item não selecionado");
            dialog.setContentText("Selecione um item na tabela.");
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
		}
	}

}
