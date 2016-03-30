package br.net.eia.model.produto.imposto.ipi;

public enum CST_IPI {
   
    	/**
	 * Entrada com recuperação de crédito.
	 */
	IPI_00("00", "00 - Entrada com recuperação de crédito", false),
	/**
	 * Entrada tributada com alíquota zero.
	 */
	IPI_01("01", "01 - Entrada tributada com alíquota zero", true),
	/**
	 * Entrada isenta.
	 */
	IPI_02("02", "02 - Entrada isenta", true),
	/**
	 * Entrada não-tributada.
	 */
	IPI_03("03", "03 - Entrada não-tributada", true),
	/**
	 * Entrada imune.
	 */
	IPI_04("04", "04 - Entrada imune", true),
	/**
	 * Entrada com suspensão.
	 */
	IPI_05("05", "05 - Entrada com suspensão", true),
	/**
	 * Outras entradas.
	 */
	IPI_49("49", "49 - Outras entradas", false),
	/**
	 * Saída tributada.
	 */
	IPI_50("50", "50 - Saída tributada", false),
	/**
	 * Saída tributada com alíquota zero
	 */
	IPI_51("51", "51 - Saída tributada com alíquota zero", true),
	/**
	 * Saída isenta.
	 */
	IPI_52("52", "52 - Saída isenta", true),
	/**
	 * Saída não-tributada.
	 */
	IPI_53("53", "53 - Saída não-tributada", true),
	/**
	 * Saída imune.
	 */
	IPI_54("54", "54 - Saída imune", true),
	/**
	 * Saída com suspensão.
	 */
	IPI_55("55", "55 - Saída com suspensão", true),
	/**
	 * Outras saídas.
	 */
	IPI_99("99", "99 - Outras saídas", false);
        
        private CST_IPI(String value, String descricion, boolean isento){
            this.valor = value;
            this.descricao = descricion;
            this.isento  = isento;
            
        }
        
        private String valor;
        private String descricao;
        private boolean isento;

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

    /**
     * @return the isento
     */
    public boolean isIsento() {
        return isento;
    }

    /**
     * @param isento the isento to set
     */
    public void setIsento(boolean isento) {
        this.isento = isento;
    }
        
        
}
