package br.net.eia.model.produto.imposto.pis;

import java.math.BigDecimal;
import javax.persistence.Embeddable;

@Embeddable
public class PISST {
    /**
     * Código de Situação Tributária do PIS
     */
    private TpCalcPIS tpCalcPISST;
    /**
     * Valor da Base de Cálculo do PIS
     */
    private BigDecimal vBCPISST;
    /**
     * Alíquota do PIS (em percentual)
     */
    private BigDecimal pPISST;
    /**
     * Valor do PIS
     */
    private BigDecimal vPISST;
    /**
     * Quantidade Vendida
     */
    private BigDecimal qBCProdPISST;
    /**
     * Alíquota do PIS (em reais)
     */
    private BigDecimal vAliqProdPISST;

    public PISST() {
    }

    public PISST(TpCalcPIS tpCalcPISST, BigDecimal vBCPISST, BigDecimal pPISST, BigDecimal vPISST, BigDecimal qBCProdPISST, BigDecimal vAliqProdPISST) {
        this.tpCalcPISST = tpCalcPISST;
        this.vBCPISST = vBCPISST;
        this.pPISST = pPISST;
        this.vPISST = vPISST;
        this.qBCProdPISST = qBCProdPISST;
        this.vAliqProdPISST = vAliqProdPISST;
    }

    /**
     * @return the tpCalcPISST
     */
    public TpCalcPIS getTpCalcPISST() {
        return tpCalcPISST;
    }

    /**
     * @param tpCalcPISST the tpCalcPISST to set
     */
    public void setTpCalcPISST(TpCalcPIS tpCalcPISST) {
        this.tpCalcPISST = tpCalcPISST;
    }

    /**
     * @return the vBCPISST
     */
    public BigDecimal getvBCPISST() {
        return vBCPISST;
    }

    /**
     * @param vBCPISST the vBCPISST to set
     */
    public void setvBCPISST(BigDecimal vBCPISST) {
        this.vBCPISST = vBCPISST;
    }

    /**
     * @return the pPISST
     */
    public BigDecimal getpPISST() {
        return pPISST;
    }

    /**
     * @param pPISST the pPISST to set
     */
    public void setpPISST(BigDecimal pPISST) {
        this.pPISST = pPISST;
    }

    /**
     * @return the vPISST
     */
    public BigDecimal getvPISST() {
        return vPISST;
    }

    /**
     * @param vPISST the vPISST to set
     */
    public void setvPISST(BigDecimal vPISST) {
        this.vPISST = vPISST;
    }

    /**
     * @return the qBCProdPISST
     */
    public BigDecimal getqBCProdPISST() {
        return qBCProdPISST;
    }

    /**
     * @param qBCProdPISST the qBCProdPISST to set
     */
    public void setqBCProdPISST(BigDecimal qBCProdPISST) {
        this.qBCProdPISST = qBCProdPISST;
    }

    /**
     * @return the vAliqProdPISST
     */
    public BigDecimal getvAliqProdPISST() {
        return vAliqProdPISST;
    }

    /**
     * @param vAliqProdPISST the vAliqProdPISST to set
     */
    public void setvAliqProdPISST(BigDecimal vAliqProdPISST) {
        this.vAliqProdPISST = vAliqProdPISST;
    }
    
}
