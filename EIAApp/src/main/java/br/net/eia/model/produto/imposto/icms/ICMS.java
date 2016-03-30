/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.model.produto.imposto.icms;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.net.eia.model.municipio.UF;

@Embeddable
public class ICMS implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * Origem da mercadoria
     *
     */
    @Enumerated(EnumType.STRING)
    private Origem origem;
    /**
     * Tributação do ICMS
     *
     */
    @Enumerated(EnumType.STRING)
    private CST_ICMS cstICMS;
    /**
     * Modalidade de determinação da BC do ICMS
     *
     */
    @Enumerated(EnumType.STRING)
    private ModBC modBCICMS;
    /**
     * Valor da BC do ICMS
     *
     */
    private BigDecimal vBCICMS;
    /**
     * Percentual da Redução de BC
     *
     */
    private BigDecimal pRedBCICMS;
    /**
     * Alíquota do imposto
     *
     */
    private BigDecimal pICMS;
    /**
     * Valor do ICMS 
     *
     */
    private BigDecimal vICMS;
    /**
     * Motivo da desoneração do ICMS
     *
     */
    @Enumerated(EnumType.STRING)
    private MotDesICMS motDesICMS;
    /**
     * Modalidade de determinação da BC do ICMS ST
     *
     */
    @Enumerated(EnumType.STRING)
    private ModBCST modBCST;
    /**
     * Percentual da margem de valor Adicionado do ICMS ST
     *
     */
    private BigDecimal pMVAST;
    /**
     * Percentual da Redução de BC do ICMS ST
     *
     */
    private BigDecimal pRedBCST;
    /**
     * Valor da BC do ICMS ST
     *
     */
    private BigDecimal vBCST;
    /**
     * Alíquota do imposto do ICMS ST 
     *
     */
    private BigDecimal pICMSST;
    /**
     * Valor do ICMS ST
     *
     */
    private BigDecimal vICMSST;
    /**
     * Valor da BC do ICMS ST retido
     *
     */
    private BigDecimal vBCSTRet;
    /**
     * Valor do ICMS ST retido
     *
     */
    private BigDecimal vICMSSTRet;
    /**
     * Valor da BC do ICMS ST da UF destino
     *
     */
    private BigDecimal vBCSTDest;
    /**
     * Valor do ICMS ST da UF destino
     *
     */
    private BigDecimal vICMSSTDest;
    /**
     * Percentual da BC operação própria 
     *
     */
    private BigDecimal pBCOp;
    /**
     * UF para qual é devido o ICMS ST
     *
     */
    @Enumerated(EnumType.STRING)
    private UF UFST;
    /**
     * Alíquota aplicável de cálculo do crédito (Simples Nacional). 
     *
     */
    private BigDecimal pCredSN;
    /**
     * Valor crédito do ICMS que pode ser aproveitado nos termos do art. 23 da
     * LC 123 (Simples Nacional)     *
     */
    private BigDecimal vCredICMSSN;    
    /*
     * Valor do ICMS desonerado
     */
    
    private BigDecimal vICMSDeson;
    /*
     * Valor do ICMS da Operação
     * Valor como se não tivesse o diferimento
     */
    private BigDecimal vICMSOp;
    /*
     * Percentual do diferimento
     */
    private BigDecimal pDif;
    /*
     * Valor do ICMS diferido
     */
    private BigDecimal vICMSDif;

    public ICMS() {
    }

    public ICMS(Origem origem, CST_ICMS cstICMS, ModBC modBCICMS, BigDecimal vBCICMS, BigDecimal pRedBCICMS, BigDecimal pICMS, BigDecimal vICMS, MotDesICMS motDesICMS, ModBCST modBCST, BigDecimal pMVAST, BigDecimal pRedBCST, BigDecimal vBCST, BigDecimal pICMSST, BigDecimal vICMSST, BigDecimal vBCSTRet, BigDecimal vICMSSTRet, BigDecimal vBCSTDest, BigDecimal vICMSSTDest, BigDecimal pBCOp, UF UFST, BigDecimal pCredSN, BigDecimal vCredICMSSN) {
        this.origem = origem;
        this.cstICMS = cstICMS;
        this.modBCICMS = modBCICMS;
        this.vBCICMS = vBCICMS;
        this.pRedBCICMS = pRedBCICMS;
        this.pICMS = pICMS;
        this.vICMS = vICMS;
        this.motDesICMS = motDesICMS;
        this.modBCST = modBCST;
        this.pMVAST = pMVAST;
        this.pRedBCST = pRedBCST;
        this.vBCST = vBCST;
        this.pICMSST = pICMSST;
        this.vICMSST = vICMSST;
        this.vBCSTRet = vBCSTRet;
        this.vICMSSTRet = vICMSSTRet;
        this.vBCSTDest = vBCSTDest;
        this.vICMSSTDest = vICMSSTDest;
        this.pBCOp = pBCOp;
        this.UFST = UFST;
        this.pCredSN = pCredSN;
        this.vCredICMSSN = vCredICMSSN;
    }

    /**
     * @return the origem
     */
    public Origem getOrigem() {
        return origem;
    }

    /**
     * @param origem the origem to set
     */
    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    /**
     * @return the cstICMS
     */
    public CST_ICMS getCstICMS() {
        return cstICMS;
    }

    /**
     * @param cstICMS the cstICMS to set
     */
    public void setCstICMS(CST_ICMS cstICMS) {
        this.cstICMS = cstICMS;
    }

    /**
     * @return the modBCICMS
     */
    public ModBC getModBCICMS() {
        return modBCICMS;
    }

    /**
     * @param modBCICMS the modBCICMS to set
     */
    public void setModBCICMS(ModBC modBCICMS) {
        this.modBCICMS = modBCICMS;
    }

    /**
     * @return the vBCICMS
     */
    public BigDecimal getvBCICMS() {
        return vBCICMS;
    }

    /**
     * @param vBCICMS the vBCICMS to set
     */
    public void setvBCICMS(BigDecimal vBCICMS) {
        this.vBCICMS = vBCICMS;
    }

    /**
     * @return the pRedBCICMS
     */
    public BigDecimal getpRedBCICMS() {
        return pRedBCICMS;
    }

    /**
     * @param pRedBCICMS the pRedBCICMS to set
     */
    public void setpRedBCICMS(BigDecimal pRedBCICMS) {
        this.pRedBCICMS = pRedBCICMS;
    }

    /**
     * @return the pICMS
     */
    public BigDecimal getpICMS() {
        return pICMS;
    }

    /**
     * @param pICMS the pICMS to set
     */
    public void setpICMS(BigDecimal pICMS) {
        this.pICMS = pICMS;
    }

    /**
     * @return the vICMS
     */
    public BigDecimal getvICMS() {
        return vICMS;
    }

    /**
     * @param vICMS the vICMS to set
     */
    public void setvICMS(BigDecimal vICMS) {
        this.vICMS = vICMS;
    }

    /**
     * @return the motDesICMS
     */
    public MotDesICMS getMotDesICMS() {
        return motDesICMS;
    }

    /**
     * @param motDesICMS the motDesICMS to set
     */
    public void setMotDesICMS(MotDesICMS motDesICMS) {
        this.motDesICMS = motDesICMS;
    }

    /**
     * @return the modBCST
     */
    public ModBCST getModBCST() {
        return modBCST;
    }

    /**
     * @param modBCST the modBCST to set
     */
    public void setModBCST(ModBCST modBCST) {
        this.modBCST = modBCST;
    }

    /**
     * @return the pMVAST
     */
    public BigDecimal getpMVAST() {
        return pMVAST;
    }

    /**
     * @param pMVAST the pMVAST to set
     */
    public void setpMVAST(BigDecimal pMVAST) {
        this.pMVAST = pMVAST;
    }

    /**
     * @return the pRedBCST
     */
    public BigDecimal getpRedBCST() {
        return pRedBCST;
    }

    /**
     * @param pRedBCST the pRedBCST to set
     */
    public void setpRedBCST(BigDecimal pRedBCST) {
        this.pRedBCST = pRedBCST;
    }

    /**
     * @return the vBCST
     */
    public BigDecimal getvBCST() {
        return vBCST;
    }

    /**
     * @param vBCST the vBCST to set
     */
    public void setvBCST(BigDecimal vBCST) {
        this.vBCST = vBCST;
    }

    /**
     * @return the pICMSST
     */
    public BigDecimal getpICMSST() {
        return pICMSST;
    }

    /**
     * @param pICMSST the pICMSST to set
     */
    public void setpICMSST(BigDecimal pICMSST) {
        this.pICMSST = pICMSST;
    }

    /**
     * @return the vICMSST
     */
    public BigDecimal getvICMSST() {
        return vICMSST;
    }

    /**
     * @param vICMSST the vICMSST to set
     */
    public void setvICMSST(BigDecimal vICMSST) {
        this.vICMSST = vICMSST;
    }

    /**
     * @return the vBCSTRet
     */
    public BigDecimal getvBCSTRet() {
        return vBCSTRet;
    }

    /**
     * @param vBCSTRet the vBCSTRet to set
     */
    public void setvBCSTRet(BigDecimal vBCSTRet) {
        this.vBCSTRet = vBCSTRet;
    }

    /**
     * @return the vICMSSTRet
     */
    public BigDecimal getvICMSSTRet() {
        return vICMSSTRet;
    }

    /**
     * @param vICMSSTRet the vICMSSTRet to set
     */
    public void setvICMSSTRet(BigDecimal vICMSSTRet) {
        this.vICMSSTRet = vICMSSTRet;
    }

    /**
     * @return the vBCSTDest
     */
    public BigDecimal getvBCSTDest() {
        return vBCSTDest;
    }

    /**
     * @param vBCSTDest the vBCSTDest to set
     */
    public void setvBCSTDest(BigDecimal vBCSTDest) {
        this.vBCSTDest = vBCSTDest;
    }

    /**
     * @return the vICMSSTDest
     */
    public BigDecimal getvICMSSTDest() {
        return vICMSSTDest;
    }

    /**
     * @param vICMSSTDest the vICMSSTDest to set
     */
    public void setvICMSSTDest(BigDecimal vICMSSTDest) {
        this.vICMSSTDest = vICMSSTDest;
    }

    /**
     * @return the pBCOp
     */
    public BigDecimal getpBCOp() {
        return pBCOp;
    }

    /**
     * @param pBCOp the pBCOp to set
     */
    public void setpBCOp(BigDecimal pBCOp) {
        this.pBCOp = pBCOp;
    }

    /**
     * @return the UFST
     */
    public UF getUFST() {
        return UFST;
    }

    /**
     * @param UFST the UFST to set
     */
    public void setUFST(UF UFST) {
        this.UFST = UFST;
    }

    /**
     * @return the pCredSN
     */
    public BigDecimal getpCredSN() {
        return pCredSN;
    }

    /**
     * @param pCredSN the pCredSN to set
     */
    public void setpCredSN(BigDecimal pCredSN) {
        this.pCredSN = pCredSN;
    }

    /**
     * @return the vCredICMSSN
     */
    public BigDecimal getvCredICMSSN() {
        return vCredICMSSN;
    }

    /**
     * @param vCredICMSSN the vCredICMSSN to set
     */
    public void setvCredICMSSN(BigDecimal vCredICMSSN) {
        this.vCredICMSSN = vCredICMSSN;
    }

	public BigDecimal getvICMSDeson() {
		return vICMSDeson;
	}

	public void setvICMSDeson(BigDecimal vICMSDeson) {
		this.vICMSDeson = vICMSDeson;
	}

	public BigDecimal getvICMSOp() {
		return vICMSOp;
	}

	public void setvICMSOp(BigDecimal vICMSOp) {
		this.vICMSOp = vICMSOp;
	}

	public BigDecimal getpDif() {
		return pDif;
	}

	public void setpDif(BigDecimal pDif) {
		this.pDif = pDif;
	}

	public BigDecimal getvICMSDif() {
		return vICMSDif;
	}

	public void setvICMSDif(BigDecimal vICMSDif) {
		this.vICMSDif = vICMSDif;
	}
}
