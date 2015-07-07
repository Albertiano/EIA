package br.net.eia.notafiscal.pagamento;

public enum TpBand {
	VISA("01"),
	MASTERCARD("02"),
	AMERICAN_EXPRESS("03"),
	SOROCRED("04"),
	OUTROS("99");
	
	private String value;
	
	private TpBand(String v) {
		this.value = v;
	}
	
	public String getValue() {
		return value;
	}
}
