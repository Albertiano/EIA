package br.net.eia.ui.compra;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import br.net.eia.compra.DetalheFiscalCompra;
import br.net.eia.compra.ItemCompra;
import br.net.eia.enums.UF;
import br.net.eia.produto.Produto;
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
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.produto.ProdutoPesquisaController;
import br.net.eia.ui.produto.RestProdutoManager;
import br.net.eia.ui.produto.unidade.RestUnidadeManager;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;

@SuppressWarnings("restriction")
public class EditItemCompraController implements Initializable {

	
	@FXML
	private TextField tfCodigo;
	@FXML
	private TextField tfDescricao;
	@FXML
	private ComboBox<Unidade> cbUnidade;
	@FXML
	private TextField tfPrecoVenda;
	@FXML
	private TextField tfQuantidade;
	@FXML
	private TextField tfTotalBruto;
	
	//Aba Detalhes Fiscais > Dados
	@FXML
	private TextField tfEAN;
	@FXML
	private TextField tfEANTrib;
	@FXML
	private TextField tfFatorQtdTrib;
	@FXML
	private TextField tfUnTrib;
	@FXML
	private TextField tfValUnTrib;
	@FXML
	private TextField tfExTipi;
	@FXML
	private TextField tfGenero;
	@FXML
	private TextField tfPercDesconto;
	@FXML
	private TextField tfPercSeguro;
	@FXML
	private TextField tfPercFrete;
	@FXML
	private TextField tfPercOutros;
	@FXML
	private CheckBox noValorNota;
	@FXML
	private TextField tfCFOP;
	@FXML
	private TextField tfNCM;
	
	//Aba Detalhes Fiscais > ICMS
	@FXML
	private ComboBox<CST_ICMS> cbSTICMS;
	@FXML
	private ComboBox<Origem> cbSOrigem;
	@FXML
	private TextField tfPercICMSAplicSN;
	@FXML
	private TextField tfvCredIcmsSN;
	@FXML
	private ComboBox<ModBC> cbTpBcICMS;
	@FXML
	private TextField tfPercBcICMS;
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
	private TextField tfValPautaICMSST;
	@FXML
	private TextField tfPercBCPropiaICMSST;
	@FXML
	private ComboBox<UF> cbUFICMSST;
	@FXML
	private TextField tfvICMS;
	@FXML
	private TextField tfvICMSST;
	
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
	@FXML
	private TextField tfvIPI;
	
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
	@FXML
	private TextField tfvPIS;
	@FXML
	private TextField tfvPISST;
	
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
	@FXML
	private TextField tfvCOFINS;
	@FXML
	private TextField tfvCOFINSST;
	
	// Aba Detalhes Fiscais > Observacoes
	@FXML
	private TextArea taObs;
	
	private Stage dialogStage;
	private ItemCompra item = new ItemCompra();

	private boolean okClicked = false;

