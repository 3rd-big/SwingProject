package project;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//https://coderoad.ru/8186395/NoClassDefFoundError-javax-activation-DataSource
// 메이븐 설정
public class SendMail {
	private String name;
	private String toMailAddress;
	private StringBuffer tmpPassword;
	
	SendMail(String name, StringBuffer tmpPassword, String toMailAddress){
		this.name = name;
		this.toMailAddress = toMailAddress;
		this.tmpPassword = tmpPassword;
	}
	
	public void authMail() {

		Properties prop = System.getProperties(); // properties가져오기
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);// 네이버 구글등 사이트에따라 포트가 바뀜
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		// 해당 웹사이트에 로그인해서 메일을 보낼 아이디정보
		Session sess = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("tsseo.sendMailTest@gmail.com", "javaswing2020!!");
			}
		});

		MimeMessage msg = new MimeMessage(sess);

		try {
			// 보내는사람 메일주소
			msg.setFrom(new InternetAddress("tsseo.sendMailTest@gmail.com"));
			// 받을사람 이메일주소
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toMailAddress));
			// 메일제목
			msg.setSubject("[세바시] 회원가입을 위한 이메일 인증번호");
			// 메일내용
			msg.setText("안녕하세요. " + name + "님 \n[세바시]회원가입을 위한 이메일 인증 임시비밀번호를 알려드립니다.\n 임시 비밀번호: " + tmpPassword);
			// 전송
			Transport.send(msg);
		} catch (AddressException ae) {
			System.out.println("AddressException: " + ae.getMessage());
		} catch (MessagingException me) {
			System.out.println("MessagingException: " + me.getMessage());
		}
	}

}
