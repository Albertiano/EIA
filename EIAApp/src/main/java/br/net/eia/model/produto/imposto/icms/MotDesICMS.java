/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.model.produto.imposto.icms;

/**
 *
 * @author user
 */
public enum MotDesICMS {
    
    /**
	 * Não aplicável.
	 */
	NA("", ""),
	/**
	 * Táxi.
	 */
	TAXI("1", "1 – Táxi"),
	/**
	 * Deficiente físico.
	 */
	DEFICIENTE_FISICO("2", "2 – Deficiente Físico"),
	/**
	 * Produtor agropecuário.
	 */
	PRODUTOR_AGROPECUARIO("3", "3 – Produtor Agropecuário"),
	/**
	 * Frotista ou locadora.
	 */
	FROTISTA_OU_LOCADORA("4", "4 – Frotista/Locadora"),
	/**
	 * Diplomático ou consular.
	 */
	DIPLOMATICO("5", "5 – Diplomático/Consular"),
	/**
	 * Utilitários e motocicletas da Amazônia ocidental e áreas de livre comércio.
	 */
	AREAS_DE_LIVRE_COMERCIO("6", "6 - Util.  e  Motoc. Amazônia  Oc.  e  Livre Comércio"),
	/**
	 * Suframa.
	 */
	SUFRAMA("7", "7 – SUFRAMA"),
        /**
	 * Venda a Órgãos Públicos.
	 */
	ORGAO_PUBLICO("8", "8 - Venda a Órgãos Públicos"),
	/**
	 * Outros
	 */
	OUTROS("9", "9 - Outros");
        
        
    private MotDesICMS(String value, String descricion){
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
