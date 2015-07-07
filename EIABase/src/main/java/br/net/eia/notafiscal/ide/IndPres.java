package br.net.eia.notafiscal.ide;

public enum IndPres {
	NAO_APLICAVEL("0"), PRESENCIAL("1"), INTERNET("2"), TELEATENDIMENTO("3"), NFCe_DOMICILIO(
			"4"), OUTRAS("9");
	private String value;

	private IndPres(String v) {
		this.value = v;
	}

	public String getValue() {
		return value;
	}
}
