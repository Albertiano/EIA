package br.net.eia.ui.emitente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import br.net.eia.emitente.Emitente;
import br.net.eia.enums.TpDoc;
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.pais.Pais;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.MunicipioClientRest;
import br.net.eia.ui.PaisClientRest;

@SuppressWarnings("restriction")
public class CadastroEmitenteController implements Initializable {

	// Reference to the main application
	private MainApp mainApp;
	@FXML
	private ComboBox<TpDoc> cbTpDoc;
	@FXML
	private ComboBox<Pais> cbPais;
	@FXML
	private ComboBox<UF> cbUF;
	@FXML
	private ComboBox<Municipio> cbMunicipios;
	@FXML
	Button btNovo;
	private ObservableList<Emitente> clientData = FXCollections
			.observableArrayList();
	@FXML
	private TableView<Emitente> clienteTable;
	@FXML
	private TableColumn<Emitente, Long> idNameColumn;
	@FXML
	private TableColumn<Emitente, String> nomeNameColumn;
	@FXML
	private TableColumn<Emitente, Municipio> municipioNameColumn;
	@FXML
	private TableColumn<Emitente, UF> ufNameColumn;
	@FXML
	private TableColumn<Emitente, String> foneNameColumn;
	@FXML
	private TableColumn<Emitente, String> celularNameColumn;
	@FXML
	private TableColumn<Emitente, String> emailNameColumn;
	@FXML
	TextField id;
	@FXML
	TextField nome;

	private RestEmitenteManager cM;

	private Stage dialogStage;
	
	ObservableList<Pais> paises = new PaisClientRest().getAll();

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */

	public CadastroEmitenteController() {
		cM = new RestEmitenteManager();		
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		cbPais.getItems().clear();
		cbPais.getItems().addAll(paises);
		cbPais.setPromptText("-- Selecione um Pais --");
		if (cbPais.getValue() == null) {
			cbPais.setValue(paises.get(26));
		}
		cbUF.getItems().clear();
		cbUF.getItems().addAll(UF.values());
		cbUF.setPromptText("-- Selecione uma UF --");

		cbMunicipios.getItems().clear();
		cbMunicipios.setPromptText("-- Selecione um Municipio --");

		// Initialize the person table
		idNameColumn
				.setCellValueFactory(new PropertyValueFactory<Emitente, Long>(
						"id"));
		nomeNameColumn
				.setCellValueFactory(new PropertyValueFactory<Emitente, String>(
						"nome"));
		municipioNameColumn
				.setCellValueFactory(new PropertyValueFactory<Emitente, Municipio>(
						"Municipio"));
		foneNameColumn
				.setCellValueFactory(new PropertyValueFactory<Emitente, String>(
						"fone"));
		celularNameColumn
				.setCellValueFactory(new PropertyValueFactory<Emitente, String>(
						"celular"));
		emailNameColumn
				.setCellValueFactory(new PropertyValueFactory<Emitente, String>(
						"email"));
		ufNameColumn.setCellValueFactory(new PropertyValueFactory<Emitente, UF>(
				"Uf"));

		// Auto resize columns
		clienteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		/**
		 * // Acao para quando trocar a selecao
		 * clienteTable.getSelectionModel().
		 * selectedItemProperty().addListener(new ChangeListener<Person>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Cliente>
		 *           observable, Cliente oldValue, Cliente newValue) {
		 *           showPersonDetails(newValue); } });
		 ***/
	}

	@FXML
	public void handleCbUF(ActionEvent event) {
		ObservableList<Municipio> municipios = new MunicipioClientRest()
				.getAllUF(cbUF.getValue().toString());
		cbMunicipios.getItems().clear();
		cbMunicipios.getItems().addAll(municipios);
	}

	@FXML
	private void handleNewCliente() {
		Emitente tempPerson = new Emitente();
		tempPerson.setPais(paises.get(26));
		tempPerson.setUf(UF.PB);
		boolean okClicked = showEmitenteEditDialog(tempPerson);
		if (okClicked) {
			Emitente client = cM.inserirCliente(tempPerson);
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.masthead(client.getNome() + "\n" + client.getMunicipio())
			.message("Cliente Inserido com sucesso.")
			.showInformation();
			clientData.add(client);
			refreshPersonTable();
		}
	}

	public boolean showEmitenteEditDialog(Emitente person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("emitente/EditEmitente.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage2 = new Stage();
			dialogStage2.setTitle("Dados da Empresa");
			dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage2.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage2.setScene(scene);

			// Set the person into the controller
			EditEmitenteController controller = loader.getController();
			controller.setDialogStage(dialogStage2);
			controller.setEmitente(person);
			controller.exibirEmitente(person);

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
	private void handleEditCliente() {
		Emitente selectedPerson = clienteTable.getSelectionModel()
				.getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = showEmitenteEditDialog(selectedPerson);
			if (okClicked) {
				Emitente client = cM.atualizarCliente(selectedPerson);
				Dialogs.create()
				.owner(dialogStage)
				.title("Aviso")
				.masthead(client.getNome() + "\n" + client.getMunicipio())
				.message("Cliente Alterado com sucesso.")
				.showInformation();

				refreshPersonTable();
			}

		} else {
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.masthead("Item não selecionado")
			.message("Selecione um item na tabela.")
			.showInformation();
		}
	}

	@FXML
	private void handleDeleteCliente() {
		int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Emitente selectedPerson = clienteTable.getSelectionModel()
					.getSelectedItem();
			boolean deletado = cM.removerEmitente(selectedPerson);
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.masthead(selectedPerson.getNome() + "\n" + selectedPerson.getMunicipio())
			.message("Cliente Inserido com sucesso.")
			.showInformation();
			if (deletado) {
				clienteTable.getItems().remove(selectedIndex);
				refreshPersonTable();
			}
		} else {
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.masthead("Item não selecionado")
			.message("Selecione um item na tabela.")
			.showInformation();
		}
	}

	private void refreshPersonTable() {
		int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
		clienteTable.setItems(null);
		clienteTable.layout();
		clienteTable.setItems(clientData);
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
			clientData = new RestEmitenteManager().getAll();
			clienteTable.setItems(clientData);
		} catch (Exception e) {
			clientData = FXCollections.observableArrayList();
		}
	}
}
