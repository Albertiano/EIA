package br.net.eia.ui.notafiscal;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.enums.UF;
import br.net.eia.item.Item;
import br.net.eia.notafiscal.NotaFiscal;
import br.net.eia.notafiscal.adicionais.InfAdicionais;
import br.net.eia.notafiscal.cobranca.Cobranca;
import br.net.eia.notafiscal.cobranca.Duplicata;
import br.net.eia.notafiscal.cobranca.Fatura;
import br.net.eia.notafiscal.ide.FinNFe;
import br.net.eia.notafiscal.ide.IdDest;
import br.net.eia.notafiscal.ide.IndFinal;
import br.net.eia.notafiscal.ide.IndPag;
import br.net.eia.notafiscal.ide.IndPres;
import br.net.eia.notafiscal.ide.TpEmis;
import br.net.eia.notafiscal.ide.TpNF;
import br.net.eia.notafiscal.ide.ref.NFref;
import br.net.eia.notafiscal.item.ItemNotaFiscal;
import br.net.eia.notafiscal.pagamento.Pagamento;
import br.net.eia.notafiscal.pagamento.TpPag;
import br.net.eia.notafiscal.total.Total;
import br.net.eia.notafiscal.transporte.ModFrete;
import br.net.eia.notafiscal.transporte.Transporte;
import br.net.eia.notafiscal.transporte.Veiculo;
import br.net.eia.notafiscal.transporte.Volume;
import br.net.eia.produto.Produto;
import br.net.eia.produto.imposto.icms.ICMS;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.contato.ContatoManager;
import br.net.eia.ui.item.EditItemController;
import br.net.eia.ui.item.ItemManager;
import br.net.eia.ui.produto.ProdutoController;
import br.net.eia.ui.produto.RestProdutoManager;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.ConversorDate;
import br.net.eia.util.Formatador;
import br.net.eia.util.nfe.ChaveAcessoNFe;

@SuppressWarnings("restriction")
public class EditNotaFiscalController implements Initializable {
	/*
	 * IDE ***********************************************************************************************************
	 */
	@FXML
	private ComboBox<TpEmis> cbTpEmis;
	@FXML
	private ComboBox<FinNFe> cbFinNFe;
	@FXML
	private TextField serie;
	@FXML 
	private TextField nNF;	
	@FXML 
	private TextField status;
	@FXML//Entrada/Saída
	private ComboBox<TpNF> cbTpNF;
	@FXML 
	private TextField tfNatOp;
	@FXML//INTERNA/INTERESTADUALEXTERIOR
	private ComboBox<IdDest> cbIdDest;
	@FXML
	private ComboBox<IndFinal> cbIndFinal;
	@FXML//Indicador de presenca
	private ComboBox<IndPres> cbIdPres;
	@FXML 
	private TextField dhEmi;
	@FXML 
	private TextField dhSaiEnt;	
	
