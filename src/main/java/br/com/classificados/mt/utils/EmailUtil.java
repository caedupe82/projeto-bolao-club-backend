package br.com.classificados.mt.utils;

public class EmailUtil {

	public static String templateEmailBoasVindas(String nomeUsuario) {

		return "<h4>Prezado(a) <b>" + nomeUsuario + "</b>, </h4>"
				+ "<p> É um prazer termos você conosco, espero que você possa divertir muito com"
				+ " o nosso aplicativo e também ganhar bastante dinheiro =D </p>"
				+ " <p> Faça bons jogos e conte sempre com a nossa equipe na parte de <b> Fale Conosco </b> no seu Aplicativo ;) </p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>Equipe Bolão CLUB </b> </h3>";
	}
	
	public static String templateResultadoDoDia(String nomeUsuario) {

		return "<h4>Prezado(a) <b>" + nomeUsuario + "</b>, </h4>"
				+ "<p> O resultado do dia acaba de sair!!! :D Confira na tela [Meu Palpite] para"
				+ " saber se você acabou de acertar novos números. ;) </p>"
				+ " <p> Faça bons jogos e conte sempre com a nossa equipe na parte de <b> Fale Conosco </b> no seu Aplicativo ;) </p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>Equipe Bolão CLUB </b> </h3>";
	}
	
	public static String templatePremio(String nomeUsuario, String vencedores, String participantes) {

		return "<h4>Prezado(a) <b>" + nomeUsuario + "</b>, </h4>"
				+ "<p> As premiações acaba de sair!!! Esperamos que você tenha ganhado e gostado de participar do nosso aplicativo =) </p>"
				+ "<p><b>Vencedores: </b></p>"
				+ vencedores
				+ "<p><b>Participantes: </b></p>"
				+ participantes
				+ " <p> Faça bons jogos e conte sempre com a nossa equipe na parte de <b> Fale Conosco </b> no seu Aplicativo ;) </p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>Equipe Bolão CLUB </b> </h3>";
	}
	
	public static String templateEmailReprovacaoRenovacao(String nomeUsuario, String motivo) {

		return "<h4>Prezado(a) <b>" + nomeUsuario + "</b>, </h4>"
				+ "<p> Infelizmente o seu comprovante de pagamento foi reprovado pela nossa equipe, não conseguimos dar continuidade pelo seguinte motivo:</p>"
				+ "<p>" + motivo + "</p>"
				+ " <p> Caso tenha qualquer tipo de dúvida conte sempre com a nossa equipe na parte de <b> Fale Conosco </b> no seu Aplicativo ;) </p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>Equipe Bolão CLUB </b> </h3>";
	}
	
	public static String templateEmailAprovacaoRenovacao(String nomeUsuario) {

		return "<h4>Prezado(a) <b>" + nomeUsuario + "</b>, </h4>"
				+ "<p>Ficamos muito felizes em lhe informar que o seu comprovante de pagamento foi aprovado =D </p>"
				+ "<p>Agora você pode customizar o seu palpite atráves da tela <b> Meu Palpite </b> e ficar rico. =D </p>"
				+ "<p>Que a sorte esteja com você!</p>"
				+ " <p> Faça bons jogos e conte sempre com a nossa equipe na parte de <b> Fale Conosco </b> no seu Aplicativo ;) </p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>Equipe Bolão CLUB </b> </h3>";
	}

	public static String templateEmailSolicitacaoRenovacao(String telefone, String nome) {

		return "<h4>Prezado <b>" + "Bolão CLUB" + "</b>, </h4>"
				+ "<p>Segue em anexo o comprovante de depósito realizado em sua conta para que eu possa "
				+ "estar utilizando o aplicativo nesse mês.</p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>" + nome + "</b> </h3> <p> Telefone: " + telefone + "</p>";
	}
	
	public static String templateNotificarUsuarioEmailContato(String nomeUsuario) {

		return "<h4>Prezado(a) <b>" + nomeUsuario + "</b>, </h4>"
				+ "<p> Muito obrigado por entrar em contato conosco, já recebemos a sua mensagem e em breve iremos entrar em contato com você para resolvermos sua dúvida =D </p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>Equipe Bolão CLUB </b> </h3>";
	}
	
	public static String templateNotificarUsuarioEmailRenovacao(String nomeUsuario) {

		return "<h4>Prezado(a) <b>" + nomeUsuario + "</b>, </h4>"
				+ "<p>Ficamos muito felizes em receber o seu comprovante de renovação de pagamento, nossa "
				+ "equipe já está analisando o comprovante e em breve iremos lhe retornar. =D</p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>Equipe Bolão CLUB </b> </h3>";
	}
	
	public static String templateNotificacao(String nomeUsuario) {

		return "<h4>Prezado(a) <b>" + nomeUsuario + "</b>, </h4>"
				+ "<p>Ficamos muito felizes em receber o seu comprovante de renovação de pagamento, nossa "
				+ "equipe já está analisando o comprovante e em breve iremos lhe retornar. =D</p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>Equipe Bolão CLUB </b> </h3>";
	}

	public static String templateEmailContato(String mensagem, String telefone, String nome) {

		return "<h4>Prezado <b>" + "Bolão CLUB" + "</b>, </h4>"
				+ "<p>" + mensagem +  "</p>"
				+ "<h4> Atenciosamente, </h4>" + "<h3><b>" + nome + "</b> </h3> <p> Telefone: " + telefone + "</p>";
	}

	public static String getExtensao(String string) {
		if (string.contains("png")) {
			return "png";
		}
		
		if (string.contains("pdf")) {
			return "pdf";
		}
		
		if (string.contains("jpg")) {
			return "jpg";
		}
		
		if (string.contains("jpeg")) {
			return "jpeg";
		}
		
		if (string.contains("word")) {
			return "doc";
		}
		
		return "invalido";
	}

}
