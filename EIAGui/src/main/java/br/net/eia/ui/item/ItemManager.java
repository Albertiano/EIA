package br.net.eia.ui.item;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import br.net.eia.enums.UF;
import br.net.eia.item.DetalheFiscal;
import br.net.eia.item.Item;
import br.net.eia.produto.Produto;
import br.net.eia.produto.imposto.Destino;
import br.net.eia.produto.imposto.Tributacao;
import br.net.eia.produto.imposto.Tributo;
import br.net.eia.produto.imposto.cofins.COFINS;
import br.net.eia.produto.imposto.cofins.COFINSST;
import br.net.eia.produto.imposto.icms.ICMS;
import br.net.eia.produto.imposto.icms.ModBC;
import br.net.eia.produto.imposto.icms.ModBCST;
import br.net.eia.produto.imposto.ipi.IPI;
import br.net.eia.produto.imposto.ipi.TpCalcIPI;
import br.net.eia.produto.imposto.pis.PIS;
import br.net.eia.produto.imposto.pis.PISST;
import br.net.eia.ui.MainApp;
import br.net.eia.ui.contato.CadastroContatoController;

@SuppressWarnings("restriction")
public class ItemManager {

	private Item item;
	private int nItem = 0;
	
	public void setnItem(int nItem) {
		this.nItem = nItem;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	
	public boolean showEditItemDialog(MainApp mainApp) {
    	try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/contato/CadastroContato.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Manutenção de Item");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(overviewPage);
            dialogStage.setScene(scene);

            // Give the controller access to the main app
            CadastroContatoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);  
            dialogStage.showAndWait();
  //          setItem(controller.getContato());
            return controller.isOkClicked();

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(
                    Level.ERROR, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    	return false;
	}
	
	public Item atualizarItem(Item i, BigDecimal quant, BigDecimal vUn,UF uf){
		BigDecimal subtotal = quant.multiply(vUn);
		i.setQuantidade(quant);
		i.setPrecoVenda(vUn);
		Produto p = i.getProduto();
		i.setPesoBruto(p.getPesoBruto().multiply(quant));
		i.setPesoLiquido(p.getPesoLiquido().multiply(quant));
		i.setSubtotal(subtotal);
		i.setvFrete(BigDecimal.ZERO);
		i.setvDesc(BigDecimal.ZERO);
		i.setvSeg(BigDecimal.ZERO);
		i.setvOutro(BigDecimal.ZERO);
		i.setNoValorNota(false);
		
		Tributo t = getTributo(p.getTributacao(), uf);		
		DetalheFiscal d  = new DetalheFiscal();
		d.setCfop(t.getCfop());
		d.setExtipi(p.getExtipi());
		d.setGenero(p.getGenero());
		d.setcEan(p.getcEan());
		d.setcEanTrib(p.getcEanTrib());
		if(p.getUtrib()!=null){
			d.setUtrib(p.getUtrib().getSigla());
		}		
		d.setqTrib(quant);
		d.setVuntrib(vUn);
		
		/**************   IPI  Inicio ****************************/
		IPI ipi = t.getIpi();
		if (ipi.getTpCalcIPI() == TpCalcIPI.ALIQUOTA) {
			ipi.setvBCIPI(i.getSubtotal());
			ipi.setvIPI(calculoPorcentagem(ipi.getvBCIPI(), ipi.getpIPI()));
		}
		
		if (ipi.getTpCalcIPI() == TpCalcIPI.UNIDADE) {
			ipi.setqUnid(quant.multiply(ipi.getqUnid()));;
			ipi.setvIPI(ipi.getqUnid().multiply(ipi.getvUnid()));
		}
		d.setIpi(ipi);
		/**************   IPI   Fim ****************************/
		
		/**************   ICMS Inicio **************************/
		ICMS icms = t.getIcms();
		icms.setvICMS(BigDecimal.ZERO);
		icms.setvICMSST(BigDecimal.ZERO);
		icms.setvBCST(BigDecimal.ZERO);
		if (icms.getModBCICMS() == ModBC.OPERACAO) {
			icms.setvBCICMS(i.getSubtotal());
			icms.setvICMS(calculoPorcentagem(icms.getvBCICMS(), icms.getpICMS()));
		}		
		if(ipi.getvIpiBcICMS()){
			icms.setvBCICMS(icms.getvBCICMS().add(ipi.getvIPI()));
			icms.setvICMS(calculoPorcentagem(icms.getvBCICMS(), icms.getpICMS()));
		}

		if (icms.getModBCST() == ModBCST.OPERACAO) {
			BigDecimal vAgreg = calculoPorcentagem(subtotal, icms.getpMVAST());

			icms.setvBCST(subtotal.add(vAgreg));
			icms.setvICMSST(calculoPorcentagem(icms.getvBCST(),
			icms.getpICMSST()).subtract(icms.getvICMS()));
		}
		d.setIcms(icms);
		/**************   ICMS Fim  **************************/
		
		PIS pis = t.getPis();
		d.setPis(pis);
		
		PISST pisST = t.getPisST();
		d.setPisST(pisST);
		
		COFINS cofins = t.getCofins();
		d.setCofins(cofins);
		
		COFINSST cofinsST = t.getCofinsST();
		d.setCofinsST(cofinsST);
		
		i.setDetFiscal(d);
		return i;
	}

	public Item gerarItem(Produto p, BigDecimal quant, BigDecimal vUn,UF uf){
		nItem++;
		Item i = new Item();
		i.setnItem(nItem);
		i.setProduto(p);		
		atualizarItem(i, quant, vUn, uf);		
		
		return i;
	}
	
	private Tributo getTributo(Tributacao trib, UF uf){
			for(Destino d: trib.getDestino()){
			if(d.getEstado().equals(uf)){
				return d.getTributos();
			}
		}	
		return null;
	}
	

	private BigDecimal calculoPorcentagem(BigDecimal valor, BigDecimal aliquota) {
		BigDecimal aux = valor;
		BigDecimal result = aux.multiply(aliquota).divide(
				new BigDecimal("100"), MathContext.DECIMAL128);
		return result;
	}
}