	private void iniciarIde(){
		cbTpEmis.getItems().clear();
		cbTpEmis.getItems().addAll(TpEmis.values());
		cbTpEmis.setPromptText("Selecione");
		
		cbFinNFe.getItems().clear();
		cbFinNFe.getItems().addAll(FinNFe.values());
		cbFinNFe.setPromptText("Selecione");
		
		cbTpNF.getItems().clear();
		cbTpNF.getItems().addAll(TpNF.values());
		cbTpNF.setPromptText("Selecione");
		
		cbIdDest.getItems().clear();
		cbIdDest.getItems().addAll(IdDest.values());
		cbIdDest.setPromptText("Selecione");
		
		cbIndFinal.getItems().clear();
		cbIndFinal.getItems().addAll(IndFinal.values());
		cbIndFinal.setPromptText("Selecione");
		
		cbIdPres.getItems().clear();
		cbIdPres.getItems().addAll(IndPres.values());
		cbIdPres.setPromptText("Selecione");
		
		dhEmi.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataData(dhEmi);
				}
			}
		});
		
		dhSaiEnt.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataData(dhSaiEnt);
				}
			}
		});
	}
	/*
	 * Dest ***********************************************************************************************************
	 */
	private Contato dest;
	@FXML 
	private TextField tfDest;
	@FXML 
	private TextField tfNumDoc;
	@FXML 
	private TextField tfMunicipio;
	@FXML 
	private TextField tfUF;
	
	@FXML
    private void handleSelecionarDestinatario(){
    	ContatoManager cM = new ContatoManager();    	
    	boolean okClicked = cM.showPesquisaContatoDialog(mainApp, TpContato.CLIENTE, dialogStage);
    	dest = cM.getContato();
    	if (dest != null) {			
			if (okClicked) {
				notaFiscal.setDest(dest);
				tfDest.setText(dest.getNome());
				tfNumDoc.setText(dest.getNumDoc());
				tfMunicipio.setText(dest.getMunicipio().getxMun());
				tfUF.setText(dest.getMunicipio().getUF().toString());
				if(!dest.getUf().equals(MainApp.getEmitente().getUf())){
					cbIdDest.setValue(IdDest.INTERESTADUAL);
				}
				
			}
		} else {
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.message(
					"Nenhum registro selecionado")
			.showWarning();
		}
    }
	
	private void exibirDest(Contato iDest) {
		if (iDest != null) {
			dest = iDest;
			tfDest.setText(iDest.getNome());
			tfNumDoc.setText(iDest.getNumDoc());
			tfMunicipio.setText(iDest.getMunicipio().getxMun());
			tfUF.setText(iDest.getMunicipio().getUF().toString());
		}
	}
	
	/* *
	 *   Itens *******************************************************************************************************
	 */
	
	@FXML
	private TextField tfCodigo;   
	@FXML
	private TextField tfDescricao;
	@FXML
	private TextField tfUn;
	@FXML
	private TextField tfQuantidade;
	@FXML
	private TextField tfPreco;
	@FXML
	private TextField tfSubtotal;
		
	private ObservableList<ItemNotaFiscal> itens = FXCollections
			.observableArrayList();
	@FXML
	private TableView<ItemNotaFiscal> table;	
	@FXML
	private TableColumn<ItemNotaFiscal, Item> nItemNameColumn;
	@FXML
	private TableColumn<ItemNotaFiscal, Item> descricaNameColumn;
	@FXML
	private TableColumn<ItemNotaFiscal, Item> ncmNameColumn;
	@FXML
	private TableColumn<ItemNotaFiscal, Item> cfopNameColumn;
	@FXML
	private TableColumn<ItemNotaFiscal, Item> quantidadeNameColumn;
	@FXML
	private TableColumn<ItemNotaFiscal, Item> unidNameColumn;
	@FXML
	private TableColumn<ItemNotaFiscal, Item> precoNameColumn;
	@FXML
	private TableColumn<ItemNotaFiscal, Item> subtotalNameColumn;
	@FXML
	private TableColumn<ItemNotaFiscal, Item> descontoNameColumn;
	
	private Produto produtoSelecionado;
	
	ItemManager iM = new ItemManager();
	
	@FXML
	private void adicionarItem() {
		if (produtoSelecionado != null) {
			if (dest != null) {
				BigDecimal q = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText());
				BigDecimal preco = ConversorBigDecimal.paraBigDecimal(tfPreco.getText());
				
				Item item = iM.gerarItem(produtoSelecionado, q, preco, dest.getUf());	
				ItemNotaFiscal i  = new ItemNotaFiscal();
				i.setItem(item);
				itens.add(i);
				refreshPersonTable();
				atualizarTotais();				
			} else {
				Dialogs.create().owner(dialogStage).title("Aviso")
						.message("Selecione um Destinatário").showWarning();
			}
		} else {
			Dialogs.create().owner(dialogStage).title("Aviso").message("Selecione um Produto")
					.showWarning();
		}
	}
	
	private void iniciarTabela(){	
		nItemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		nItemNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							super.setText(String.valueOf(item.getnItem()));

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		descricaNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		descricaNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							super.setText(item.getProduto().getDescricao());

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		
		ncmNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		ncmNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							super.setText(item.getProduto().getNcm());

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		
		cfopNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		cfopNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							super.setText(item.getDetFiscal().getCfop());

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		
		unidNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		unidNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							if(item.getProduto().getUnidade()!=null){
								super.setText(item.getProduto().getUnidade().getSigla());
							}else{
								super.setText("");
							}
						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		
		quantidadeNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		quantidadeNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							
							super.setText(ConversorBigDecimal.paraString(item.getQuantidade()));

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		
		precoNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		precoNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							super.setText(ConversorBigDecimal.paraString(item.getPrecoVenda(),2));

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		
		subtotalNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		subtotalNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							
							super.setText(ConversorBigDecimal.paraString(item.getSubtotal(),2));

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		
		descontoNameColumn.setCellValueFactory(new PropertyValueFactory<ItemNotaFiscal, Item>("item"));
		descontoNameColumn.setCellFactory(new Callback<TableColumn<ItemNotaFiscal, Item>, TableCell<ItemNotaFiscal, Item>>() {
			@Override
			public TableCell<ItemNotaFiscal, Item> call(
					TableColumn<ItemNotaFiscal, Item> param) {
				TableCell<ItemNotaFiscal, Item> cell = new TableCell<ItemNotaFiscal, Item>() {
					@Override
					public void updateItem(Item item,
							boolean empty) {
						if (item != null) {
							
							super.setText(ConversorBigDecimal.paraString(item.getvDesc(),2));

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
	}
	
	private void refreshPersonTable() {
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		table.setItems(null);
		table.layout();
		table.setItems(itens);
		table.getSelectionModel().select(selectedIndex);
	}
		
	@FXML
    private void handleSelecionarProduto(){
    	ProdutoController cM = new ProdutoController();    	
    	boolean okClicked = cM.showPesquisaProdutoDialog(mainApp, dialogStage);
    	produtoSelecionado = cM.getProdutoSelecionado();
    	if (produtoSelecionado != null) {			
			if (okClicked) {
				atualizarProduto(true);
								
			}

		} else {
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.message(
					"Nenhum registro selecionado")
			.showWarning();
		}
    }
	
	public void atualizarProduto(boolean atualizaPreco){
			
		if(produtoSelecionado!=null){	
			if(produtoSelecionado.getUnidade()!=null){
				tfUn.setText(produtoSelecionado.getUnidade().getSigla());
			}else{
				tfUn.setText("");
			}
		
		if(atualizaPreco){
			tfPreco.setText(ConversorBigDecimal.paraString(produtoSelecionado.getPrecoVenda(),2));
		}		
		tfCodigo.setText(produtoSelecionado.getCodigo());       
	    tfDescricao.setText(produtoSelecionado.getDescricao());  
	    BigDecimal qtd = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText());
		BigDecimal preco = ConversorBigDecimal.paraBigDecimal(tfPreco.getText());
		BigDecimal subtotal = qtd.multiply(preco).setScale(2,RoundingMode.HALF_UP);
		tfSubtotal.setText(ConversorBigDecimal.paraString(subtotal,2));
	    
		}
	}
	
	@FXML
	private void handleDelete() {
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0 && itens.size()>0) {
			itens.remove(selectedIndex);
			refreshPersonTable();
			atualizarTotais();

		} else {
			Dialogs.create().title("Aviso")
					.message("Nenhum registro selecionado").showWarning();
		}
	}
	
	@FXML
	private void handleEdit() {
		Item i = table.getSelectionModel().getSelectedItem().getItem();
		if (i != null) {
			boolean okClicked = showEditDialog(i, dialogStage);
			if (okClicked) {
				atualizarTotais();
				refreshPersonTable();
			}

		} else {
			Dialogs.create().owner(dialogStage).title("Aviso")
					.masthead("Nenhum registro selecionado")
					.message("Nenhum registro selecionado").showWarning();
		}
	}
	
	public boolean showEditDialog(Item person, Stage dialogStageMain) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/fxml/item/EditItem.fxml"));
            BorderPane page = loader.load();
            Stage dialogStage2 = new Stage();
            dialogStage2.setTitle("Manutenção de Item");
            dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
            // Image("file:resources/images/edit.png"));
            dialogStage2.initOwner(dialogStageMain);
            Scene scene = new Scene(page);
            dialogStage2.setScene(scene);

            // Set the person into the controller
            EditItemController controller = loader.getController();
            controller.setDialogStage(dialogStage2);
            controller.setMainApp(mainApp);
            controller.setItem(person);
            controller.setUFDest(dest.getUf());
            controller.exibir();

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
	
	/**************  Totais  ****************************************************/
	@FXML
	private TextField tfbcIcms;
	@FXML
	private TextField tfvIcms;
	@FXML
	private TextField tfbcIcmsST;
	@FXML
	private TextField tfvIcmsST;
	@FXML
	private TextField tfvIpi;
	@FXML
	private TextField tfvProdutos;
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
	private TextField tfvDesconto;
	@FXML
	private TextField tfvNota;
	
	private BigDecimal pLiq = BigDecimal.ZERO;
	private BigDecimal pBru = BigDecimal.ZERO;
	private BigDecimal qVol = BigDecimal.ZERO;
	private BigDecimal vbc = BigDecimal.ZERO;
	private BigDecimal vicms = BigDecimal.ZERO;
	private BigDecimal vbcst = BigDecimal.ZERO;
	private BigDecimal vst = BigDecimal.ZERO;
	private BigDecimal vProd = BigDecimal.ZERO;
	private BigDecimal vFrete = BigDecimal.ZERO;
	private BigDecimal vSeg = BigDecimal.ZERO;
	private BigDecimal vDesc = BigDecimal.ZERO;
	private BigDecimal vipi = BigDecimal.ZERO;
	private BigDecimal vpis = BigDecimal.ZERO;
	private BigDecimal vcofins = BigDecimal.ZERO;
	private BigDecimal vOutro = BigDecimal.ZERO;
	private BigDecimal vnf = BigDecimal.ZERO;
	
	private void atualizarTotais(){
		pLiq = BigDecimal.ZERO;
		pBru = BigDecimal.ZERO;
		qVol = BigDecimal.ZERO;
		vbc = BigDecimal.ZERO;
		vicms = BigDecimal.ZERO;
		vbcst = BigDecimal.ZERO;
		vst = BigDecimal.ZERO;
		vProd = BigDecimal.ZERO;
		vFrete = BigDecimal.ZERO;
		vSeg = BigDecimal.ZERO;
		vDesc = BigDecimal.ZERO;
		vipi = BigDecimal.ZERO;
		vpis = BigDecimal.ZERO;
		vcofins = BigDecimal.ZERO;
		vOutro = BigDecimal.ZERO;
		vnf = BigDecimal.ZERO;
		
		for(ItemNotaFiscal it : itens){
			Item i = it.getItem();
			pLiq = pLiq.add(i.getPesoLiquido());
			pBru = pBru.add(i.getPesoBruto());
			qVol = qVol.add(i.getQuantidade());

			ICMS icms = i.getDetFiscal().getIcms();
                        if(icms.getvBCICMS()!=null){
                            vbc = vbc.add(icms.getvBCICMS());
                        }			
			vicms = vicms.add(icms.getvICMS());
			vbcst = vbcst.add(icms.getvBCST());
			vst = vst.add(icms.getvICMSST());
			
			vProd = vProd.add(i.getSubtotal());
			vFrete = vFrete.add(i.getvFrete());
			vSeg = vSeg.add(i.getvSeg());
			vDesc = vDesc.add(i.getvDesc());
			vipi = vipi.add(i.getDetFiscal().getIpi().getvIPI());
			vpis = vpis.add(i.getDetFiscal().getPis().getvPIS());
			vcofins = vcofins.add(i.getDetFiscal().getCofins().getvCOFINS());
			vOutro = vOutro.add(i.getvOutro());			
		}
		vnf = vProd.subtract(vDesc).add(vst).add(vFrete).add(vSeg).add(vOutro)
				.add(vipi);
		exibirTotais();
	}
	
	private void exibirTotais(){
		tfbcIcms.setText(ConversorBigDecimal.paraString(vbc, 2));
		tfvIcms.setText(ConversorBigDecimal.paraString(vicms, 2));
		tfbcIcmsST.setText(ConversorBigDecimal.paraString(vbcst, 2));
		tfvIcmsST.setText(ConversorBigDecimal.paraString(vst, 2));
		tfvIpi.setText(ConversorBigDecimal.paraString(vipi, 2));
		tfvProdutos.setText(ConversorBigDecimal.paraString(vProd, 2));
		tfvfrete.setText(ConversorBigDecimal.paraString(vFrete, 2));
		tfvSeguro.setText(ConversorBigDecimal.paraString(vSeg, 2));
		tfvOutros.setText(ConversorBigDecimal.paraString(vOutro, 2));
		tfvPis.setText(ConversorBigDecimal.paraString(vpis, 2));
		tfvConfins.setText(ConversorBigDecimal.paraString(vcofins, 2));
		tfvDesconto.setText(ConversorBigDecimal.paraString(vDesc, 2));
		tfvNota.setText(ConversorBigDecimal.paraString(vnf, 2));
	}
	
	private void carregarTotais(){
		Total t = notaFiscal.getTotal();
		if(t==null){
			t = new Total();
		}
		t.setVbc(vbc);
		t.setVbcst(vbcst);
		t.setVcofins(vcofins);
		t.setvDesc(vDesc);
		t.setvFrete(vFrete);
		t.setVicms(vicms);
		t.setvOutro(vOutro);
		t.setvProd(vProd);
		t.setvSeg(vSeg);
		t.setVipi(vipi);
		t.setVnf(vnf);
		t.setVpis(vpis);
		t.setVst(vst);
		notaFiscal.setTotal(t);
	}
		
	/*
	 *  Transporte *******************************************************************************************************
	 */
	@FXML
	private ComboBox<ModFrete> cbModFrete;
	@FXML
	private TextField tfNameTransp;
	@FXML
	private TextField tfNumDocTransp;
	@FXML
	private TextField tfMunicipioTransp;
	@FXML
	private TextField tfUFTransp;
	private Contato transportador;
	
	@FXML
	private TextField tfVeiculoPlaca;
	@FXML
	private ComboBox<UF> cbVeiculoUF;
	@FXML
	private TextField tfVeiculoRntc;
	@FXML
	private TextField tfReboquePlaca;
	@FXML
	private ComboBox<UF> cbReboqueUF;
	@FXML
	private TextField tfReboqueRntc;
	
	private ObservableList<Veiculo> reboques = FXCollections
			.observableArrayList();
	@FXML
	private TableView<Veiculo> tableReboque;	
	@FXML
	private TableColumn<Veiculo, String> placaNameColumn;
	@FXML
	private TableColumn<Veiculo, String> ufNameColumn;
	@FXML
	private TableColumn<Veiculo, String> rntcNameColumn;
	
	private void iniciarTabelaReboques(){
		placaNameColumn.setCellValueFactory(new PropertyValueFactory<Veiculo, String>(
				"placa"));
		ufNameColumn.setCellValueFactory(new PropertyValueFactory<Veiculo, String>(
				"uf"));
		rntcNameColumn.setCellValueFactory(new PropertyValueFactory<Veiculo, String>(
				"rntc"));
	}
		
	@FXML
	private void handleDeleteReboque() {
		int selectedIndex = tableReboque.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0 && reboques.size()>0) {
			reboques.remove(selectedIndex);
			refreshPersonTableReboques();
		} else {
			Dialogs.create().owner(dialogStage).title("Aviso")
					.message("Nenhum registro selecionado").showWarning();
		}
	}
	
	@FXML
	private void adicionarReboque(){
		Veiculo v = new Veiculo();
		v.setPlaca(tfReboquePlaca.getText());
		v.setUf(cbReboqueUF.getValue());
		v.setRntc(tfReboqueRntc.getText());
		reboques.add(v);
		refreshPersonTableReboques();
	}
	
	private void refreshPersonTableReboques() {
		int selectedIndex = table.getSelectionModel().getSelectedIndex();
		tableReboque.setItems(null);
		tableReboque.layout();
		tableReboque.setItems(reboques);
		tableReboque.getSelectionModel().select(selectedIndex);
	}
	
	private void iniciarTransporte(){
		cbModFrete.getItems().clear();
		cbModFrete.getItems().addAll(ModFrete.values());
		cbModFrete.setPromptText("Selecione");
		
		cbVeiculoUF.getItems().clear();
		cbVeiculoUF.getItems().addAll(UF.values());
		cbVeiculoUF.setPromptText("Selecione");
		
		cbReboqueUF.getItems().clear();
		cbReboqueUF.getItems().addAll(UF.values());
		cbReboqueUF.setPromptText("Selecione");
	}
	
	@FXML
    private void handleSelecionarTransportador(){
    	ContatoManager cM = new ContatoManager();    	
    	boolean okClicked = cM.showPesquisaContatoDialog(mainApp, TpContato.TRANSPORTADOR, dialogStage);
    	transportador = cM.getContato();
    	if (transportador != null) {			
			if (okClicked) {
				tfNameTransp.setText(transportador.getNome());
				tfNumDocTransp.setText(transportador.getNumDoc());
				tfMunicipioTransp.setText(transportador.getMunicipio().getxMun());
				tfUFTransp.setText(transportador.getMunicipio().getUF().toString());
				
			}

		} else {
			Dialogs.create()
			.owner(dialogStage)
			.title("Aviso")
			.message(
					"Nenhum registro selecionado")
			.showWarning();
		}
    }
	
	private void carregarTransporte(){
		Transporte t = notaFiscal.getTransp();
		if(t==null){
			t = new Transporte();
		}
		t.setModFrete(cbModFrete.getValue());
		
		t.setTransporta(transportador);
		
		Veiculo veicTransp = new Veiculo();
		veicTransp.setPlaca(tfVeiculoPlaca.getText());
		veicTransp.setUf(cbVeiculoUF.getValue());
		veicTransp.setRntc(tfVeiculoRntc.getText());
		t.setVeicTransp(veicTransp);
		t.setReboque(reboques);
		notaFiscal.setTransp(t);
		
	}
	
	private void exibirTransporte(Transporte t) {
		if (t != null) {
			transportador = t.getTransporta();
			if(t.getReboque()!=null){
			reboques = FXCollections.observableArrayList(t.getReboque());
			}
			if(transportador!=null){
				tfNameTransp.setText(transportador.getNome());
				tfNumDocTransp.setText(transportador.getNumDoc());
				tfMunicipioTransp.setText(transportador.getMunicipio()
						.getxMun());
				tfUFTransp.setText(transportador.getMunicipio().getUF()
						.toString());
			}
			
			cbModFrete.setValue(t.getModFrete());
			if(t.getVeicTransp()!=null){
			tfVeiculoPlaca.setText(t.getVeicTransp().getPlaca());
			cbVeiculoUF.setValue(t.getVeicTransp().getUf());
			tfVeiculoRntc.setText(t.getVeicTransp().getRntc());
			}
			refreshPersonTableReboques();
		}
	}
	
	
	//**   Notas Referenciadas ***********************************************************************************************
	@FXML
	private TextField tfChaveRef;
	
	private ObservableList<NFref> nfsRef = FXCollections
			.observableArrayList();
	@FXML
	private TableView<NFref> tabelaRefNF;	
	@FXML
	private TableColumn<NFref, String> chaveNameCol;
	
	private void iniciarTabelaRefNF(){
		chaveNameCol.setCellValueFactory(new PropertyValueFactory<NFref, String>(
				"refNFe"));
	}
	
	private void refreshTableRefNF() {
		int selectedIndex = tabelaRefNF.getSelectionModel().getSelectedIndex();
		tabelaRefNF.getItems().clear();
		tabelaRefNF.getItems().addAll(nfsRef);
		tabelaRefNF.getSelectionModel().select(selectedIndex);
	}
	
	@FXML
	private void adicionarRefNF(){
		NFref nf = new NFref();
		nf.setRefNFe(tfChaveRef.getText());
		nfsRef.add(nf);
		refreshTableRefNF();
	}
	
	@FXML
	private void removerRefNF() {
		int selectedIndex = tabelaRefNF.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0 && nfsRef.size()>0) {
			nfsRef.remove(selectedIndex);
			refreshTableRefNF();;
		} else {
			Dialogs.create().owner(dialogStage).title("Aviso")
					.message("Nenhum registro selecionado").showWarning();
		}
	}
	
	/**
	 * Informações Adicionais
	 */
	@FXML
	private TextArea taInfAd;
	@FXML
	private TextArea taInfAdFisco;
	
	private InfAdicionais carregarInfAdicionais(){
		InfAdicionais infAdic = new InfAdicionais();
		infAdic.setInfCpl(taInfAd.getText());
		infAdic.setInfAdFisco(taInfAdFisco.getText());		
		return infAdic;
	}
	
	private void exibirInfAdicionais(InfAdicionais infAdic){
		if(infAdic!=null){
		taInfAd.setText(infAdic.getInfCpl());
		taInfAdFisco.setText(infAdic.getInfAdFisco());
		}
	}

	/**
	 * Pagamento
	 */
	@FXML
	private ComboBox<IndPag> cbIndPag;
	
	private void exibirPagamento(IndPag pg){
		cbIndPag.setValue(pg);
	}
		
	private Cobranca carregarCobranca(){
		Cobranca c = new Cobranca();
		c.setFat(carregarFatura());
		c.setDup(duplicatas);
		return c;
	}
	
	@FXML
	private TextField tfNFatura;
	@FXML
	private TextField tfVOrigFatura;
	@FXML
	private TextField tfVDescFatura;
	@FXML
	private TextField tfVLiqFatura;
	
	private Fatura carregarFatura(){
		Fatura f = new Fatura();
		f.setnFat(tfNFatura.getText());		
		f.setvOrig(ConversorBigDecimal.paraBigDecimal( tfVOrigFatura.getText()));
		f.setvDesc(ConversorBigDecimal.paraBigDecimal( tfVDescFatura.getText()));
		f.setvLiq(ConversorBigDecimal.paraBigDecimal( tfVLiqFatura.getText()));
		return f;
	}
	private void exibirFatura(Fatura f){
		if(f!=null){
		tfNFatura.setText(f.getnFat());
		tfVOrigFatura.setText(ConversorBigDecimal.paraString(f.getvOrig()));
		tfVDescFatura.setText(ConversorBigDecimal.paraString(f.getvDesc()));
		tfVLiqFatura.setText(ConversorBigDecimal.paraString(f.getvLiq()));
		}
	}
	
	@FXML
	private TextField tfNDuplicata;
	@FXML
	private TextField tfVencDuplicata;
	@FXML
	private TextField tfVDuplicata;
	
	private ObservableList<Duplicata> duplicatas = FXCollections
			.observableArrayList();
	@FXML
	private TableView<Duplicata> tableDuplicatas;	
	@FXML
	private TableColumn<Duplicata, String> nDupNameColumn;
	@FXML
	private TableColumn<Duplicata, Date> dVencNameColumn;
	@FXML
	private TableColumn<Duplicata, BigDecimal> vDupNameColumn;
	
	private void iniciarTabelaDuplicata(){
		nDupNameColumn.setCellValueFactory(new PropertyValueFactory<Duplicata, String>(
				"numero"));
		
		dVencNameColumn.setCellValueFactory(new PropertyValueFactory<Duplicata, Date>("vencimento"));
		dVencNameColumn.setCellFactory(new Callback<TableColumn<Duplicata, Date>, TableCell<Duplicata, Date>>() {
			@Override
			public TableCell<Duplicata, Date> call(
					TableColumn<Duplicata, Date> param) {
				TableCell<Duplicata, Date> cell = new TableCell<Duplicata, Date>() {
					@Override
					public void updateItem(Date item,
							boolean empty) {
						if (item != null) {
							super.setText(ConversorDate.retornaData(item));
						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
		vDupNameColumn.setCellValueFactory(new PropertyValueFactory<Duplicata, BigDecimal>(
				"valor"));
		vDupNameColumn.setCellFactory(new Callback<TableColumn<Duplicata, BigDecimal>, TableCell<Duplicata, BigDecimal>>() {
			@Override
			public TableCell<Duplicata, BigDecimal> call(
					TableColumn<Duplicata, BigDecimal> param) {
				TableCell<Duplicata, BigDecimal> cell = new TableCell<Duplicata, BigDecimal>() {
					@Override
					public void updateItem(BigDecimal item,
							boolean empty) {
						if (item != null) {
							super.setText(ConversorBigDecimal.paraString(item, 2));

						}else{
							super.setText("");
						}						
					}
				};
				return cell;
			}
		});
	}
	
	private Duplicata carregarDuplicata(){
		Duplicata d = new Duplicata();
		d.setNumero(tfNDuplicata.getText());
		d.setVencimento(ConversorDate.gerarData(tfVencDuplicata.getText()));
		d.setValor(ConversorBigDecimal.paraBigDecimal(tfVDuplicata.getText()));
		return d;		
	}

	private void exibirDuplicatas() {
		int selectedIndex = tableDuplicatas.getSelectionModel().getSelectedIndex();
		tableDuplicatas.getItems().clear();
		tableDuplicatas.getItems().addAll(duplicatas);
		tableDuplicatas.getSelectionModel().select(selectedIndex);

	}
	
	@FXML
	private void adicionarDuplicata(){
		duplicatas.add(carregarDuplicata());
		exibirDuplicatas();
	}
	
	@FXML
	private void removerDuplicata() {
		int selectedIndex = tableDuplicatas.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0 && itens.size()>0) {
			duplicatas.remove(selectedIndex);
			exibirDuplicatas();

		} else {
			Dialogs.create().title("Aviso")
					.message("Nenhum registro selecionado").showWarning();
		}
	}
		
	/**
	 *    Outras Funções
	 */	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tfCodigo.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						produtoSelecionado = new RestProdutoManager().pesquisar(MainApp.getEmitente(), tfCodigo
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
		
		tfQuantidade.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfQuantidade,0);
					
					BigDecimal qtd = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText());
					BigDecimal preco = ConversorBigDecimal.paraBigDecimal(tfPreco.getText());
					BigDecimal subtotal = qtd.multiply(preco).setScale(2,RoundingMode.HALF_UP);
					tfSubtotal.setText(ConversorBigDecimal.paraString(subtotal,2));				
				}
			}
		});
		
		tfPreco.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source,
					Boolean wasFocused, Boolean isFocused) {
				if (wasFocused) {
					formataCampo(tfPreco, 2);
					
					BigDecimal qtd = ConversorBigDecimal.paraBigDecimal(tfQuantidade.getText());
					BigDecimal preco = ConversorBigDecimal.paraBigDecimal(tfPreco.getText());
					BigDecimal subtotal = qtd.multiply(preco).setScale(2,RoundingMode.HALF_UP);
					tfSubtotal.setText(ConversorBigDecimal.paraString(subtotal,2));
					
				}
			}
		});
	
		cbIndPag.getItems().clear();
		cbIndPag.getItems().addAll(IndPag.values());
		cbIndPag.setPromptText("Selecione");
		
		iniciarTabela();
		iniciarIde();
		iniciarTransporte();
		iniciarTabelaReboques();
		iniciarTabelaDuplicata();
		iniciarTabelaRefNF();
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

		if (dest==null) {
			errorMessage += "Selecione um Destinatario válido!\n";
		}
		if (itens.size()<1) {
			errorMessage += "Adicione algum item!\n";
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

	public void exibir() {
		status.setText(notaFiscal.getSitNfe().toString());
		chave.setText(notaFiscal.getChave());
		cbTpEmis.setValue(notaFiscal.getTpEmis());
		cbFinNFe.setValue(notaFiscal.getFinNFe());
		serie.setText(String.valueOf(notaFiscal.getSerie()));
		nNF.setText(String.valueOf(notaFiscal.getNumero()));
		cbTpNF.setValue(notaFiscal.getTpNF());
		tfNatOp.setText(notaFiscal.getNatOp());
		cbIdDest.setValue(notaFiscal.getIdDest());
		cbIndFinal.setValue(notaFiscal.getIndFinal());
		cbIdPres.setValue(notaFiscal.getIndPres());
		dhEmi.setText(ConversorDate.retornaDataHora(notaFiscal.getDhEmi()));
		dhSaiEnt.setText(ConversorDate.retornaDataHora(notaFiscal.getDhSaiEnt()));
		exibirDest(notaFiscal.getDest());
		exibirTransporte(notaFiscal.getTransp());		
		if(notaFiscal.getItens()!=null){
			int nItemMax = 0;
			for(ItemNotaFiscal it : notaFiscal.getItens()){
				if(it.getItem().getnItem()>nItemMax){
					nItemMax = it.getItem().getnItem();
				}
			}
			iM.setnItem(nItemMax);			
			itens.clear();
			itens.addAll(notaFiscal.getItens());
			refreshPersonTable();
		}
		atualizarTotais();
		exibirPagamento(notaFiscal.getIndPag());
		if(notaFiscal.getCobr()!=null){
			exibirFatura(notaFiscal.getCobr().getFat());
			duplicatas.clear();
			if(notaFiscal.getCobr().getDup()!=null){
			duplicatas.addAll(notaFiscal.getCobr().getDup());
			exibirDuplicatas();
			}
		}	
		if(notaFiscal.getNFRef()!=null){
			nfsRef.clear();
			nfsRef.addAll(notaFiscal.getNFRef());
			refreshTableRefNF();
		}
		exibirInfAdicionais(notaFiscal.getInfAdic());
		if(notaFiscal.getChave()!=null){
			chave.setText(notaFiscal.getChave());
		}
	}
	
	@FXML
	public void handleBtSalvar(ActionEvent event) {
		if (isInputValid()) {
			notaFiscal.setTpEmis(cbTpEmis.getValue());
			notaFiscal.setFinNFe(cbFinNFe.getValue());
			notaFiscal.setSerie(Integer.parseInt(serie.getText()));
			notaFiscal.setNumero(Integer.parseInt(nNF.getText()));
			notaFiscal.setTpNF(cbTpNF.getValue());
			notaFiscal.setNatOp(tfNatOp.getText());
			notaFiscal.setIdDest(cbIdDest.getValue());
			notaFiscal.setIndFinal(cbIndFinal.getValue());
			notaFiscal.setIndPres(cbIdPres.getValue());
			notaFiscal.setDhEmi(ConversorDate.gerarDatahora(dhEmi.getText()));
			notaFiscal.setDhSaiEnt(ConversorDate.gerarDatahora(dhSaiEnt.getText()));
			notaFiscal.setItens(itens);
			carregarTransporte();
			carregarTotais();
			notaFiscal.setEmitente(MainApp.getEmitente());
			notaFiscal.setIndPag(cbIndPag.getValue());
			notaFiscal.setCobr(carregarCobranca());
			
			notaFiscal.setNFRef(nfsRef);
			
			notaFiscal.setInfAdic(carregarInfAdicionais());
			Volume vol = notaFiscal.getTransp().getVol().get(0);
			vol.setPesoB(pBru);
			vol.setPesoL(pLiq);
			vol.setqVol(qVol);
			
			okClicked = true;
			dialogStage.close();
		}
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

	private Stage dialogStage;
	private boolean okClicked = false;
	private MainApp mainApp;
	private NotaFiscal notaFiscal =  new NotaFiscal();
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setNotaFiscal(NotaFiscal nf) {
		this.notaFiscal = nf;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	public void handleBtCancelar(ActionEvent event) {
		dialogStage.close();
	}
	
	@FXML 
	private TextField chave;
}
