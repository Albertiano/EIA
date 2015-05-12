package br.net.eia.produto;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.contato.Contato;
import br.net.eia.entities.BaseEntity;
import br.net.eia.produto.unidade.Unidade;

@XmlRootElement
@Entity
public class FornecedorProduto extends BaseEntity{
	
	@OneToOne
	private Contato fornecedor;
	private String codFornecedor;
	@OneToOne
	private Unidade unidFornecedor;
	private BigDecimal fatorConversao;
	
	public final Contato getFornecedor() {
		return fornecedor;
	}
	public final void setFornecedor(Contato fornecedor) {
		this.fornecedor = fornecedor;
	}
	public final String getCodFornecedor() {
		return codFornecedor;
	}
	public final void setCodFornecedor(String codFornecedor) {
		this.codFornecedor = codFornecedor;
	}
	public final Unidade getUnidFornecedor() {
		return unidFornecedor;
	}
	public final void setUnidFornecedor(Unidade unidFornecedor) {
		this.unidFornecedor = unidFornecedor;
	}
	public final BigDecimal getFatorConversao() {
		return fatorConversao;
	}
	public final void setFatorConversao(BigDecimal fatorConversao) {
		this.fatorConversao = fatorConversao;
	}
	

}
