package br.net.eia.ui.contato;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.contato.campoextra.CampoExtra;
import br.net.eia.enums.TpDoc;
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.notafiscal.adicionais.IndIEDest;
import br.net.eia.pais.Pais;
import br.net.eia.util.Formatador;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.MunicipioClientRest;
import br.net.eia.ui.PaisClientRest;

public class EditContatoController implements Initializable {

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
	private TextField tfCpfCnpj;
	@FXML
	private CheckBox ckIsento;
	@FXML
	private TextField tfIE;
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfEndereco;
	@FXML
	private TextField tfNro;
	@FXML
	private TextField tfCpl;
	@FXML
	private TextField tfBairro;
	@FXML
	private TextField tfCep;
	@FXML
	private TextField tffone;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextArea taObs;

	@FXML
	Button btSalvar;
	@FXML
	Button btCancelar;
	@FXML
	private TextField tfFantasia;
	@FXML
	private TextField tfContato;
	@FXML
	private TextField tfCelular;
	@FXML
	private TextField tfFoneRes;
	@FXML
	private TextField tfInscSuframa;
	@FXML
	private CheckBox ckBloqueado;
	@FXML
	private CheckBox ckDesabilitado;
	@FXML
	private CheckBox ckFonte;
        

	@FXML
	private TextField tfCExtraNome;
	@FXML
	private TextField tfCExtraValue;

	private Stage dialogStage;
	private Contato contato;

	private ObservableList<CampoExtra> camposExtras = FXCollections
			.observableArrayList();

	@FXML
	private TableView<CampoExtra> campoExtraTable;
	@FXML
	private TableColumn<CampoExtra, String> nomeNameColumn;
	@FXML
	private TableColumn<CampoExtra, String> valueNameColumn;

