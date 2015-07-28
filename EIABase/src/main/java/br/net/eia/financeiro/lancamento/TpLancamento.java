package br.net.eia.financeiro.lancamento;

public enum TpLancamento {
	CREDITO("C","Crédito"),DEBITO("D", "Débito");
	
	private String sigla;
	private String descricao;
	
	private TpLancamento(String s, String d) {
		this.sigla = s;
		this.descricao = d;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return getDescricao();
	}
}
