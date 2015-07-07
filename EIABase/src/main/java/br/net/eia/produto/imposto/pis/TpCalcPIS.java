/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.produto.imposto.pis;

/**
 *
 * @author user
 */
public enum TpCalcPIS {
    
    NA(""),
    ALIQUOTA("Percentual"),
    UNIDADE("Em Valor");
    
    TpCalcPIS(String description){
        this.descricao = description;
    }
    private String descricao;

    @Override
    public String toString() {
        return getDescricao();
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }
    
    
}
