/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.produto.imposto.pis;

/**
 *
 * @author user
 */
public enum CST_PIS {
    
    /**
	 * Base de cálculo = valor da operação alíquota normal 
	 * (cumulativo/nãocumulativo).
	 */
	PIS_01("01", "01 – Operação Tributável (base de cálculo = valor da operação alíquota normal (cumulativo/não cumulativo))"),
	/**
	 * Base de cálculo = valor da operação 
	 * (alíquota diferenciada).
	 */
	PIS_02("02", "02 - Operação Tributável (base de cálculo = valor da operação (alíquota diferenciada))"),
	/**
	 * Base de cálculo = quantidade vendida x alíquota por 
	 * unidade de produto.
	 */
	PIS_03("03", "03 - Operação Tributável (base de cálculo = quantidade vendida x alíquota por unidade de produto)"),
	/**
	 * Tributação monofásica (alíquota zero).
	 */
	PIS_04("04", "04 - Tributação monofásica"),
	
	PIS_05("05","05 - =Operação Tributável (Substituição Tributária)"),
	/**
	 * Alíquota zero.
	 */
	PIS_06("06", "06 - Operação Tributável (alíquota zero)"),
	/** 
	 * Operação Isenta da contribuição;
	 */
	PIS_07("07", "07 - Isento da contribuição"),
	/**
	 * Operação sem Incidência da contribuição.
	 */
	PIS_08("08", "08 - Sem Incidência da contribuição"),
	/**
	 * Operação com suspensão da contribuição.
	 */
	PIS_09("09", "09 - Com suspensão da contribuição"),
        
        PIS_49("49", "49 - Outras Operações de Saída"),
        PIS_50("50", "50 - Operação com Direito a Crédito - Vinculada Exclusivamente a Receita Tributada no Mercado Interno"),
        PIS_51("51", "51 - Operação com Direito a Crédito - Vinculada Exclusivamente a Receita Não Tributada no Mercado Interno"),
        PIS_52("52", "52 - Operação com Direito a Crédito - Vinculada Exclusivamente a Receita de Exportação"),
        PIS_53("53", "53 - Operação com Direito a Crédito - Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno"),
        PIS_54("54", "54 - Operação com Direito a Crédito - Vinculada a Receitas Tributadas no Mercado Interno e de Exportação"),
        PIS_55("55", "55 - Operação com Direito a Crédito - Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação"),
        PIS_56("56", "56 - Operação com Direito a Crédito - Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação"),
        PIS_60("60", "60 - Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita Tributada no Mercado Interno"),
        PIS_61("61", "61 - Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita Não-Tributada no Mercado Interno"),
        PIS_62("62", "62 - Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita de Exportação"),
        PIS_63("63", "63 - Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno"),
        PIS_64("64", "64 - Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas no Mercado Interno e de Exportação"),
        PIS_65("65", "65 - Crédito Presumido - Operação de Aquisição Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação"),
        PIS_66("66", "66 - Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação"),
        PIS_67("67", "67 - Crédito Presumido - Outras Operações"),
        PIS_70("70", "70 - Operação de Aquisição sem Direito a Crédito"),
        PIS_71("71", "71 - Operação de Aquisição com Isenção"),
        PIS_72("72", "72 - Operação de Aquisição com Suspensão"),
        PIS_73("73", "73 - Operação de Aquisição a Alíquota Zero"),
        PIS_74("74", "74 - Operação de Aquisição sem Incidência da Contribuição"),
        PIS_75("75", "75 - Operação de Aquisição por Substituição Tributária"),
        PIS_98("98", "98 - Outras Operações de Entrada"),        
        
        
	/**
	 * Outras operações.
	 */
	PIS_99("99", "99 - Outras operações");
	
	private CST_PIS(String value, String desc) {
		this.valor = value;
		this.descricao = desc;
	}
	
	private String valor;
	private String descricao;

    @Override
    public String toString() {
        return getDescricao();
    }
        
        

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
