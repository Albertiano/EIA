package br.net.eia.ui.produto;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.net.eia.produto.Produto;
import br.net.eia.ui.MainApp;

@SuppressWarnings("restriction")
public class ProdutoController {
	
	private Produto produtoSelecionado;
	
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public boolean showPesquisaProdutoDialog(MainApp mainApp, Stage dialogStageMain) {
    	try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/produto/CadastroProduto.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Contatos");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.initOwner(dialogStageMain);
            Scene scene = new Scene(overviewPage);
            dialogStage.setScene(scene);

            // Give the controller access to the main app
            CadastroProdutoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);  
            dialogStage.showAndWait();
            setProdutoSelecionado(controller.getProduto());
            return controller.isOkClicked();

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(
                    Level.ERROR, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    	return false;
	}
}
