package br.net.eia.ui.notafiscal;

import java.io.ByteArrayInputStream;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.geometry.Rectangle2D;

import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import javafx.stage.Screen;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;

import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.enums.UF;
import br.net.eia.nfe.NFe;
import br.net.eia.nfe.evento.retEnvEvento.TRetEnvEvento;
import br.net.eia.nfe.v310.TNFe;
import br.net.eia.nfe.v310.enviNFe.TNFe.InfNFe.Transp.Vol;
import br.net.eia.nfe.v310.retConsSitNFe.ObjectFactory;
import br.net.eia.nfe.v310.retConsSitNFe.TRetConsSitNFe;
import br.net.eia.nfe.v310.retConsStatServ.TRetConsStatServ;
import br.net.eia.nfe.v310.retEnviNFe.TRetEnviNFe;
import br.net.eia.notafiscal.NotaFiscal;
import br.net.eia.notafiscal.adicionais.InfAdicionais;
import br.net.eia.notafiscal.adicionais.SitNFe;
import br.net.eia.notafiscal.ide.FinNFe;
import br.net.eia.notafiscal.ide.IdDest;
import br.net.eia.notafiscal.ide.IndFinal;
import br.net.eia.notafiscal.ide.IndPag;
import br.net.eia.notafiscal.ide.IndPres;
import br.net.eia.notafiscal.ide.ModNFe;
import br.net.eia.notafiscal.ide.TpEmis;
import br.net.eia.notafiscal.ide.TpImp;
import br.net.eia.notafiscal.ide.TpNF;
import br.net.eia.notafiscal.ide.Versao;
import br.net.eia.notafiscal.item.ItemNotaFiscal;
import br.net.eia.notafiscal.total.Total;
import br.net.eia.notafiscal.transporte.Transporte;
import br.net.eia.notafiscal.transporte.Veiculo;
import br.net.eia.notafiscal.transporte.Volume;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.certificado.CarregarCertificadoController;
import br.net.eia.ui.contato.ContatoManager;
import br.net.eia.ui.nfe.RestNFeManager;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.ConversorDate;
import br.net.eia.util.Formatador;
import br.net.eia.util.certificado.PegarKs;
import br.net.eia.util.nfe.AssinaturaDigital;
import br.net.eia.util.nfe.ConsultarNFe;
import br.net.eia.util.nfe.ConsultarStatusServicos;
import br.net.eia.util.nfe.ConversorNFe;
import br.net.eia.util.nfe.EnviarLote;
import br.net.eia.util.nfe.EventoNFe;
import br.net.eia.util.nfe.GerarProcNFe;
import br.net.eia.util.nfe.evento.GerarProcEvento;
import br.net.eia.util.nfe.evento.TpEvento;

@SuppressWarnings("restriction")
public class CadastroNotaFiscalController implements Initializable {

    // Reference to the main application
    private MainApp mainApp;
    private ObservableList<NotaFiscal> dados = FXCollections
            .observableArrayList();
    @FXML
    private TableView<NotaFiscal> notaFiscalTable;
    @FXML
    private TableColumn<NotaFiscal, Integer> nNFNameColumn;
    @FXML
    private TableColumn<NotaFiscal, Date> emissaoNameColumn;
    @FXML
    private TableColumn<NotaFiscal, Contato> destNameColumn;
    @FXML
    private TableColumn<NotaFiscal, Contato> nDocNameColumn;
    @FXML
    private TableColumn<NotaFiscal, Contato> municipioNameColumn;
    @FXML
    private TableColumn<NotaFiscal, Contato> ufNameColumn;
    @FXML
    private TableColumn<NotaFiscal, SitNFe> sitNameColumn;
    @FXML
    private TableColumn<NotaFiscal, Total> vTotalNameColumn;
    @FXML
    private TableColumn<NotaFiscal, TpNF> tipoNameColumn;
    @FXML
    private TableColumn<NotaFiscal, String> naturezaNameColumn;
    
    
    @FXML
    private TextField tfDataIni;
    @FXML
    private TextField tfDataFim;
    @FXML
    private TextField tfnNF;
    @FXML
    private TextField tfDest;    
    private Contato contato;
    private RestNotaFiscalManager rest;
    private Stage dialogStage;

