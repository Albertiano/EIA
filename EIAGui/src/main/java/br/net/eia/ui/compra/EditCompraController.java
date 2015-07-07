package br.net.eia.ui.compra;

import java.io.File;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;

import br.net.eia.compra.Compra;
import br.net.eia.compra.ItemCompra;
import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.enums.StatusOperacao;
import br.net.eia.enums.UF;
import br.net.eia.nfe.v310.TNFe.InfNFe;
import br.net.eia.nfe.v310.TNfeProc;
import br.net.eia.notafiscal.transporte.ModFrete;
import br.net.eia.produto.FornecedorProduto;
import br.net.eia.produto.Produto;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.contato.ContatoManager;
import br.net.eia.ui.contato.EditContatoController;
import br.net.eia.ui.contato.RestContatoManager;
import br.net.eia.ui.produto.EditProdutoController;
import br.net.eia.ui.produto.ProdutoPesquisaController;
import br.net.eia.ui.produto.RestProdutoManager;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.ConversorDate;
import br.net.eia.util.Formatador;
import br.net.eia.xml.lerXML_JAXB;

@SuppressWarnings("restriction")
public class EditCompraController implements Initializable {

	@FXML
	private Button btSalvar;
	@FXML
	private TextField tfFornecedor;
	@FXML
	private TextField tfnPedido;
	@FXML
	private TextField tfnNotaFiscal;
	@FXML
	private TextField tfModeloNF;
	@FXML
	private TextField tfSerieNF;
	@FXML
	private TextField tfEmissao;
	@FXML
	private TextField tfEntrada;
	@FXML
	private TextField tfChaveNFe;
	@FXML
	private TextField tfbcICMS;
	@FXML
	private TextField tfvIcms;
	@FXML
	private TextField tfbcIcmsST;
	@FXML
	private TextField tfvIcmsST;
	@FXML
	private TextField tfvIpi;
	@FXML
	private TextField tfvfrete;
	@FXML
	private TextField tfvSeguro;
	@FXML
	private TextField tfvOutros;
	@FXML
	private TextField tfvPis;
	@FXML
	private TextField tfvConfins;
	@FXML
	private TextField tfvProdutos;
	@FXML
	private TextField tfvDesconto;
	@FXML
	private TextField tfvCompra;
	@FXML
	private ComboBox<ModFrete> cbModFrete;
	@FXML
	private TextField tfTransportador;
	@FXML
	private TextField tfPlaca;
	@FXML
	private ComboBox<UF> cbUf;
	@FXML
	private TextField tfRntc;
	@FXML
	private Label lbStatus;
	
	private ObservableList<ItemCompra> produtoData = FXCollections
			.observableArrayList();
	@FXML
	private TableView<ItemCompra> clienteTable;
	@FXML
	private TableColumn<ItemCompra, String> codigoNameColumn;
	@FXML
	private TableColumn<ItemCompra, String> descricaNameColumn;
	@FXML
	private TableColumn<ItemCompra, String> ncmNameColumn;
	@FXML
	private TableColumn<ItemCompra, BigDecimal> quantidadeNameColumn;
	@FXML
	private TableColumn<ItemCompra, Unidade> UnidNameColumn;
	@FXML
	private TableColumn<ItemCompra, BigDecimal> precoNameColumn;
	@FXML
	private TableColumn<ItemCompra, BigDecimal> subtotalNameColumn;
	@FXML
	private TableColumn<ItemCompra, BigDecimal> bcIcmsNameColumn;
	@FXML
	private TableColumn<ItemCompra, BigDecimal> valorIcmsNameColumn;
	@FXML
	private TableColumn<ItemCompra, BigDecimal> valorIpiNameColumn;
	
	private Contato fornecedor;
	private Contato transportador;

	private Stage dialogStage;
	private Compra compra = new Compra();

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

