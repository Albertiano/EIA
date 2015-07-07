package br.net.eia.ui.produto;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

import br.net.eia.contato.Contato;
import br.net.eia.produto.FornecedorProduto;
import br.net.eia.produto.Produto;
import br.net.eia.produto.imposto.Tributacao;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.produto.imposto.RestTributacaoManager;
import br.net.eia.ui.produto.unidade.RestUnidadeManager;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;

@SuppressWarnings("restriction")
public class EditProdutoController implements Initializable {

	@FXML
	private TextField tfCodigo;
	@FXML
	private TextField tfReferencia;
	@FXML
	private TextField tfDescricao;
	@FXML
	private ComboBox<Unidade> cbUnidade;
	@FXML
	private CheckBox ckDesativado;
	@FXML
	private ComboBox<Tributacao> cbTrib;

	@FXML
	private TextField tfNCM;
	@FXML
	private TextField tfEstMin;
	@FXML
	private TextField tfEstAtual;
	@FXML
	private TextField tfPesoBruto;
	@FXML
	private TextField tfPesoLiq;
	@FXML
	private TextField tfPrecoCusto;
	@FXML
	private TextField tfMLucro;
	@FXML
	private TextField tfPrecoVenda;
	@FXML
	private TextField tfDescMax;
	@FXML
	private TextField tfLocalEstoque;
	
	@FXML
	private TextField tfExtipi;
	@FXML
	private TextField tfGenero;
	@FXML
	private TextField tfcEan;
	@FXML
	private TextField tfcEanTrib;
	@FXML
	private TextField tfVuntrib;
	@FXML
	private ComboBox<Unidade> cbUtrib;

	@FXML
	Button btSalvar;
	@FXML
	Button btCancelar;

	private Stage dialogStage;
	private Produto produto = new Produto();

	private boolean okClicked = false;

	// Reference to the main application
	private MainApp mainApp;

	private ObservableList<FornecedorProduto> produtoData = FXCollections
			.observableArrayList();
	@FXML
	private TableView<FornecedorProduto> clienteTable;
	@FXML
	private TableColumn<FornecedorProduto, Contato> nomeFornecedorNameColumn;
	@FXML
	private TableColumn<FornecedorProduto, String> codFornecedorNameColumn;
	@FXML
	private TableColumn<FornecedorProduto, Unidade> unidFornecedorNameColumn;
	@FXML
	private TableColumn<FornecedorProduto, BigDecimal> fatorConversaoNameColumn;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
		
