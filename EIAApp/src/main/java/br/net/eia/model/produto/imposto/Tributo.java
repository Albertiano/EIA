package br.net.eia.model.produto.imposto;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.model.BaseEntity;
import br.net.eia.model.produto.imposto.cofins.COFINS;
import br.net.eia.model.produto.imposto.cofins.COFINSST;
import br.net.eia.model.produto.imposto.icms.ICMS;
import br.net.eia.model.produto.imposto.ipi.IPI;
import br.net.eia.model.produto.imposto.pis.PIS;
import br.net.eia.model.produto.imposto.pis.PISST;

@Entity
@XmlRootElement
public class Tributo  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String nome;
	private String descricao;
	private String cfop;
	@Embedded
    private ICMS icms;
    @Embedded
    private IPI ipi;
    @Embedded
    private PIS pis;
    @Embedded
    private PISST pisST;
    @Embedded
    private COFINS cofins;
    @Embedded
    private COFINSST cofinsST;  
    
    public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public ICMS getIcms() {
		return icms;
	}
	public void setIcms(ICMS icms) {
		this.icms = icms;
	}
	public IPI getIpi() {
		return ipi;
	}
	public void setIpi(IPI ipi) {
		this.ipi = ipi;
	}
	public PIS getPis() {
		return pis;
	}
	public void setPis(PIS pis) {
		this.pis = pis;
	}
	public PISST getPisST() {
		return pisST;
	}
	public void setPisST(PISST pisST) {
		this.pisST = pisST;
	}
	public COFINS getCofins() {
		return cofins;
	}
	public void setCofins(COFINS cofins) {
		this.cofins = cofins;
	}
	public COFINSST getCofinsST() {
		return cofinsST;
	}
	public void setCofinsST(COFINSST cofinsST) {
		this.cofinsST = cofinsST;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getCfop();
	}
}
