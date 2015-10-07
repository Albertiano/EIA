package br.net.eia.ui.produto.imposto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import br.net.eia.enums.UF;
import br.net.eia.produto.FornecedorProduto;
import br.net.eia.produto.imposto.Destino;
import br.net.eia.produto.imposto.Tributacao;
import br.net.eia.produto.imposto.Tributo;
import br.net.eia.produto.imposto.icms.CST_ICMS;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.produto.ProdutoFornecedorController;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;
import javafx.beans.property.SimpleStringProperty;

public class EditTributacaoController implements Initializable {

	private Stage dialogStage;

	private boolean okClicked = false;

	// Reference to the main application
	@SuppressWarnings("unused")
	private MainApp mainApp;
	
	private ObservableList<Destino> produtoData = FXCollections
            .observableArrayList();
    @FXML
    private TableView<Destino> clienteTable;
    @FXML
    private TableColumn<Destino, String> destinoNameColumn;
    @FXML
    private TableColumn<Destino, String> stNameColumn;
    @FXML
    private TableColumn<Destino, String> icmsNameColumn;
    @FXML
    private TableColumn<Destino, String> ipiNameColumn;
    @FXML
    private TableColumn<Destino, String> pisNameColumn;
    @FXML
    private TableColumn<Destino, String> cofinsNameColumn;
    @FXML
    TextField tfNome;
    @FXML
    TextField tfDescricao;
    
    Tributacao trib;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize the person table
		destinoNameColumn
				.setCellValueFactory(new PropertyValueFactory<Destino, String>(
						"estado"));
		stNameColumn
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Destino, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Destino, String> c) {
						return new SimpleStringProperty(c.getValue()
								.getTributos().getIcms().getCstICMS()
								.toString());
					}
				});
		icmsNameColumn
		.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Destino, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Destino, String> c) {
				return new SimpleStringProperty(ConversorBigDecimal.paraString(c.getValue()
						.getTributos().getIcms().getpICMS(),2));
			}
		});
		ipiNameColumn
		.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Destino, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Destino, String> c) {
				return new SimpleStringProperty(ConversorBigDecimal.paraString(c.getValue()
						.getTributos().getIpi().getvBCIPI(),2));
			}
		});
		pisNameColumn
		.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Destino, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Destino, String> c) {
				return new SimpleStringProperty(ConversorBigDecimal.paraString(c.getValue()
						.getTributos().getPis().getpPIS(),2));
			}
		});
		cofinsNameColumn
		.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Destino, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Destino, String> c) {
				return new SimpleStringProperty(ConversorBigDecimal.paraString(c.getValue()
						.getTributos().getCofins().getpCOFINS(),2));
			}
		});

		// Auto resize columns
        clienteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
	}
	
	
	private boolean isInputValid() {
		String errorMessage = "";

		if (tfNome.getText() == null || tfNome.getText().length() == 0) {
			errorMessage += "Nome inválido!\n";
		}
		if (tfDescricao.getText() == null || tfDescricao.getText().length() == 0) {
			errorMessage += "Descrição inválida!\n";
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
			trib.setNome(tfNome.getText());       
			trib.setDescricao(tfDescricao.getText());
			
			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}
	
	public void exibir(){
		tfNome.setText(trib.getNome());
        tfDescricao.setText(trib.getDescricao());
        refreshPersonTable();
	}
	
	@FXML
	private void handleNew() {
		Destino tempPerson = new Destino();
		tempPerson.setEstado(UF.PB);
		boolean okClicked2 = showEditDialog(tempPerson);
		if (okClicked2) {
			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText("Inserido com sucesso.");
            dialog.setContentText(tempPerson.getEstado().getNomeUF());
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
			if(trib.getDestino()==null){
				trib.setDestino(new ArrayList<Destino>());
			}
			trib.getDestino().add(tempPerson);
			refreshPersonTable();
		}
	}
	
	public boolean showEditDialog(Destino person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/produto/imposto/EditDestino.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage2 = new Stage();
			dialogStage2.setTitle("Cadastro de Destino");
			dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage2.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage2.setScene(scene);

			// Set the person into the controller
			EditDestinoController controller = loader.getController();
			controller.setDialogStage(dialogStage2);
			controller.setMainApp(mainApp);
			controller.setDestino(person);
			controller.exibir();

			// Show the dialog and wait until the user closes it
			dialogStage2.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return false;
		}
	}

	@FXML
	private void handleEdit() {
		int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
		Destino tempPerson = trib.getDestino().get(selectedIndex);
		if (tempPerson != null) {
			boolean okClicked = showEditDialog(tempPerson);
			if (okClicked) {
				
				Alert dialog = new Alert(Alert.AlertType.INFORMATION);
	            dialog.setHeaderText("Alterado com sucesso.");
	            dialog.setContentText(tempPerson.getEstado().getNomeUF());
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
			Destino tempPerson = clienteTable.getSelectionModel()
					.getSelectedItem();
			
			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText("Removido com sucesso.");
            dialog.setContentText(tempPerson.getEstado().getNomeUF());
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
			clienteTable.getItems().remove(selectedIndex);
			trib.getDestino().remove(selectedIndex);
			refreshPersonTable();

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
		produtoData = FXCollections.observableArrayList(trib.getDestino());
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

	public void setTributacao(Tributacao person) {
		trib = person;		
	}

}
