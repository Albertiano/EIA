package br.net.eia.notafiscal.ide;

public enum IdDest {
	/**
	 * Identificador de local de destino da operação
	 */
	
	INTERNA("1","Operação interna"),
	INTERESTADUAL("2","Operação interestadual"),
	EXTERIOR("3","Operação com exterior");	 
	
	private String value;
	private String desc;
	private IdDest(String valor, String desc) {
		this.value = valor;
		this.desc = desc;
	}
	public String getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
}
