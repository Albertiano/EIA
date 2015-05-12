package br.net.eia.ui.compra;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.produto.FornecedorProduto;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.contato.ContatoManager;
import br.net.eia.ui.produto.unidade.RestUnidadeManager;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;

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
		ObservableList<Unidade> paises = new RestUnidadeManager().getAll(MainApp.getEmitente());
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
			Dialogs.create()
			.owner(mainApp.getPrimaryStage())
			.title("Aviso")
			.masthead(errorMessage)
			.message("Por Favor Corrija os Campos Inválidos")
			.showError();
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
		if (fornecedor != null) {			
			if (okClicked) {
				tfFornecedor.setText(fornecedor.getNome());
			}

		} else {
			Dialogs.create()
			.owner(mainApp.getPrimaryStage())
			.title("Aviso")
			.masthead("Sem registro selecionado")
			.message("Por Favor Selecione na tabela")
			.showError();
		}
	}

}