    public CadastroNotaFiscalController() {
        rest = new RestNotaFiscalManager();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	tipoNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, TpNF>("tpNF"));
    	tipoNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, TpNF>, TableCell<NotaFiscal, TpNF>>() {
            @Override
            public TableCell<NotaFiscal, TpNF> call(
                    TableColumn<NotaFiscal, TpNF> param) {
                TableCell<NotaFiscal, TpNF> cell = new TableCell<NotaFiscal, TpNF>() {
                    @Override
                    public void updateItem(TpNF item,
                            boolean empty) {
                        if (item != null) {
                            super.setText(String.valueOf(item).substring(0, 1));
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER);
                    }
                };
                return cell;
            }
        });
    	
    	naturezaNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, String>("natOp"));
    	naturezaNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, String>, TableCell<NotaFiscal, String>>() {
            @Override
            public TableCell<NotaFiscal, String> call(
                    TableColumn<NotaFiscal, String> param) {
                TableCell<NotaFiscal, String> cell = new TableCell<NotaFiscal, String>() {
                    @Override
                    public void updateItem(String item,
                            boolean empty) {
                        if (item != null) {
                            super.setText(String.valueOf(item));
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER_LEFT);
                    }
                };
                return cell;
            }
        });
    	
    	
    	nNFNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, Integer>("numero"));
    	nNFNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, Integer>, TableCell<NotaFiscal, Integer>>() {
            @Override
            public TableCell<NotaFiscal, Integer> call(
                    TableColumn<NotaFiscal, Integer> param) {
                TableCell<NotaFiscal, Integer> cell = new TableCell<NotaFiscal, Integer>() {
                    @Override
                    public void updateItem(Integer item,
                            boolean empty) {
                        if (item != null) {
                            super.setText(String.valueOf(item));
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER_RIGHT);
                    }
                };
                return cell;
            }
        });
    	
    	sitNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, SitNFe>("sitNfe"));
    	sitNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, SitNFe>, TableCell<NotaFiscal, SitNFe>>() {
            @Override
            public TableCell<NotaFiscal, SitNFe> call(
                    TableColumn<NotaFiscal, SitNFe> param) {
                TableCell<NotaFiscal, SitNFe> cell = new TableCell<NotaFiscal, SitNFe>() {
                    @Override
                    public void updateItem(SitNFe item,
                            boolean empty) {
                        if (item != null) {
                            super.setText(String.valueOf(item));
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER_RIGHT);
                    }
                };
                return cell;
            }
        });
    	
    	emissaoNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, Date>("dhEmi"));
    	emissaoNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, Date>, TableCell<NotaFiscal, Date>>() {
            @Override
            public TableCell<NotaFiscal, Date> call(
                    TableColumn<NotaFiscal, Date> param) {
                TableCell<NotaFiscal, Date> cell = new TableCell<NotaFiscal, Date>() {
                    @Override
                    public void updateItem(Date item,
                            boolean empty) {
                        if (item != null) {
                            super.setText(ConversorDate.retornaData(item));
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER);
                    }
                };
                return cell;
            }
        });
    	
    	destNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, Contato>("dest"));
    	destNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, Contato>, TableCell<NotaFiscal, Contato>>() {
            @Override
            public TableCell<NotaFiscal, Contato> call(
                    TableColumn<NotaFiscal, Contato> param) {
                TableCell<NotaFiscal, Contato> cell = new TableCell<NotaFiscal, Contato>() {
                    @Override
                    public void updateItem(Contato c,
                            boolean empty) {
                        if (c != null) {
                            super.setText(c.getNome());
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER_LEFT);
                    }
                };
                return cell;
            }
        });
    	
    	nDocNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, Contato>("dest"));
    	nDocNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, Contato>, TableCell<NotaFiscal, Contato>>() {
            @Override
            public TableCell<NotaFiscal, Contato> call(
                    TableColumn<NotaFiscal, Contato> param) {
                TableCell<NotaFiscal, Contato> cell = new TableCell<NotaFiscal, Contato>() {
                    @Override
                    public void updateItem(Contato c,
                            boolean empty) {
                        if (c != null) {
                        	super.setText(c.getNumDoc());
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER);
                    }
                };
                return cell;
            }
        });
    	
    	municipioNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, Contato>("dest"));
    	municipioNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, Contato>, TableCell<NotaFiscal, Contato>>() {
            @Override
            public TableCell<NotaFiscal, Contato> call(
                    TableColumn<NotaFiscal, Contato> param) {
                TableCell<NotaFiscal, Contato> cell = new TableCell<NotaFiscal, Contato>() {
                    @Override
                    public void updateItem(Contato c,
                            boolean empty) {
                        if (c != null) {                            
                            super.setText(c.getMunicipio().getxMun());
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER);
                    }
                };
                return cell;
            }
        });
    	
    	ufNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, Contato>("dest"));
    	ufNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, Contato>, TableCell<NotaFiscal, Contato>>() {
            @Override
            public TableCell<NotaFiscal, Contato> call(
                    TableColumn<NotaFiscal, Contato> param) {
                TableCell<NotaFiscal, Contato> cell = new TableCell<NotaFiscal, Contato>() {
                    @Override
                    public void updateItem(Contato c,
                            boolean empty) {
                        if (c != null) {                            
                            super.setText(c.getMunicipio().getUF().toString());
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER);
                    }
                };
                return cell;
            }
        });
        
        vTotalNameColumn.setCellValueFactory(new PropertyValueFactory<NotaFiscal, Total>("total"));
        vTotalNameColumn.setCellFactory(new Callback<TableColumn<NotaFiscal, Total>, TableCell<NotaFiscal, Total>>() {
            @Override
            public TableCell<NotaFiscal, Total> call(
                    TableColumn<NotaFiscal, Total> param) {
                TableCell<NotaFiscal, Total> cell = new TableCell<NotaFiscal, Total>() {
                    @Override
                    public void updateItem(Total c,
                            boolean empty) {
                        if (c != null) {
                            super.setText(ConversorBigDecimal.paraString(c.getVnf(),2));
                        }else{
                        	super.setText("");
                        }
                        this.setAlignment(Pos.CENTER_RIGHT);
                    }
                };
                return cell;
            }
        });

        notaFiscalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        notaFiscalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        tfDataIni.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> source,
                    Boolean wasFocused, Boolean isFocused) {
                if (wasFocused) {
                    formataData(tfDataIni);
                }
            }
        });

        tfDataFim.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> source,
                    Boolean wasFocused, Boolean isFocused) {
                if (wasFocused) {
                    formataData(tfDataFim);
                }
            }
        });
        
        Date data = new Date();        
        tfDataIni.setText(ConversorDate.retornaData(data)+" 00:00:00");
        tfDataFim.setText(ConversorDate.retornaData(data)+" 23:59:59");

    }

    private void formataData(TextField tf) {
        try {
            Formatador formatter = new Formatador();
            String formatado = formatter.formatterDate(tf.getText());
            tf.setText(formatado);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(
                    Level.ERROR, e.getLocalizedMessage(), e);
        }
    }

    @FXML
    private void handleNew() {
    	NotaFiscal nf = new NotaFiscal();
    	nf.setSitNfe(SitNFe.DIGITACAO);
    	nf.setTpEmis(TpEmis.NORMAL);
    	nf.setFinNFe(FinNFe.NORMAL);
    	nf.setNatOp("Venda");
    	nf.setSerie(0);
    	nf.setNumero(0);
    	nf.setTpNF(TpNF.SAIDA);
    	nf.setIdDest(IdDest.INTERNA);
    	nf.setIndFinal(IndFinal.NAO);
    	nf.setIndPres(IndPres.NAO_APLICAVEL);    	
    	nf.setIndPag(IndPag.A_VISTA);
    	nf.setVersao(Versao.VERSAO_3_1);
    	nf.setVerProc(Versao.VERSAO_3_1);
    	nf.setTpImp(TpImp.RETRATO);
    	nf.setMod(ModNFe._55);
    	nf.setDhEmi(new Date());
    	nf.setDhSaiEnt(new Date());
    	Transporte t = new Transporte();
    	Volume v = new Volume();
    	List<Volume> vols = new ArrayList<Volume>();
    	vols.add(v);
    	t.setVol(vols);
    	nf.setTransp(t);
        InfAdicionais obs = new InfAdicionais();
        obs.setInfCpl(MainApp.getProps().getProperty("obs"));
    	nf.setInfAdic(obs);
        boolean okClicked = showEditDialog(nf, dialogStage);
       
        if (okClicked) {   
            rest.inserir(nf);
            dados.add(nf);
            handleFiltrar();
			Notifications
					.create()
					.position(Pos.CENTER)
					.owner(dialogStage)
					.title("Aviso")
					.text(nf.getDest().getNome() + "\n"
							+ nf.getTotal().getVnf()
							+ "\nInserido com sucesso.").showInformation();
		
        }
    }

    public boolean showEditDialog(NotaFiscal person, Stage dialogStageMain) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/fxml/notafiscal/EditNotaFiscal.fxml"));
            BorderPane page = loader.load();
            Stage dialogStage2 = new Stage();
            dialogStage2.setTitle("Cadastro de Nota Fiscal");
            dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
            // Image("file:resources/images/edit.png"));
            dialogStage2.initOwner(dialogStageMain);
            Scene scene = new Scene(page);
            dialogStage2.setScene(scene);

            // Set the person into the controller
            EditNotaFiscalController controller = loader.getController();
            controller.setDialogStage(dialogStage2);
            controller.setMainApp(mainApp);
            controller.setNotaFiscal(person);
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

    @FXML
    private void handleEdit() {
    	NotaFiscal nf = notaFiscalTable.getSelectionModel()
                .getSelectedItem();
        if (nf != null) {
            boolean okClicked = showEditDialog(nf, dialogStage);
            if (okClicked) {
                if (nf.getSitNfe() != SitNFe.AUTORIZADA && nf.getSitNfe() != SitNFe.CANCELADA) {
                    NotaFiscal nota = rest.atualizar(nf);
                    Dialogs.create().owner(dialogStage)
                            .title("Aviso")
                            .masthead(
                            		nota.getDest().getNome() + "\n")
                            .message(
                            		nota.getTotal().getVnf()
                                    + "Alterado com sucesso.")
                            .showInformation();

                }else{
                	Dialogs.create().owner(dialogStage)
                    .title("Aviso")
                    .masthead("Não pode ser alterado")
                    .showInformation();
                }
                handleFiltrar();
            }

        } else {
            Dialogs.create().owner(dialogStage)
                    .title("Aviso")
                    .masthead("Nenhum registro selecionado")
                    .message(
                            "Nenhum registro selecionado")
                    .showWarning();
        }
    }

    @FXML
    private void handleDelete() {
        int selectedIndex = notaFiscalTable.getSelectionModel().getSelectedIndex();
        NotaFiscal selected = notaFiscalTable.getSelectionModel()
                .getSelectedItem();
        if (selectedIndex >= 0) {            

            if (selected.getSitNfe().equals(SitNFe.AUTORIZADA) 
            	|| selected.getSitNfe().equals(SitNFe.CANCELADA)) {
            	Dialogs.create()	
                        .owner(dialogStage)
                        .title("Operação não permitida.")
                        .message("Nota Fiscal não permite edição?")
                        .showInformation();
            } else {
                Action response2 = Dialogs
                        .create().owner(dialogStage)
                        .title("Exclusão em andamento.")
                        .message("Tem certesa que deseja remover?")
                        .actions(Actions.YES, Actions.NO)
                        .showConfirm();

                if (response2.equals(Actions.YES)) {

                    boolean deletado = rest.remover(selected);
                    Dialogs.create()
                            .owner(dialogStage)
                            .title("Aviso")
                            .masthead(selected.getChave())
                            .message("Removido com sucesso")
                            .showInformation();
                    if (deletado) {
                        handleFiltrar();
                    }
                }

            }
        } else {
            Dialogs.create()
                    .owner(dialogStage)
                    .title("Aviso")
                    .masthead("Nenhum registro selecionado")
                    .message(
                            "Nenhum registro selecionado")
                    .showWarning();
        }

    }

    private void refreshPersonTable() {
        int selectedIndex = notaFiscalTable.getSelectionModel().getSelectedIndex();
        notaFiscalTable.getItems().clear();
        notaFiscalTable.getItems().addAll(dados);
        notaFiscalTable.getSelectionModel().select(selectedIndex);
    }

    @FXML
    private void handleFechar() {
        dialogStage.close();
    }

    @FXML
    private void handleFiltrar() {
        Date dataIni = ConversorDate.gerarDatahora(tfDataIni.getText());
        Date dataFim = ConversorDate.gerarDatahora(tfDataFim.getText());
        String nNF= tfnNF.getText();
        String dest = tfDest.getText();
        try {
            dados = new RestNotaFiscalManager().filtrar(MainApp.getEmitente(), dataIni, dataFim, nNF, dest);
            refreshPersonTable();
        } catch (Exception e) {
            dados = FXCollections.observableArrayList();
            refreshPersonTable();
            Logger.getLogger(getClass().getName()).log(
                    Level.ERROR, e.getLocalizedMessage(), e);
        }
    }

    @FXML
    private void handleTodos() {
        try {
           dados = new RestNotaFiscalManager().filtrar(MainApp.getEmitente(),null,null,"","");  
           refreshPersonTable();
        } catch (Exception e) {
            dados = FXCollections.observableArrayList();
        }
    }
    
    @FXML
    private void handleSelecionarDestinatario(){
    	ContatoManager cM = new ContatoManager();    	
    	boolean okClicked = cM.showPesquisaContatoDialog(mainApp, TpContato.CLIENTE, dialogStage);
    	contato = cM.getContato();
    	if (contato != null) {			
			if (okClicked) {
				tfDest.setText(contato.getNome());
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
    private void duplicar(){
    	
    	NotaFiscal notaFiscal = notaFiscalTable.getSelectionModel().getSelectedItem();
    	
    	NotaFiscal nf = new NotaFiscal();
    	nf.setSitNfe(SitNFe.DIGITACAO);
    	nf.setTpEmis(notaFiscal.getTpEmis());
    	nf.setFinNFe(notaFiscal.getFinNFe());
    	nf.setNatOp(notaFiscal.getNatOp());
    	nf.setSerie(0);
    	nf.setNumero(0);
    	nf.setTpNF(notaFiscal.getTpNF());
    	nf.setIdDest(notaFiscal.getIdDest());
    	nf.setIndFinal(notaFiscal.getIndFinal());
    	nf.setIndPres(notaFiscal.getIndPres());    	
    	nf.setIndPag(notaFiscal.getIndPag());
    	nf.setVersao(Versao.VERSAO_3_1);
    	nf.setVerProc(Versao.VERSAO_3_1);
    	nf.setTpImp(TpImp.RETRATO);
    	nf.setMod(notaFiscal.getMod());
		nf.setDhEmi(new Date());
		nf.setDhSaiEnt(new Date());

		Transporte t = notaFiscal.getTransp();
		t.setId(null);
		t.getVeicTransp().setId(null);
		
		List<Veiculo> reboques = t.getReboque();
		if (reboques != null) {
			for (Veiculo v : reboques) {
				v.setId(null);
			}
			t.setReboque(reboques);
		}
		for (Volume v : t.getVol()) {
			v.setId(null);
		}
		
		
		nf.setTransp(t);
		
		nf.setDest(notaFiscal.getDest());
		
		InfAdicionais infAdic = notaFiscal.getInfAdic();
		infAdic.setId(null);
		nf.setInfAdic(infAdic);
		
		nf.setItens(new ArrayList<ItemNotaFiscal>());		
		if (notaFiscal.getItens() != null) {
			for (ItemNotaFiscal i : notaFiscal.getItens()) {
				i.setId(null);
				i.getItem().getDetFiscal().setId(null);
				nf.getItens().add(i);
			}
		}
		
		Total total = notaFiscal.getTotal();
		total.setId(null);
		nf.setTotal(total);
		
		nf.setEmitente(MainApp.getEmitente());
		
		nf = rest.inserir(nf);
		handleFiltrar();
	}

    @FXML
    private void transmitir() {
        if (PegarKs.getKS() == null) {
            CarregarCertificadoController cr = new CarregarCertificadoController();
            cr.setDialogStage(dialogStage);
            cr.setMainApp(mainApp);
            cr.show(null);
        }
        
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws InterruptedException {
                        boolean servico = false;
                        updateProgress(0, 4);
                        updateMessage("Verificando Status do Servico");
                        try {
                            TRetConsStatServ retConsStatServ = new ConsultarStatusServicos()
                                    .consultaStatusServico(UF.PB, MainApp.getProps().getProperty("ambiente"), MainApp.getProps().getProperty("NfeStatusServico"));
                            updateMessage(retConsStatServ.getXMotivo());
                            servico = true;
                        } catch (Exception e) {
                            updateMessage("Erro ao consultar\nVerifique a conexão com a Internet.\n\r"+e.getMessage());
                            Thread.sleep(5000);
                        }
                        if (servico) {
                            String ambiente = MainApp.getProps().getProperty("ambiente");
                            List<NotaFiscal> notas = notaFiscalTable.getSelectionModel().getSelectedItems();
                        	int nNotas = notas.size();
                        	int currentNota = 1;
                            for(NotaFiscal notaFiscal : notas){
                            	updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
                            	+"\n Gerando o xml ...");
								updateProgress(currentNota, nNotas);
								currentNota++;
                            TNFe nfe = ConversorNFe.getnFe(notaFiscal, ambiente);
                            String xml = "";
							try {
								xml = new AssinaturaDigital().assinarDocumento(
								        ConversorNFe.getXML(nfe));
							} catch (Exception e1) {
								 updateMessage("Erro\n\r"+e1.getMessage());
		                            Thread.sleep(5000);
							}
                            StringBuilder xmlEnvi = new StringBuilder();
                            xmlEnvi.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                                    .append("<enviNFe versao=\"3.10\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
                                    .append("<idLote>")
                                    .append(notaFiscal.getChave().substring(29))
                                    .append("</idLote>")
                                    .append("<indSinc>1</indSinc>")
                                    .append(xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""))
                                    .append("</enviNFe>");    
                            
							boolean xmlOk = true;
							
                     if(xmlOk) {     
							try {
								Files.write(Paths.get(System.getProperty("user.home")+"/envioNFe.xml"),
										xmlEnvi.toString().getBytes());
							} catch (IOException ex) {
								java.util.logging.Logger.getLogger(
										CadastroNotaFiscalController.class
												.getName()).log(
										java.util.logging.Level.SEVERE, null,
										ex);
							}
                            try {
                            	updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
                            	+"\n Transmitindo a Nota Fiscal ...");
                            	EnviarLote enviaLote = new EnviarLote();
                                String retEnvi = enviaLote.enviar(xmlEnvi.toString(), UF.PB, MainApp.getProps().getProperty("NFeAutorizacao"));
								TRetEnviNFe retEnvio = enviaLote.retEnviNFe(retEnvi);
								updateMessage(retEnvio.getProtNFe().getInfProt().getXMotivo());									
								String cStat = retEnvio.getProtNFe().getInfProt().getCStat();
								switch (cStat) {
								case "100":
									updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
	                            					+"\n Enviando ao Repositorio ...");
									notaFiscal.setSitNfe(SitNFe.AUTORIZADA);	
										String xmlProc = new GerarProcNFe().gerarProcNFeEnvi(xmlEnvi.toString(), retEnvi);
										NFe nfeRep = new NFe();
										nfeRep.setChave(notaFiscal.getChave());
										nfeRep.setXmlNFe(xmlProc);
										new RestNFeManager().inserir(nfeRep);
									
										notaFiscal = rest.atualizar(notaFiscal);
									break;
								case "301":
									updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
                					+"\n Uso Denegado");
	                                Thread.sleep(3000);
									notaFiscal.setSitNfe(SitNFe.DENEGADA);
									notaFiscal = rest.atualizar(notaFiscal);
									Thread.sleep(5000);
									break;
								case "302":
									updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
                					+"\n Uso Denegado");
	                                Thread.sleep(3000);
									notaFiscal.setSitNfe(SitNFe.DENEGADA);
									notaFiscal = rest.atualizar(notaFiscal);
									Thread.sleep(5000);
									break;
								case "204":
									updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
                									+"\n Codigo: "+retEnvio.getProtNFe().getInfProt().getCStat()+"\n"+retEnvio.getProtNFe().getInfProt().getXMotivo());
	                                Thread.sleep(3000);
									TRetConsSitNFe ret = new ConsultarNFe().consultar(
											UF.valueOf(MainApp.getProps().getProperty("UF")),
											MainApp.getProps().getProperty("ambiente"), 
											MainApp.getProps().getProperty("NfeConsultaProtocolo"), 
											notaFiscal.getChave());
									if(ret.getCStat().equals("100")){
										try {
											updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
		                					+"\n Nota já Autorizada\nRecuperando XML");
								            JAXBContext context = JAXBContext.newInstance(TRetConsSitNFe.class);
								            Marshaller marshaller = context.createMarshaller();
								            JAXBElement<TRetConsSitNFe> element = new ObjectFactory().createRetConsSitNFe(ret);
								            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
								            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

								            StringWriter sw = new StringWriter();
								            marshaller.marshal(element, sw);

								            String xmlRet = sw.toString();
								            String procNFe = new GerarProcNFe().gerarProcNFeEnvi(xmlEnvi.toString(), xmlRet);
								            updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
		                					+"\n Verificando Existencia no Repositorio");
								            NFe nfePes = new RestNFeManager().pesquisaChave(notaFiscal.getChave());
								            if(nfePes==null){
								            	nfeRep = new NFe();
								            	nfeRep.setChave(notaFiscal.getChave());
									    		nfeRep.setXmlNFe(procNFe);	    		
									    		nfeRep = new RestNFeManager().inserir(nfeRep);
									    		updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
			                					+"\n Não Existia\n Inserida com sucesso.");
								            }else{
								            	updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
			                					+"\n Já Localizado no Repositorio");
								            }
								            notaFiscal.setSitNfe(SitNFe.AUTORIZADA);
											notaFiscal = rest.atualizar(notaFiscal);
											
								        } catch (JAXBException ex) {
								            Logger.getLogger(ConversorNFe.class.getName()).log(Level.FATAL, null, ex);
								        }
									}
									break;
									
								default:
									updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
									+"\n Codigo: "+retEnvio.getProtNFe().getInfProt().getCStat()+"\n"+retEnvio.getProtNFe().getInfProt().getXMotivo());
                    				notaFiscal.setSitNfe(SitNFe.DIGITACAO);
									notaFiscal = rest.atualizar(notaFiscal);
									Thread.sleep(3000);
									break;
								}
							} catch (Exception e) {
								updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero()
								+"\n Erro ao Enviar Nota Fiscal\n\r"+e.getMessage());
	                            Thread.sleep(5000);
	                            e.printStackTrace();
									}
								}
							}
                        handleFiltrar();                       
                    }
                        return null;
                    }

                };
            }
        };
        Dialogs.create().owner(dialogStage).owner(dialogStage)
                .title("Transmitindo Notas").showWorkerProgress(service);

        service.start();
    }

    
    @FXML
    private void evento(){
    	if (PegarKs.getKS() == null) {
            CarregarCertificadoController cr = new CarregarCertificadoController();
            cr.setDialogStage(dialogStage);
            cr.setMainApp(mainApp);
            cr.show(null);
        }
    	
    	Optional<String> response = Dialogs.create()
    	        .owner(dialogStage)
    	        .title("Cancelamento")
    	        .masthead("Informações para Cancelamento")
    	        .message("Motivo:")
    	        .showTextInput("");

    	// One way to get the response value.
    	if (response.isPresent()) {
    	    if(!response.get().isEmpty() && response.get().length()>14){
    	    	NotaFiscal notaFiscal = notaFiscalTable.getSelectionModel().getSelectedItem();
    	    	
    	    	TRetConsSitNFe ret = new ConsultarNFe().consultar(
    					UF.valueOf(MainApp.getProps().getProperty("UF")),
    					MainApp.getProps().getProperty("ambiente"), 
    					MainApp.getProps().getProperty("NfeConsultaProtocolo"), 
    					notaFiscal.getChave());
    			if(ret.getCStat().equals("100")){
    				System.out.println(ret.getCStat());
    				System.out.println(ret.getXMotivo());
    				TpEvento tipo = TpEvento.CANCELAMENTO;
    				String ambiente = MainApp.getProps().getProperty("ambiente");
    				String nSeqEvento = "1";
    				String motivo = response.get();
    				String nProt = ret.getProtNFe().getInfProt().getNProt();
    				String url = MainApp.getProps().getProperty("RecepcaoEvento");
    				System.out.println(url);
    				EventoNFe evento = new EventoNFe();
    				TRetEnvEvento retEvento= evento.enviarEvento(tipo, ambiente, nSeqEvento, motivo, nProt, notaFiscal, url);
    				String proc = new GerarProcEvento().gerarProcNFeEnvi(evento.getEnvio(), evento.getRetorno());
    				System.out.println("Proc -------------------------------");
    				System.out.println(proc);
    				
    				notaFiscal.setSitNfe(SitNFe.CANCELADA);
    				notaFiscal = rest.atualizar(notaFiscal);
    				refreshPersonTable();
    				try {
    					Files.write(Paths.get(System.getProperty("user.home")+"/procEvento"
    							+ notaFiscal.getNumero() +"-"+ nSeqEvento+".xml"),
    							proc.toString().getBytes());
    				} catch (IOException ex) {
    					java.util.logging.Logger.getLogger(
    							CadastroNotaFiscalController.class
    									.getName()).log(
    							java.util.logging.Level.SEVERE, null,
    							ex);
    				}
    			}else if(ret.getCStat().equals("101")){
    				notaFiscal.setSitNfe(SitNFe.CANCELADA);
    				notaFiscal = rest.atualizar(notaFiscal);	
    				refreshPersonTable();
    			}else{
    				System.out.println(ret.getCStat());
    				System.out.println(ret.getXMotivo());
    			}
    	    }
    	}

    	
    	
    }    
    
    @FXML
    public void exportar()
    {
    	if (PegarKs.getKS() == null) {
            CarregarCertificadoController cr = new CarregarCertificadoController();
            cr.setDialogStage(dialogStage);
            cr.setMainApp(mainApp);
            cr.show(null);
        }
    	String pathAux = System.getProperty("user.home");
    	DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = 
                directoryChooser.showDialog(dialogStage);
        
        if(selectedDirectory!=null){
        	pathAux= selectedDirectory.getAbsolutePath();
        }
        
        final String path = pathAux;
        
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call()
                            throws InterruptedException {
                    	List<NotaFiscal> notas = notaFiscalTable.getSelectionModel().getSelectedItems();
                    	int nNotas = notas.size();
                    	int currentNota = 1;
                        for(NotaFiscal notaFiscal : notas){
                        
                        	NFe nfe = new RestNFeManager().pesquisaChave(notaFiscal.getChave());
                        	if(nfe!=null){
							if (!notaFiscal.getSitNfe().equals(SitNFe.AUTORIZADA)
									&& !notaFiscal.getSitNfe().equals(SitNFe.CANCELADA)) {
								try {
									notaFiscal.setSitNfe(SitNFe.AUTORIZADA);
									notaFiscal = rest.atualizar(notaFiscal);
								} catch (Exception e) {
									updateMessage("Erro ao Mudar Situacão da Nota");
								}
							}
                        		String xml = nfe.getXmlNFe();
                        		try {
        								Files.write(Paths.get(path+"/"
        										+ notaFiscal.getNumero()+".xml"),
        										xml.getBytes());
        								updateMessage("Exportado: "+path+"/"
        										+ notaFiscal.getNumero()+".xml");
        								updateProgress(currentNota, nNotas);
        								currentNota++;
        							                                  
                                 } catch (Exception e1) {
                                	 updateMessage("Erro\n"
                             				+ e1.getMessage());
                                     e1.printStackTrace();
                                 }
                        	}else{
                        		updateMessage("Não Localizada no Repositorio\n"
                        				+ "Transmita Novamente.");
                        		
                                try {
                                 	TNFe Tnfe = ConversorNFe.getnFe(notaFiscal, MainApp.getProps().getProperty("ambiente"));
                                     String xml = "";
									try {
										xml = new AssinaturaDigital().assinarDocumento(ConversorNFe.getXML(Tnfe));
									} catch (Exception e) {
										updateMessage("Erro\n\r"+e.getMessage());
				                            Thread.sleep(10000);
									}
									try {
										Files.write(Paths.get(path+"/"
        										+ notaFiscal.getNumero()+".xml"),
        										xml.getBytes());
        								updateMessage("Exportado: "+path+"/"
        										+ notaFiscal.getNumero()+".xml");
        								updateProgress(currentNota, nNotas);
        								currentNota++;
        							                                  
                                 } catch (Exception e1) {
                                	 updateMessage("Erro\n"
                             				+ e1.getMessage());
                             		Thread.sleep(5000);
                                     e1.printStackTrace();
                                 }
                                                                           
                                 } catch (Exception e1) {
                                     e1.printStackTrace();
                                 }
                        	}
                        	}                 
                 return null;
                    }
                };
            }
        };
        
        Dialogs.create()
        .owner(dialogStage)
        .title("Exportando Nota")
        .masthead("Carregando ...\nAguarde")
        .showWorkerProgress(service);

        service.start();
    }

    @FXML
    public void imprimir(){
    	if (PegarKs.getKS() == null) {
            CarregarCertificadoController cr = new CarregarCertificadoController();
            cr.setDialogStage(dialogStage);
            cr.setMainApp(mainApp);
            cr.show(null);
        }
    	final SwingNode swingNode = new SwingNode();
    	
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call()
                            throws InterruptedException {
                    	List<NotaFiscal> notas = notaFiscalTable.getSelectionModel().getSelectedItems();
                    	int nNotas = notas.size();
                    	int currentNota = 1;
                    	InputStream stream = null;
                        JRXmlDataSource xmlPath = null;
                        
                        HashMap<String, Object> parametros = new HashMap<String, Object>();
                        parametros.put("LOGO", MainApp.getProps().getProperty("logo"));
                        
                        JasperPrint paginas = null;
						try {
							paginas = JasperFillManager.fillReport(getClass().getResourceAsStream("/relatorios/danfeRetrato.jasper"), parametros,new JREmptyDataSource());
							paginas.removePage(0);
						} catch (JRException e2) {
							e2.printStackTrace();
						}                        
                        
                        for(NotaFiscal notaFiscal : notas){
                        
                        	NFe nfe = new RestNFeManager().pesquisaChave(notaFiscal.getChave());
                        	if(nfe!=null){
							if (!notaFiscal.getSitNfe().equals(SitNFe.AUTORIZADA)
									&& !notaFiscal.getSitNfe().equals(SitNFe.CANCELADA)) {
								try {
									notaFiscal.setSitNfe(SitNFe.AUTORIZADA);
									notaFiscal = rest.atualizar(notaFiscal);
								} catch (Exception e) {
									updateMessage("Erro ao Mudar Situacão da Nota");
								}
							}
							String xml = nfe.getXmlNFe();
                    		try {
                    			updateMessage("Processando: Nota Fiscal "+notaFiscal.getNumero());
								updateProgress(currentNota, nNotas);
								currentNota++;
								
                                 stream = new ByteArrayInputStream(xml.getBytes());
                                 xmlPath = new JRXmlDataSource(stream, "/nfeProc/NFe/infNFe/det");
                                 
                                 JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("/relatorios/danfeRetrato.jasper"), parametros, xmlPath);
                                 
                                 List<JRPrintPage> pgs = jp.getPages();                                 
                                 for (JRPrintPage pg : pgs) {
                                     paginas.addPage(pg);
                                 }                                 
                                                                      
                             } catch (JRException e1) {
                            	 updateMessage("Erro\n"
                         				+ e1.getMessage());
                         		Thread.sleep(5000);
                                 e1.printStackTrace();
                             }
                        	}else{
                        		updateMessage("Não Localizada no Repositorio\n"
                        				+ "Transmita Novamente.");
                        		
                                try {
                                 	TNFe Tnfe = ConversorNFe.getnFe(notaFiscal, MainApp.getProps().getProperty("ambiente"));
                                     String xml = "";
									try {
										xml = new AssinaturaDigital().assinarDocumento(ConversorNFe.getXML(Tnfe));
									} catch (Exception e) {
										updateMessage("Erro\n\r"+e.getMessage());
				                            Thread.sleep(10000);
									}
									try {
										updateMessage("Processando: Nota Fiscal"+notaFiscal.getNumero());
										updateProgress(currentNota, nNotas);
										currentNota++;
										
		                                 stream = new ByteArrayInputStream(xml.getBytes());
		                                 xmlPath = new JRXmlDataSource(stream, "/nfeProc/NFe/infNFe/det");
		                                 
		                                 JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("/relatorios/danfeRetrato.jasper"), parametros, xmlPath);

		                                 List<JRPrintPage> pgs = jp.getPages();                                 
		                                 for (JRPrintPage pg : pgs) {
		                                     paginas.addPage(pg);
		                                 } 
        							                                  
                                 } catch (Exception e1) {
                                	 updateMessage("Erro\n"
                             				+ e1.getMessage());
                             		Thread.sleep(5000);
                                     e1.printStackTrace();
                                 }
                                                                           
                                 } catch (Exception e1) {
                                     e1.printStackTrace();
                                 }
                        	}
                        	}
                        swingNode.setContent(new JRViewer(paginas));
                 return null;
                    }
                };
            }
        };
        
        Dialogs.create()
        .owner(dialogStage)
        .title("Impressão de Notas")
        .masthead("Carregando ...\nAguarde")
        .showWorkerProgress(service);

        service.start();
        
        Stage stage = new Stage();        
        stage.setTitle("Danfe");
        stage.initModality(Modality.WINDOW_MODAL);
        StackPane pane = new StackPane();
        pane.getChildren().add(swingNode);        
        stage.setScene(new Scene(pane,960,668));
        stage.initOwner(dialogStage); 
        stage.showAndWait();
    }
}
