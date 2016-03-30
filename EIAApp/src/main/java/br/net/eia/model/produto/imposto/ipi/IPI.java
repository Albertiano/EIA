package br.net.eia.model.produto.imposto.ipi;

import java.math.BigDecimal;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class IPI{
    /*
     * Classe de enquadramento do IPI para Cigarros e Bebidas
     */
    private String clEnq;
    /*
     * CNPJ do produtor da mercadoria, quando  diferente  do  emitente. 
     * Somente  para  os  casos  de exportação direta ou indireta. 
     */
    private String CNPJProd;
    /*
     * Código do selo de controle IPI
     */
    private String cSelo;
    /*
     * Quantidade de selo de controle
     */
    private BigDecimal qSelo;
    /*
     * Código  de  Enquadramento  Legal do IPI
     * Tabela a ser criada pela RFB, informar 999 enquanto a tabela não for criada
     */
    private String cEnq;
    /*
     * Código  da  situação  tributária  do IPI
     */
    @Enumerated(EnumType.STRING)
    private CST_IPI cstIPI;
    /*
     * Valor da BC do IPI
     */
    private BigDecimal vBCIPI;
    /*
     * Alíquota do IPI
     */
    private BigDecimal pIPI;
    /*
     * Quantidade  total  na  unidade padrão  para  tributação  (somente para  os  produtos  tributados  por unidade)
     */
    private BigDecimal qUnid;
    /*
     * Valor por Unidade Tributável 
     */
    private BigDecimal vUnid;
    /*
     * Valor do IPI 
     */
    private BigDecimal vIPI;
    /*
     * Tipo de Calculo Valor ou Percentual
     */
    @Enumerated(EnumType.STRING)
    private TpCalcIPI tpCalcIPI;
    
    private boolean vIpiBcICMS;
    private boolean vIpiBcICMSST;

    public IPI() {
    }

    public IPI(String clEnq, String CNPJProd, String cSelo, BigDecimal qSelo, String cEnq, CST_IPI cstIPI, BigDecimal vBCIPI, BigDecimal pIPI, BigDecimal qUnid, BigDecimal vUnid, BigDecimal vIPI, TpCalcIPI tpCalcIPI, boolean vIpiBcICMS, boolean vIpiBcICMSST) {
        this.clEnq = clEnq;
        this.CNPJProd = CNPJProd;
        this.cSelo = cSelo;
        this.qSelo = qSelo;
        this.cEnq = cEnq;
        this.cstIPI = cstIPI;
        this.vBCIPI = vBCIPI;
        this.pIPI = pIPI;
        this.qUnid = qUnid;
        this.vUnid = vUnid;
        this.vIPI = vIPI;
        this.tpCalcIPI = tpCalcIPI;
        this.vIpiBcICMS = vIpiBcICMS;
        this.vIpiBcICMSST = vIpiBcICMSST;
    }

    /**
     * @return the clEnq
     */
    public String getClEnq() {
        return clEnq;
    }

    /**
     * @param clEnq the clEnq to set
     */
    public void setClEnq(String clEnq) {
        this.clEnq = clEnq;
    }

    /**
     * @return the CNPJProd
     */
    public String getCNPJProd() {
        return CNPJProd;
    }

    /**
     * @param CNPJProd the CNPJProd to set
     */
    public void setCNPJProd(String CNPJProd) {
        this.CNPJProd = CNPJProd;
    }

    /**
     * @return the cSelo
     */
    public String getcSelo() {
        return cSelo;
    }

    /**
     * @param cSelo the cSelo to set
     */
    public void setcSelo(String cSelo) {
        this.cSelo = cSelo;
    }

    /**
     * @return the qSelo
     */
    public BigDecimal getqSelo() {
        return qSelo;
    }

    /**
     * @param qSelo the qSelo to set
     */
    public void setqSelo(BigDecimal qSelo) {
        this.qSelo = qSelo;
    }

    /**
     * @return the cEnq
     */
    public String getcEnq() {
        return cEnq;
    }

    /**
     * @param cEnq the cEnq to set
     */
    public void setcEnq(String cEnq) {
        this.cEnq = cEnq;
    }

    /**
     * @return the cstIPI
     */
    public CST_IPI getCstIPI() {
        return cstIPI;
    }

    /**
     * @param cstIPI the cstIPI to set
     */
    public void setCstIPI(CST_IPI cstIPI) {
        this.cstIPI = cstIPI;
    }

    /**
     * @return the vBCIPI
     */
    public BigDecimal getvBCIPI() {
        return vBCIPI;
    }

    /**
     * @param vBCIPI the vBCIPI to set
     */
    public void setvBCIPI(BigDecimal vBCIPI) {
        this.vBCIPI = vBCIPI;
    }

    /**
     * @return the pIPI
     */
    public BigDecimal getpIPI() {
        return pIPI;
    }

    /**
     * @param pIPI the pIPI to set
     */
    public void setpIPI(BigDecimal pIPI) {
        this.pIPI = pIPI;
    }

    /**
     * @return the qUnid
     */
    public BigDecimal getqUnid() {
        return qUnid;
    }

    /**
     * @param qUnid the qUnid to set
     */
    public void setqUnid(BigDecimal qUnid) {
        this.qUnid = qUnid;
    }

    /**
     * @return the vUnid
     */
    public BigDecimal getvUnid() {
        return vUnid;
    }

    /**
     * @param vUnid the vUnid to set
     */
    public void setvUnid(BigDecimal vUnid) {
        this.vUnid = vUnid;
    }

    /**
     * @return the vIPI
     */
    public BigDecimal getvIPI() {
    	if(vIPI==null){
    		vIPI=BigDecimal.ZERO;
    	}
        return vIPI;
    }

    /**
     * @param vIPI the vIPI to set
     */
    public void setvIPI(BigDecimal vIPI) {
        this.vIPI = vIPI;
    }

    /**
     * @return the tpCalcIPI
     */
    public TpCalcIPI getTpCalcIPI() {
        return tpCalcIPI;
    }

    /**
     * @param tpCalcIPI the tpCalcIPI to set
     */
    public void setTpCalcIPI(TpCalcIPI tpCalcIPI) {
        this.tpCalcIPI = tpCalcIPI;
    }

    /**
     * @return the vIpiBcICMS
     */
    public boolean getvIpiBcICMS() {
        return vIpiBcICMS;
    }

    /**
     * @param vIpiBcICMS the vIpiBcICMS to set
     */
    public void setvIpiBcICMS(boolean vIpiBcICMS) {
        this.vIpiBcICMS = vIpiBcICMS;
    }

    /**
     * @return the vIpiBcICMSST
     */
    public boolean getvIpiBcICMSST() {
        return vIpiBcICMSST;
    }

    /**
     * @param vIpiBcICMSST the vIpiBcICMSST to set
     */
    public void setvIpiBcICMSST(boolean vIpiBcICMSST) {
        this.vIpiBcICMSST = vIpiBcICMSST;
    }

    
}
