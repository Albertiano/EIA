package br.net.eia.ui.item;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
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
import javafx.scene.control.Alert;
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
import br.net.eia.contato.Contato;
import br.net.eia.enums.UF;
import br.net.eia.item.DetalheFiscal;
import br.net.eia.item.Item;
import br.net.eia.produto.Produto;
import br.net.eia.produto.Produtos;
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
import br.net.eia.ui.produto.ProdutoController;
import br.net.eia.ui.produto.ProdutoPesquisaController;
import br.net.eia.ui.produto.RestProdutoManager;
import br.net.eia.ui.produto.unidade.RestUnidadeManager;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;

@SuppressWarnings("restriction")
public class EditItemController implements Initializable {
	@FXML
	private TextField tfCodigo;
	@FXML
	private TextField tfDescricao;
	@FXML
	private TextField tfUn;
	@FXML
	private TextField tfPrecoVenda;
	@FXML
	private TextField tfQuantidade;
	@FXML
	private TextField tfSubtotal;
	@FXML
	private TextField tfCFOP;
	@FXML
	private TextField tfDesc;
	@FXML
	private TextField tfFrete;
	@FXML
	private TextField tfSeg;
	@FXML
	private TextField tfOutros;

	// Tributis > ICMS
	@FXML
	private ComboBox<CST_ICMS> cbSTICMS;
	@FXML
	private ComboBox<Origem> cbOrigem;
	@FXML
	private TextField tfPercICMSAplicSN;
	@FXML
	private TextField tfvCredIcmsSN;
	@FXML
	private ComboBox<ModBC> cbTpBcICMS;
	@FXML
	private TextField tfBcICMS;
	@FXML
	private TextField tfPercRedICMS;
	@FXML
	private TextField tfAliqICMS;
	@FXML
	private TextField tfvICMS;
	@FXML
	private ComboBox<MotDesICMS> cbMotDesICMS;
	@FXML
	private TextField tfvICMSDesonerado;
	@FXML
	private ComboBox<ModBCST> cbTpBcICMSST;
	@FXML
	private TextField tfPercRedICMSST;
	@FXML
	private TextField tfPercAdicICMSST;
	@FXML
	private TextField tfbcICMSST;
	@FXML
	private TextField tfAliqICMSST;
	@FXML
	private TextField tfvICMSST;
	@FXML
	private TextField tfPercBCPropiaICMSST;
	@FXML
	private ComboBox<UF> cbUFICMSST;

	// Tributos > IPI
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
	private TextField tfvBcIPI;
	@FXML
	private TextField tfAliqIPI;
	@FXML
	private TextField tfTotUniIPI;
	@FXML
	private TextField tfValUnIPI;
	@FXML
	private TextField tfvIPI;

	// Aba Detalhes Fiscais > PIS
	@FXML
	private ComboBox<CST_PIS> cbSTPIS;
	@FXML
	private ComboBox<TpCalcPIS> cbTipoCalcPIS;
	@FXML
	private TextField tfbcPIS;
	@FXML
	private TextField tfAliqPIS;
	@FXML
	private TextField tfValUnPIS;
	@FXML
	private TextField tfQtdVendPIS;
	@FXML
	private TextField tfvPIS;
	@FXML
	private ComboBox<TpCalcPIS> cbTipoCalcPISST;
	@FXML
	private TextField tfbcPISST;
	@FXML
	private TextField tfAliqPISST;
	@FXML
	private TextField tfValUnPISST;
	@FXML
	private TextField tfQtdVendPISST;

	@FXML
	private TextField tfvPISST;

	// Aba Detalhes Fiscais > COFINS
	@FXML
	private ComboBox<CST_COFINS> cbSTCOFINS;
	@FXML
	private ComboBox<TpCalcCOFINS> cbTipoCalcCOFINS;
	@FXML
	private TextField tfbcCOFINS;
	@FXML
	private TextField tfAliqCOFINS;
	@FXML
	private TextField tfValUnCOFINS;
	@FXML
	private TextField tfQtdVendCOFINS;
	@FXML
	private TextField tfvCOFINS;
	@FXML
	private ComboBox<TpCalcCOFINS> cbTipoCalcCOFINSST;
	@FXML
	private TextField tfbcCOFINSST;
	@FXML
	private TextField tfAliqCOFINSST;
	@FXML
	private TextField tfValUnCOFINSST;
	@FXML
	private TextField tfQtdVendCOFINSST;