	// Reference to the main application
	private MainApp mainApp;
	private Produto produto;
	@FXML
	private Button btSalvar;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setItem(ItemCompra produto) {
		this.item = produto;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		tfCodigo.requestFocus();
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
		
		tfCodigo.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						produto = new RestProdutoManager().pesquisar(MainApp.getEmitente(), tfCodigo
								.getText());
						tfQuantidade.requestFocus();
						atualizarProduto(true);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						tfCodigo.requestFocus();
						tfCodigo.selectAll();
					}
				}
			}
		});
		
		tfQuantidade.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					tfPrecoVenda.requestFocus();
					tfPrecoVenda.selectAll();
				}
			}
		});
		
		tfPrecoVenda.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					btSalvar.requestFocus();
					
				}
			}
		});
		
		tfQuantidade.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					BigDecimal qtd = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText());
					BigDecimal preco = ConversorBigDecimal.paraBigDecimal(tfPrecoVenda.getText());
					BigDecimal subtotal = qtd.multiply(preco).setScale(2,RoundingMode.HALF_UP);
					tfTotalBruto.setText(ConversorBigDecimal.paraString(subtotal,2));
					atualizarProduto(false);					
				}
			}
		});
		
		tfPrecoVenda.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {					
					
					BigDecimal qtd = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText());
					BigDecimal preco = ConversorBigDecimal.paraBigDecimal(tfPrecoVenda.getText());
					BigDecimal subtotal = qtd.multiply(preco).setScale(2,RoundingMode.HALF_UP);
					tfTotalBruto.setText(ConversorBigDecimal.paraString(subtotal,2));
					atualizarProduto(false);
				}
			}
		});
			
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

		if (tfDescricao.getText() == null || tfDescricao.getText().length() == 0) {
			errorMessage += "Descrição do Produto inválida!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.masthead(errorMessage)
			.message("Por Favor Corrija os Campos Inválidos")
			.showError();
			return false;
		}
	}
		
	private void carregarCombos(){
		try{
			ObservableList<Unidade> unidades = new RestUnidadeManager().getAll(MainApp.getEmitente());
	        cbUnidade.getItems().clear();
	        cbUnidade.getItems().addAll(unidades);
	        cbUnidade.setPromptText("-- Selecione --");
		}catch(Exception e){
			cbUnidade.getItems().clear();
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);			
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.masthead("Sem Unidades")
			.message("Por Favor verifique o cadastro de unidades.")
			.showError();
			
		}	
        
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
		
		
		tfPrecoVenda.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPrecoVenda, 2);
				}
			}
		});
		
		tfFatorQtdTrib.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfFatorQtdTrib, 0);
				}
			}
		});
		
		tfPercDesconto.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercDesconto, 2);
				}
			}
		});
		
		tfPercSeguro.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercSeguro, 2);
				}
			}
		});
	
		tfPercFrete.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercFrete, 2);
				}
			}
		});
		
		tfPercOutros.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercOutros, 2);
				}
			}
		});
		
		tfPercICMSAplicSN.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercICMSAplicSN, 0);
				}
			}
		});
		
		tfvCredIcmsSN.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfvCredIcmsSN, 2);
				}
			}
		});
		
		tfPercBcICMS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPercBcICMS, 0);
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
		
		tfvICMS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfvICMS, 2);
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
		
		tfValPautaICMSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfValPautaICMSST, 0);
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

	public void atualizarProduto(boolean atualizaPreco){
			
		if(produto!=null){			
		cbUnidade.setValue(produto.getUnidade());
		if(atualizaPreco){
			tfPrecoVenda.setText(ConversorBigDecimal.paraString(produto.getPrecoVenda(),2));
		}		
		tfCodigo.setText(produto.getCodigo());       
        tfDescricao.setText(produto.getDescricao());
        //tfCFOP.setText(produto.getTributacao().getDestino().g);
        tfNCM.setText(produto.getNcm());   
        
        BigDecimal qtd = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText());
		BigDecimal preco = ConversorBigDecimal.paraBigDecimal(tfPrecoVenda.getText());
		BigDecimal subtotal = qtd.multiply(preco).setScale(2,RoundingMode.HALF_UP);
		tfTotalBruto.setText(ConversorBigDecimal.paraString(subtotal,2));
        
		}
	}
	
	@FXML
	private void handlePesquisa() {
		boolean okClicked = showPesquisaProdutoDialog(produto);
		if (produto != null) {			
			if (okClicked) {
				atualizarProduto(true);
				tfQuantidade.requestFocus();
			}

		} else {
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.masthead("Por Favor selecione um item na tabela.")
			.message("Sem item selecionado")
			.showError();
		}
	}
	
	public boolean showPesquisaProdutoDialog(Produto person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/produto/ProdutoPesquisa.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastro de Produtos");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller
			ProdutoPesquisaController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(mainApp);
			controller.setProduto(person);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			produto =  controller.getProduto();
			return controller.isOkClicked();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return false;
		}
	}
	
	@FXML
	public void handleBtSalvar(ActionEvent event) {
		salvar();
	}

	private void salvar() {
		
		if (isInputValid()) {
			item.setProduto(produto);	        
	        item.setCfop(tfCFOP.getText());
	        item.setNcm(tfNCM.getText());
	        item.setPrecoVenda(ConversorBigDecimal.paraBigDecimal(tfPrecoVenda.getText()));
	        item.setQuantidade(ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText()));
	        item.setSubtotal(ConversorBigDecimal.paraBigDecimal(tfTotalBruto.getText()));
	        
	        BigDecimal pBruto = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText()).multiply(produto.getPesoBruto());
	        BigDecimal pLiq = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText()).multiply(produto.getPesoBruto());
	        
	        item.setPesoBruto(pBruto);
	        item.setPesoLiquido(pLiq);
	        	        
	        item.setDetFiscal(carregarDetalheFiscal());
	        
			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}
	
	public DetalheFiscalCompra carregarDetalheFiscal() {
		
		DetalheFiscalCompra d = new DetalheFiscalCompra();
        d.setcEan(tfEAN.getText());
        d.setcEanTrib(tfEANTrib.getText());
        d.setFatorQtdTrib(ConversorBigDecimal.paraBigDecimal(tfFatorQtdTrib.getText()));
        d.setUtrib(tfUnTrib.getText());
        d.setExtipi(tfExTipi.getText());
        d.setVuntrib(ConversorBigDecimal.paraBigDecimal(tfValUnTrib.getText()));
        d.setGenero(tfGenero.getText());
        d.setTotalDesconto(ConversorBigDecimal.paraBigDecimal(tfPercDesconto.getText()));
        d.setTotalSeguro(ConversorBigDecimal.paraBigDecimal(tfPercSeguro.getText()));
        d.setTotalFrete(ConversorBigDecimal.paraBigDecimal(tfPercFrete.getText()));
        d.setOutrasDespesas(ConversorBigDecimal.paraBigDecimal(tfPercOutros.getText()));
        d.setIcms(carregarICMS());
        d.setIpi(carregarIPI());
        d.setPis(carregarPIS());
        d.setPisST(carregarPISST());
        d.setCofins(carregarCOFINS());
        d.setCofinsST(carregarCOFINSST());
        return d;
    }
	
	private ICMS carregarICMS() {
		
        ICMS icms = new ICMS();
        icms.setOrigem(cbSOrigem.getValue());
        icms.setCstICMS(cbSTICMS.getValue());
        icms.setpCredSN(ConversorBigDecimal.paraBigDecimal(tfPercICMSAplicSN.getText()));
        icms.setModBCICMS(cbTpBcICMS.getValue());
        icms.setvBCICMS(ConversorBigDecimal.paraBigDecimal(tfPercBcICMS.getText()));
        icms.setpRedBCICMS(ConversorBigDecimal.paraBigDecimal(tfPercRedICMS.getText()));
        icms.setpICMS(ConversorBigDecimal.paraBigDecimal(tfAliqICMS.getText()));
        icms.setMotDesICMS(cbMotDesICMS.getValue());
        icms.setvICMS(ConversorBigDecimal.paraBigDecimal(tfvICMS.getText()));
        
        icms.setModBCST(cbTpBcICMSST.getValue());
        icms.setpRedBCST(ConversorBigDecimal.paraBigDecimal(tfPercRedICMSST.getText()));
        icms.setpMVAST(ConversorBigDecimal.paraBigDecimal(tfPercAdicICMSST.getText()));
        icms.setvBCST(ConversorBigDecimal.paraBigDecimal(tfValPautaICMSST.getText()));
        icms.setpICMSST(ConversorBigDecimal.paraBigDecimal(tfAliqICMSST.getText()));
        icms.setpBCOp(ConversorBigDecimal.paraBigDecimal(tfPercBCPropiaICMSST.getText()));
        icms.setUFST(cbUFICMSST.getValue());
        icms.setvICMSST(ConversorBigDecimal.paraBigDecimal(tfvICMSST.getText()));
        
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
        ipi.setvIPI(ConversorBigDecimal.paraBigDecimal(tfvIPI.getText()));
        return ipi;
    }
	
	public PIS carregarPIS() {
		
        PIS pis = new PIS();
        pis.setCstPIS(cbSTPIS.getValue());
        pis.setTpCalcPis(cbTipoCalcPIS.getValue());
        pis.setpPIS(ConversorBigDecimal.paraBigDecimal(tfAliqPIS.getText()));
        pis.setvAliqProdPIS(ConversorBigDecimal.paraBigDecimal(tfValUnPIS.getText()));
        pis.setqBCProdPIS(ConversorBigDecimal.paraBigDecimal(tfFatorQtdVendPIS.getText()));
        pis.setvPIS(ConversorBigDecimal.paraBigDecimal(tfvPIS.getText()));
        return pis;
    }

    public PISST carregarPISST() {
    	
        PISST pisST = new PISST();
        pisST.setTpCalcPISST(cbTipoCalcPISST.getValue());
        pisST.setpPISST(ConversorBigDecimal.paraBigDecimal(tfAliqPISST.getText()));
        pisST.setvAliqProdPISST(ConversorBigDecimal.paraBigDecimal(tfValUnPISST.getText()));
        pisST.setqBCProdPISST(ConversorBigDecimal.paraBigDecimal(tfFatorQtdVendPISST.getText()));
        pisST.setvPISST(ConversorBigDecimal.paraBigDecimal(tfvPISST.getText()));
        
        return pisST;
    }
    
    public COFINS carregarCOFINS() {
		
		COFINS cofins = new COFINS();
        cofins.setCstCOFINS(cbSTCOFINS.getValue());
        cofins.setTpCalcCofins(cbTipoCalcCOFINS.getValue());
        cofins.setpCOFINS(ConversorBigDecimal.paraBigDecimal(tfAliqCOFINS.getText()));
        cofins.setvAliqProdCOFINS(ConversorBigDecimal.paraBigDecimal(tfValUnCOFINS.getText()));
        cofins.setqBCProdCOFINS(ConversorBigDecimal.paraBigDecimal(tfFatorQtdVendCOFINS.getText()));
        cofins.setvCOFINS(ConversorBigDecimal.paraBigDecimal(tfvCOFINS.getText()));
        return cofins;
    }

    public COFINSST carregarCOFINSST() {
    	
    	COFINSST cofinsST = new COFINSST();
        cofinsST.setTpCalcCOFINSST(cbTipoCalcCOFINSST.getValue());
        cofinsST.setpCOFINSST(ConversorBigDecimal.paraBigDecimal(tfAliqCOFINSST.getText()));
        cofinsST.setvAliqProdCOFINSST(ConversorBigDecimal.paraBigDecimal(tfValUnCOFINSST.getText()));
        cofinsST.setqBCProdCOFINSST(ConversorBigDecimal.paraBigDecimal(tfFatorQtdVendCOFINSST.getText()));
        cofinsST.setvCOFINSST(ConversorBigDecimal.paraBigDecimal(tfvCOFINSST.getText()));
        return cofinsST;
    }
    
	
	public void exibir(){
				
		tfCodigo.setText(item.getProduto().getCodigo());   			    
        tfDescricao.setText(item.getProduto().getDescricao());
        tfCFOP.setText(item.getCfop());
        tfNCM.setText(item.getNcm());
        tfPrecoVenda.setText(ConversorBigDecimal.paraString(item.getPrecoVenda()));
        tfQuantidade.setText(ConversorBigDecimal.paraString(item.getQuantidade()));
        tfTotalBruto.setText(ConversorBigDecimal.paraString(item.getSubtotal()));
        noValorNota.setSelected(item.isNoValorNota());       
        cbUnidade.setValue(item.getProduto().getUnidade());
        
        DetalheFiscalCompra d = item.getDetFiscal();
        if(d==null){
        	d= new DetalheFiscalCompra();
        }
        exibirDetalheFiscal(d);
	}

	private void exibirDetalheFiscal(DetalheFiscalCompra d) {
		
        tfEAN.setText(d.getcEan());
        tfEANTrib.setText(d.getcEanTrib());
        tfFatorQtdTrib.setText(ConversorBigDecimal.paraString(d.getFatorQtdTrib()));
        tfUnTrib.setText(d.getUtrib());
        tfExTipi.setText(d.getExtipi());
        tfValUnTrib.setText(ConversorBigDecimal.paraString(d.getVuntrib(),2));
        tfGenero.setText(d.getGenero());
        tfPercDesconto.setText(ConversorBigDecimal.paraString(d.getTotalDesconto()));
        tfPercSeguro.setText(ConversorBigDecimal.paraString(d.getTotalSeguro()));
        tfPercFrete.setText(ConversorBigDecimal.paraString(d.getTotalFrete()));
        tfPercOutros.setText(ConversorBigDecimal.paraString(d.getOutrasDespesas()));
        
        ICMS icms =d.getIcms();
        if(icms==null){
        	icms=new ICMS();
        }
		exibirICMS(icms);
		
		IPI ipi = d.getIpi();
		if(ipi==null){
			ipi=new IPI();
		}
		exibirIPI(ipi);
		
		PIS pis = d.getPis();
		if(pis==null){
			pis=new PIS();
		}
		exibirPIS(pis);
		
		PISST pisST = d.getPisST();
		if(pisST==null){
			pisST=new PISST();
		}
		exibirPISST(pisST);
		
		COFINS cofins = d.getCofins();
		if(cofins==null){
			cofins=new COFINS();
		}
		exibirCOFINS(cofins);
		
		COFINSST cofinsST = d.getCofinsST();
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
        tfPercRedICMS.setText(ConversorBigDecimal.paraString(icms.getpRedBCICMS(),2));
        tfPercBcICMS.setText(ConversorBigDecimal.paraString(icms.getvBCICMS(),2));
        tfAliqICMS.setText(ConversorBigDecimal.paraString(icms.getpICMS(),2));
        cbMotDesICMS.setValue(icms.getMotDesICMS());
        tfvICMS.setText(ConversorBigDecimal.paraString(icms.getvICMS()));
        
        cbTpBcICMSST.setValue(icms.getModBCST());
        tfPercRedICMSST.setText(ConversorBigDecimal.paraString(icms.getpRedBCST()));
        tfPercAdicICMSST.setText(ConversorBigDecimal.paraString(icms.getpMVAST()));
        tfValPautaICMSST.setText(ConversorBigDecimal.paraString(icms.getvBCST()));
        tfAliqICMSST.setText(ConversorBigDecimal.paraString(icms.getpICMSST()));
        tfPercBCPropiaICMSST.setText(ConversorBigDecimal.paraString(icms.getpBCOp()));
        cbUFICMSST.setValue(icms.getUFST());
        tfvICMSST.setText(ConversorBigDecimal.paraString(icms.getvICMSST()));
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
        tfvIPI.setText(ConversorBigDecimal.paraString(ipi.getvIPI()));
	}
	
	private void exibirPIS(PIS pis){
		
        cbSTPIS.setValue(pis.getCstPIS());
        cbTipoCalcPIS.setValue(pis.getTpCalcPis());
        tfAliqPIS.setText(ConversorBigDecimal.paraString(pis.getpPIS()));
        tfValUnPIS.setText(ConversorBigDecimal.paraString(pis.getvAliqProdPIS()));
        tfFatorQtdVendPIS.setText(ConversorBigDecimal.paraString(pis.getqBCProdPIS()));
        tfvPIS.setText(ConversorBigDecimal.paraString(pis.getvPIS()));
	}
	
	private void exibirPISST(PISST pisST){
		
        cbTipoCalcPISST.setValue(pisST.getTpCalcPISST());
        tfAliqPISST.setText(ConversorBigDecimal.paraString(pisST.getpPISST()));
        tfValUnPISST.setText(ConversorBigDecimal.paraString(pisST.getvAliqProdPISST()));
        tfFatorQtdVendPISST.setText(ConversorBigDecimal.paraString(pisST.getqBCProdPISST()));
        tfvPISST.setText(ConversorBigDecimal.paraString(pisST.getpPISST()));
	}
	
	private void exibirCOFINS(COFINS confins){
		
        cbSTCOFINS.setValue(confins.getCstCOFINS());
        cbTipoCalcCOFINS.setValue(confins.getTpCalcCofins());
        tfAliqCOFINS.setText(ConversorBigDecimal.paraString(confins.getpCOFINS()));
        tfValUnCOFINS.setText(ConversorBigDecimal.paraString(confins.getvAliqProdCOFINS()));
        tfFatorQtdVendCOFINS.setText(ConversorBigDecimal.paraString(confins.getqBCProdCOFINS()));
        tfvCOFINS.setText(ConversorBigDecimal.paraString(confins.getvCOFINS()));
	}

	private void exibirCOFINSST(COFINSST confisST){
		
        cbTipoCalcCOFINSST.setValue(confisST.getTpCalcCOFINSST());
        tfAliqCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getpCOFINSST()));
        tfValUnCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getvAliqProdCOFINSST()));
        tfFatorQtdVendCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getqBCProdCOFINSST()));
        tfvCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getvCOFINSST()));
	}
}
