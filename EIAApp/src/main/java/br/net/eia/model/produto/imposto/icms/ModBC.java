/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.model.produto.imposto.icms;

/**
 *
 * @author user
 */
public enum ModBC {
    
    /**
	 * Margem Valor Agregado (%).
	 */
	MARGEM("0", "0 - Margem Valor Agregado (%)"),
	/**
	 * Pauta (Valor).
	 */
	PAUTA("1", "1 - Pauta (Valor)"),
	/**
	 * Preço Tabelado Máx. (valor).
	 */
	TABELA("2", "2 - Preço Tabelado Máx. (valor)"),
	/**
	 * Valor da operação.
	 */
	OPERACAO("3", "3 - Valor da operação");
	
	private ModBC(String value, String description) {
		this.valor = value;
                this.descricao = description;
	}
	
	private String valor;
        private String descricao;

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

    @Override
    public String toString() {
        return getDescricao();
    }
    
    
}
