package br.net.eia.ui.contato;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.enums.TpDoc;
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.pais.Pais;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.MunicipioClientRest;
import br.net.eia.ui.PaisClientRest;
import br.net.eia.util.nfe.ConversorNFe;

@SuppressWarnings("restriction")
public class CadastroContatoController implements Initializable {

	private MainApp mainApp;
	@FXML
	private ComboBox<TpContato> cbTpContato;
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
	@FXML
	Button btSelecionar;
	private ObservableList<Contato> clientData = FXCollections
			.observableArrayList();
	@FXML
	private TableView<Contato> contatoTable;
	@FXML
	private TableColumn<Contato, TpContato> tpContatoNameColumn;
	@FXML
	private TableColumn<Contato, String> nomeNameColumn;
	@FXML
	private TableColumn<Contato, Municipio> municipioNameColumn;
	@FXML
	private TableColumn<Contato, UF> ufNameColumn;
	@FXML
	private TableColumn<Contato, String> foneNameColumn;
	@FXML
	private TableColumn<Contato, String> celularNameColumn;
	@FXML
	private TableColumn<Contato, String> emailNameColumn;
	@FXML
	TextField id;
	@FXML
	TextField nome;

	private RestContatoManager cM;

	private Stage dialogStage;
	
	ObservableList<Pais> paises = new PaisClientRest().getAll();
	
	private Contato contato;
	private boolean okClicked = false;

	public CadastroContatoController() {
		cM = new RestContatoManager();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setcbTpContato(TpContato tpContato){
		cbTpContato.setValue(tpContato);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
				
		cbTpContato.getItems().clear();
		cbTpContato.getItems().addAll(TpContato.values());
		cbTpContato.setPromptText("-- Selecione --");
		
		cbPais.getItems().clear();
		cbPais.getItems().addAll(paises);
		cbPais.setPromptText("-- Selecione um Pais --");
		cbPais.setValue(paises.get(26));
		
		cbUF.getItems().clear();
		cbUF.getItems().addAll(UF.values());
		cbUF.setPromptText("-- Selecione uma UF --");

		cbMunicipios.getItems().clear();
		cbMunicipios.setPromptText("-- Selecione um Municipio --");

		// Initialize the person table
		tpContatoNameColumn
				.setCellValueFactory(new PropertyValueFactory<Contato, TpContato>(
						"tpContato"));
		nomeNameColumn
				.setCellValueFactory(new PropertyValueFactory<Contato, String>(
						"nome"));
		municipioNameColumn
				.setCellValueFactory(new PropertyValueFactory<Contato, Municipio>(
						"Municipio"));
		foneNameColumn
				.setCellValueFactory(new PropertyValueFactory<Contato, String>(
						"fone"));
		celularNameColumn
				.setCellValueFactory(new PropertyValueFactory<Contato, String>(
						"celular"));
		emailNameColumn
				.setCellValueFactory(new PropertyValueFactory<Contato, String>(
						"email"));
		ufNameColumn.setCellValueFactory(new PropertyValueFactory<Contato, UF>(
				"Uf"));

		// Auto resize columns
		contatoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
		Contato tempPerson = new Contato();
		tempPerson.setPais(paises.get(26));
		boolean okClicked = showEditDialog(tempPerson);
		if (okClicked) {
			Contato client = cM.inserir(tempPerson);
			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText(client.getNome() + "\n" + client.getMunicipio());
            dialog.setContentText("Inserido com sucesso.");
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
			clientData.add(client);
			refreshPersonTable();
		}
	}

	public boolean showEditDialog(Contato person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/contato/EditContato.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage2 = new Stage();
			dialogStage2.setTitle("Manutenção de Contatos");
			dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage2.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage2.setScene(scene);

			// Set the person into the controller
			EditContatoController controller = loader.getController();
			controller.setDialogStage(dialogStage2);
			controller.setContato(person);
			controller.exibirContato(person);

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
		Contato selectedPerson = contatoTable.getSelectionModel()
				.getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = showEditDialog(selectedPerson);
			if (okClicked) {
				Contato client = cM.atualizar(selectedPerson);
				
				Alert dialog = new Alert(Alert.AlertType.INFORMATION);
	            dialog.setHeaderText(client.getNome() + "\n" + client.getMunicipio());
	            dialog.setContentText("Alterado Inserido com sucesso.");
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
	private void handleDeleteCliente() {
		int selectedIndex = contatoTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Contato selectedPerson = contatoTable.getSelectionModel().getSelectedItem();
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    alert.setTitle("Exclusão?");
		    alert.setHeaderText("Tem certesa que deseja remover?");
		    alert.setContentText(selectedPerson.getNome() + "\n" + selectedPerson.getMunicipio());

		    alert.getButtonTypes().clear();
		    alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		    //Deactivate Defaultbehavior for yes-Button:
		    Button yesButton = (Button) alert.getDialogPane().lookupButton( ButtonType.YES );
		    yesButton.setDefaultButton( false );

		    //Activate Defaultbehavior for no-Button:
		    Button noButton = (Button) alert.getDialogPane().lookupButton( ButtonType.NO );
		    noButton.setDefaultButton( true );
		    
		    Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.YES) {
				boolean deletado = cM.remover(selectedPerson);				
				if (deletado) {
					contatoTable.getItems().remove(selectedIndex);
					refreshPersonTable();
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
		            dialog.setHeaderText(selectedPerson.getNome() + "\n" + selectedPerson.getMunicipio());
		            dialog.setContentText("Removido com sucesso.");
		            dialog.setResizable(true);
		            dialog.getDialogPane().setPrefSize(480, 320);
		            dialog.showAndWait();
				}

			}
		}
	}

	private void refreshPersonTable() {
		int selectedIndex = contatoTable.getSelectionModel().getSelectedIndex();
		contatoTable.setItems(null);
		contatoTable.layout();
		contatoTable.setItems(clientData);
		// Must set the selected index again (see
		// http://javafx-jira.kenai.com/browse/RT-26291)
		contatoTable.getSelectionModel().select(selectedIndex);
	}

	@FXML
	private void handleFechar() {
		dialogStage.close();
	}

	@FXML
	private void handleTodos() {
		try {
			clientData = new RestContatoManager().filtrar("", MainApp.getEmitente(), "", "null",
					"null", "null");
			contatoTable.setItems(clientData);
		} catch (Exception e) {
			clientData = FXCollections.observableArrayList();
		}
	}

	@FXML
	private void handleFiltrar() {
		String tpContato = String.valueOf(cbTpContato.getValue());
		String xNome = nome.getText();
		String xPais = String.valueOf(cbPais.getValue().getxPais());
		String xUf = String.valueOf(cbUF.getValue());
		String xMunicipio = String.valueOf(cbMunicipios.getValue());
		try {
			clientData = new RestContatoManager().filtrar(tpContato, MainApp.getEmitente(), xNome, xPais,
					xUf, xMunicipio);
			refreshPersonTable();
		} catch (Exception e) {
			clientData = FXCollections.observableArrayList();
			refreshPersonTable();
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}
	@FXML
	public void handleSelecionar(){
		contato = contatoTable.getSelectionModel().getSelectedItem();
		if (contato != null) {
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
}
