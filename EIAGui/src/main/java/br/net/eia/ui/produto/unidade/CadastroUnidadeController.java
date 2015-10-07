package br.net.eia.ui.produto.unidade;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.ui.MainApp;

public class CadastroUnidadeController implements Initializable {

	// Reference to the main application
	private MainApp mainApp;
	private ObservableList<Unidade> produtoData = FXCollections
			.observableArrayList();
	@FXML
	private TableView<Unidade> clienteTable;
	@FXML
	private TableColumn<Unidade, String> siglaNameColumn;
	@FXML
	private TableColumn<Unidade, String> descricaoNameColumn;

	private RestUnidadeManager cM;

	private Stage dialogStage;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */

	public CadastroUnidadeController() {
		cM = new RestUnidadeManager();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			produtoData = new RestUnidadeManager().getAll(MainApp.getEmitente());
			clienteTable.setItems(produtoData);
		} catch (Exception e) {
			produtoData = FXCollections.observableArrayList();
		}
		
		// Initialize the person table
		siglaNameColumn
				.setCellValueFactory(new PropertyValueFactory<Unidade, String>(
						"sigla"));
		descricaoNameColumn
				.setCellValueFactory(new PropertyValueFactory<Unidade, String>(
						"descricao"));

		// Auto resize columns
		clienteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
				
	}

	@FXML
	private void handleNew() {
		Unidade tempPerson = new Unidade();
		boolean okClicked = showEditDialog(tempPerson);
		if (okClicked) {
			Unidade client = cM.inserir(tempPerson);	
			
			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText("Inserido com sucesso.");
            dialog.setContentText(client.getDescricao() + "\n" + client.getSigla());
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
			produtoData.add(client);
			refreshPersonTable();
		}
	}

	public boolean showEditDialog(Unidade person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/produto/unidade/EditUnidade.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage2 = new Stage();
			dialogStage2.setTitle("Cadastro de Unidades");
			dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage2.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage2.setScene(scene);

			// Set the person into the controller
			EditUnidadeController controller = loader.getController();
			controller.setDialogStage(dialogStage2);
			controller.setMainApp(mainApp);
			controller.setProduto(person);
			controller.exibir();

			// Show the dialog and wait until the user closes it
			dialogStage2.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return false;
		}
	}

	@FXML
	private void handleEdit() {
		Unidade selectedPerson = clienteTable.getSelectionModel()
				.getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = showEditDialog(selectedPerson);
			if (okClicked) {
				Unidade client = cM.atualizar(selectedPerson);
				
				Alert dialog = new Alert(Alert.AlertType.INFORMATION);
	            dialog.setHeaderText("Alterado com sucesso.");
	            dialog.setContentText(client.getDescricao() + "\n" + client.getSigla());
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
			Unidade selectedPerson = clienteTable.getSelectionModel()
					.getSelectedItem();
			boolean deletado = cM.remover(selectedPerson);
			
			if (deletado) {
				clienteTable.getItems().remove(selectedIndex);
				refreshPersonTable();
				
				Alert dialog = new Alert(Alert.AlertType.INFORMATION);
	            dialog.setHeaderText("Removido com sucesso.");
	            dialog.setContentText(selectedPerson.getDescricao() + "\n" + selectedPerson.getSigla());
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
			produtoData = new RestUnidadeManager().getAll(MainApp.getEmitente());
			clienteTable.setItems(produtoData);
		} catch (Exception e) {
			produtoData = FXCollections.observableArrayList();
		}
	}

}
