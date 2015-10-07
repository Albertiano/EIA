package br.net.eia.ui.produto;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import br.net.eia.produto.Produto;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.produto.unidade.RestUnidadeManager;
import net.sf.jasperreports.engine.JRException;

@SuppressWarnings("restriction")
public class CadastroProdutoController implements Initializable {

	// Reference to the main application
	private MainApp mainApp;
	private ObservableList<Produto> produtoData = FXCollections
			.observableArrayList();
	@FXML
	private TableView<Produto> clienteTable;
	@FXML
	private TableColumn<Produto, String> codigoNameColumn;
	@FXML
	private TableColumn<Produto, String> descricaoNameColumn;
	@FXML
	private TableColumn<Produto, String> unidadeNameColumn;
	@FXML
	private TableColumn<Produto, BigDecimal> precoNameColumn;
	@FXML
	private TableColumn<Produto, BigDecimal> estoqueNameColumn;
	@FXML
	private TableColumn<Produto, String> ncmNameColumn;
	@FXML
	TextField tfCodigo;
	@FXML
	TextField tfDescricao;
	@FXML
	ComboBox<Unidade> cbUnidade;

	private RestProdutoManager cM;

	private Stage dialogStage;
	
	private Produto produto;
	private boolean okClicked = false;
	
	@FXML
	public void handleSelecionar(){
		produto = clienteTable.getSelectionModel().getSelectedItem();
		if (produto != null) {
			okClicked = true;
			dialogStage.close();
		} else {
			Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setHeaderText("Item não selecionado");
            dialog.setContentText("Selecione um item na tabela.");
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
		}
		
	}
	
	public boolean isOkClicked() {
		return okClicked ;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */

	public CadastroProdutoController() {
		cM = new RestProdutoManager();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	public void visualiarRelatorio() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		try { 
		//parametros.put("EMITENTE", emitente);
		parametros.put("SUBREPORT_DIR", getClass().getResource("/relatorios/"));
    	//parametros.put("LOGO", new Config().getCofiguracoes().getProperty("logo"));
    	JRDataSource jrds = new JRBeanCollectionDataSource(produtoData);
    	JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("/relatorios/Produto.jasper"), parametros, jrds);
    	JasperViewer.viewReport(jp, false);  
    	
    	
		} catch (JRException ex) {
                        Logger.getLogger(getClass().getName()).log(
					Level.ERROR, ex.getLocalizedMessage(), ex);
                        
                        Alert dialog = new Alert(Alert.AlertType.ERROR);
                        dialog.setHeaderText("Erro");
                        dialog.setContentText(ex.getMessage());
                        dialog.setResizable(true);
                        dialog.getDialogPane().setPrefSize(480, 320);
                        dialog.showAndWait();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList<Unidade> unidades = new RestUnidadeManager().getAll(MainApp.getEmitente());
        cbUnidade.getItems().clear();
        if(unidades!=null){
        	cbUnidade.getItems().addAll(unidades);
        }        
        cbUnidade.setPromptText("-- Selecione --");
        

		// Initialize the person table
		codigoNameColumn
				.setCellValueFactory(new PropertyValueFactory<Produto, String>(
						"codigo"));
		descricaoNameColumn
				.setCellValueFactory(new PropertyValueFactory<Produto, String>(
						"descricao"));
		unidadeNameColumn
				.setCellValueFactory(new PropertyValueFactory<Produto, String>(
						"unidade"));
		precoNameColumn
				.setCellValueFactory(new PropertyValueFactory<Produto, BigDecimal>(
						"precoVenda"));
		estoqueNameColumn
				.setCellValueFactory(new PropertyValueFactory<Produto, BigDecimal>(
						"estoque"));
		ncmNameColumn
				.setCellValueFactory(new PropertyValueFactory<Produto, String>(
						"ncm"));

		// Auto resize columns
		clienteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		estoqueNameColumn
				.setCellFactory(new Callback<TableColumn<Produto, BigDecimal>, TableCell<Produto, BigDecimal>>() {
					@Override
					public TableCell<Produto, BigDecimal> call(
							TableColumn<Produto, BigDecimal> param) {
						TableCell<Produto, BigDecimal> cell = new TableCell<Produto, BigDecimal>() {
							@Override
							public void updateItem(BigDecimal item,
									boolean empty) {
								if (item != null) {
									super.setText(ConversorBigDecimal.paraString(item));

								}
							}
						};
						return cell;
					}
				});
	}

	@FXML
	private void handleNew() {
		Produto tempPerson = new Produto();
		
		boolean okClicked = showProdutoEditDialog(tempPerson);
		if (okClicked) {
			Produto client = cM.inserir(tempPerson);
						
			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText("Inserido com sucesso.");
            dialog.setContentText(client.getDescricao() + "\n" + client.getNcm());
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
			produtoData.add(client);
			refreshPersonTable();
		}
	}

	public boolean showProdutoEditDialog(Produto person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/produto/EditProduto.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage2 = new Stage();
			dialogStage2.setTitle("Cadastro de Produto");
			dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage2.initOwner(dialogStage);
			Scene scene = new Scene(page);
			dialogStage2.setScene(scene);

			// Set the person into the controller
			EditProdutoController controller = loader.getController();
			controller.setDialogStage(dialogStage2);
			controller.setProduto(person);
			controller.setMainApp(mainApp);
			controller.exibirProduto();

			// Show the dialog and wait until the user closes it
			dialogStage2.showAndWait();

			return controller.isOkClicked();

		} catch (Exception e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			e.printStackTrace();
			Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setHeaderText("Erro");
            dialog.setContentText(e.getMessage());
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
			return false;
		}
	}

