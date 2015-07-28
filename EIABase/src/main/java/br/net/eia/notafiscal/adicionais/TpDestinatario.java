package br.net.eia.notafiscal.adicionais;

public enum TpDestinatario {
	CLIENTE("Cliente"), FORNECEDOR("Fornecedor"), TRANSPORTADOR("Transportador"), COLABORADOR("Colaborador"), OUTRO("Outro");

	private String value;

	TpDestinatario(String desc) {
		this.value = desc;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	}
}
