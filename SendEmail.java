import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SendEmail {
    public static void main(String[] args) {
        String host = "smtp.gmail.com"; // Replace with your email provider's SMTP host
        String port = "587"; // Replace with your email provider's SMTP port
        String username = "@gmail.com"; // Replace with your email address
        String password = ""; // Replace with your email password
        
        String senderName = " "; // Replace with your name
        String senderEmail = "@gmail.com"; // Replace with your email address
        
        List<String> recipientList = new ArrayList<String>();
        String fileName = "crawlerEmailsCopy.txt"; // Replace with your file name
        
        try {
            // Read file contents into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(fileName));
            // Convert byte array to a String
            String fileContents = new String(fileBytes);
            String[] array = fileContents.split(",");
            for (String s : array) {
                recipientList.add(s);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        
//        recipientList = new ArrayList<String>();
//        recipientList.add("albiecodeutopia@gmail.com");
        
        String subject = "Software Engineering Position Application"; // Replace with your email subject
        String body = ""; // Replace with your email body
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            for (String recipient : recipientList) {
                
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail, senderName));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);
                
                // Create the email body and add the attachment
                Multipart multipart = new MimeMultipart();
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);
                multipart.addBodyPart(messageBodyPart);
                
                // Add the attachment
                String filename = ".pdf"; // Replace with your attachment filename
                DataSource source = new FileDataSource(filename);
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
                
                // Set the email content to include the body and attachment
                message.setContent(multipart);
                
                // Send the email
                Transport.send(message);
                System.out.println("Email sent to " + recipient);
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