	@FXML
	private void handleEdit() {
		Produto selectedPerson = clienteTable.getSelectionModel()
				.getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = showProdutoEditDialog(selectedPerson);
			if (okClicked) {
				Produto client = cM.atualizar(selectedPerson);
								
				Alert dialog = new Alert(Alert.AlertType.INFORMATION);
	            dialog.setHeaderText("Alterado com sucesso.");
	            dialog.setContentText(client.getDescricao() + "\n" + client.getNcm());
	            dialog.setResizable(true);
	            dialog.getDialogPane().setPrefSize(480, 320);
	            dialog.showAndWait();
	            
				refreshPersonTable();
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

	@FXML
	private void handleDelete() {
		int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Produto selectedPerson = clienteTable.getSelectionModel()
					.getSelectedItem();
			
			boolean deletado = cM.remover(selectedPerson);
			
			if (deletado) {
				clienteTable.getItems().remove(selectedIndex);
				refreshPersonTable();
				
				Alert dialog = new Alert(Alert.AlertType.INFORMATION);
	            dialog.setHeaderText("Removido com sucesso.");
	            dialog.setContentText(selectedPerson.getDescricao() + "\n" + selectedPerson.getNcm());
	            dialog.setResizable(true);
	            dialog.getDialogPane().setPrefSize(480, 320);
	            dialog.showAndWait();
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

	private void refreshPersonTable() {
		int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
		clienteTable.setItems(null);
		clienteTable.layout();
		clienteTable.setItems(produtoData);
		// Must set the selected index again (see
		// http://javafx-jira.kenai.com/browse/RT-26291)
		clienteTable.getSelectionModel().select(selectedIndex);
	}

	@FXML
	private void handleFechar() {
		dialogStage.close();
	}

	@FXML
	private void handleTodos() {
		try {
			produtoData = new RestProdutoManager().filtrar(MainApp.getEmitente(), "", "", "");
			clienteTable.setItems(produtoData);
		} catch (Exception e) {
			produtoData = FXCollections.observableArrayList();
		}
	}

	@FXML
	private void handleFiltrar() {
		String xId = tfCodigo.getText();
		String xDescricao = tfDescricao.getText();
		String xUnidade="";
		if(cbUnidade.getValue()!=null){
			xUnidade=cbUnidade.getValue().getId().toString();
		}
		try {
			produtoData = new RestProdutoManager().filtrar(MainApp.getEmitente(), xId, xDescricao, xUnidade);
			refreshPersonTable();
		} catch (Exception e) {
			produtoData = FXCollections.observableArrayList();
			refreshPersonTable();
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}
	}
}
