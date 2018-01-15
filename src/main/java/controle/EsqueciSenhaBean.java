package controle;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



@Named
@ViewScoped
public class EsqueciSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;

	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	private static final String SMTP_AUTH_USER = "carloseduardowarsz"; // System.getenv("SENDGRID_USERNAME");
	private static final String SMTP_AUTH_PWD = "newtech@123"; // System.getenv("SENDGRID_PASSWORD");

	// private PreCadastroEmpregador preCadastroEmpregador = null;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static void send(String fromEmail, String toEmail, String senha,
			String nome, String codigoAtivacao) throws Exception {

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
		Authenticator auth = new SMTPAuthenticator();
		Session mailSession = Session.getDefaultInstance(props, auth);
		Transport transport = mailSession.getTransport();
		MimeMessage message = new MimeMessage(mailSession);
		Multipart multipart = new MimeMultipart("alternative");
		BodyPart bodyPart = new MimeBodyPart();

		String htmlText = "<html><header>";
		htmlText += "</header>";
		htmlText += "<body >";
		htmlText += "Ol&aacute;  " + nome + ", </p> ";
		htmlText += "<br/>";
		htmlText += "<p> Segue sua senha recuperada do sistema.</p> ";
		htmlText += "<br/>";
		htmlText += "<p  <span style=\"color:blue\"> Senha :" + senha
				+ " </span> </p> ";
		htmlText += "<br/>";
		htmlText += "<p  <span style=\"color:blue\"> Empregador ID :"
				+ codigoAtivacao + " </span> </p> ";
		htmlText += "<br/>";
		htmlText += " <strong>Equipe de suporte t&aacute;nahora ! </strong>";
		htmlText += "</body>";
		htmlText += "</html>";
		bodyPart.setContent(htmlText, "text/html");
		multipart.addBodyPart(bodyPart);
		message.setContent(multipart);
		message.setFrom(new InternetAddress(fromEmail));
		message.setSubject("Solicitação tánahora");
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				toEmail));
		transport.connect();
		transport.sendMessage(message,
				message.getRecipients(Message.RecipientType.TO));
		transport.close();

	}

	private static class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}

	}

	public void enviarSenhaRecuperada() {

		System.out.println("antes 1");
		Connection conn = null;
		PreparedStatement ppStm1 = null;
		ResultSet rs = null;
		try {
			conn = ConectaPostgre.obtemConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// retorna registro para pegar email

		try {
			String query = "select nome,senha,email,cpf, codigoativacao from precadempregador where email = ?";
			ppStm1 = conn.prepareStatement(query); // create a statement
			ppStm1.setString(1, email);
			rs = ppStm1.executeQuery();
			rs.next();
			senha = rs.getString(2);

			try {

				send("newtechrio@gmail.com", email, senha, rs.getString(1),
						rs.getString(5));
			} catch (Exception ex) {

			}

			conn.close();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Enviado com sucesso !"));

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
