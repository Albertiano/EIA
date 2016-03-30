/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.model.produto.imposto.cofins;

import java.math.BigDecimal;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class COFINS{
    /**
     * Código de Situação Tributária do COFINS
     */
    @Enumerated(EnumType.STRING)
    private CST_COFINS cstCOFINS;
    /**
     * Valor da Base de Cálculo do COFINS
     */
    private BigDecimal vBCCOFINS;
    /**
     * Alíquota do COFINS (em percentual)
     */
    private BigDecimal pCOFINS;
    /**
     * Valor do COFINS
     */
    private BigDecimal vCOFINS;
    /**
     * Quantidade Vendida
     */
    private BigDecimal qBCProdCOFINS;
    /**
     * Alíquota do COFINS (em reais)
     */
    private BigDecimal vAliqProdCOFINS;
    
    @Enumerated(EnumType.STRING)
    private TpCalcCOFINS tpCalcCofins;

    public COFINS() {
    }

    public COFINS(CST_COFINS cstCOFINS, BigDecimal vBCCOFINS, BigDecimal pCOFINS, BigDecimal vCOFINS, BigDecimal qBCProdCOFINS, BigDecimal vAliqProdCOFINS) {
        this.cstCOFINS = cstCOFINS;
        this.vBCCOFINS = vBCCOFINS;
        this.pCOFINS = pCOFINS;
        this.vCOFINS = vCOFINS;
        this.qBCProdCOFINS = qBCProdCOFINS;
        this.vAliqProdCOFINS = vAliqProdCOFINS;
    }

    /**
     * @return the cstCOFINS
     */
    public CST_COFINS getCstCOFINS() {
        return cstCOFINS;
    }

    /**
     * @param cstCOFINS the cstCOFINS to set
     */
    public void setCstCOFINS(CST_COFINS cstCOFINS) {
        this.cstCOFINS = cstCOFINS;
    }

    /**
     * @return the vBCCOFINS
     */
    public BigDecimal getvBCCOFINS() {
        return vBCCOFINS;
    }

    /**
     * @param vBCCOFINS the vBCCOFINS to set
     */
    public void setvBCCOFINS(BigDecimal vBCCOFINS) {
        this.vBCCOFINS = vBCCOFINS;
    }

    /**
     * @return the pCOFINS
     */
    public BigDecimal getpCOFINS() {
        return pCOFINS;
    }

    /**
     * @param pCOFINS the pCOFINS to set
     */
    public void setpCOFINS(BigDecimal pCOFINS) {
        this.pCOFINS = pCOFINS;
    }

    /**
     * @return the vCOFINS
     */
    public BigDecimal getvCOFINS() {
    	if(vCOFINS==null){
    		vCOFINS=BigDecimal.ZERO;
    	}
        return vCOFINS;
    }

    /**
     * @param vCOFINS the vCOFINS to set
     */
    public void setvCOFINS(BigDecimal vCOFINS) {
        this.vCOFINS = vCOFINS;
    }

    /**
     * @return the qBCProdCOFINS
     */
    public BigDecimal getqBCProdCOFINS() {
        return qBCProdCOFINS;
    }

    /**
     * @param qBCProdCOFINS the qBCProdCOFINS to set
     */
    public void setqBCProdCOFINS(BigDecimal qBCProdCOFINS) {
        this.qBCProdCOFINS = qBCProdCOFINS;
    }

    /**
     * @return the vAliqProdCOFINS
     */
    public BigDecimal getvAliqProdCOFINS() {
        return vAliqProdCOFINS;
    }

    /**
     * @param vAliqProdCOFINS the vAliqProdCOFINS to set
     */
    public void setvAliqProdCOFINS(BigDecimal vAliqProdCOFINS) {
        this.vAliqProdCOFINS = vAliqProdCOFINS;
    }

    /**
     * @return the tpCalcCofins
     */
    public TpCalcCOFINS getTpCalcCofins() {
        return tpCalcCofins;
    }

    /**
     * @param tpCalcCofins the tpCalcCofins to set
     */
    public void setTpCalcCofins(TpCalcCOFINS tpCalcCofins) {
        this.tpCalcCofins = tpCalcCofins;
    }

}
