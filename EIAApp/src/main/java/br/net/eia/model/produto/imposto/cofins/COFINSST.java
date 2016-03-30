/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.model.produto.imposto.cofins;

import java.math.BigDecimal;
import javax.persistence.Embeddable;
@Embeddable
public class COFINSST{
    /**
     * Código de Situação Tributária do COFINS
     */
    private TpCalcCOFINS tpCalcCOFINSST;
    /**
     * Valor da Base de Cálculo do COFINS
     */
    private BigDecimal vBCCOFINSST;
    /**
     * Alíquota do COFINS (em percentual)
     */
    private BigDecimal pCOFINSST;
    /**
     * Valor do COFINS
     */
    private BigDecimal vCOFINSST;
    /**
     * Quantidade Vendida
     */
    private BigDecimal qBCProdCOFINSST;
    /**
     * Alíquota do COFINS (em reais)
     */
    private BigDecimal vAliqProdCOFINSST;

    public COFINSST() {
    }

    public COFINSST(TpCalcCOFINS tpCalcCOFINSST, BigDecimal vBCCOFINSST, BigDecimal pCOFINSST, BigDecimal vCOFINSST, BigDecimal qBCProdCOFINSST, BigDecimal vAliqProdCOFINSST) {
        this.tpCalcCOFINSST = tpCalcCOFINSST;
        this.vBCCOFINSST = vBCCOFINSST;
        this.pCOFINSST = pCOFINSST;
        this.vCOFINSST = vCOFINSST;
        this.qBCProdCOFINSST = qBCProdCOFINSST;
        this.vAliqProdCOFINSST = vAliqProdCOFINSST;
    }

    /**
     * @return the tpCalcCOFINSST
     */
    public TpCalcCOFINS getTpCalcCOFINSST() {
        return tpCalcCOFINSST;
    }

    /**
     * @param tpCalcCOFINSST the tpCalcCOFINSST to set
     */
    public void setTpCalcCOFINSST(TpCalcCOFINS tpCalcCOFINSST) {
        this.tpCalcCOFINSST = tpCalcCOFINSST;
    }

    /**
     * @return the vBCCOFINSST
     */
    public BigDecimal getvBCCOFINSST() {
        return vBCCOFINSST;
    }

    /**
     * @param vBCCOFINSST the vBCCOFINSST to set
     */
    public void setvBCCOFINSST(BigDecimal vBCCOFINSST) {
        this.vBCCOFINSST = vBCCOFINSST;
    }

    /**
     * @return the pCOFINSST
     */
    public BigDecimal getpCOFINSST() {
        return pCOFINSST;
    }

    /**
     * @param pCOFINSST the pCOFINSST to set
     */
    public void setpCOFINSST(BigDecimal pCOFINSST) {
        this.pCOFINSST = pCOFINSST;
    }

    /**
     * @return the vCOFINSST
     */
    public BigDecimal getvCOFINSST() {
        return vCOFINSST;
    }

    /**
     * @param vCOFINSST the vCOFINSST to set
     */
    public void setvCOFINSST(BigDecimal vCOFINSST) {
        this.vCOFINSST = vCOFINSST;
    }

    /**
     * @return the qBCProdCOFINSST
     */
    public BigDecimal getqBCProdCOFINSST() {
        return qBCProdCOFINSST;
    }

    /**
     * @param qBCProdCOFINSST the qBCProdCOFINSST to set
     */
    public void setqBCProdCOFINSST(BigDecimal qBCProdCOFINSST) {
        this.qBCProdCOFINSST = qBCProdCOFINSST;
    }

    /**
     * @return the vAliqProdCOFINSST
     */
    public BigDecimal getvAliqProdCOFINSST() {
        return vAliqProdCOFINSST;
    }

    /**
     * @param vAliqProdCOFINSST the vAliqProdCOFINSST to set
     */
    public void setvAliqProdCOFINSST(BigDecimal vAliqProdCOFINSST) {
        this.vAliqProdCOFINSST = vAliqProdCOFINSST;
    }

    
}
