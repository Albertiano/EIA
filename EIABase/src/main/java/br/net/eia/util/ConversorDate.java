package br.net.eia.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class ConversorDate {
	
	public static Date gerarData(String data) {
        Date dataDB = null;
        try {
            SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
            Date dataTemp = formatadorData.parse(data);
            dataDB = dataTemp;
        } catch (ParseException ex) {
        	 Logger.getLogger(ConversorDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataDB;
    }
	
	public static Date gerarDatahora(String data) {
        Date dataDB = null;
        try {
            SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date dataTemp = formatadorData.parse(data);
            dataDB = dataTemp;
        } catch (ParseException ex) {
        	 Logger.getLogger(ConversorDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataDB;
    }
	
	public static String retornaDataHora(Date data){
		SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataFormatada = formatadorData.format(data);        
        return dataFormatada;
	}
	
	public static String retornaData(Date data){
		try{
			SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
			String dataFormatada = formatadorData.format(data);        
	        return dataFormatada;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public static String retornaDataNFe(Date data){
		SimpleDateFormat formatadorData = new SimpleDateFormat("yyyy-MM-dd");
		String dataFormatada = formatadorData.format(data);        
        return dataFormatada;
	}

    public static String retornaDataHoraNFe(Date dataASerFormatada) { 
    	if(dataASerFormatada==null){
    		return null;
    	}
        GregorianCalendar calendar = new GregorianCalendar();    
        calendar.setTime(dataASerFormatada);    
        XMLGregorianCalendar xmlCalendar = null;  
        try {  
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);  
            xmlCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);  
          
            return(xmlCalendar.toString());  
        } catch (DatatypeConfigurationException ex) {  
            Logger.getLogger(ConversorDate.class.getName()).log(Level.SEVERE, null, ex);  
        }  
        return null;  
    }
    
    public static void main(String[] args) {
    	Date data = new Date();
		System.out.println(ConversorDate.retornaDataHoraNFe(data));
		System.out.println(ConversorDate.retornaDataHora(data));
		System.out.println(ConversorDate.gerarDatahora(ConversorDate.retornaDataHora(data)));		
	}
}
