/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.produto.imposto.icms;

/**
 *
 * @author user
 */
public enum CST_ICMS {
    /**
	 * Tributada integralmente.
	 */
	ICMS_00("00", "00 - Tributada integralmente"),
	/**
	 * Tributada e com cobrança do ICMS por substituição tributária.
	 */
	ICMS_10("10", "10 - Tributada com cobr. por subst. trib."),
	/**
	 * Com redução de base de cálculo.
	 */
	ICMS_20("20", "20 - Com redução de base de cálculo"),
	/**
	 * Isenta ou não tributada e com cobrança do ICMS por
	 * substituição tributária.
	 */
	ICMS_30("30", "30 - Isenta ou não trib com cobr por subst trib"),
	/**
	 * Isenta.
	 */
	ICMS_40("40", "40 - Isenta"),
	/**
	 * Não tributada.
	 */
	ICMS_41("41", "41 - Não tributada"),
	/**
	 * Suspensão.
	 */
	ICMS_50("50", "50 - Suspesão"),
	/**
	 * Diferimento.
	 */
	ICMS_51("51", "51 - Diferimento"),
	/**
	 * ICMS cobrado anteriormente por substituição tributária.
	 */
	ICMS_60("60", "60 - ICMS cobrado anteriormente por subst trib"),
	/**
	 * Com redução de base de cálculo e cobrança do ICMS por 
	 * substituição tributária.
	 */
	ICMS_70("70", "70 - Redução de Base Calc e cobr ICMS por subst trib"),
	/**
	 * Outros.
	 */
	ICMS_90("90", "90 - Outros"),
        /**
	 * 
	 */
	ICMS_Part10("Part10", "Partilha 10 - Entre UF origem e destino ou definida na legislação"),
        /**
	 * 
	 */
	ICMS_Part90("Part90", "Partilha 90 - Entre UF origem e destino ou definida na legislação - outros"),
        /**
	 * Tributada pelo Simples nacional com permissão de crédito.
	 */
	SN_101("101", "Simples Nacional: 101 - Com permissão de crédito"),
	/**
	 * Tributada pelo Simples nacional sem permissão de crédito.
	 */
	SN_102("102", "Simples Nacional: 102 - Sem permissão de crédito"),
	/**
	 * Isenção do Simples nacional por faixa de receita bruta.
	 */
	SN_103("103", "Simples Nacional: 103 - Isenção do ICMS para faixa de receita bruta"),
	/**
	 * Tributada pelo Simples nacional com permissão de crédito e
	 * com cobrança de ICMS por substituição tributária.
	 */
	SN_201("201", "Simples Nacional: 201 - Com permissão de crédito, com cobr ICMS por Subst Trib"),
	/**
	 * Tributada pelo Simples nacional sem permissão de crédito e
	 * com cobrança de ICMS por substituição tributária.
	 */
	SN_202("202", "Simples Nacional: 202 - Sem permissão de crédito, com cobr ICMS por Subst Trib"),
	/**
	 * Isenção do Simples nacional por faixa de receita bruta e
	 * com cobrança de ICMS por substituição tributária.
	 */
	SN_203("203", "Simples Nacional: 203 - Isenção ICMS p/ faixa de receita bruta e cobr do ICMS por ST"),
	/**
	 * Imune.
	 */
	SN_300("300", "Simples Nacional: 300 - Imune"),
	/**
	 * Não tributada pelo Simples nacional.
	 */
	SN_400("400", "Simples Nacional: 400 - Não tributada"),
	/**
	 * ICMS cobrado anteriormente por ST.
	 */
	SN_500("500", "Simples Nacional: 500 - ICMS cobrado antes por subst trib ou antecipação"),
	/**
	 * Outros.
	 */
	SN_900("900", "Simples Nacional: 900 - Outros");
        
        private CST_ICMS(String value, String descricion){
            this.valor = value;
            this.descricao = descricion;
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
