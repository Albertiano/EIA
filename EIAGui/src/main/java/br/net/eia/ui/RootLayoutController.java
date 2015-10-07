package br.net.eia.ui;

import br.net.eia.compra.ItemCompra;
import br.net.eia.contato.Contato;
import br.net.eia.contato.TpContato;
import br.net.eia.emitente.Emitente;
import br.net.eia.municipio.Municipio;
import br.net.eia.nfe.v310.TNfeProc;
import br.net.eia.produto.FornecedorProduto;
import br.net.eia.produto.Produto;
import br.net.eia.ui.certificado.CarregarCertificadoController;
import br.net.eia.ui.contato.CadastroContatoController;
import br.net.eia.ui.contato.EditContatoController;
import br.net.eia.ui.contato.RestContatoManager;
import br.net.eia.ui.emitente.EditEmitenteController;
import br.net.eia.ui.emitente.RestEmitenteManager;
import br.net.eia.ui.notafiscal.CadastroNotaFiscalController;
import br.net.eia.ui.produto.CadastroProdutoController;
import br.net.eia.ui.produto.EditProdutoController;
import br.net.eia.ui.produto.RestProdutoManager;
import br.net.eia.ui.produto.imposto.CadastroTributacaoController;
import br.net.eia.ui.produto.unidade.CadastroUnidadeController;
import br.net.eia.ui.produto.unidade.RestUnidadeManager;
import br.net.eia.util.nfe.ConversorNFe;
import br.net.eia.xml.lerXML_JAXB;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.commons.collections.functors.ExceptionFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@SuppressWarnings("restriction")
public class RootLayoutController extends BorderPane {

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

	public MainApp getMainApp() {
		return mainApp;
	}