	@FXML
	private TextField tfvCOFINSST;

	// Aba Detalhes Fiscais > Observacoes
	@FXML
	private TextArea taObs;

	private Stage dialogStage;
	private Item item;
	private UF ufDest;

	private boolean okClicked = false;

	// Reference to the main application
	private MainApp mainApp;
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

	public void setItem(Item produto) {
		this.item = produto;
	}
	
	public void setUFDest(UF uf){
		ufDest = uf;
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
						Produto p = new RestProdutoManager().pesquisar(
								MainApp.getEmitente(), tfCodigo.getText());
						item.setProduto(p);
						tfQuantidade.requestFocus();
						atualizarItem(true);
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

	}

	private void formataCampo(TextField tf, int digitosMin) {
		try {
			
			String formatado = ConversorBigDecimal.paraString(tf.getText(), digitosMin);
			tf.setText(formatado);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (tfDescricao.getText() == null
				|| tfDescricao.getText().length() == 0) {
			errorMessage += "Descrição do Produto inválida!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setHeaderText("Por favor corrija os campos inválidos");
            dialog.setContentText(errorMessage);
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            return false;
		}
	}

	private void carregarCombos() {

		cbSTICMS.getItems().clear();
		cbSTICMS.getItems().addAll(CST_ICMS.values());
		cbSTICMS.setPromptText("-- Selecione --");

		cbOrigem.getItems().clear();
		cbOrigem.getItems().addAll(Origem.values());
		cbOrigem.setPromptText("-- Selecione --");

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

	private void carregarFormatacao() {
		tfQuantidade.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfQuantidade, 0);
							
							BigDecimal qtd = ConversorBigDecimal
									.paraBigDecimal(tfQuantidade.getText());
							BigDecimal preco = ConversorBigDecimal
									.paraBigDecimal(tfPrecoVenda.getText());
							BigDecimal subtotal = qtd.multiply(preco).setScale(
									2, RoundingMode.HALF_UP);
							tfSubtotal.setText(ConversorBigDecimal
									.paraString(subtotal, 2));
							atualizarItem(false);
						}
					}
				});

		tfPrecoVenda.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfPrecoVenda, 2);
							
							BigDecimal qtd = ConversorBigDecimal
									.paraBigDecimal(tfQuantidade.getText());
							BigDecimal preco = ConversorBigDecimal
									.paraBigDecimal(tfPrecoVenda.getText());
							BigDecimal subtotal = qtd.multiply(preco).setScale(
									2, RoundingMode.HALF_UP);
							tfSubtotal.setText(ConversorBigDecimal
									.paraString(subtotal, 2));
							atualizarItem(false);
						}
					}
				});

		tfDesc.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfDesc, 2);
				}
			}
		});

		tfSeg.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfSeg, 2);
				}
			}
		});

		tfFrete.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfFrete, 2);
				}
			}
		});

		tfOutros.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfOutros, 2);
				}
			}
		});

		tfPercICMSAplicSN.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfPercICMSAplicSN, 0);
						}
					}
				});

		tfvCredIcmsSN.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfvCredIcmsSN, 2);
						}
					}
				});

		tfBcICMS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfBcICMS, 0);
				}
			}
		});

		tfPercRedICMS.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
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

		tfPercRedICMSST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfPercRedICMSST, 0);
						}
					}
				});

		tfPercAdicICMSST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfPercAdicICMSST, 0);
						}
					}
				});

		tfAliqICMSST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfAliqICMSST, 0);
						}
					}
				});

		tfbcICMSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfbcICMSST, 2);
				}
			}
		});
		
		tfvICMSST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfvICMSST, 2);
				}
			}
		});

		tfPercBCPropiaICMSST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
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

		tfvBcIPI.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfvBcIPI, 0);
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

		tfTotUniIPI.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
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

		tfbcPIS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfbcPIS, 2);
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

		tfQtdVendPIS.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfQtdVendPIS, 0);
						}
					}
				});
		tfbcPISST.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfbcPISST, 2);
				}
			}
		});

		tfAliqPISST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfAliqPISST, 0);
						}
					}
				});

		tfValUnPISST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfValUnPISST, 0);
						}
					}
				});

		tfQtdVendPISST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfQtdVendPISST, 0);
						}
					}
				});
		tfbcCOFINS.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfbcPIS, 2);
				}
			}
		});

		tfAliqCOFINS.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfAliqCOFINS, 0);
						}
					}
				});

		tfValUnCOFINS.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfValUnCOFINS, 0);
						}
					}
				});

		tfQtdVendCOFINS.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfQtdVendCOFINS, 0);
						}
					}
				});

		tfbcCOFINSST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfQtdVendCOFINS, 2);
						}
					}
				});

		tfAliqCOFINSST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfAliqCOFINSST, 0);
						}
					}
				});

		tfValUnCOFINSST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfValUnCOFINSST, 0);
						}
					}
				});

		tfQtdVendCOFINSST.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfQtdVendCOFINSST, 0);
						}
					}
				});

	}

	public void atualizarItem(boolean atualizaPreco) {
		Produto p = item.getProduto();
		if (p != null) {
			tfUn.setText(p.getUnidade().getSigla());
			if (atualizaPreco) {
				tfPrecoVenda
						.setText(ConversorBigDecimal.paraString(p.getPrecoVenda(), 2));
				tfQuantidade.setText("1");
			}
			BigDecimal qtd = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText());
			BigDecimal preco = ConversorBigDecimal.paraBigDecimal(tfPrecoVenda.getText());
			BigDecimal subtotal = qtd.multiply(preco).setScale(2,
					RoundingMode.HALF_UP);
			tfSubtotal.setText(ConversorBigDecimal.paraString(subtotal, 2));

			ItemManager iM = new ItemManager();
			item = iM.atualizarItem(item, qtd, preco, ufDest);
			exibir();
		}
	}

	@FXML
	private void handleSelecionarProduto() {
		ProdutoController cM = new ProdutoController();
		Produto p = item.getProduto();
		boolean okClicked = cM.showPesquisaProdutoDialog(mainApp, dialogStage);
		p = cM.getProdutoSelecionado();
		if (p != null) {
			if (okClicked) {
				tfCodigo.setText(p.getCodigo());
				tfDescricao.setText(p.getDescricao());
				tfUn.setText(p.getUnidade().getSigla());
				tfQuantidade.setText("1");
				tfPrecoVenda.setText(ConversorBigDecimal.paraString(p.getPrecoVenda(), 2));
				tfSubtotal.setText(ConversorBigDecimal.paraString(p.getPrecoVenda(), 2));

				atualizarItem(true);
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
	public void handleBtSalvar(ActionEvent event) {
		salvar();
	}

	private void salvar() {
		if (isInputValid()) {
			item.setProduto(item.getProduto());
			item.setPrecoVenda(ConversorBigDecimal.paraBigDecimal(tfPrecoVenda.getText()));
			item.setQuantidade(ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText()));
			item.setSubtotal(ConversorBigDecimal.paraBigDecimal(tfSubtotal.getText()));
			item.setvDesc(ConversorBigDecimal.paraBigDecimal(tfDesc.getText()));
			item.setvFrete(ConversorBigDecimal.paraBigDecimal(tfFrete.getText()));
			item.setvSeg(ConversorBigDecimal.paraBigDecimal(tfSeg.getText()));
			item.setvOutro(ConversorBigDecimal.paraBigDecimal(tfOutros.getText()));

			BigDecimal pBruto = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText())
					.multiply(item.getProduto().getPesoBruto());
			BigDecimal pLiq = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText())
					.multiply(item.getProduto().getPesoLiquido());

			item.setPesoBruto(pBruto);
			item.setPesoLiquido(pLiq);

			item.setDetFiscal(carregarDetalheFiscal());
			
			item.setIfAdic(taObs.getText());

			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}

	public DetalheFiscal carregarDetalheFiscal() {
		
		DetalheFiscal d = new DetalheFiscal();
		d.setCfop(tfCFOP.getText());
		d.setExtipi(item.getProduto().getExtipi());
		d.setGenero(item.getProduto().getGenero());
		d.setcEan(item.getProduto().getcEan());
		d.setcEanTrib(item.getProduto().getcEanTrib());
		d.setUtrib(item.getProduto().getUtrib().getSigla());
		d.setqTrib(ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText()));
		d.setVuntrib(ConversorBigDecimal.paraBigDecimal(tfPrecoVenda.getText()));

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
		icms.setOrigem(cbOrigem.getValue());
		icms.setCstICMS(cbSTICMS.getValue());
		icms.setpCredSN(ConversorBigDecimal.paraBigDecimal(tfPercICMSAplicSN.getText()));
		icms.setvCredICMSSN(ConversorBigDecimal.paraBigDecimal(tfvCredIcmsSN.getText()));
		icms.setModBCICMS(cbTpBcICMS.getValue());
		icms.setvBCICMS(ConversorBigDecimal.paraBigDecimal(tfBcICMS.getText()));
		icms.setpRedBCICMS(ConversorBigDecimal.paraBigDecimal(tfPercRedICMS.getText()));
		icms.setpICMS(ConversorBigDecimal.paraBigDecimal(tfAliqICMS.getText()));
		icms.setvICMS(ConversorBigDecimal.paraBigDecimal(tfvICMS.getText()));
		icms.setMotDesICMS(cbMotDesICMS.getValue());
		icms.setvICMSDeson(ConversorBigDecimal.paraBigDecimal(tfvICMSDesonerado.getText()));

		icms.setModBCST(cbTpBcICMSST.getValue());
		icms.setpRedBCST(ConversorBigDecimal.paraBigDecimal(tfPercRedICMSST.getText()));
		icms.setpMVAST(ConversorBigDecimal.paraBigDecimal(tfPercAdicICMSST.getText()));
		icms.setvBCST(ConversorBigDecimal.paraBigDecimal(tfbcICMSST.getText()));
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
		ipi.setvBCIPI(ConversorBigDecimal.paraBigDecimal(tfvBcIPI.getText()));
		ipi.setpIPI(ConversorBigDecimal.paraBigDecimal(tfAliqIPI.getText()));
		ipi.setqUnid(ConversorBigDecimal.paraBigDecimal(tfTotUniIPI.getText()));
		ipi.setvUnid(ConversorBigDecimal.paraBigDecimal(tfValUnIPI.getText()));
		ipi.setvIPI(ConversorBigDecimal.paraBigDecimal(tfvIPI.getText()));
		return ipi;
	}

	public PIS carregarPIS() {
		
		PIS pis = new PIS();
		pis.setCstPIS(cbSTPIS.getValue());
		pis.setTpCalcPis(cbTipoCalcPIS.getValue());
		pis.setvBCPIS(ConversorBigDecimal.paraBigDecimal(tfbcPIS.getText()));
		pis.setpPIS(ConversorBigDecimal.paraBigDecimal(tfAliqPIS.getText()));
		pis.setvAliqProdPIS(ConversorBigDecimal.paraBigDecimal(tfValUnPIS.getText()));
		pis.setqBCProdPIS(ConversorBigDecimal.paraBigDecimal(tfQtdVendPIS.getText()));
		pis.setvPIS(ConversorBigDecimal.paraBigDecimal(tfvPIS.getText()));
		return pis;
	}

	public PISST carregarPISST() {
		
		PISST pisST = new PISST();
		pisST.setTpCalcPISST(cbTipoCalcPISST.getValue());
		pisST.setvBCPISST(ConversorBigDecimal.paraBigDecimal(tfbcPISST.getText()));
		pisST.setpPISST(ConversorBigDecimal.paraBigDecimal(tfAliqPISST.getText()));
		pisST.setvAliqProdPISST(ConversorBigDecimal.paraBigDecimal(tfValUnPISST.getText()));
		pisST.setqBCProdPISST(ConversorBigDecimal.paraBigDecimal(tfQtdVendPISST.getText()));
		pisST.setvPISST(ConversorBigDecimal.paraBigDecimal(tfvPISST.getText()));

		return pisST;
	}

	public COFINS carregarCOFINS() {
		
		COFINS cofins = new COFINS();
		cofins.setCstCOFINS(cbSTCOFINS.getValue());
		cofins.setTpCalcCofins(cbTipoCalcCOFINS.getValue());
		cofins.setvBCCOFINS(ConversorBigDecimal.paraBigDecimal(tfbcCOFINS.getText()));
		cofins.setpCOFINS(ConversorBigDecimal.paraBigDecimal(tfAliqCOFINS.getText()));
		cofins.setvAliqProdCOFINS(ConversorBigDecimal.paraBigDecimal(tfValUnCOFINS
				.getText()));
		cofins.setqBCProdCOFINS(ConversorBigDecimal.paraBigDecimal(tfQtdVendCOFINS
				.getText()));
		cofins.setvCOFINS(ConversorBigDecimal.paraBigDecimal(tfvCOFINS.getText()));
		return cofins;
	}

	public COFINSST carregarCOFINSST() {
		
		COFINSST cofinsST = new COFINSST();
		cofinsST.setTpCalcCOFINSST(cbTipoCalcCOFINSST.getValue());
		cofinsST.setvBCCOFINSST(ConversorBigDecimal.paraBigDecimal(tfbcCOFINSST.getText()));
		cofinsST.setpCOFINSST(ConversorBigDecimal.paraBigDecimal(tfAliqCOFINSST.getText()));
		cofinsST.setvAliqProdCOFINSST(ConversorBigDecimal.paraBigDecimal(tfValUnCOFINSST
				.getText()));
		cofinsST.setqBCProdCOFINSST(ConversorBigDecimal.paraBigDecimal(tfQtdVendCOFINSST
				.getText()));
		cofinsST.setvCOFINSST(ConversorBigDecimal.paraBigDecimal(tfvCOFINSST.getText()));
		return cofinsST;
	}

	public void exibir() {
		tfCodigo.setText(item.getProduto().getCodigo());
		tfDescricao.setText(item.getProduto().getDescricao());
		tfUn.setText(item.getProduto().getUnidade().getSigla());
		tfPrecoVenda.setText(ConversorBigDecimal.paraString(item.getPrecoVenda(), 2));
		tfQuantidade.setText(ConversorBigDecimal.paraString(item.getQuantidade()));
		tfSubtotal.setText(ConversorBigDecimal.paraString(item.getSubtotal(), 2));
		tfCFOP.setText(item.getDetFiscal().getCfop());
		tfDesc.setText(ConversorBigDecimal.paraString(item.getvDesc(), 2));
		tfFrete.setText(ConversorBigDecimal.paraString(item.getvFrete(), 2));
		tfSeg.setText(ConversorBigDecimal.paraString(item.getvSeg(), 2));
		tfOutros.setText(ConversorBigDecimal.paraString(item.getvOutro(), 2));
		taObs.setText(item.getIfAdic());

		DetalheFiscal d = item.getDetFiscal();
		if (d == null) {
			d = new DetalheFiscal();
		}
		exibirDetalheFiscal(d);
	}

	private void exibirDetalheFiscal(DetalheFiscal d) {

		ICMS icms = d.getIcms();
		if (icms == null) {
			icms = new ICMS();
		}
		exibirICMS(icms);

		IPI ipi = d.getIpi();
		if (ipi == null) {
			ipi = new IPI();
		}
		exibirIPI(ipi);

		PIS pis = d.getPis();
		if (pis == null) {
			pis = new PIS();
		}
		exibirPIS(pis);

		PISST pisST = d.getPisST();
		if (pisST == null) {
			pisST = new PISST();
		}
		exibirPISST(pisST);

		COFINS cofins = d.getCofins();
		if (cofins == null) {
			cofins = new COFINS();
		}
		exibirCOFINS(cofins);

		COFINSST cofinsST = d.getCofinsST();
		if (cofinsST == null) {
			cofinsST = new COFINSST();
		}
		exibirCOFINSST(cofinsST);
	}

	private void exibirICMS(ICMS icms) {
		cbOrigem.setValue(icms.getOrigem());
		cbSTICMS.setValue(icms.getCstICMS());

		tfPercICMSAplicSN.setText(ConversorBigDecimal.paraString(icms.getpCredSN()));
		cbTpBcICMS.setValue(icms.getModBCICMS());
		tfPercRedICMS.setText(ConversorBigDecimal.paraString(icms.getpRedBCICMS(), 2));
		tfvCredIcmsSN.setText(ConversorBigDecimal.paraString(icms.getvCredICMSSN(), 2));
		tfBcICMS.setText(ConversorBigDecimal.paraString(icms.getvBCICMS(), 2));
		tfAliqICMS.setText(ConversorBigDecimal.paraString(icms.getpICMS(), 2));
		tfvICMS.setText(ConversorBigDecimal.paraString(icms.getvICMS(), 2));
		cbMotDesICMS.setValue(icms.getMotDesICMS());
		tfvICMSDesonerado.setText(ConversorBigDecimal.paraString(icms.getvICMSDeson(), 2));

		cbTpBcICMSST.setValue(icms.getModBCST());
		tfPercRedICMSST.setText(ConversorBigDecimal.paraString(icms.getpRedBCST()));
		tfPercAdicICMSST.setText(ConversorBigDecimal.paraString(icms.getpMVAST()));
		tfbcICMSST.setText(ConversorBigDecimal.paraString(icms.getvBCST(),2));
		tfAliqICMSST.setText(ConversorBigDecimal.paraString(icms.getpICMSST()));
		tfPercBCPropiaICMSST.setText(ConversorBigDecimal.paraString(icms.getpBCOp()));
		cbUFICMSST.setValue(icms.getUFST());
		tfvICMSST.setText(ConversorBigDecimal.paraString(icms.getvICMSST(), 2));
	}

	private void exibirIPI(IPI ipi) {
		
		cbSTIPI.setValue(ipi.getCstIPI());
		tfClEnq.setText(ipi.getClEnq());
		tfCodEnq.setText(ipi.getcEnq());
		tfCNPJProd.setText(ipi.getCNPJProd());
		tfCodSelo.setText(ipi.getcSelo());
		tfQtdSelo.setText(ConversorBigDecimal.paraString(ipi.getqSelo()));
		cbTipoCalcIPI.setValue(ipi.getTpCalcIPI());
		tfvBcIPI.setText(ConversorBigDecimal.paraString(ipi.getvBCIPI(), 2));
		tfAliqIPI.setText(ConversorBigDecimal.paraString(ipi.getpIPI()));
		tfTotUniIPI.setText(ConversorBigDecimal.paraString(ipi.getqUnid()));
		tfValUnIPI.setText(ConversorBigDecimal.paraString(ipi.getvUnid(), 2));
		tfvIPI.setText(ConversorBigDecimal.paraString(ipi.getvIPI(), 2));
	}

	private void exibirPIS(PIS pis) {
		
		cbSTPIS.setValue(pis.getCstPIS());
		cbTipoCalcPIS.setValue(pis.getTpCalcPis());
		tfbcPIS.setText(ConversorBigDecimal.paraString(pis.getvBCPIS(), 2));
		tfAliqPIS.setText(ConversorBigDecimal.paraString(pis.getpPIS()));
		tfValUnPIS.setText(ConversorBigDecimal.paraString(pis.getvAliqProdPIS(), 2));
		tfQtdVendPIS.setText(ConversorBigDecimal.paraString(pis.getqBCProdPIS()));
		tfvPIS.setText(ConversorBigDecimal.paraString(pis.getvPIS(), 2));
	}

	private void exibirPISST(PISST pisST) {
		
		cbTipoCalcPISST.setValue(pisST.getTpCalcPISST());
		tfbcPISST.setText(ConversorBigDecimal.paraString(pisST.getvBCPISST(), 2));
		tfAliqPISST.setText(ConversorBigDecimal.paraString(pisST.getpPISST()));
		tfValUnPISST
				.setText(ConversorBigDecimal.paraString(pisST.getvAliqProdPISST(), 2));
		tfQtdVendPISST.setText(ConversorBigDecimal.paraString(pisST.getqBCProdPISST()));
		tfvPISST.setText(ConversorBigDecimal.paraString(pisST.getpPISST(), 2));
	}

	private void exibirCOFINS(COFINS confins) {
		
		cbSTCOFINS.setValue(confins.getCstCOFINS());
		cbTipoCalcCOFINS.setValue(confins.getTpCalcCofins());
		tfbcCOFINS.setText(ConversorBigDecimal.paraString(confins.getvBCCOFINS(), 2));
		tfAliqCOFINS.setText(ConversorBigDecimal.paraString(confins.getpCOFINS()));
		tfValUnCOFINS.setText(ConversorBigDecimal.paraString(
				confins.getvAliqProdCOFINS(), 2));
		tfQtdVendCOFINS
				.setText(ConversorBigDecimal.paraString(confins.getqBCProdCOFINS()));
		tfvCOFINS.setText(ConversorBigDecimal.paraString(confins.getvCOFINS(), 2));
	}

	private void exibirCOFINSST(COFINSST confisST) {
		
		cbTipoCalcCOFINSST.setValue(confisST.getTpCalcCOFINSST());
		tfbcCOFINSST
				.setText(ConversorBigDecimal.paraString(confisST.getvBCCOFINSST(), 2));
		tfAliqCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getpCOFINSST()));
		tfValUnCOFINSST.setText(ConversorBigDecimal.paraString(
				confisST.getvAliqProdCOFINSST(), 2));
		tfQtdVendCOFINSST.setText(ConversorBigDecimal.paraString(confisST
				.getqBCProdCOFINSST()));
		tfvCOFINSST.setText(ConversorBigDecimal.paraString(confisST.getvCOFINSST(), 2));
	}
}
