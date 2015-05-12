package br.net.eia.notafiscal.ide;

public enum TpNF {
	ENTRADA("0", "Entrada"), SAIDA("1", "Saída");

	String indicador;
	String descricao;

	TpNF(String indicador, String descricao) {
		this.indicador = indicador;
		this.descricao = descricao;
	}

	public String getIndicador() {
		return indicador;
	}

	public String getDescricao() {
		return descricao;
	}
}
