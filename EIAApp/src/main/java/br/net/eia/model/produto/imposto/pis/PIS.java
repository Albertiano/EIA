package br.net.eia.model.produto.imposto.pis;

import java.math.BigDecimal;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class PIS {
    /**
     * Código de Situação Tributária do PIS
     */
   @Enumerated(EnumType.STRING)
    private CST_PIS cstPIS;
    /**
     * Valor da Base de Cálculo do PIS
     */
    private BigDecimal vBCPIS;
    /**
     * Alíquota do PIS (em percentual)
     */
    private BigDecimal pPIS;
    /**
     * Valor do PIS
     */
    private BigDecimal vPIS;
    /**
     * Quantidade Vendida
     */
    private BigDecimal qBCProdPIS;
    /**
     * Alíquota do PIS (em reais)
     */
    private BigDecimal vAliqProdPIS;
    
    @Enumerated(EnumType.STRING)
    private TpCalcPIS tpCalcPis;

    public PIS() {
    }

    public PIS(CST_PIS cstPIS, BigDecimal vBCPIS, BigDecimal pPIS, BigDecimal vPIS, BigDecimal qBCProdPIS, BigDecimal vAliqProdPIS) {
        this.cstPIS = cstPIS;
        this.vBCPIS = vBCPIS;
        this.pPIS = pPIS;
        this.vPIS = vPIS;
        this.qBCProdPIS = qBCProdPIS;
        this.vAliqProdPIS = vAliqProdPIS;
    }

    

    /**
     * @return the cstPIS
     */
    public CST_PIS getCstPIS() {
        return cstPIS;
    }

    /**
     * @param cstPIS the cstPIS to set
     */
    public void setCstPIS(CST_PIS cstPIS) {
        this.cstPIS = cstPIS;
    }

    /**
     * @return the vBCPIS
     */
    public BigDecimal getvBCPIS() {
        return vBCPIS;
    }

    /**
     * @param vBCPIS the vBCPIS to set
     */
    public void setvBCPIS(BigDecimal vBCPIS) {
        this.vBCPIS = vBCPIS;
    }

    /**
     * @return the pPIS
     */
    public BigDecimal getpPIS() {
        return pPIS;
    }

    /**
     * @param pPIS the pPIS to set
     */
    public void setpPIS(BigDecimal pPIS) {
        this.pPIS = pPIS;
    }

    /**
     * @return the vPIS
     */
    public BigDecimal getvPIS() {
    	if(vPIS==null){
    		vPIS=BigDecimal.ZERO;
    	}
        return vPIS;
    }

    /**
     * @param vPIS the vPIS to set
     */
    public void setvPIS(BigDecimal vPIS) {
        this.vPIS = vPIS;
    }

    /**
     * @return the qBCProdPIS
     */
    public BigDecimal getqBCProdPIS() {
        return qBCProdPIS;
    }

    /**
     * @param qBCProdPIS the qBCProdPIS to set
     */
    public void setqBCProdPIS(BigDecimal qBCProdPIS) {
        this.qBCProdPIS = qBCProdPIS;
    }

    /**
     * @return the vAliqProdPIS
     */
    public BigDecimal getvAliqProdPIS() {
        return vAliqProdPIS;
    }

    /**
     * @param vAliqProdPIS the vAliqProdPIS to set
     */
    public void setvAliqProdPIS(BigDecimal vAliqProdPIS) {
        this.vAliqProdPIS = vAliqProdPIS;
    }

    /**
     * @return the tpCalcPis
     */
    public TpCalcPIS getTpCalcPis() {
        return tpCalcPis;
    }

    /**
     * @param tpCalcPis the tpCalcPis to set
     */
    public void setTpCalcPis(TpCalcPIS tpCalcPis) {
        this.tpCalcPis = tpCalcPis;
    }

}
