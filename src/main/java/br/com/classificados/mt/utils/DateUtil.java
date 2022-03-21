package br.com.classificados.mt.utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static String convertBrDateToEnglishDate(String brDate) {
		if (brDate.contains("-")) {
			return brDate;
		}
		
		String ano = brDate.replace("/", "").substring(4, 8);
		String mes = brDate.replace("/", "").substring(2, 4);
		String dia = brDate.replace("/", "").substring(0, 2);
		
		return ano + "-" + mes + "-" + dia;
	}
	
	public static String getMesExtenso(Month mes) {

		if (mes.equals(Month.JANUARY)) {
			return "Janeiro";
		} else if (mes.equals(Month.FEBRUARY)) {
			return "Fevereiro";
		} else if (mes.equals(Month.MARCH)) {
			return "Março";
		} else if (mes.equals(Month.APRIL)) {
			return "Abril";
		} else if (mes.equals(Month.MAY)) {
			return "Maio";
		} else if (mes.equals(Month.JUNE)) {
			return "Junho";
		} else if (mes.equals(Month.JULY)) {
			return "Julho";
		} else if (mes.equals(Month.AUGUST)) {
			return "Agosto";
		} else if (mes.equals(Month.SEPTEMBER)) {
			return "Setembro";
		} else if (mes.equals(Month.OCTOBER)) {
			return "Outubro";
		} else if (mes.equals(Month.NOVEMBER)) {
			return "Novembro";
		} else {
			return "Dezembro";
		}
		
	}
	
	public static String converterDataPorFormato(LocalDate localDate) {
		if (localDate == null) {
			return "";
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	    return formatter.format(localDate); 
	}
	
}