	public void setProduto(Compra produto) {
		this.compra = produto;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbUf.getItems().clear();
		cbUf.getItems().addAll(UF.values());
		cbUf.setPromptText("-- Selecione --");
		
		cbModFrete.getItems().clear();
		cbModFrete.getItems().addAll(ModFrete.values());
		cbModFrete.setPromptText("-- Selecione --");
		
		tfEmissao.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataData(tfEmissao);
				}
			}
		});
		
		tfEntrada.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataData(tfEntrada);
				}
			}
		});
		
		codigoNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, String>(
				"codigo"));
		descricaNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, String>(
				"descricao"));
		ncmNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, String>(
				"ncm"));
		quantidadeNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, BigDecimal>(
				"quantidade"));
		UnidNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, Unidade>(
				"unidade"));
		precoNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, BigDecimal>(
				"precoVenda"));
		subtotalNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, BigDecimal>(
				"subtotal"));
		bcIcmsNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, BigDecimal>(
				"bcIcms"));
		valorIcmsNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, BigDecimal>(
				"vIcms"));
		valorIpiNameColumn
		.setCellValueFactory(new PropertyValueFactory<ItemCompra, BigDecimal>(
				"vIpi"));
		
		quantidadeNameColumn.setCellFactory(new Callback<TableColumn<ItemCompra, BigDecimal>, TableCell<ItemCompra, BigDecimal>>() {
			@Override
			public TableCell<ItemCompra, BigDecimal> call(
					TableColumn<ItemCompra, BigDecimal> param) {
				TableCell<ItemCompra, BigDecimal> cell = new TableCell<ItemCompra, BigDecimal>() {
					@Override
					public void updateItem(BigDecimal item,
							boolean empty) {
						if (item != null) {
							super.setText(ConversorBigDecimal.paraString(item,2));

						}
					}
				};
				return cell;
			}
		});
		
		precoNameColumn.setCellFactory(new Callback<TableColumn<ItemCompra, BigDecimal>, TableCell<ItemCompra, BigDecimal>>() {
			@Override
			public TableCell<ItemCompra, BigDecimal> call(
					TableColumn<ItemCompra, BigDecimal> param) {
				TableCell<ItemCompra, BigDecimal> cell = new TableCell<ItemCompra, BigDecimal>() {
					@Override
					public void updateItem(BigDecimal item,
							boolean empty) {
						if (item != null) {
							super.setText(ConversorBigDecimal.paraString(item,2));

						}
					}
				};
				return cell;
			}
		});
		subtotalNameColumn.setCellFactory(new Callback<TableColumn<ItemCompra, BigDecimal>, TableCell<ItemCompra, BigDecimal>>() {
			@Override
			public TableCell<ItemCompra, BigDecimal> call(
					TableColumn<ItemCompra, BigDecimal> param) {
				TableCell<ItemCompra, BigDecimal> cell = new TableCell<ItemCompra, BigDecimal>() {
					@Override
					public void updateItem(BigDecimal item,
							boolean empty) {
						if (item != null) {
							super.setText(ConversorBigDecimal.paraString(item,2));

						}
					}
				};
				return cell;
			}
		});
		bcIcmsNameColumn.setCellFactory(new Callback<TableColumn<ItemCompra, BigDecimal>, TableCell<ItemCompra, BigDecimal>>() {
			@Override
			public TableCell<ItemCompra, BigDecimal> call(
					TableColumn<ItemCompra, BigDecimal> param) {
				TableCell<ItemCompra, BigDecimal> cell = new TableCell<ItemCompra, BigDecimal>() {
					@Override
					public void updateItem(BigDecimal item,
							boolean empty) {
						if (item != null) {
							super.setText(ConversorBigDecimal.paraString(item,2));

						}
					}
				};
				return cell;
			}
		});
		valorIcmsNameColumn.setCellFactory(new Callback<TableColumn<ItemCompra, BigDecimal>, TableCell<ItemCompra, BigDecimal>>() {
			@Override
			public TableCell<ItemCompra, BigDecimal> call(
					TableColumn<ItemCompra, BigDecimal> param) {
				TableCell<ItemCompra, BigDecimal> cell = new TableCell<ItemCompra, BigDecimal>() {
					@Override
					public void updateItem(BigDecimal item,
							boolean empty) {
						if (item != null) {
							
							super.setText(ConversorBigDecimal.paraString(item,2));

						}
					}
				};
				return cell;
			}
		});
		valorIpiNameColumn.setCellFactory(new Callback<TableColumn<ItemCompra, BigDecimal>, TableCell<ItemCompra, BigDecimal>>() {
			@Override
			public TableCell<ItemCompra, BigDecimal> call(
					TableColumn<ItemCompra, BigDecimal> param) {
				TableCell<ItemCompra, BigDecimal> cell = new TableCell<ItemCompra, BigDecimal>() {
					@Override
					public void updateItem(BigDecimal item,
							boolean empty) {
						if (item != null) {
							
							super.setText(ConversorBigDecimal.paraString(item,2));

						}
					}
				};
				return cell;
			}
		});
			
	}
	
	private void formataData(TextField tf) {
		try {
			
			String formatado = new Formatador().formatterDate(tf.getText());
			tf.setText(formatado);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}
	}
	
	
	private boolean isInputValid() {
		String errorMessage = "";

		if (tfFornecedor.getText() == null || tfFornecedor.getText().length() == 0) {
			errorMessage += "Fornecedor inválido!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.masthead(errorMessage)
			.message(
					"Por Favor Corrija os Campos Inválidos")
			.showError();
			return false;
		}
	}
	
    
	@FXML
	public void handleBtSalvar(ActionEvent event) {
		if (isInputValid()) {
			
			compra.setFornecedor(fornecedor);
			compra.setPedido(tfnPedido.getText());
			compra.setNotaFiscal(tfnNotaFiscal.getText());
			compra.setModeloNF(tfModeloNF.getText());
			compra.setSerieNF(tfSerieNF.getText());
			compra.setEmissao(ConversorDate.gerarData(tfEmissao.getText()));
			compra.setEntrada(ConversorDate.gerarData(tfEntrada.getText()));
			compra.setChaveNFe(tfChaveNFe.getText());
			compra.setBcICMS(ConversorBigDecimal.paraBigDecimal(tfbcICMS.getText()));
			compra.setIcms(ConversorBigDecimal.paraBigDecimal(tfvIcms.getText()));
			compra.setBcIcmsST(ConversorBigDecimal.paraBigDecimal(tfbcIcmsST.getText()));
			compra.setIcmsST(ConversorBigDecimal.paraBigDecimal(tfvIcmsST.getText()));
			compra.setIpi(ConversorBigDecimal.paraBigDecimal(tfvIpi.getText()));
			compra.setFrete(ConversorBigDecimal.paraBigDecimal(tfvfrete.getText()));
			compra.setSeguro(ConversorBigDecimal.paraBigDecimal(tfvSeguro.getText()));
			compra.setOutros(ConversorBigDecimal.paraBigDecimal(tfvOutros.getText()));
			compra.setPis(ConversorBigDecimal.paraBigDecimal(tfvPis.getText()));
			compra.setConfins(ConversorBigDecimal.paraBigDecimal(tfvConfins.getText()));
			compra.setProdutos(ConversorBigDecimal.paraBigDecimal(tfvProdutos.getText()));
			compra.setDesconto(ConversorBigDecimal.paraBigDecimal(tfvDesconto.getText()));
			compra.setCompra(ConversorBigDecimal.paraBigDecimal(tfvCompra.getText()));
			compra.setModFrete(cbModFrete.getValue());
			compra.setTransportador(transportador);
			compra.setPlaca(tfPlaca.getText());
			compra.setUf(cbUf.getValue());
			compra.setRntc(tfRntc.getText());
			
			compra.setItemCompra(produtoData);
			if(compra.getStatus().equals(StatusOperacao.EM_ABERTO)){    		
    		Action response = Dialogs
					.create()
					.owner(dialogStage)
					.title("Operação em aberto")
					.message(
							"Deseja Finalizar e fazer laçamentos no estoque?")
					.actions(Actions.YES, Actions.NO)
					.showConfirm();
    		if(response.equals(Actions.YES)){
    			compra.setStatus(StatusOperacao.FINALIZADO);
    			compra = new RestCompraManager(mainApp).atualizarEntrada(compra);  			
    		}
    		
			}
			okClicked = true;
			dialogStage.close();
		}
	}
	
	

	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}
	
	public void exibir(){
		if(compra.getStatus().equals(StatusOperacao.FINALIZADO)){
			btSalvar.setDisable(false);
		}
		
		lbStatus.setText(compra.getStatus().getValue());
		if(compra.getFornecedor()!=null){
			fornecedor = compra.getFornecedor();
			tfFornecedor.setText(compra.getFornecedor().getNome());
		}		
		tfnPedido.setText(compra.getPedido());
		tfnNotaFiscal.setText(compra.getNotaFiscal());
		tfModeloNF.setText(compra.getModeloNF());
		tfSerieNF.setText(compra.getSerieNF());
		tfEmissao.setText(ConversorDate.retornaData(compra.getEmissao()));
		tfEntrada.setText(ConversorDate.retornaData(compra.getEntrada()));
		tfChaveNFe.setText(compra.getChaveNFe());
		tfbcICMS.setText(ConversorBigDecimal.paraString(compra.getBcICMS(),2));
		tfvIcms.setText(ConversorBigDecimal.paraString(compra.getIcms(),2));
		tfbcIcmsST.setText(ConversorBigDecimal.paraString(compra.getBcIcmsST(),2));
		tfvIcmsST.setText(ConversorBigDecimal.paraString(compra.getIcmsST(),2));
		tfvIpi.setText(ConversorBigDecimal.paraString(compra.getIpi(),2));
		tfvfrete.setText(ConversorBigDecimal.paraString(compra.getFrete(),2));
		tfvSeguro.setText(ConversorBigDecimal.paraString(compra.getSeguro(),2));
		tfvOutros.setText(ConversorBigDecimal.paraString(compra.getOutros(),2));
		tfvPis.setText(ConversorBigDecimal.paraString(compra.getPis(),2));
		tfvConfins.setText(ConversorBigDecimal.paraString(compra.getConfins(),2));
		tfvProdutos.setText(ConversorBigDecimal.paraString(compra.getProdutos(),2));
		tfvDesconto.setText(ConversorBigDecimal.paraString(compra.getDesconto(),2));
		tfvCompra.setText(ConversorBigDecimal.paraString(compra.getCompra(),2));
		cbModFrete.setValue(compra.getModFrete());
		if(compra.getTransportador()!=null){
			fornecedor = compra.getFornecedor();
			tfTransportador.setText(compra.getTransportador().getNome());
			transportador = compra.getTransportador();
		}		
		tfPlaca.setText(compra.getPlaca());
		cbUf.setValue(compra.getUf());
		tfRntc.setText(compra.getRntc());
		
		List<ItemCompra> itens = compra.getItemCompra();
        if(itens!=null){
        	produtoData = FXCollections.observableArrayList(itens);
        	refreshPersonTable();
        }
		
		if(compra.getStatus()==null){
			compra.setStatus(StatusOperacao.EM_ABERTO);
		}
		lbStatus.setText(compra.getStatus().getValue());
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
	private void handleNew() {
		ItemCompra tempPerson = new ItemCompra();
		tempPerson.setProduto(new Produto());
		boolean okClicked = showEditDialog(tempPerson);
		if(compra.getItemCompra()==null){
			compra.setItemCompra(new ArrayList<ItemCompra>());
		}
		if (okClicked) {
			produtoData.add(tempPerson);
			compra.getItemCompra().add(tempPerson);
			refreshPersonTable();
		}
		
		atualizaValores();
	}
	
	private void atualizaValores(){
		
		BigDecimal vTotal=BigDecimal.ZERO;
		BigDecimal vProduto=BigDecimal.ZERO;
		BigDecimal vDesc=BigDecimal.ZERO;
		BigDecimal vBcICMS=BigDecimal.ZERO;
		BigDecimal vIcms=BigDecimal.ZERO;
		BigDecimal vBcIcmsST=BigDecimal.ZERO;
		BigDecimal vIcmsST=BigDecimal.ZERO;
		BigDecimal vIpi=BigDecimal.ZERO;
		BigDecimal vPis=BigDecimal.ZERO;
		BigDecimal vCofins=BigDecimal.ZERO;
		BigDecimal vFrete=BigDecimal.ZERO;
		BigDecimal vSeg=BigDecimal.ZERO;
		BigDecimal vOut = BigDecimal.ZERO;

		for (ItemCompra i : compra.getItemCompra()) {	
			if(i.getSubtotal()!=null){
				vProduto = vProduto.add(i.getSubtotal());
				tfvProdutos.setText(ConversorBigDecimal.paraString(vProduto,2));
			}
			if (i.getBcIcms() != null) {
				vBcICMS = vBcICMS.add(i.getBcIcms());
				tfbcICMS.setText(ConversorBigDecimal.paraString(vBcICMS,2));
			}
			if ( i.getVIcms()!= null) {
				vIcms = vIcms.add(i.getVIcms());
				tfvIcms.setText(ConversorBigDecimal.paraString(vIcms,2));
			}
			if (i.getVIpi() != null) {
				vIpi = vIpi.add(i.getVIpi());
				tfvIpi.setText(ConversorBigDecimal.paraString(vIpi,2));
			}
			
			
			if (i.getDetFiscal() != null) {
				vDesc = vDesc.add(i.getDetFiscal().getTotalDesconto());
				tfvDesconto.setText(ConversorBigDecimal.paraString(vDesc,2));

				if (i.getDetFiscal().getIcms().getvBCST() != null) {
					vBcIcmsST = vBcIcmsST
							.add(i.getDetFiscal().getIcms().getvBCST());
					tfbcIcmsST.setText(ConversorBigDecimal.paraString(vBcIcmsST,2));
				}
				if (i.getDetFiscal().getIcms().getvICMSST() != null) {
					vIcmsST = vIcmsST.add(i.getDetFiscal().getIcms().getvICMSST());
					tfvIcmsST.setText(ConversorBigDecimal.paraString(vIcmsST,2));
				}
				if (i.getDetFiscal().getPis().getvPIS() != null) {
					vPis = vPis.add(i.getDetFiscal().getPis().getvPIS());
					tfvPis.setText(ConversorBigDecimal.paraString(vPis,2));
				}
				if (i.getDetFiscal().getCofins().getvCOFINS() != null) {
					vCofins = vCofins
							.add(i.getDetFiscal().getCofins().getvCOFINS());
					tfvConfins.setText(ConversorBigDecimal.paraString(vCofins,2));
				}
				if (i.getDetFiscal().getTotalFrete() != null) {
					vFrete = vFrete.add(i.getDetFiscal().getTotalFrete());
					tfvfrete.setText(ConversorBigDecimal.paraString(vFrete,2));
				}
				if (i.getDetFiscal().getTotalSeguro() != null) {
					vSeg = vSeg.add(i.getDetFiscal().getTotalSeguro());
					tfvSeguro.setText(ConversorBigDecimal.paraString(vSeg,2));
				}
				if (i.getDetFiscal().getOutrasDespesas() != null) {
					vOut = vOut.add(i.getDetFiscal().getOutrasDespesas());
					tfvOutros.setText(ConversorBigDecimal.paraString(vOut,2));
				}
			}

		}
		vTotal = vTotal.add(vProduto).subtract(vDesc).add(vIcmsST).add(vIpi)
				.add(vFrete).add(vSeg).add(vOut);
		tfvCompra.setText(ConversorBigDecimal.paraString(vTotal,2));
	}
	
	@FXML
	private void handleEdit() {
		ItemCompra selectedPerson = clienteTable.getSelectionModel()
				.getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = showEditDialog(selectedPerson);
			if (okClicked) {	
				atualizaValores();
				refreshPersonTable();
			}

		} else {
			Dialogs.create()
			.title("Aviso")
			.masthead("Nenhum registro selecionado")
			.message(
					"Nenhum registro selecionado")
			.showWarning();
		}
	}

	@FXML
	private void handleDelete() {
		int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {		
				clienteTable.getItems().remove(selectedIndex);
				compra.getItemCompra().remove(selectedIndex);
				refreshPersonTable();
				atualizaValores();
			
		} else {
			Dialogs.create()
			.title("Aviso")
			.message(
					"Nenhum registro selecionado")
			.showWarning();
		}
	}
	
	public boolean showEditDialog(ItemCompra person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/compra/EditItemCompra.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Inclusão de Itens");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller
			EditItemCompraController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(mainApp);
			controller.setItem(person);
			controller.exibir();

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return false;
		}
	}
	
	@FXML
	private void handlePesquisaFornecedor() {
		ContatoManager cM = new ContatoManager();    	
    	boolean okClicked = cM.showPesquisaContatoDialog(mainApp, TpContato.FORNECEDOR, dialogStage);
    	fornecedor = cM.getContato();
		if (fornecedor != null) {			
			if (okClicked) {
				tfFornecedor.setText(fornecedor.getNome());
			}

		} else {
			Dialogs.create()
			.title("Aviso")
			.message(
					"Nenhum registro selecionado")
			.showWarning();
		}
	}
	
	@FXML
	private void handlePesquisaTransportador() {
		ContatoManager cM = new ContatoManager();    	
    	boolean okClicked = cM.showPesquisaContatoDialog(mainApp, TpContato.TRANSPORTADOR, dialogStage);
    	transportador = cM.getContato();
		if (transportador != null) {			
			if (okClicked) {
				tfTransportador.setText(transportador.getNome());
				//tfPlaca.setText(transportador.getPlaca());
				//tfRntc.setText(transportador.getRntc());
				//cbUf.setValue(transportador.getUf());
			}

		} else {
			Dialogs.create()
			.title("Aviso")
			.message(
					"Nenhum registro selecionado")
			.showWarning();
		}
	}

	@FXML
	private void handleOpenXML(){
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(dialogStage);
        if (file != null) {
            TNfeProc nfe = new lerXML_JAXB().getTNfeProc(file.getAbsolutePath());   
           
            infNFe(nfe.getNFe().getInfNFe());
            
            try{
            	fornecedor = new RestContatoManager().pesquisaNumDoc(mainApp.getEmitente().getToken().toString(),nfe.getNFe().getInfNFe().getEmit().getCNPJ());
            	tfFornecedor.setText(fornecedor.getNome());
			} catch (Exception e) {
				Action response = Dialogs.create()
						.owner(dialogStage)
						.title("Exclusão em andamento.")
						.masthead("Fornecedor Não Encontrado")
						.message("Deseja Fazer o Cadastramento?")
						.actions(Actions.YES, Actions.NO).showConfirm();
      		if(response.equals(Actions.YES)){
      			Contato client = new ConverterNfeCompra().forFornecedor(nfe.getNFe().getInfNFe().getEmit());
					boolean okClicked = showEditFornecedorDialog(client);
					if (okClicked) {
						RestContatoManager cM = new RestContatoManager();
						fornecedor = cM.inserir(client);
						tfFornecedor.setText(fornecedor.getNome());
						Dialogs.create()
								.owner(dialogStage)
								.title("Aviso")
								.masthead(
										client.getNome() + "\n"
												+ client.getMunicipio())
								.message("Fornecedor Inserido com sucesso.")
								.showInformation();
					}

				}
			}
            
            compra.setItemCompra(new ArrayList<ItemCompra>());

			for (int i = 0; i < nfe.getNFe().getInfNFe().getDet().size(); i++) {
			
				Produto produto = null;
				try {

					produto = new RestProdutoManager().pesquisar(
							fornecedor.getId(), nfe.getNFe().getInfNFe()
									.getDet().get(i).getProd().getCProd());
					
				} catch (Exception e) {					
					Action response = Dialogs.create()
							.owner(dialogStage)
							.title("Exclusão em andamento.")
							.masthead("Produto Não Encontrado.")
							.message("Deseja Fazer o Cadastramento?")
							.actions(Actions.YES, Actions.NO)
							.showConfirm();					
					if (response.equals(Actions.YES)) {
						Produto tempPerson = new ConverterNfeCompra()
								.forProduto(((nfe.getNFe().getInfNFe().getDet()
										.get(i))));
						FornecedorProduto f = new FornecedorProduto();
						f.setFornecedor(fornecedor);
						f.setCodFornecedor(nfe.getNFe().getInfNFe().getDet()
								.get(i).getProd().getCProd());
						f.setFatorConversao(BigDecimal.ONE);
						List<FornecedorProduto> forns = new ArrayList<FornecedorProduto>();
						forns.add(f);
						tempPerson.setFornecedores(forns);
												
						produto = showPesquisaProdutoDialog(tempPerson);
						
						}
					}
				if(produto!=null){
					FornecedorProduto f = new FornecedorProduto();
					f.setFatorConversao(BigDecimal.ONE);
					for(FornecedorProduto fTemp : produto.getFornecedores()){
						if(fTemp.getFornecedor().equals(fornecedor)){
							f=fTemp;
						}
					}
					ItemCompra tempPerson = new ConverterNfeCompra()
					.forItem(((nfe.getNFe().getInfNFe().getDet()
							.get(i))),produto, f);
						if(compra.getItemCompra()==null){
							compra.setItemCompra(new ArrayList<ItemCompra>());
						}
						compra.getItemCompra().add(tempPerson);
						
				}
			}
			produtoData.setAll(compra.getItemCompra());
			refreshPersonTable();
			atualizaValores();

		}
	}
	
	private void infNFe(InfNFe nfe){
		tfnNotaFiscal.setText(nfe.getIde().getNNF());
		tfModeloNF.setText(nfe.getIde().getMod());
		tfSerieNF.setText(nfe.getIde().getSerie());
		//tfEmissao.setText(ConversorDate.retornaData(nfe.getIde().getDhEmi())));
		//tfEntrada.setText(ConversorBigDecimal.paraStringDate(ConversorBigDecimal.paraDateXML(nfe.getIde().getDhSaiEnt())));
		tfChaveNFe.setText(nfe.getId());
		tfbcICMS.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVBC(),2));
		tfvIcms.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVICMS(),2));
		tfbcIcmsST.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVBCST(),2));
		tfvIcmsST.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVST(),2));
		tfvIpi.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVIPI(),2));
		tfvfrete.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVFrete(),2));
		tfvSeguro.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVSeg(),2));
		tfvOutros.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVOutro(),2));
		tfvPis.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVPIS(),2));
		tfvConfins.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVCOFINS(),2));
		tfvProdutos.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVProd(),2));
		tfvDesconto.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVDesc(),2));
		tfvCompra.setText(ConversorBigDecimal.paraStringXML(nfe.getTotal().getICMSTot().getVNF(),2));
		
		//cbModFrete.setValue(nfe.getTransp().);
		if(compra.getTransportador()!=null){
			fornecedor = compra.getFornecedor();
			tfTransportador.setText(compra.getTransportador().getNome());
			transportador = compra.getTransportador();
		}		
		//tfPlaca.setText(nfe.getTransp().);
		//cbUf.setValue(compra.getUf());
		//tfRntc.setText(compra.getRntc());
	}
	
	
	
	
	public boolean showEditFornecedorDialog(Contato person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/fornecedor/EditFornecedor.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastro de Fornecedor");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller
			EditContatoController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setContato(person);
			controller.exibirContato(person);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return false;
		}
	}
	
	public boolean showProdutoEditDialog(Produto person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/produto/EditProduto.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastro de Produto");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(dialogStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller
			EditProdutoController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProduto(person);
			controller.setMainApp(mainApp);
			controller.exibirProduto();

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return false;
		}
	}
	
	public Produto showPesquisaProdutoDialog(Produto person) {
		Produto produto;
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
			controller.setTempXml(person);
			controller.exibir();
			controller.handleFiltrar();

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			produto =  controller.getProduto();
			return produto;

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			return null;
		}
	}
}
