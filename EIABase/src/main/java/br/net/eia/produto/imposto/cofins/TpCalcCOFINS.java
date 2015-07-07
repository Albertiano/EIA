/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.produto.imposto.cofins;

public enum TpCalcCOFINS {
    
    NA(""),
    ALIQUOTA("Percentual"),
    UNIDADE("Em Valor");
    
    TpCalcCOFINS(String description){
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
