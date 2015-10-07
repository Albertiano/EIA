package br.net.eia.ui.produto.imposto;

import br.net.eia.enums.UF;
import br.net.eia.produto.imposto.Destino;
import br.net.eia.produto.imposto.Tributacao;
import br.net.eia.produto.imposto.Tributo;
import br.net.eia.produto.imposto.cofins.COFINS;
import br.net.eia.produto.imposto.cofins.CST_COFINS;
import br.net.eia.produto.imposto.icms.CST_ICMS;
import br.net.eia.produto.imposto.icms.ICMS;
import br.net.eia.produto.imposto.icms.Origem;
import br.net.eia.produto.imposto.ipi.CST_IPI;
import br.net.eia.produto.imposto.ipi.IPI;
import br.net.eia.produto.imposto.pis.CST_PIS;
import br.net.eia.produto.imposto.pis.PIS;
import br.net.eia.ui.MainApp;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CadastroTributacaoController implements Initializable {

    // Reference to the main application
    private MainApp mainApp;
    private ObservableList<Tributacao> produtoData = FXCollections
            .observableArrayList();
    @FXML
    private TableView<Tributacao> clienteTable;
    @FXML
    private TableColumn<Tributacao, String> nomeNameColumn;
    @FXML
    private TableColumn<Tributacao, String> descricaoNameColumn;
    @FXML
    TextField tfNome;
    @FXML
    TextField tfDescricao;

    private RestTributacaoManager cM;

    private Stage dialogStage;

    public CadastroTributacaoController() {
        cM = new RestTributacaoManager();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Initialize the person table
        nomeNameColumn
                .setCellValueFactory(new PropertyValueFactory<Tributacao, String>(
                                "nome"));
        descricaoNameColumn
                .setCellValueFactory(new PropertyValueFactory<Tributacao, String>(
                                "descricao"));

        // Auto resize columns
        clienteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    @FXML
    private void handleNew() {
        Tributacao temp = new Tributacao();
        temp.setEmitente(MainApp.getEmitente());
        temp.setNome("Padrão");
        temp.setDescricao("Padrao do sistema");
        temp.setDestino(createDestinos());

        boolean okClicked = showProdutoEditDialog(temp);
        if (okClicked) {
            Tributacao trib = cM.inserir(temp);
            
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setHeaderText("Inserido com sucesso.");
            dialog.setContentText(trib.getNome() + "\n" + trib.getDescricao());
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(480, 320);
            dialog.showAndWait();
            
            produtoData.add(trib);
            refreshPersonTable();
        }
    }

    private List<Destino> createDestinos() {
        List<Destino> destinos = new ArrayList<>();
        for (UF uf : UF.values()) {
            Destino d = new Destino();            
            d.setEstado(uf);
            d.setTributos(createTributos900());
            destinos.add(d);
        }
        return destinos;
    }

    private Tributo createTributos900() {
        ICMS icms = new ICMS();
        icms.setCstICMS(CST_ICMS.SN_900);
        icms.setOrigem(Origem.NACIONAL);
        icms.setvBCICMS(BigDecimal.ZERO);        
        icms.setpICMS(BigDecimal.ZERO);
        icms.setvICMS(BigDecimal.ZERO);
        icms.setpMVAST(BigDecimal.ZERO);
        icms.setpICMSST(BigDecimal.ZERO);
        IPI ipi = new IPI();
        ipi.setCstIPI(CST_IPI.IPI_52);
        PIS pis = new PIS();
        pis.setCstPIS(CST_PIS.PIS_07);
        COFINS cofins = new COFINS();
        cofins.setCstCOFINS(CST_COFINS.COFINS_07);

        Tributo t = new Tributo();
        t.setNome("Padrao");
        t.setDescricao("Padrao");
        t.setCfop("5102");
        t.setIcms(icms);
        t.setIpi(ipi);
        t.setPis(pis);
        t.setCofins(cofins);

        return t;
    }

    public boolean showProdutoEditDialog(Tributacao person) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/fxml/produto/imposto/EditTributacao.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage2 = new Stage();
            dialogStage2.setTitle("Cadastro de Tributos");
            dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
            // Image("file:resources/images/edit.png"));
            dialogStage2.initOwner(dialogStage);
            Scene scene = new Scene(page);
            dialogStage2.setScene(scene);

            // Set the person into the controller
            EditTributacaoController controller = loader.getController();
            controller.setDialogStage(dialogStage2);
            controller.setTributacao(person);
            controller.setMainApp(mainApp);
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
        Tributacao selectedPerson = clienteTable.getSelectionModel()
                .getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = showProdutoEditDialog(selectedPerson);
            if (okClicked) {
                Tributacao client = cM.atualizar(selectedPerson);
                
                Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.setHeaderText("Alterado com sucesso.");
                dialog.setContentText(client.getNome() + "\n" + client.getDescricao());
                dialog.setResizable(true);
                dialog.getDialogPane().setPrefSize(480, 320);
                dialog.showAndWait();
                
                refreshPersonTable();
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
    private void handleDelete() {
        int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Tributacao selectedPerson = clienteTable.getSelectionModel()
                    .getSelectedItem();

            boolean deletado = cM.remover(selectedPerson);

            if (deletado) {
                clienteTable.getItems().remove(selectedIndex);
                refreshPersonTable();
                Alert dialog = new Alert(Alert.AlertType.INFORMATION);
	            dialog.setHeaderText("Removido com sucesso.");
	            dialog.setContentText(selectedPerson.getNome() + "\n" + selectedPerson.getDescricao());
	            dialog.setResizable(true);
	            dialog.getDialogPane().setPrefSize(480, 320);
	            dialog.showAndWait();
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
    private void handleFechar() {
        dialogStage.close();
    }

    @FXML
    private void handleTodos() {
        try {
            produtoData = new RestTributacaoManager().getAll(MainApp.getEmitente());
            clienteTable.setItems(produtoData);
        } catch (Exception e) {
            produtoData = FXCollections.observableArrayList();
        }
    }

}
