package com.bs.firstboot;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.val;

@SpringBootTest
class HelloBootApplicationTests {
	
	@Autowired
	private JavaMailSender javaMailSender;

    public void send(Map<String,String> msg) throws MessagingException{

        MimeMessage mimeMessage=javaMailSender.createMimeMessage();

        MimeMessageHelper help=new MimeMessageHelper(mimeMessage,"UTF-8");
        help.setFrom(msg.get("sender"));
        help.setTo(msg.get("receiver"));
        help.setSubject(msg.get("title"));
        help.setText(getMailBody(msg.get("msg")),true);
        javaMailSender.send(mimeMessage);
    }

    private String getMailBody(String msg){
        String body="";
        body+="<h1>"+msg+"<h1>";
        body+="<h3 style='color:red;'>타인에게 공지하지마세요</h3>";
        return body;
    }
	@Test
	void contextLoads() throws MessagingException {
		 MimeMessage mimeMessage=javaMailSender.createMimeMessage();

	    MimeMessageHelper help=new MimeMessageHelper(mimeMessage,"UTF-8");
	    //help.setFrom("teacherdev09@gmail.com");
	    help.setTo("prince0324@naver.com");
	    help.setSubject("test");
	    help.setText("test",true);
	    
	    javaMailSender.send(mimeMessage);
	
	}
	
	
	

}
