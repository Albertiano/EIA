package br.net.eia.ui.produto.imposto;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import br.net.eia.enums.UF;
import br.net.eia.produto.imposto.Destino;
import br.net.eia.produto.imposto.Tributo;
import br.net.eia.produto.imposto.cofins.COFINS;
import br.net.eia.produto.imposto.cofins.COFINSST;
import br.net.eia.produto.imposto.cofins.CST_COFINS;
import br.net.eia.produto.imposto.cofins.TpCalcCOFINS;
import br.net.eia.produto.imposto.icms.CST_ICMS;
import br.net.eia.produto.imposto.icms.ICMS;
import br.net.eia.produto.imposto.icms.ModBC;
import br.net.eia.produto.imposto.icms.ModBCST;
import br.net.eia.produto.imposto.icms.MotDesICMS;
import br.net.eia.produto.imposto.icms.Origem;
import br.net.eia.produto.imposto.ipi.CST_IPI;
import br.net.eia.produto.imposto.ipi.IPI;
import br.net.eia.produto.imposto.ipi.TpCalcIPI;
import br.net.eia.produto.imposto.pis.CST_PIS;
import br.net.eia.produto.imposto.pis.PIS;
import br.net.eia.produto.imposto.pis.PISST;
import br.net.eia.produto.imposto.pis.TpCalcPIS;
import br.net.eia.ui.MainApp;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;

public class EditDestinoController implements Initializable {

	@FXML
	private ComboBox<UF> cbDestino;
	@FXML
	private TextField tfCfop;
	
	//Aba Detalhes Fiscais > ICMS
	@FXML
	private ComboBox<CST_ICMS> cbSTICMS;
	@FXML
	private ComboBox<Origem> cbSOrigem;
	@FXML
	private TextField tfPercICMSAplicSN;
	@FXML
	private ComboBox<ModBC> cbTpBcICMS;
	@FXML
	private TextField tfPercRedICMS;
	@FXML
	private TextField tfAliqICMS;
	@FXML
	private ComboBox<MotDesICMS> cbMotDesICMS;	
	@FXML
	private ComboBox<ModBCST> cbTpBcICMSST;	
	@FXML
	private TextField tfPercRedICMSST;
	@FXML
	private TextField tfPercAdicICMSST;
	@FXML
	private TextField tfAliqICMSST;
	@FXML
	private TextField tfPercBCPropiaICMSST;
	@FXML
	private ComboBox<UF> cbUFICMSST;
	
	//Aba Detalhes Fiscais > IPI
	@FXML
	private ComboBox<CST_IPI> cbSTIPI;
	@FXML
	private TextField tfClEnq;
	@FXML
	private TextField tfCodEnq;
	@FXML
	private TextField tfCNPJProd;
	@FXML
	private TextField tfCodSelo;
	@FXML
	private TextField tfQtdSelo;
	@FXML
	private ComboBox<TpCalcIPI> cbTipoCalcIPI;
	@FXML
	private TextField tfAliqIPI;
	@FXML
	private TextField tfTotUniIPI;
	@FXML
	private TextField tfValUnIPI;
	@FXML
	private CheckBox ckValIPIBcICMSProp;
	@FXML
	private CheckBox ckValIPIBcICMSST;
	
	//Aba Detalhes Fiscais > PIS
	@FXML
	private ComboBox<CST_PIS> cbSTPIS;
	@FXML
	private ComboBox<TpCalcPIS> cbTipoCalcPIS;
	@FXML		
	private TextField tfAliqPIS;
	@FXML
	private TextField tfValUnPIS;
	@FXML
	private TextField tfFatorQtdVendPIS;
	@FXML
	private ComboBox<TpCalcPIS> cbTipoCalcPISST;
	@FXML		
	private TextField tfAliqPISST;
	@FXML
	private TextField tfValUnPISST;
	@FXML
	private TextField tfFatorQtdVendPISST;
	
	// Aba Detalhes Fiscais > COFINS
	@FXML
	private ComboBox<CST_COFINS> cbSTCOFINS;
	@FXML
	private ComboBox<TpCalcCOFINS> cbTipoCalcCOFINS;
	@FXML
	private TextField tfAliqCOFINS;
	@FXML
	private TextField tfValUnCOFINS;
	@FXML
	private TextField tfFatorQtdVendCOFINS;
	@FXML
	private ComboBox<TpCalcCOFINS> cbTipoCalcCOFINSST;
	@FXML
	private TextField tfAliqCOFINSST;
	@FXML
	private TextField tfValUnCOFINSST;
	@FXML
	private TextField tfFatorQtdVendCOFINSST;
	
