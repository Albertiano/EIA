package br.net.eia.ui.compra;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import br.net.eia.compra.ItemCompra;
import br.net.eia.contato.Contato;
import br.net.eia.enums.UF;
import br.net.eia.nfe.v310.TNFe.InfNFe.Det;
import br.net.eia.nfe.v310.TNFe.InfNFe.Emit;
import br.net.eia.produto.FornecedorProduto;
import br.net.eia.produto.Produto;
import br.net.eia.util.ConversorBigDecimal;
import br.net.eia.util.Formatador;

public class ConverterNfeCompra {

	ConverterNfeCompra(){
		
	}
	
	public Contato forFornecedor(Emit emit){
		Formatador formatter = new Formatador();
		Contato fornec = new Contato();
		//fornec.setTpDoc(emit);
		fornec.setNumDoc(formatter.formatterCNPJCPF(emit.getCNPJ()));
		fornec.setIE(emit.getIE());
		fornec.setNome(emit.getXNome());
		fornec.setLogradouro(emit.getEnderEmit().getXLgr());
		fornec.setNumero(emit.getEnderEmit().getNro());
		fornec.setComplemento(emit.getEnderEmit().getXCpl());
		fornec.setBairro(emit.getEnderEmit().getXBairro());
		fornec.setCep(formatter.formatterCEP(emit.getEnderEmit().getCEP()));
		//fornecedor.setPais(cbPais.getValue());
		fornec.setUf(UF.valueOf(emit.getEnderEmit().getUF().value()));
		//fornecedor.setMunicipio(cbMunicipios.getValue());
		fornec.setFone(emit.getEnderEmit().getFone());
		//fornec.setObs(taObs.getText());
		//fornec.setEmail(emit.getEnderEmit();
		//fornec.setContato(tfContato.getText());
		fornec.setFantasia(emit.getXFant());
		//fornec.setCelular(tfCelular.getText());
		//fornec.setFoneRes(tfFoneRes.getText());
		//fornec.setBloqueado(ckBloqueado.isSelected());
		//fornec.setDesabilitado(ckDesabilitado.isSelected());
		//fornec.setClienteFonte(ckFonte.isSelected());
		//fornec.setISUF(emit.get);
		
		return fornec;
	}
	
	public Produto forProduto(Det d){
		Formatador formatter = new Formatador();
		Produto produto = new Produto();
      
        //produto.setReferencia(d.getProd().get);
        produto.setDescricao(d.getProd().getXProd());
        //produto.setUnidade(cbUnidade.getValue());
        //produto.setDesativado(ckDesativado.isSelected());
        //produto.setCfop(d.getProd().getCFOP());
        produto.setNcm(d.getProd().getNCM());
        produto.setPrecoCusto(ConversorBigDecimal.paraBigDecimalXML(d.getProd().getVUnCom()));
        //produto.setmLucro(formatter.paraBigDecimal(tfMLucro.getText()));
        //produto.setPrecoVenda(formatter.paraBigDecimal(tfPrecoVenda.getText()));
        //produto.setDescMax(formatter.paraBigDecimal(tfDescMax.getText()));
        //produto.setPesoBruto(formatter.paraBigDecimal(d.getProd().get));
        //produto.setPesoLiquido(formatter.paraBigDecimal(tfPesoLiq.getText()));
        //produto.setEstoqueMin(formatter.paraBigDecimal(tfEstMin.getText()));
        //produto.setEstoque(formatter.paraBigDecimal(tfEstAtual.getText()));
        
        //produto.setLocalizacao(tfLocalEstoque.getText());
        
        //produto.setDetFiscal(carregarDetalheFiscal());
        
		
		return produto;
	}
	
	
	public ItemCompra forItem(Det d, Produto p, FornecedorProduto f){
		Formatador formatter = new Formatador();
		ItemCompra item =new ItemCompra();
		item.setProduto(p);
        
        item.setCfop(d.getProd().getCFOP());
        item.setNcm(d.getProd().getNCM());
        item.setQuantidade(ConversorBigDecimal.paraBigDecimalXML(d.getProd().getQCom()).multiply(f.getFatorConversao()));
        item.setSubtotal(ConversorBigDecimal.paraBigDecimalXML(d.getProd().getVProd()));
        BigDecimal sub = item.getSubtotal();
        item.setPrecoVenda(sub.divide(item.getQuantidade(), MathContext.DECIMAL128).setScale(10,RoundingMode.HALF_UP));
        item.setPesoBruto(p.getPesoBruto().multiply(item.getQuantidade()));
        item.setPesoLiquido(p.getPesoLiquido().multiply(item.getQuantidade()));
        
        	        
       // item.setDetFiscal(carregarDetalheFiscal());
		
		
		return item;
		
	}
	
}
