package br.net.eia.notafiscal.transporte;

public enum ModFrete {
		
	EMITENTE("0","Por conta do emitente"),
	DESTINATARIO("1","Por conta do destinatário/remetente"),
	TERCEIROS("2","Por conta de terceiros"),
	SEM_FRETE("9","Sem frete");
	
	private String value;
	private String descricao;
	
	private ModFrete(String valor, String descricao){
		this.value = valor;
		this.descricao = descricao;
	}

	public final String getValue() {
		return value;
	}

	public final String getDescricao() {
		return descricao;
	}

}
