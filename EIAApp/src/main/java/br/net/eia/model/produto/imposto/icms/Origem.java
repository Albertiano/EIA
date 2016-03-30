/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.model.produto.imposto.icms;

/**
 *
 * @author user
 */
public enum Origem {

    /**
     * Origem nacional.
     */
    NACIONAL("0", true, "0 - Nacional"),
    /**
     * Origem estrangeira, importação direta.
     */
    ESTRANGEIRA_IMPDIR("1", false, "1 - Estrangeira, importação direta"),
    /**
     * Origem estrangeira, adquirida nomercado interno.
     */
    ESTRANGEIRA_ADQINT("2", false, "2 - Estrangeira, adquirida no mercado interno");

    private Origem(String value, boolean national, String description) {
        this.valor = value;
        this.nacional = national;
        this.descricao = description;
    }
    private String valor;
    private boolean nacional;
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
     * @return the nacional
     */
    public boolean isNacional() {
        return nacional;
    }

    /**
     * @param nacional the nacional to set
     */
    public void setNacional(boolean nacional) {
        this.nacional = nacional;
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
