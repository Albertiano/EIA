package br.net.eia.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ConversorBigDecimal {

	public static BigDecimal paraBigDecimal(String valor){
        NumberFormat instance = DecimalFormat.getInstance(); 
        instance.setMinimumFractionDigits(2);
        BigDecimal dValue = null;  
        valor = valor.replaceAll("[^0-9,]", "");
        if(valor.isEmpty()){
            valor="0";
            return BigDecimal.ZERO;
        }
        try {  
            dValue = new BigDecimal(String.valueOf(instance.parse(valor)));   
        } catch (ParseException e) {  
            Logger.getLogger(Formatador.class.getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);  
            throw new Error(e.getMessage());
        }  
        return dValue;
    }
	
	public static BigDecimal paraBigDecimalXML(String valor){
        NumberFormat instance = DecimalFormat.getInstance(); 
        instance.setMinimumFractionDigits(2);
        BigDecimal dValue = null;  
        valor = valor.replace(",", "");
        valor = valor.replace(".", ",");
        valor = valor.replaceAll("[^0-9,]", "");
        if(valor.isEmpty()){
            valor="0";
            return BigDecimal.ZERO;
        }
        try {  
            dValue = new BigDecimal(String.valueOf(instance.parse(valor)));   
        } catch (ParseException e) {  
            Logger.getLogger(ConversorBigDecimal.class.getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);  
            throw new Error(e.getMessage());
        }  
        return dValue;
    }
	
	public static String paraString(BigDecimal valor){
		if(valor==null){
			valor=BigDecimal.ZERO;
		}
        NumberFormat instance = DecimalFormat.getInstance();  
        instance.setMaximumFractionDigits(10);
        instance.setMinimumFractionDigits(0);
        String text = instance.format(valor); 
        
        return text;
    }
	
	public static String paraString(BigDecimal valor, int nDecimalMin){
		if(valor==null){
			valor=BigDecimal.ZERO;
		}
        NumberFormat instance = DecimalFormat.getInstance();  
        instance.setMaximumFractionDigits(10);
        instance.setMinimumFractionDigits(nDecimalMin);
        String text = instance.format(valor); 
        
        return text;
    }
	
	public static String paraString(String text, int nDecimalMin){
		BigDecimal number = BigDecimal.ZERO;
		if(!text.isEmpty()){
			number = paraBigDecimal(text);
		}
        
        NumberFormat instance = DecimalFormat.getInstance();  
        instance.setMinimumFractionDigits(nDecimalMin);
        instance.setMaximumFractionDigits(10);;
        String txt = instance.format(number.setScale(4, RoundingMode.HALF_UP)); 
        
        return txt;
    }
	
	public static String paraStringXML(String text, int nDecimalMin){
		BigDecimal number = BigDecimal.ZERO;
		if(!text.isEmpty()){
			number = paraBigDecimalXML(text);
		}
        
        NumberFormat instance = DecimalFormat.getInstance();  
        instance.setMinimumFractionDigits(nDecimalMin);
        instance.setMaximumFractionDigits(10);;
        String txt = instance.format(number); 
        
        return txt;
    }
	
	public static String paraStringNFeQuant(BigDecimal valor){
		if(valor==null){
			valor=BigDecimal.ZERO;
		}
        NumberFormat instance = DecimalFormat.getInstance(Locale.US);  
        instance.setMaximumFractionDigits(4);
        instance.setMinimumFractionDigits(0);
        String text = instance.format(valor); 
        
        return text.replace(",", "");
    }
	
	public static String paraStringNFePreco(BigDecimal valor){
		if(valor==null){
			valor=BigDecimal.ZERO;
		}
        NumberFormat instance = DecimalFormat.getInstance(Locale.US);  
        instance.setMaximumFractionDigits(10);
        instance.setMinimumFractionDigits(2);
        String text = instance.format(valor); 
        
        return text.replace(",", "");
    }
	
	public static String paraStringNFeValor(BigDecimal valor){
		if(valor==null){
			valor=BigDecimal.ZERO;
		}
        NumberFormat instance = DecimalFormat.getInstance(Locale.US);  
        instance.setMaximumFractionDigits(2);
        instance.setMinimumFractionDigits(2);
        String text = instance.format(valor); 
        
        return text.replace(",", "");
    }
	
	public static String paraStringNFeAliq(BigDecimal valor){
		if(valor==null){
			valor=BigDecimal.ZERO;
		}
        NumberFormat instance = DecimalFormat.getInstance(Locale.US);  
        instance.setMaximumFractionDigits(4);
        instance.setMinimumFractionDigits(2);
        String text = instance.format(valor); 
        
        return text.replace(",", "");
    }
	
	public static String paraStringNFePeso(BigDecimal valor){
		if(valor==null){
			valor=BigDecimal.ZERO;
		}
        NumberFormat instance = DecimalFormat.getInstance(Locale.US);  
        instance.setMaximumFractionDigits(3);
        instance.setMinimumFractionDigits(3);
        String text = instance.format(valor); 
        
        return text.replace(",", "");
    }
}