	// Aba Detalhes Fiscais > Observacoes
	@FXML
	private TextArea taObs;
	
	@FXML
	Button btSalvar;
	@FXML
	Button btCancelar;

	private Stage dialogStage;
	private Tributo tributo;
	private Destino destino= new Destino();

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

	public void setDestino(Destino tributos) {
		this.destino = tributos;
		this.tributo = destino.getTributos();
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
		carregarCombos();
		carregarFormatacao();
			
	}
			
	private void formataCampo(TextField tf, int digitosMin) {
		try {
			
			String formatado = ConversorBigDecimal.paraString(tf.getText(), digitosMin);
			tf.setText(formatado);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}
	}
	
	private boolean isInputValid() {
		String errorMessage = "";

		if (cbSTICMS.getValue() == null) {
			errorMessage += "Selecione uma Situação tributaria do ICMS!\n";
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
	
	private void carregarCombos(){
		
		cbDestino.getItems().clear();
		cbDestino.getItems().addAll(UF.values());
		cbDestino.setPromptText("-- Selecione --");
        
		cbSTICMS.getItems().clear();
		cbSTICMS.getItems().addAll(CST_ICMS.values());
		cbSTICMS.setPromptText("-- Selecione --");
		
		cbSOrigem.getItems().clear();
		cbSOrigem.getItems().addAll(Origem.values());
		cbSOrigem.setPromptText("-- Selecione --");
		
		cbTpBcICMS.getItems().clear();
		cbTpBcICMS.getItems().addAll(ModBC.values());
		cbTpBcICMS.setPromptText("-- Selecione --");
		
		cbTpBcICMS.getItems().clear();
		cbTpBcICMS.getItems().addAll(ModBC.values());
		cbTpBcICMS.setPromptText("-- Selecione --");
		
		cbMotDesICMS.getItems().clear();
		cbMotDesICMS.getItems().addAll(MotDesICMS.values());
		cbMotDesICMS.setPromptText("-- Selecione --");
		
		cbTpBcICMSST.getItems().clear();
		cbTpBcICMSST.getItems().addAll(ModBCST.values());
		cbTpBcICMSST.setPromptText("-- Selecione --");
		
		cbUFICMSST.getItems().clear();
		cbUFICMSST.getItems().addAll(UF.values());
		cbUFICMSST.setPromptText("-- Selecione --");
		
		cbSTIPI.getItems().clear();
		cbSTIPI.getItems().addAll(CST_IPI.values());
		cbSTIPI.setPromptText("-- Selecione --");
		
		cbTipoCalcIPI.getItems().clear();
		cbTipoCalcIPI.getItems().addAll(TpCalcIPI.values());
		cbTipoCalcIPI.setPromptText("-- Selecione --");
		
		cbSTPIS.getItems().clear();
		cbSTPIS.getItems().addAll(CST_PIS.values());
		cbSTPIS.setPromptText("-- Selecione --");
		
		cbTipoCalcPIS.getItems().clear();
		cbTipoCalcPIS.getItems().addAll(TpCalcPIS.values());
		cbTipoCalcPIS.setPromptText("-- Selecione --");
		
		cbTipoCalcPISST.getItems().clear();
		cbTipoCalcPISST.getItems().addAll(TpCalcPIS.values());
		cbTipoCalcPISST.setPromptText("-- Selecione --");
		
		cbSTCOFINS.getItems().clear();
		cbSTCOFINS.getItems().addAll(CST_COFINS.values());
		cbSTCOFINS.setPromptText("-- Selecione --");
		
		cbTipoCalcCOFINS.getItems().clear();
		cbTipoCalcCOFINS.getItems().addAll(TpCalcCOFINS.values());
		cbTipoCalcCOFINS.setPromptText("-- Selecione --");
		
		cbTipoCalcCOFINSST.getItems().clear();
		cbTipoCalcCOFINSST.getItems().addAll(TpCalcCOFINS.values());
		cbTipoCalcCOFINSST.setPromptText("-- Selecione --");
	}
	
	private void carregarFormatacao(){				
		tfPercICMSAplicSN.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercICMSAplicSN, 0);
				}
			}
		});
				
		tfPercRedICMS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercRedICMS, 0);
				}
			}
		});
		
		tfAliqICMS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfAliqICMS, 0);
				}
			}
		});
		
		tfPercRedICMSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercRedICMSST, 0);
				}
			}
		});
		
		tfPercAdicICMSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercAdicICMSST, 0);
				}
			}
		});
		
		tfAliqICMSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfAliqICMSST, 0);
				}
			}
		});
		
		tfPercBCPropiaICMSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercBCPropiaICMSST, 0);
				}
			}
		});
		
		tfQtdSelo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfQtdSelo, 0);
				}
			}
		});
		
		tfAliqIPI.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfAliqIPI, 0);
				}
			}
		});
		
		tfTotUniIPI.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfTotUniIPI, 0);
				}
			}
		});
		
		tfValUnIPI.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfValUnIPI, 0);
				}
			}
		});
		
		tfAliqPIS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfAliqPIS, 0);
				}
			}
		});
		
		tfValUnPIS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfValUnPIS, 0);
				}
			}
		});
		
		tfFatorQtdVendPIS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfFatorQtdVendPIS, 0);
				}
			}
		});
		
		tfAliqPISST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfAliqPISST, 0);
				}
			}
		});
		
		tfValUnPISST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfValUnPISST, 0);
				}
			}
		});
		
		tfFatorQtdVendPISST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfFatorQtdVendPISST, 0);
				}
			}
		});
		
		
		
		
		
		
		tfAliqCOFINS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfAliqCOFINS, 0);
				}
			}
		});
		
		tfValUnCOFINS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfValUnCOFINS, 0);
				}
			}
		});
		
		tfFatorQtdVendCOFINS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfFatorQtdVendCOFINS, 0);
				}
			}
		});
		
		tfAliqCOFINSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfAliqCOFINSST, 0);
				}
			}
		});
		
		tfValUnCOFINSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfValUnCOFINSST, 0);
				}
			}
		});
		
		tfFatorQtdVendCOFINSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfFatorQtdVendCOFINSST, 0);
				}
			}
		});
		
	}
	
	private ICMS carregarICMS() {
		
        ICMS icms = new ICMS();
        icms.setOrigem(cbSOrigem.getValue());
        icms.setCstICMS(cbSTICMS.getValue());
        icms.setpCredSN(ConversorBigDecimal.paraBigDecimal(tfPercICMSAplicSN.getText()));
        icms.setModBCICMS(cbTpBcICMS.getValue());
        icms.setpRedBCICMS(ConversorBigDecimal.paraBigDecimal(tfPercRedICMS.getText()));
        icms.setpICMS(ConversorBigDecimal.paraBigDecimal(tfAliqICMS.getText()));
        icms.setMotDesICMS(cbMotDesICMS.getValue());

        icms.setModBCST(cbTpBcICMSST.getValue());
        icms.setpRedBCST(ConversorBigDecimal.paraBigDecimal(tfPercRedICMSST.getText()));
        icms.setpMVAST(ConversorBigDecimal.paraBigDecimal(tfPercAdicICMSST.getText()));
        icms.setpICMSST(ConversorBigDecimal.paraBigDecimal(tfAliqICMSST.getText()));
        icms.setpBCOp(ConversorBigDecimal.paraBigDecimal(tfPercBCPropiaICMSST.getText()));
        icms.setUFST(cbUFICMSST.getValue());
        return icms;
    }
	
	private IPI carregarIPI() {
		
        IPI ipi = new IPI();
        ipi.setCstIPI(cbSTIPI.getValue());
        ipi.setClEnq(tfClEnq.getText());
        ipi.setcEnq(tfCodEnq.getText());
        ipi.setCNPJProd(tfCNPJProd.getText());
        ipi.setcSelo(tfCodSelo.getText());
        ipi.setqSelo(ConversorBigDecimal.paraBigDecimal(tfQtdSelo.getText()));
        ipi.setTpCalcIPI(cbTipoCalcIPI.getValue());
        ipi.setpIPI(ConversorBigDecimal.paraBigDecimal(tfAliqIPI.getText()));
        ipi.setqUnid(ConversorBigDecimal.paraBigDecimal(tfTotUniIPI.getText()));
        ipi.setvUnid(ConversorBigDecimal.paraBigDecimal(tfValUnIPI.getText()));
        ipi.setvIpiBcICMS(ckValIPIBcICMSProp.isSelected());
        ipi.setvIpiBcICMSST(ckValIPIBcICMSST.isSelected());
        return ipi;
    }
	
	public PIS carregarPIS() {
		
        PIS pis = new PIS();
        pis.setCstPIS(cbSTPIS.getValue());
        pis.setTpCalcPis(cbTipoCalcPIS.getValue());
        pis.setpPIS(ConversorBigDecimal.paraBigDecimal(tfAliqPIS.getText()));
        pis.setvAliqProdPIS(ConversorBigDecimal.paraBigDecimal(tfValUnPIS.getText()));
        pis.setqBCProdPIS(ConversorBigDecimal.paraBigDecimal(tfFatorQtdVendPIS.getText()));
        return pis;
    }

    public PISST carregarPISST() {
    	
        PISST pisST = new PISST();
        pisST.setTpCalcPISST(cbTipoCalcPISST.getValue());
        pisST.setpPISST(ConversorBigDecimal.paraBigDecimal(tfAliqPISST.getText()));
        pisST.setvAliqProdPISST(ConversorBigDecimal.paraBigDecimal(tfValUnPISST.getText()));
        pisST.setqBCProdPISST(ConversorBigDecimal.paraBigDecimal(tfFatorQtdVendPISST.getText()));
        return pisST;
    }
    
    public COFINS carregarCOFINS() {
		
		COFINS cofins = new COFINS();
        cofins.setCstCOFINS(cbSTCOFINS.getValue());
        cofins.setTpCalcCofins(cbTipoCalcCOFINS.getValue());
        cofins.setpCOFINS(ConversorBigDecimal.paraBigDecimal(tfAliqCOFINS.getText()));
        cofins.setvAliqProdCOFINS(ConversorBigDecimal.paraBigDecimal(tfValUnCOFINS.getText()));
        cofins.setqBCProdCOFINS(ConversorBigDecimal.paraBigDecimal(tfFatorQtdVendCOFINS.getText()));
        return cofins;
    }

    public COFINSST carregarCOFINSST() {
    	
    	COFINSST cofinsST = new COFINSST();
        cofinsST.setTpCalcCOFINSST(cbTipoCalcCOFINSST.getValue());
        cofinsST.setpCOFINSST(ConversorBigDecimal.paraBigDecimal(tfAliqCOFINSST.getText()));
        cofinsST.setvAliqProdCOFINSST(ConversorBigDecimal.paraBigDecimal(tfValUnCOFINSST.getText()));
        cofinsST.setqBCProdCOFINSST(ConversorBigDecimal.paraBigDecimal(tfFatorQtdVendCOFINSST.getText()));
        return cofinsST;
    }
    
	public Tributo carregarTributos(){
		Tributo t = new Tributo();
		t.setCfop(tfCfop.getText());
		t.setIcms(carregarICMS());
		t.setIpi(carregarIPI());
		t.setPis(carregarPIS());
		t.setPisST(carregarPISST());
		t.setCofins(carregarCOFINS());
		t.setCofinsST(carregarCOFINSST());
				
		return t;
	}


    @FXML
	public void handleBtSalvar(ActionEvent event) {
		
		if (isInputValid()) {
			destino.setEmitente(mainApp.getEmitente());
			destino.setEstado(cbDestino.getValue());
			destino.setTributos(carregarTributos());
	        
			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}
	
	public void exibirTributos(){
		
        ICMS icms =tributo.getIcms();
        if(icms==null){
        	icms=new ICMS();
        }
		exibirICMS(icms);
		
		IPI ipi = tributo.getIpi();
		if(ipi==null){
			ipi=new IPI();
		}
		exibirIPI(ipi);
		
		PIS pis = tributo.getPis();
		if(pis==null){
			pis=new PIS();
		}
		exibirPIS(pis);
		
		PISST pisST = tributo.getPisST();
		if(pisST==null){
			pisST=new PISST();
		}
		exibirPISST(pisST);
		
		COFINS cofins = tributo.getCofins();
		if(cofins==null){
			cofins=new COFINS();
		}
		exibirCOFINS(cofins);
		
		COFINSST cofinsST = tributo.getCofinsST();
		if(cofinsST==null){
			cofinsST=new COFINSST();
		}
		exibirCOFINSST(cofinsST);
   
        	}
	
	private void exibirICMS(ICMS icms){
		
        
        cbSOrigem.setValue(icms.getOrigem());
        cbSTICMS.setValue(icms.getCstICMS());
        tfPercICMSAplicSN.setText(ConversorBigDecimal.paraString(icms.getpCredSN()));
        cbTpBcICMS.setValue(icms.getModBCICMS());
        tfPercRedICMS.setText(ConversorBigDecimal.paraString(icms.getpRedBCICMS()));
        tfAliqICMS.setText(ConversorBigDecimal.paraString(icms.getpICMS()));
        cbMotDesICMS.setValue(icms.getMotDesICMS());

        cbTpBcICMSST.setValue(icms.getModBCST());
        tfPercRedICMSST.setText(ConversorBigDecimal.paraString(icms.getpRedBCST()));
        tfPercAdicICMSST.setText(ConversorBigDecimal.paraString(icms.getpMVAST()));
        tfAliqICMSST.setText(ConversorBigDecimal.paraString(icms.getpICMSST()));
        tfPercBCPropiaICMSST.setText(ConversorBigDecimal.paraString(icms.getpBCOp()));
        cbUFICMSST.setValue(icms.getUFST());
	}
	
	private void exibirIPI(IPI ipi){
		
        cbSTIPI.setValue(ipi.getCstIPI());
        tfClEnq.setText(ipi.getClEnq());
        tfCodEnq.setText(ipi.getcEnq());
        tfCNPJProd.setText(ipi.getCNPJProd());
        tfCodSelo.setText(ipi.getcSelo());
        tfQtdSelo.setText(ConversorBigDecimal.paraString(ipi.getqSelo()));
        cbTipoCalcIPI.setValue(ipi.getTpCalcIPI());
        tfAliqIPI.setText(ConversorBigDecimal.paraString(ipi.getpIPI()));
        tfTotUniIPI.setText(ConversorBigDecimal.paraString(ipi.getqUnid()));
        tfValUnIPI.setText(ConversorBigDecimal.paraString(ipi.getvUnid()));
        ckValIPIBcICMSProp.setSelected(ipi.getvIpiBcICMS());
        ckValIPIBcICMSST.setSelected(ipi.getvIpiBcICMSST());
	}
	
	private void exibirPIS(PIS pis){
		
        cbSTPIS.setValue(pis.getCstPIS());
        cbTipoCalcPIS.setValue(pis.getTpCalcPis());
        tfAliqPIS.setText(ConversorBigDecimal.paraString(pis.getpPIS()));
        tfValUnPIS.setText(ConversorBigDecimal.paraString(pis.getvAliqProdPIS()));
        tfFatorQtdVendPIS.setText(ConversorBigDecimal.paraString(pis.getqBCProdPIS()));
	}
	
	private void exibirPISST(PISST pisST){
		
        cbTipoCalcPISST.setValue(pisST.getTpCalcPISST());
        tfAliqPISST.setText(ConversorBigDecimal.paraString(pisST.getpPISST()));
        tfValUnPISST.setText(ConversorBigDecimal.paraString(pisST.getvAliqProdPISST()));
        tfFatorQtdVendPISST.setText(ConversorBigDecimal.paraString(pisST.getqBCProdPISST()));
	}
	
	private void exibirCOFINS(COFINS confins){
		
        cbSTCOFINS.setValue(confins.getCstCOFINS());
        cbTipoCalcCOFINS.setValue(confins.getTpCalcCofins());
        tfAliqCOFINS.setText(ConversorBigDecimal.paraString(confins.getpCOFINS()));
        tfValUnCOFINS.setText(ConversorBigDecimal.paraString(confins.getvAliqProdCOFINS()));
        tfFatorQtdVendCOFINS.setText(ConversorBigDecimal.paraString(confins.getqBCProdCOFINS()));
	}

	private void exibirCOFINSST(COFINSST confisST){
		
        cbTipoCalcCOFINSST.setValue(confisST.getTpCalcCOFINSST());
        tfAliqCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getpCOFINSST()));
        tfValUnCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getvAliqProdCOFINSST()));
        tfFatorQtdVendCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getqBCProdCOFINSST()));
	}

	
	public void exibir() {
		cbDestino.setValue(destino.getEstado());
		tfCfop.setText(destino.getTributos().getCfop());
		exibirTributos();
		
	}
}