		cbUnidade.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Unidade>() {
		    @Override
		    public void changed(ObservableValue<? extends Unidade> observable, 
		                                    Unidade oldValue, Unidade newValue) {
		        cbUtrib.setValue(newValue);
		    }
		});

		nomeFornecedorNameColumn
				.setCellValueFactory(new PropertyValueFactory<FornecedorProduto, Contato>(
						"fornecedor"));
		codFornecedorNameColumn
				.setCellValueFactory(new PropertyValueFactory<FornecedorProduto, String>(
						"codFornecedor"));
		unidFornecedorNameColumn
				.setCellValueFactory(new PropertyValueFactory<FornecedorProduto, Unidade>(
						"unidFornecedor"));
		fatorConversaoNameColumn
				.setCellValueFactory(new PropertyValueFactory<FornecedorProduto, BigDecimal>(
						"fatorConversao"));

		// Auto resize columns
		clienteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	@FXML
	private void handleNew() {
		FornecedorProduto tempPerson = new FornecedorProduto();
		boolean okClicked2 = showEditDialog(tempPerson);
		if (okClicked2) {
			Dialogs.create().owner(dialogStage).title("Aviso")
					.masthead(tempPerson.getFornecedor().getNome())
					.message("Inserido com sucesso.").showInformation();
			produtoData.add(tempPerson);
			if (produto.getFornecedores() == null) {
				produto.setFornecedores(new ArrayList<FornecedorProduto>());
			}
			produto.getFornecedores().add(tempPerson);
			refreshPersonTable();
		}
	}

	public boolean showEditDialog(FornecedorProduto person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class
							.getResource("/fxml/produto/ProdutoFornecedor.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage2 = new Stage();
			dialogStage2.setTitle("Cadastro de Fornecedores");
			dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage2.initOwner(dialogStage);
			Scene scene = new Scene(page);
			dialogStage2.setScene(scene);

			// Set the person into the controller
			ProdutoFornecedorController controller = loader.getController();
			controller.setDialogStage(dialogStage2);
			controller.setMainApp(mainApp);
			controller.setProduto(person);
			controller.exibir();

			// Show the dialog and wait until the user closes it
			dialogStage2.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
			return false;
		}
	}

	@FXML
	private void handleEdit() {
		FornecedorProduto tempPerson = clienteTable.getSelectionModel()
				.getSelectedItem();
		if (tempPerson != null) {
			boolean okClicked = showEditDialog(tempPerson);
			if (okClicked) {
				Dialogs.create().owner(dialogStage).title("Aviso")
						.masthead(tempPerson.getFornecedor().getNome())
						.message("Alterado com sucesso.").showInformation();

				refreshPersonTable();
			}

		} else {
			Dialogs.create().owner(dialogStage).title("Aviso")
					.masthead("Item não selecionado")
					.message("Selecione um item na tabela.").showInformation();
		}
	}

	@FXML
	private void handleDelete() {
		int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			FornecedorProduto tempPerson = clienteTable.getSelectionModel()
					.getSelectedItem();

			Dialogs.create().owner(dialogStage).title("Aviso")
					.masthead(tempPerson.getFornecedor().getNome())
					.message("Removido com sucesso.").showInformation();

			clienteTable.getItems().remove(selectedIndex);
			produto.getFornecedores().remove(selectedIndex);
			refreshPersonTable();

		} else {
			Dialogs.create().owner(dialogStage).title("Aviso")
					.masthead("Item não selecionado")
					.message("Selecione um item na tabela.").showInformation();
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
			Dialogs.create().owner(dialogStage).title("Aviso")
					.masthead(errorMessage)
					.message("Por Favor Corrija os Campos Inválidos")
					.showInformation();
			return false;
		}
	}

	private void carregarCombos() {
		ObservableList<Unidade> paises = new RestUnidadeManager()
				.getAll(MainApp.getEmitente());
		cbUnidade.getItems().clear();
		cbUnidade.getItems().addAll(paises);
		cbUnidade.setPromptText("-- Selecione --");
		
		cbUtrib.getItems().clear();
		cbUtrib.getItems().addAll(paises);
		cbUtrib.setPromptText("-- Selecione --");

		try {
			ObservableList<Tributacao> tribs = new RestTributacaoManager()
					.getAll(MainApp.getEmitente());
			cbTrib.getItems().clear();
			cbTrib.getItems().addAll(tribs);
			cbTrib.setPromptText("-- Selecione --");
		} catch (Exception e) {
			Dialogs.create().owner(dialogStage).title("Aviso")
					.masthead("Sem Tributação cadastrada.")
					.message("Cadastre uma tributação antes.")
					.showInformation();
			cbTrib.getItems().clear();
			cbTrib.setPromptText("-- Cadastre --");
		}

	}

	private void carregarFormatacao() {

		tfEstMin.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfEstMin, 0);
				}
			}
		});

		tfEstAtual.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfEstAtual, 0);
				}
			}
		});

		tfPesoBruto.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfPesoBruto, 3);
						}
					}
				});

		tfPesoLiq.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPesoLiq, 3);
				}
			}
		});

		tfPrecoCusto.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfPrecoCusto, 2);
						}
					}
				});

		tfDescMax.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfDescMax, 0);
				}
			}
		});

		tfMLucro.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfMLucro, 0);
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
							tfVuntrib.setText(tfPrecoVenda.getText());
							formataCampo(tfVuntrib, 2);
						}
					}
				});
		
		tfVuntrib.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> source,
							Boolean wasFocused, Boolean isFocused) {
						if (wasFocused) {
							formataCampo(tfVuntrib, 2);
						}
					}
				});
		
		tfCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					if(tfCodigo.getText().length()==13){
						tfcEan.setText(tfCodigo.getText());
						tfcEanTrib.setText(tfCodigo.getText());
					}
				}
			}
		});

	}

	@FXML
	public void handleBtSalvar(ActionEvent event) {
		
		if (isInputValid()) {
			produto.setCodigo(tfCodigo.getText());
			produto.setReferencia(tfReferencia.getText());
			produto.setDescricao(tfDescricao.getText());
			produto.setUnidade(cbUnidade.getValue());
			produto.setDesativado(ckDesativado.isSelected());
			produto.setNcm(tfNCM.getText());
			produto.setPrecoCusto(ConversorBigDecimal.paraBigDecimal(tfPrecoCusto
					.getText()));
			produto.setmLucro(ConversorBigDecimal.paraBigDecimal(tfMLucro.getText()));
			produto.setPrecoVenda(ConversorBigDecimal.paraBigDecimal(tfPrecoVenda
					.getText()));
			produto.setDescMax(ConversorBigDecimal.paraBigDecimal(tfDescMax.getText()));
			produto.setPesoBruto(ConversorBigDecimal.paraBigDecimal(tfPesoBruto.getText()));
			produto.setPesoLiquido(ConversorBigDecimal.paraBigDecimal(tfPesoLiq.getText()));
			produto.setEstoqueMin(ConversorBigDecimal.paraBigDecimal(tfEstMin.getText()));
			produto.setEstoque(ConversorBigDecimal.paraBigDecimal(tfEstAtual.getText()));

			produto.setLocalizacao(tfLocalEstoque.getText());
			
			produto.setExtipi(tfExtipi.getText());
			produto.setGenero(tfGenero.getText());
			produto.setcEan(tfcEan.getText());
			produto.setcEanTrib(tfcEanTrib.getText());
			produto.setVuntrib(ConversorBigDecimal.paraBigDecimal(tfVuntrib.getText()));
			produto.setUtrib(cbUtrib.getValue());			

			produto.setTributacao(cbTrib.getValue());
			produto.setEmitente(MainApp.getEmitente());

			okClicked = true;

			dialogStage.close();
		}
	}

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}

	public void exibirProduto() {		
		tfCodigo.setText(produto.getCodigo());
		tfReferencia.setText(produto.getReferencia());
		tfDescricao.setText(produto.getDescricao());
		cbUnidade.setValue(produto.getUnidade());
		if(cbUnidade.getValue()==null){
			if(cbUnidade.getItems().size()>0){
				cbUnidade.setValue(cbUnidade.getItems().get(0));	
			}
		}
		
		
		ckDesativado.setSelected(produto.getDesativado());
		tfNCM.setText(produto.getNcm());
		tfPrecoCusto.setText(ConversorBigDecimal.paraString(produto.getPrecoCusto(), 2));
		tfMLucro.setText(ConversorBigDecimal.paraString(produto.getmLucro(), 2));
		tfPrecoVenda.setText(ConversorBigDecimal.paraString(produto.getPrecoVenda(), 2));
		tfDescMax.setText(ConversorBigDecimal.paraString(produto.getDescMax()));
		tfPesoBruto.setText(ConversorBigDecimal.paraString(produto.getPesoBruto(), 3));
		tfPesoLiq.setText(ConversorBigDecimal.paraString(produto.getPesoLiquido(), 3));
		tfEstMin.setText(ConversorBigDecimal.paraString(produto.getEstoqueMin()));
		tfEstAtual.setText(ConversorBigDecimal.paraString(produto.getEstoque()));

		tfLocalEstoque.setText(produto.getLocalizacao());
		
		tfExtipi.setText(produto.getExtipi());
		tfGenero.setText(produto.getGenero());
		tfcEan.setText(produto.getcEan());
		tfcEanTrib.setText(produto.getcEanTrib());
		tfVuntrib.setText(ConversorBigDecimal.paraString(produto.getVuntrib(), 2));
		cbUtrib.setValue(produto.getUtrib());		
		if(cbUtrib.getValue()==null){
			if(cbUtrib.getItems().size()>0){
				cbUtrib.setValue(cbUtrib.getItems().get(0));	
			}
		}
		
		if (produto.getTributacao() != null) {
			cbTrib.setValue(produto.getTributacao());
		}else{
			if(cbTrib.getItems().size()>0){
			   cbTrib.setValue(cbTrib.getItems().get(0));
			}
			
		}
		List<FornecedorProduto> fornecedores = produto.getFornecedores();
		if (fornecedores != null) {
			produtoData = FXCollections.observableArrayList(fornecedores);
			refreshPersonTable();
		}
	}

}
