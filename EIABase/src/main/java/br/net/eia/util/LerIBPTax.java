package br.net.eia.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LerIBPTax {

	public static void main(String[] args) {

		LerIBPTax obj = new LerIBPTax();
		obj.run();

	}

	public void run() {

		String arquivoCSV = "C:/Users/Albertiano/Desktop/TabelaIBPTaxPB14.2.b.csv";
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new FileReader(arquivoCSV));
			while ((linha = br.readLine()) != null ) {

				String[] campos = linha.split(csvDivisor);
				if(campos.length > 0){
					System.out.println("ncm = "+campos[0]);
					System.out.println("ex = "+campos[1]);
					System.out.println("tipo = "+campos[2]);
					System.out.println("descricao = "+campos[3]);
					System.out.println("% Federal = "+campos[4]);
					System.out.println("% Importado = "+campos[5]);
					System.out.println("% Estadual = "+campos[6]);
					System.out.println("% Municipal = "+campos[7]);
				}				
				
				System.out.println(" ------------------------------------------------ ");
				

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}