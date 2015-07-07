package br.net.eia.notafiscal.pagamento;

public enum TpPag {
	DINHEIRO("01", "Dinheiro"),
	CHEQUE("02","Cheque"),
	CARTAO_CREDITO("03","Cartão de Crédito"),
	CARTAO_DEBITO("04","Cartão de Débito"),
	CREDITO("05","Crédito Loja"),
	VALE_ALIMENTACAO("10", "Vale Alimentação"),
	VALE_REFEICAO("11", "Vale Refeição"),
	VALE_PRESENTE("12", "Vale Presente"),
	VALE_COMBUSTIVEL("13", "Vale Combustível"),
	OUTROS("99","Outros");
	
	private String value;
	private String desc;
	
	TpPag(String v, String d){
		this.value = v;
		this.desc = d;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}
