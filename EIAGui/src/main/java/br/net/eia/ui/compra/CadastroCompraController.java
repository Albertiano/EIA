package br.net.eia.ui.compra;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;

import br.net.eia.compra.Compra;
import br.net.eia.contato.Contato;
import br.net.eia.enums.StatusOperacao;
import br.net.eia.ui.MainApp;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.ConversorDate;
import br.net.eia.util.Formatador;

@SuppressWarnings("restriction")
public class CadastroCompraController implements Initializable {

    // Reference to the main application
    private MainApp mainApp;
    private ObservableList<Compra> produtoData = FXCollections
            .observableArrayList();
    @FXML
    private TableView<Compra> clienteTable;
    @FXML
    private TableColumn<Compra, Date> emissaoNameColumn;
    @FXML
    private TableColumn<Compra, Date> entradaNameColumn;
    @FXML
    private TableColumn<Compra, Contato> fornecedorNameColumn;
    @FXML
    private TableColumn<Compra, String> pedidoNameColumn;
    @FXML
    private TableColumn<Compra, String> notaFiscalNameColumn;
    @FXML
    private TableColumn<Compra, BigDecimal> vCompraNameColumn;

    @FXML
    private TextField tfDataIni;
    @FXML
    private TextField tfDataFim;
    @FXML
    private TextField tfPedido;
    @FXML
    private TextField tfNotaFiscal;
    @FXML
    private TextField tfFornecedor;

    private RestCompraManager cM;

    private Stage dialogStage;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public CadastroCompraController() {
        cM = new RestCompraManager();
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
        emissaoNameColumn
                .setCellValueFactory(new PropertyValueFactory<Compra, Date>(
                                "emissao"));
        entradaNameColumn
                .setCellValueFactory(new PropertyValueFactory<Compra, Date>(
                                "entrada"));
        fornecedorNameColumn
                .setCellValueFactory(new PropertyValueFactory<Compra, Contato>(
                                "fornecedor"));
        pedidoNameColumn
                .setCellValueFactory(new PropertyValueFactory<Compra, String>(
                                "pedido"));
        notaFiscalNameColumn
                .setCellValueFactory(new PropertyValueFactory<Compra, String>(
                                "notaFiscal"));
        vCompraNameColumn
                .setCellValueFactory(new PropertyValueFactory<Compra, BigDecimal>(
                                "compra"));

        // Auto resize columns
        clienteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        emissaoNameColumn.setCellFactory(new Callback<TableColumn<Compra, Date>, TableCell<Compra, Date>>() {
            @Override
            public TableCell<Compra, Date> call(
                    TableColumn<Compra, Date> param) {
                TableCell<Compra, Date> cell = new TableCell<Compra, Date>() {
                    @Override
                    public void updateItem(Date item,
                            boolean empty) {
                        if (item != null) {
                            super.setText(ConversorDate.retornaData(item));
                        }
                        this.setAlignment(Pos.CENTER);
                    }
                };
                return cell;
            }
        });

        entradaNameColumn.setCellFactory(new Callback<TableColumn<Compra, Date>, TableCell<Compra, Date>>() {
            @Override
            public TableCell<Compra, Date> call(
                    TableColumn<Compra, Date> param) {
                TableCell<Compra, Date> cell = new TableCell<Compra, Date>() {
                    @Override
                    public void updateItem(Date item,
                            boolean empty) {
                        if (item != null) {
                            super.setText(ConversorDate.retornaData(item));
                        }
                        this.setAlignment(Pos.CENTER);
                    }
                };
                return cell;
            }
        });

        vCompraNameColumn
                .setCellFactory(new Callback<TableColumn<Compra, BigDecimal>, TableCell<Compra, BigDecimal>>() {
                    @Override
                    public TableCell<Compra, BigDecimal> call(
                            TableColumn<Compra, BigDecimal> param) {
                                TableCell<Compra, BigDecimal> cell = new TableCell<Compra, BigDecimal>() {
                                    @Override
                                    public void updateItem(BigDecimal item,
                                            boolean empty) {
                                        if (item != null) {
                                            Formatador formatter = new Formatador();
                                            super.setText(ConversorBigDecimal.paraString(item, 2));

                                        }
                                        this.setAlignment(Pos.CENTER_RIGHT);
                                    }
                                };

                                return cell;
                            }
                });

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
        Compra tempPerson = new Compra();
        tempPerson.setStatus(StatusOperacao.EM_ABERTO);
        boolean okClicked = showEditDialog(tempPerson);
        if (okClicked) {
            Compra compra = tempPerson;
            if (tempPerson.getStatus() != StatusOperacao.FINALIZADO) {
                compra = cM.inserir(tempPerson);
            }
            Dialogs.create().owner(dialogStage)
                    .title("Aviso")
                    .masthead(compra.getFornecedor().getNome() + "\n"
                            + compra.getCompra()
                            + "\nInserido com sucesso.")
                    .message(
                            compra.getFornecedor().getNome() + "\n"
                            + compra.getCompra()
                            + "\nInserido com sucesso.")
                    .showInformation();
            produtoData.add(compra);
            refreshPersonTable();
        }
    }

