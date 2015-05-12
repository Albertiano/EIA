package br.net.eia.ui.emitente;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import br.net.eia.emitente.CRT;
import br.net.eia.emitente.Emitente;
import br.net.eia.enums.TpDoc;
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.pais.Pais;
import br.net.eia.util.Formatador;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.MunicipioClientRest;
import br.net.eia.ui.PaisClientRest;

@SuppressWarnings("restriction")
public class EditEmitenteController implements Initializable {

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
	private ComboBox<CRT> cbCRT;
	

	private Stage dialogStage;
	private Emitente cliente;

	private boolean okClicked = false;

	private MainApp mainApp;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEmitente(Emitente cliente) {
		this.cliente = cliente;
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
		
		cbCRT.getItems().clear();
		cbCRT.getItems().addAll(CRT.values());
		cbCRT.setPromptText("-- Selecione --");
		
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
    	try{
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
    	}catch(Exception e){
    		Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
    	}
    }

    private void formataCEP() {
    	try{
        Formatador formatter = new Formatador();
        String formatado = formatter.formatterCEP(tfCep.getText());
        tfCep.setText(formatado);
    	}catch(Exception e){
    		Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
    	}
    }

    private void formataFone() {
    	try{	
        Formatador formatter = new Formatador();
        String formatado = formatter.formatterFone(tffone.getText());
        tffone.setText(formatado);          		
    	}catch(Exception e){
    		Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
    	}
    }

	@FXML
	public void handleBtSalvar(ActionEvent event) {
		if (isInputValid()) {
			cliente.setTpDoc(cbTpDoc.getValue());
			cliente.setNumDoc(tfCpfCnpj.getText());
			cliente.setIE(tfIE.getText());
			cliente.setNome(tfNome.getText());
			cliente.setLogradouro(tfEndereco.getText());
			cliente.setNumero(tfNro.getText());
			cliente.setComplemento(tfCpl.getText());
			cliente.setBairro(tfBairro.getText());
			cliente.setCep(tfCep.getText());
			cliente.setPais(cbPais.getValue());
			cliente.setUf(cbUF.getValue());
			cliente.setMunicipio(cbMunicipios.getValue());
			cliente.setFone(tffone.getText());
			cliente.setObs(taObs.getText());
			cliente.setEmail(tfEmail.getText());
			cliente.setContato(tfContato.getText());
			cliente.setFantasia(tfFantasia.getText());
			cliente.setCelular(tfCelular.getText());
			cliente.setFoneRes(tfFoneRes.getText());
			cliente.setBloqueado(ckBloqueado.isSelected());
			cliente.setDesabilitado(ckDesabilitado.isSelected());
			cliente.setClienteFonte(ckFonte.isSelected());
			cliente.setISUF(tfInscSuframa.getText());
			cliente.setCrt(cbCRT.getValue());

			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}

	private void formataFone(TextField fone) {
		if(fone!=null){
		Formatador formatter = new Formatador();
		String formatado = formatter.formatterFone(fone.getText());
		fone.setText(formatado);
		}
	}

	public void exibirEmitente(Emitente c) {
		try{
		if(c!=null){
			cbTpDoc.setValue(c.getTpDoc());
			tfCpfCnpj.setText(c.getNumDoc());
			tfIE.setText(c.getIE());
		}
		cbCRT.setValue(c.getCrt());
		
		tfNome.setText(c.getNome());
		tfEndereco.setText(c.getLogradouro());
		tfNro.setText(c.getNumero());
		tfCpl.setText(c.getComplemento());
		tfBairro.setText(c.getBairro());
		tfCep.setText(c.getCep());
		cbPais.setValue(c.getPais());		
		cbUF.setValue(c.getUf());		
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
		ckFonte.setSelected(c.isClienteFonte());
		tfInscSuframa.setText(c.getISUF());
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";

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
			Dialogs.create()
			.owner(mainApp.getPrimaryStage())
			.title("Aviso")
			.masthead(errorMessage)
			.message("Por Favor Corrija os Campos Inválidos")
			.showError();
			return false;
		}
	}

}
