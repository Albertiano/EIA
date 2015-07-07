package br.net.eia.compra;

import br.net.eia.produto.imposto.cofins.COFINS;
import br.net.eia.produto.imposto.cofins.COFINSST;
import br.net.eia.produto.imposto.icms.ICMS;
import br.net.eia.produto.imposto.ipi.IPI;
import br.net.eia.produto.imposto.pis.PIS;
import br.net.eia.produto.imposto.pis.PISST;

import java.math.BigDecimal;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;


@XmlRootElement
@Entity
public class DetalheFiscalCompra{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Long id;
    private BigDecimal totalSeguro;
    private BigDecimal totalDesconto;
    private BigDecimal totalFrete;
    private BigDecimal outrasDespesas;
    private String extipi;
    private String genero;
    private String cEan;
    private String cEanTrib;
    private String utrib;
    private BigDecimal vuntrib;
    private BigDecimal fatorQtdTrib;
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

    /**
     * @return the totalSeguro
     */
    public BigDecimal getTotalSeguro() {
        return totalSeguro;
    }

    /**
     * @param totalSeguro the totalSeguro to set
     */
    public void setTotalSeguro(BigDecimal totalSeguro) {
        this.totalSeguro = totalSeguro;
    }

    /**
     * @return the totalDesconto
     */
    public BigDecimal getTotalDesconto() {
        return totalDesconto;
    }

    /**
     * @param totalDesconto the totalDesconto to set
     */
    public void setTotalDesconto(BigDecimal totalDesconto) {
        this.totalDesconto = totalDesconto;
    }

    /**
     * @return the totalFrete
     */
    public BigDecimal getTotalFrete() {
        return totalFrete;
    }

    /**
     * @param totalFrete the totalFrete to set
     */
    public void setTotalFrete(BigDecimal totalFrete) {
        this.totalFrete = totalFrete;
    }

    /**
     * @return the outrasDespesas
     */
    public BigDecimal getOutrasDespesas() {
        return outrasDespesas;
    }

    /**
     * @param outrasDespesas the outrasDespesas to set
     */
    public void setOutrasDespesas(BigDecimal outrasDespesas) {
        this.outrasDespesas = outrasDespesas;
    }

    /**
     * @return the extipi
     */
    public String getExtipi() {
        return extipi;
    }

    /**
     * @param extipi the extipi to set
     */
    public void setExtipi(String extipi) {
        this.extipi = extipi;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the cEan
     */
    public String getcEan() {
        return cEan;
    }

    /**
     * @param cEan the cEan to set
     */
    public void setcEan(String cEan) {
        this.cEan = cEan;
    }

    /**
     * @return the cEanTrib
     */
    public String getcEanTrib() {
        return cEanTrib;
    }

    /**
     * @param cEanTrib the cEanTrib to set
     */
    public void setcEanTrib(String cEanTrib) {
        this.cEanTrib = cEanTrib;
    }

    /**
     * @return the utrib
     */
    public String getUtrib() {
        return utrib;
    }

    /**
     * @param utrib the utrib to set
     */
    public void setUtrib(String utrib) {
        this.utrib = utrib;
    }

    /**
     * @return the vuntrib
     */
    public BigDecimal getVuntrib() {
        return vuntrib;
    }

    /**
     * @param vuntrib the vuntrib to set
     */
    public void setVuntrib(BigDecimal vuntrib) {
        this.vuntrib = vuntrib;
    }

    /**
     * @return the icms
     */
    public ICMS getIcms() {
        return icms;
    }

    /**
     * @param icms the icms to set
     */
    public void setIcms(ICMS icms) {
        this.icms = icms;
    }

    /**
     * @return the ipi
     */
    public IPI getIpi() {
        return ipi;
    }

    /**
     * @param ipi the ipi to set
     */
    public void setIpi(IPI ipi) {
        this.ipi = ipi;
    }

    /**
     * @return the pis
     */
    public PIS getPis() {
        return pis;
    }

    /**
     * @param pis the pis to set
     */
    public void setPis(PIS pis) {
        this.pis = pis;
    }

    /**
     * @return the pisST
     */
    public PISST getPisST() {
        return pisST;
    }

    /**
     * @param pisST the pisST to set
     */
   public void setPisST(PISST pisST) {
        this.pisST = pisST;
    }

    /**
     * @return the cofins
     */
    public COFINS getCofins() {
        return cofins;
    }

    /**
     * @param cofins the cofins to set
     */
    public void setCofins(COFINS cofins) {
        this.cofins = cofins;
    }

    /**
     * @return the cofinsST
     */
    public COFINSST getCofinsST() {
        return cofinsST;
    }

    /**
     * @param cofinsST the cofinsST to set
     */
    public void setCofinsST(COFINSST cofinsST) {
        this.cofinsST = cofinsST;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

	public BigDecimal getFatorQtdTrib() {
		return fatorQtdTrib;
	}

	public void setFatorQtdTrib(BigDecimal fatorQtdTrib) {
		this.fatorQtdTrib = fatorQtdTrib;
	}
}
