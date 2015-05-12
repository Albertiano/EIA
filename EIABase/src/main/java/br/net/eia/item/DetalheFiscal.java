package br.net.eia.item;

import java.math.BigDecimal;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.entities.BaseEntity;
import br.net.eia.produto.imposto.cofins.COFINS;
import br.net.eia.produto.imposto.cofins.COFINSST;
import br.net.eia.produto.imposto.icms.ICMS;
import br.net.eia.produto.imposto.ipi.IPI;
import br.net.eia.produto.imposto.pis.PIS;
import br.net.eia.produto.imposto.pis.PISST;

@Entity
@XmlRootElement
public class DetalheFiscal extends BaseEntity {

    private String cfop;
    private String extipi;
    private String genero;
    private String cEan;
    private String cEanTrib;
    private String utrib;
    private BigDecimal qTrib;
    private BigDecimal vuntrib;
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
    
    public DetalheFiscal() {
		// TODO Auto-generated constructor stub
	}
    
	public String getExtipi() {
		return extipi;
	}
	public void setExtipi(String extipi) {
		this.extipi = extipi;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getcEan() {
		return cEan;
	}
	public void setcEan(String cEan) {
		this.cEan = cEan;
	}
	public String getcEanTrib() {
		return cEanTrib;
	}
	public void setcEanTrib(String cEanTrib) {
		this.cEanTrib = cEanTrib;
	}
	public String getUtrib() {
		return utrib;
	}
	public void setUtrib(String utrib) {
		this.utrib = utrib;
	}
	public BigDecimal getVuntrib() {
		return vuntrib;
	}
	public void setVuntrib(BigDecimal vuntrib) {
		this.vuntrib = vuntrib;
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
	public String getCfop() {
		return cfop;
	}
	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public BigDecimal getqTrib() {
		return qTrib;
	}

	public void setqTrib(BigDecimal qTrib) {
		this.qTrib = qTrib;
	}
}
