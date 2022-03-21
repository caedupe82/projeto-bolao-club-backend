package br.com.classificados.mt.utils;

public class TypeUtil {

	public static final String validarString(String txt) {
		
		if (txt == null) {
			return "";
		}
		
		return txt;
	}
	
	public static final String validarStringComTracoAnterior(String txt) {
		
		if (txt == null || txt.length() == 0) {
			return "";
		}
		
		return "-".concat(txt);
	}
	
	public static final String validarStringComTracoProximo(String txt) {
		
		if (txt == null || txt.length() == 0) {
			return "";
		}
		
		return txt.concat("-");
	}
	
	public static final Long validarNumero(Long numero) {
		if (numero == null) {
			return 0L;
		}
		
		return numero;
	}
	
	public static final String possuiString(String txt, String txt2) {
		
		if (txt.contains(txt2)) {
			return "";
		}
		
		return txt2.concat("-");
		
	}
	
	public static final Double validarDouble(Double valor) {
		
		if (valor == null) {
			return 0.0;
		}
		
		return valor;
	}
	
	
}
