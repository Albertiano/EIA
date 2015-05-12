package br.net.eia.notafiscal.ide;

public enum IndPag {
	A_VISTA("0", "Pagamento à vista"), PRAZO("1", "Pagamento à Prazo"), OUTROS(
			"2", "Outros");

	String indicador;
	String descricao;

	IndPag(String indicador, String descricao) {
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
