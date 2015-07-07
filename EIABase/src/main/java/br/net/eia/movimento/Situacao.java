package br.net.eia.movimento;

public enum Situacao {
	FINALIZADO("Finalizado"), CANCELADO("Cancelado"),ABERTO("Em Aberto");
String txt;
	
Situacao(String string){
		this.txt = string;
	}

@Override
public String toString() {
	return txt;
}
}
