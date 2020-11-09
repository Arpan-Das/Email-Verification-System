package application;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.Label;

public class MainController {
	@FXML
	private TextField txtemail;
	@FXML
	private TextField txtotp;
	@FXML
	private Button butverify, butsend, butreset;
	@FXML
	private Label labstatus, hide;
	


	public void Random() {
		Random rd = new Random();
		hide.setText(""+rd.nextInt(10000 + 1));		
	}
	
	public void sendmail() {
		Properties props=new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port",465);
        props.put("mail.smtp.user","ad0084763@gmail.com"); // your email id
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.debug",true);
        props.put("mail.smtp.socketFactory.port",465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback",false); 
        
        try {             
                Session session = Session.getDefaultInstance(props, null);
                session.setDebug(true);
                MimeMessage message = new MimeMessage(session);
                message.setText("Your OTP is " + hide.getText());	// msg send to the email id
                message.setSubject("Email Verification System");
                message.setFrom(new InternetAddress("ad0084763@gmail.com"));
                message.addRecipient(RecipientType.TO, new InternetAddress(txtemail.getText().trim()));		// email of the reciever
                message.saveChanges();
                try
                {
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com","ad0084763@gmail.com","Enter Your password");	// enter your email id and password
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                              
                JOptionPane.showMessageDialog(null,"OTP has send to your Email id"); 
                }catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"Please check your internet connection");
                }              
            
        } catch (Exception e) {
            e.printStackTrace();  
            JOptionPane.showMessageDialog(null,e);
        }  

	}
	
	@FXML
	public void handler(ActionEvent event) {
		if(event.getSource() == butsend) {
			Random();
			sendmail();
			
		}else if(event.getSource() == butverify) {
			
			if(txtotp.getText().equals(hide.getText())) {
				labstatus.setText("Email verified successfully!!!");
				labstatus.setVisible(true);
			}else {
				labstatus.setText("Incorrect OTP!!!");
				labstatus.setVisible(true);
			}
			
		}else if(event.getSource() == butreset) {
			
			txtemail.setText("");
			txtotp.setText("");
			labstatus.setVisible(false);
		}
	}
}
