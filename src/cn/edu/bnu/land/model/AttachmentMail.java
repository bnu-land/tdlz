package cn.edu.bnu.land.model;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class AttachmentMail {
	
	public boolean sendAttachmentMail(MailSenderInfo mailInfo) throws MessagingException, IOException {
	        MyAuthenticator authenticator = null;
	        Properties pro = mailInfo.getProperties();
	        if (mailInfo.isValidate()) {
	            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
	        }
	        Session sendMailSession = Session.getInstance(pro, authenticator);


	        //创建发送的message
	        MimeMessage message = new MimeMessage(sendMailSession);
	        message.setFrom(new InternetAddress(mailInfo.getFromAddress()));
	        
	        InternetAddress to = new InternetAddress(mailInfo.getToAddress());
	        InternetAddress[] address = {to};
	        // 设置邮件接收者
	        message.addRecipients(Message.RecipientType.TO, address);
	        // 设置邮件消息的主题
	        message.setSubject(mailInfo.getSubject());
	        // 设置邮件消息发送的时间
	        message.setSentDate(new Date());


	        // 邮件内容部分
	        MimeBodyPart mbp1 = new MimeBodyPart();
	        mbp1.setText(mailInfo.getContent());


	        // 邮件内容部分，添加file附件
//	        MimeBodyPart mbp2 = new MimeBodyPart();
//	        File file = new File("c:/i.txt");
//	        mbp2.attachFile(file);


	        Multipart mp = new MimeMultipart();


	        mp.addBodyPart(mbp1);
//	        mp.addBodyPart(mbp2);

	        message.setContent(mp);

	        // 发送message
	        Transport.send(message); 
	        
	        return true; 
	    
	 }
}