    public boolean showEditDialog(Compra person) {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/fxml/compra/EditCompra.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage2 = new Stage();
            dialogStage2.setTitle("Cadastro de Compras");
            dialogStage2.initModality(Modality.WINDOW_MODAL);
			// dialogStage.getIcons().add(new
            // Image("file:resources/images/edit.png"));
            dialogStage2.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage2.setScene(scene);

            // Set the person into the controller
            EditCompraController controller = loader.getController();
            controller.setDialogStage(dialogStage2);
            controller.setMainApp(mainApp);
            controller.setProduto(person);
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
        Compra selectedPerson = clienteTable.getSelectionModel()
                .getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = showEditDialog(selectedPerson);
            if (okClicked) {
                if (selectedPerson.getStatus() != StatusOperacao.FINALIZADO) {
                    Compra client = cM.atualizar(selectedPerson);

                    Dialogs.create().owner(dialogStage)
                            .title("Aviso")
                            .masthead(client.getFornecedor() + "\n"
                                    + client.getCompra()
                                    + "Alterado com sucesso.")
                            .message(
                                    client.getFornecedor() + "\n"
                                    + client.getCompra()
                                    + "Alterado com sucesso.")
                            .showInformation();

                }
                refreshPersonTable();
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
        int selectedIndex = clienteTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Compra selectedPerson = clienteTable.getSelectionModel()
                    .getSelectedItem();

            if (selectedPerson.getStatus()
                    .equals(StatusOperacao.FINALIZADO)) {
                Action response2 = Dialogs
                        .create().owner(dialogStage)
                        .title("Exclusão em andamento.")
                        .message(
                                "Deseja Finalizar e fazer laçamentos no estoque?")
                        .actions(Actions.YES, Actions.NO)
                        .showConfirm();

                if (response2.equals(Actions.YES)) {

                    boolean deletado = cM.atualizarRemover(selectedPerson);
                    Dialogs.create()
                            .owner(dialogStage)
                            .title("Aviso")
                            .masthead(selectedPerson.getFornecedor() + "\n"
                                    + selectedPerson.getCompra())
                            .message(
                                    "Removido com sucesso")
                            .showInformation();
                    if (deletado) {
                        clienteTable.getItems().remove(selectedIndex);
                        refreshPersonTable();
                    }

                }

            } else {
                Action response2 = Dialogs
                        .create().owner(dialogStage)
                        .title("Exclusão em andamento.")
                        .message(
                                "Deseja Finalizar e fazer laçamentos no estoque?")
                        .actions(Actions.YES, Actions.NO)
                        .showConfirm();

                if (response2.equals(Actions.YES)) {

                    boolean deletado = cM.remover(selectedPerson);
                    Dialogs.create()
                            .owner(dialogStage)
                            .title("Aviso")
                            .masthead(selectedPerson.getFornecedor() + "\n"
                                    + selectedPerson.getCompra())
                            .message(
                                    "Removido com sucesso")
                            .showInformation();
                    if (deletado) {
                        clienteTable.getItems().remove(selectedIndex);
                        refreshPersonTable();
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
    private void handleFiltrar() {
        Date dataIni = ConversorDate.gerarData(tfDataIni.getText());
        Date dataFim = ConversorDate.gerarData(tfDataFim.getText());
        String pedido = tfPedido.getText();
        String notaFiscal = tfNotaFiscal.getText();
        String fornecedor = tfFornecedor.getText();
        try {
            produtoData = new RestCompraManager().filtrar(MainApp.getEmitente(), dataIni, dataFim, pedido,
                    notaFiscal, fornecedor);
            refreshPersonTable();
        } catch (Exception e) {
            produtoData = FXCollections.observableArrayList();
            refreshPersonTable();
            Logger.getLogger(getClass().getName()).log(
                    Level.ERROR, e.getLocalizedMessage(), e);
        }
    }

    @FXML
    private void handleTodos() {
        try {
            produtoData = new RestCompraManager().filtrar(
                    MainApp.getEmitente(), null, null, "", "", "");
            clienteTable.setItems(produtoData);
        } catch (Exception e) {
            produtoData = FXCollections.observableArrayList();
        }
    }

}