	@FXML
	public void showCadCContato(ActionEvent event) {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					MainApp.class
							.getResource("/fxml/contato/CadastroContato.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastro de Contatos");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(overviewPage);
			dialogStage.setScene(scene);

			// Give the controller access to the main app
			CadastroContatoController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(mainApp);

			dialogStage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
			e.printStackTrace();
		}

	}

	@FXML
	public void showCadProduto(ActionEvent event) {

		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					MainApp.class
							.getResource("/fxml/produto/CadastroProduto.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastro de Produto");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(overviewPage);
			dialogStage.setScene(scene);

			// Give the controller access to the main app
			CadastroProdutoController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(mainApp);

			dialogStage.showAndWait();

		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}

	}

	@FXML
	public void showCadUnidade(ActionEvent event) {

		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					MainApp.class
							.getResource("/fxml/produto/unidade/CadastroUnidade.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastro de Unidade");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(overviewPage);
			dialogStage.setScene(scene);

			// Give the controller access to the main app
			CadastroUnidadeController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(mainApp);

			dialogStage.showAndWait();

		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}

	}

	@FXML
	public void showCadCompra(ActionEvent event) {


	}

	@FXML
	public void showCadTributos(ActionEvent event) {

		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					MainApp.class
							.getResource("/fxml/produto/imposto/CadastroTributacao.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastro de Tributa��o");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(overviewPage);
			dialogStage.setScene(scene);

			// Give the controller access to the main app
			CadastroTributacaoController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(mainApp);

			dialogStage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}

	}

	@FXML
	public void showCadEmitente(ActionEvent event) {
		if (MainApp.getEmitente() == null) {
			Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setHeaderText("Connection Failed");
            dialog.setContentText("Nenhuma Empresa Encontrada.");
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);

            dialog.showAndWait();
			mainApp.userLogout();
		} else {
			try {
				String fxml = "/fxml/emitente/EditEmitente.fxml";
				FXMLLoader loader = new FXMLLoader();
				InputStream in = MainApp.class.getResourceAsStream(fxml);
				loader.setBuilderFactory(new JavaFXBuilderFactory());
				loader.setLocation(MainApp.class.getResource(fxml));
				BorderPane page;
				try {
					page = (BorderPane) loader.load(in);
				} finally {
					in.close();
				}
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Dados da Empresa");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				// dialogStage.getIcons().add(new
				// Image("file:resources/images/edit.png"));
				dialogStage.initOwner(mainApp.getPrimaryStage());
				Scene scene = new Scene(page);
				dialogStage.setScene(scene);

				// Set the person into the controller
				EditEmitenteController controller = loader.getController();
				controller.setDialogStage(dialogStage);
				controller.setEmitente(MainApp.getEmitente());
				controller.setMainApp(mainApp);
				controller.exibirEmitente(MainApp.getEmitente());

				// Show the dialog and wait until the user closes it
				dialogStage.showAndWait();

				boolean okClicked = controller.isOkClicked();
				if (okClicked) {

					Emitente client = new RestEmitenteManager()
							.atualizarCliente(mainApp.getEmitente());
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
		            dialog.setHeaderText("Connection Failed");
		            dialog.setContentText("Alterado com sucesso.");
		            dialog.setResizable(true);
		            dialog.getDialogPane().setPrefSize(480, 320);
		            dialog.showAndWait();
				}

			} catch (IOException e) {
				Logger.getLogger(getClass().getName()).log(Level.ERROR,
						e.getLocalizedMessage(), e);
				Alert dialog = new Alert(Alert.AlertType.ERROR);
	            dialog.setHeaderText("Connection Failed");
	            dialog.setContentText(e.getMessage());
	            dialog.setResizable(true);
	            dialog.getDialogPane().setPrefSize(480, 320);
	            dialog.showAndWait();
			}

		}
	}

	@FXML
	public void showCadNotaFiscal(ActionEvent event) {

		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					MainApp.class
							.getResource("/fxml/notafiscal/CadastroNotaFiscal.fxml"));
			BorderPane overviewPage = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Notas Fiscais");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(overviewPage);
			dialogStage.setScene(scene);

			// Give the controller access to the main app
			CadastroNotaFiscalController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(mainApp);

			dialogStage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
		}

	}

	@FXML
	private void carregarXML2() {
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Notas Fiscais");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		// dialogStage.getIcons().add(new
		// Image("file:resources/images/edit.png"));
		dialogStage.initOwner(mainApp.getPrimaryStage());
		
		

		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		File defaultDirectory = new File(System.getProperty("user.home"));
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(dialogStage);
		
		FilenameFilter filtro = new FilenameFilter() {
    	    public boolean accept(File dir, String name) {  
    	        return name.endsWith("-procNfe.xml");   
    	    }  
    	};  
    	File arquivos = new File(selectedDirectory.getAbsolutePath());  
    	String[] nomeArquivos = arquivos.list(filtro); 
		
    	 for (String nomeArquivo : nomeArquivos) {
    		 
    	 
		Contato fornecedor = null;
		
			TNfeProc nfe = new lerXML_JAXB()
					.getTNfeProc(selectedDirectory+ "/" + nomeArquivo);

			try {
				fornecedor = new RestContatoManager().pesquisaNumDoc(mainApp.getEmitente().getToken().toString(),nfe
						.getNFe().getInfNFe().getDest().getCNPJ().replaceAll("[^0-9]", ""));
				
				Alert dialog = new Alert(Alert.AlertType.INFORMATION);
	            dialog.setHeaderText(fornecedor.getNome() + "\n"
						+ fornecedor.getMunicipio());
	            dialog.setContentText("Cadastrado com sucesso.");
	            dialog.setResizable(true);
	            dialog.getDialogPane().setPrefSize(480, 320);
	            dialog.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
								
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			    alert.setTitle("Em andamento.");
			    alert.setHeaderText(nfe.getNFe().getInfNFe().getDest().getXNome()
						+ "\nNão Encontrado");
			    alert.setContentText("Deseja Fazer o Cadastramento?");

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
					Contato client = new ConversorNFe().forContato(nfe);
					Municipio mun = new MunicipioClientRest().carregar(Integer
							.parseInt(nfe.getNFe().getInfNFe().getDest()
									.getEnderDest().getCMun()));
					client.setMunicipio(mun);
					client.setUf(mun.getUF());
					client.setTpContato(TpContato.CLIENTE);
					boolean okClicked = showEditFornecedorDialog(client);
					if (okClicked) {
						RestContatoManager cM = new RestContatoManager();
						fornecedor = cM.inserir(client);
						Alert dialog = new Alert(Alert.AlertType.INFORMATION);
			            dialog.setHeaderText(fornecedor.getNome() + "\n"
								+ fornecedor.getMunicipio());
			            dialog.setContentText("Inserido com sucesso.");
			            dialog.setResizable(true);
			            dialog.getDialogPane().setPrefSize(480, 320);
			            dialog.showAndWait();
					}

				}
			}
			for (int i = 0; i < nfe.getNFe().getInfNFe().getDet().size(); i++) {

				Produto produto = null;
				try {
					produto = new RestProdutoManager().pesquisar(
							MainApp.getEmitente(), nfe.getNFe().getInfNFe()
									.getDet().get(i).getProd().getCProd());
					
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
		            dialog.setHeaderText(produto.getCodigo() + "\n"
							+ produto.getDescricao());
		            dialog.setContentText("Já Cadastrado.");
		            dialog.setResizable(true);
		            dialog.getDialogPane().setPrefSize(480, 320);
		            dialog.showAndWait();
				} catch (Exception e) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Em andamento.");
					String s = "Produto Não Encontrado.\n"
							+ nfe.getNFe().getInfNFe().getDet().get(i).getProd().getCProd() + "\n"
							+ nfe.getNFe().getInfNFe().getDet().get(i).getProd().getXProd() + "\n"
							+ "Deseja Fazer o Cadastramento?";
					alert.setContentText(s);

					alert.getButtonTypes().clear();
				    alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

				    Button yesButton = (Button) alert.getDialogPane().lookupButton( ButtonType.YES );
				    yesButton.setDefaultButton( true );

				    Button noButton = (Button) alert.getDialogPane().lookupButton( ButtonType.NO );
				    noButton.setDefaultButton( false );

				    final Optional<ButtonType> result = alert.showAndWait();
				    
				    if (result.isPresent() && result.get() == ButtonType.OK) {
				    	Produto tempPerson = new ConversorNFe()
								.forProduto(((nfe.getNFe().getInfNFe().getDet()
										.get(i))));
						new RestUnidadeManager();
						boolean okClicked = showProdutoEditDialog(tempPerson);
						if (okClicked) {
							produto = new RestProdutoManager()
									.inserir(tempPerson);
							Alert dialog = new Alert(Alert.AlertType.INFORMATION);
				            dialog.setHeaderText(produto.getCodigo() + "\n"
									+ produto.getDescricao());
				            dialog.setContentText("Inserido com Sucesso.");
				            dialog.setResizable(true);
				            dialog.getDialogPane().setPrefSize(480, 320);
				            dialog.showAndWait();
						}
				    }
				}

			}

		}
	}

	@FXML
	private void carregarXML() {
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Notas Fiscais");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		// dialogStage.getIcons().add(new
		// Image("file:resources/images/edit.png"));
		dialogStage.initOwner(mainApp.getPrimaryStage());
		
		

		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		File defaultDirectory = new File(System.getProperty("user.home"));
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(dialogStage);
		
		FilenameFilter filtro = new FilenameFilter() {
    	    public boolean accept(File dir, String name) {  
    	        return name.endsWith(".xml");   
    	    }  
    	};  
    	File arquivos = new File(selectedDirectory.getAbsolutePath());  
    	String[] nomeArquivos = arquivos.list(filtro); 
		
    	 for (String nomeArquivo : nomeArquivos) {
    		 
    	 
		Contato fornecedor = null;
		
			TNfeProc nfe = new lerXML_JAXB()
					.getTNfeProc(selectedDirectory+ "/" + nomeArquivo);

			try {
				fornecedor = new RestContatoManager().pesquisaNumDoc(MainApp.getEmitente().getToken().toString(),nfe
						.getNFe().getInfNFe().getDest().getCNPJ());
				
			}catch (Exception e) {
				e.printStackTrace();
				
				if (true) {					
					try {
						Contato client = new ConversorNFe().forContato(nfe);
						Municipio mun = new MunicipioClientRest().carregar(Integer
								.parseInt(nfe.getNFe().getInfNFe().getDest()
										.getEnderDest().getCMun()));
						client.setMunicipio(mun);
						client.setUf(mun.getUF());
						client.setTpContato(TpContato.CLIENTE);
						boolean okClicked = showEditFornecedorDialog(client);
						if (okClicked) {
							RestContatoManager cM = new RestContatoManager();
							fornecedor = cM.inserir(client);
							
						}						
					} catch (Exception exx) {
						exx.printStackTrace();
					}
				}
			}
			for (int i = 0; i < nfe.getNFe().getInfNFe().getDet().size(); i++) {

				Produto produto = null;
				try {
					produto = new RestProdutoManager().pesquisar(
							MainApp.getEmitente(), nfe.getNFe().getInfNFe()
									.getDet().get(i).getProd().getCProd());
					
				} catch (Exception e) {
					
					if (true) {
						try{
							Produto tempPerson = new ConversorNFe()
							.forProduto(((nfe.getNFe().getInfNFe().getDet()
									.get(i))));
					boolean okClicked = showProdutoEditDialog(tempPerson);
					if (okClicked) {
						produto = new RestProdutoManager()
								.inserir(tempPerson);
						
					}
						}catch(Exception ex){
							e.printStackTrace();
						}
					}
				}

			}

		}
	}


	public boolean showEditFornecedorDialog(Contato person) {
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
			controller.setMainApp(mainApp);
			controller.setContato(person);
			controller.exibirContato(person);

			// Show the dialog and wait until the user closes it
			dialogStage2.show();
			controller.handleBtSalvar(null);

			return controller.isOkClicked();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
			return false;
		}
	}

	public boolean showProdutoEditDialog(Produto person) {
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("/fxml/produto/EditProduto.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage2 = new Stage();
			dialogStage2.setTitle("Cadastro de Produto");
			dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
			// Image("file:resources/images/edit.png"));
			dialogStage2.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage2.setScene(scene);

			// Set the person into the controller
			EditProdutoController controller = loader.getController();
			controller.setDialogStage(dialogStage2);
			controller.setProduto(person);
			controller.setMainApp(mainApp);
			controller.exibirProduto();

			// Show the dialog and wait until the user closes it
			dialogStage2.show();
			controller.handleBtSalvar(null);
			return controller.isOkClicked();

		} catch (Exception e) {
			// Exception gets thrown if the fxml file could not be loaded
			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);
			e.printStackTrace();
			Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setHeaderText("Erro");
            dialog.setContentText(e.getMessage());
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            return false;
		}
	}

	public void setApp(MainApp main) {
		this.mainApp = main;

	}

}