	private boolean okClicked = false;

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setContato(Contato cliente) {
		this.contato = cliente;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		nomeNameColumn
				.setCellValueFactory(new PropertyValueFactory<CampoExtra, String>(
						"name"));
		valueNameColumn
				.setCellValueFactory(new PropertyValueFactory<CampoExtra, String>(
						"value"));

		// Auto resize columns
		campoExtraTable
				.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tfCelular.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataFone(tfCelular);
				}
			}
		});

		tfFoneRes.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataFone(tfFoneRes);
				}
			}
		});

		cbTpContato.getItems().clear();
		cbTpContato.getItems().addAll(TpContato.values());
		cbTpContato.setPromptText("Selecione");

		cbTpDoc.getItems().clear();
		cbTpDoc.getItems().addAll(TpDoc.values());
		cbTpDoc.setPromptText("-- Selecione --");

		ObservableList<Pais> paises = new PaisClientRest().getAll();
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

		tfCpfCnpj.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCNPJCPF();
				}
			}
		});
		tfCep.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCEP();
				}
			}
		});
		tffone.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataFone();
				}
			}
		});
	}

	@FXML
	public void handleCbUF(ActionEvent event) {
		ObservableList<Municipio> municipios = new MunicipioClientRest()
				.getAllUF(cbUF.getValue().toString());
		cbMunicipios.getItems().clear();
		cbMunicipios.getItems().addAll(municipios);
	}

	@FXML
	public void handleckIsento(ActionEvent event) {
		if (ckIsento.isSelected()) {
			tfIE.setText("ISENTO");
			tfIE.setEditable(false);
		} else {
			tfIE.setText("");
			tfIE.setEditable(true);
		}
	}

	private void formataCNPJCPF() {
		try {
			Formatador formatter = new Formatador();
			String formatado = formatter.formatterCNPJCPF(tfCpfCnpj.getText());
			tfCpfCnpj.setText(formatado);
			if (formatado.length() == 14) {
				cbTpDoc.setValue(TpDoc.CPF);
			} else if (formatado.length() == 18) {
				cbTpDoc.setValue(TpDoc.CNPJ);
			} else {
				cbTpDoc.setValue(null);
			}
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}
	}

	private void formataCEP() {
		try {
			Formatador formatter = new Formatador();
			String formatado = formatter.formatterCEP(tfCep.getText());
			tfCep.setText(formatado);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}
	}

	private void formataFone() {
		try {
			Formatador formatter = new Formatador();
			String formatado = formatter.formatterFone(tffone.getText());
			tffone.setText(formatado);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}
	}

	@FXML
	public void handleBtSalvar(ActionEvent event) {
		if (isInputValid()) {
			contato.setTpContato(cbTpContato.getValue());
			contato.setTpDoc(cbTpDoc.getValue());
			contato.setNumDoc(tfCpfCnpj.getText());
			contato.setIE(tfIE.getText());
			contato.setNome(tfNome.getText());
			contato.setLogradouro(tfEndereco.getText());
			contato.setNumero(tfNro.getText());
			contato.setComplemento(tfCpl.getText());
			contato.setBairro(tfBairro.getText());
			contato.setCep(tfCep.getText());
			contato.setPais(cbPais.getValue());
			contato.setUf(cbUF.getValue());
			contato.setMunicipio(cbMunicipios.getValue());
			contato.setFone(tffone.getText());
			contato.setObs(taObs.getText());
			contato.setEmail(tfEmail.getText());
			contato.setContato(tfContato.getText());
			contato.setFantasia(tfFantasia.getText());
			contato.setCelular(tfCelular.getText());
			contato.setFoneRes(tfFoneRes.getText());
			contato.setBloqueado(ckBloqueado.isSelected());
			contato.setDesabilitado(ckDesabilitado.isSelected());
			contato.setContatoFonte(ckFonte.isSelected());
			contato.setISUF(tfInscSuframa.getText());
                        if(ckIsento.isSelected()){
                            contato.setIndIEDest(IndIEDest.ISENTO);
                        }else if(tfIE.getText()==null){
                            contato.setIndIEDest(IndIEDest.NAO_CONTRIBUINTE);
                        }else{
                            contato.setIndIEDest(IndIEDest.CONTRIBUINTE);
                        }
			contato.setEmitente(MainApp.getEmitente());
			contato.setCamposExtras(camposExtras);

			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}

	private void formataFone(TextField fone) {
		Formatador formatter = new Formatador();
		String formatado = formatter.formatterFone(fone.getText());
		fone.setText(formatado);
	}

	public void exibirContato(Contato c) {
		cbTpContato.setValue(c.getTpContato());
		cbTpDoc.setValue(c.getTpDoc());
		formataCNPJCPF();
		tfCpfCnpj.setText(c.getNumDoc());
		tfIE.setText(c.getIE());
		tfNome.setText(c.getNome());
		tfEndereco.setText(c.getLogradouro());
		tfNro.setText(c.getNumero());
		tfCpl.setText(c.getComplemento());
		tfBairro.setText(c.getBairro());
		tfCep.setText(c.getCep());
		cbPais.setValue(c.getPais());
		ObservableList<Pais> paises = new PaisClientRest().getAll();
		if (cbPais.getValue() == null) {
			cbPais.setValue(paises.get(26));
		}
		cbUF.setValue(c.getUf());
		if(cbUF.getValue()==null){
			cbUF.setValue(UF.PB);
		}
		handleCbUF(null);
		cbMunicipios.setValue(c.getMunicipio());
		tffone.setText(c.getFone());
		taObs.setText(c.getObs());
		tfEmail.setText(c.getEmail());
		tfContato.setText(c.getContato());
		tfFantasia.setText(c.getFantasia());
		tfCelular.setText(c.getCelular());
		tfFoneRes.setText(c.getFoneRes());
		ckBloqueado.setSelected(c.isBloqueado());
		ckDesabilitado.setSelected(c.isDesabilitado());
		ckFonte.setSelected(c.isContatoFonte());
		tfInscSuframa.setText(c.getISUF());
                boolean b = c.getIndIEDest()==IndIEDest.ISENTO;
                ckIsento.setSelected(b);
		
		if(c.getCamposExtras()!=null){
			camposExtras.setAll(c.getCamposExtras());
			refreshPersonTable();
		}		
		
	}

	@FXML
	private void handleNew() {
		CampoExtra cExtra = new CampoExtra();
		cExtra.setName(tfCExtraNome.getText());
		cExtra.setValue(tfCExtraValue.getText());
		tfCExtraNome.setText("");
		tfCExtraValue.setText("");
		camposExtras.add(cExtra);
		refreshPersonTable();
	}

	@FXML
	private void handleRemover() {
		int selectedIndex = campoExtraTable.getSelectionModel()
				.getSelectedIndex();
		if (selectedIndex >= 0) {
			CampoExtra cExtra = campoExtraTable.getSelectionModel()
					.getSelectedItem();
			
			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText(cExtra.getName());
            dialog.setContentText("Removido com sucesso.");
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
			camposExtras.remove(selectedIndex);
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
		int selectedIndex = campoExtraTable.getSelectionModel()
				.getSelectedIndex();
		campoExtraTable.setItems(null);
		campoExtraTable.layout();
		campoExtraTable.setItems(camposExtras);
		campoExtraTable.getSelectionModel().select(selectedIndex);
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (cbTpContato == null) {
			errorMessage += "Selecione um tipo de Contato!\n";
		}

		if (tfNome.getText() == null || tfNome.getText().length() == 0) {
			errorMessage += "Nome inválido!\n";
		}
		if (cbPais.getValue() == null) {
			errorMessage += "País inválido!\n";
		}
		if (cbUF.getValue() == null) {
			errorMessage += "UF inválida!\n";
		}
		if (cbMunicipios.getValue() == null) {
			errorMessage += "Municipio inválido!\n";
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

}
