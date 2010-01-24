package Utilities;

import java.security.Security;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import EntityBeans.Emploee;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;
import SessionBeans.UserSessionBeanLocal;

public class MailResender {

	
	public static boolean sendRezervationAcceptInformation(Rezerwation rez) {
		String message="Twoja rezerwacja dla celu: "+rez.getNeed()+" zosta³a zaakceptowana.";
		message+="\nOto dane dostêpu do poszczególnych maszyn:";
		String subject="Potwierdzenie akceptacji zamówienia maszyn";
		for(Machine mach:rez.getMachine())
		{
			message+="\n\nProcesor: "+mach.getProcessor().getName()+" Bitów: "+mach.getProcessor().getBits()+ " Mhz: "+mach.getProcessor().getClock();
			message+="\nPamiêæ: "+mach.getMemory();
			message+="\nSystem: "+mach.getOs().getName()+" Bitów:"+mach.getOs().getBits()+" Version: "+mach.getOs().getVersion()+" Patch:"+mach.getOs().getPatch();
			message+="\nLogin: "+mach.getLogin();
			message+="\nHas³o: "+mach.getPassword();
			message+="\nIP: "+mach.getIP();
		}
		return sendToOneMessage(message,subject,rez.getEmploee().getEmail());
	}
	
	public static boolean sendToAllMessage(String message,String title) {
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		properties.put("java.naming.factory.url.pkgs","=org.jboss.naming:org.jnp.interfaces");
		properties.put("java.naming.provider.url","localhost:1099");
		try {
			Context context = new InitialContext(properties);
			UserSessionBeanLocal usb=(UserSessionBeanLocal) context.lookup("UserSessionBean/local");
			List<Emploee> emploees=usb.getAll(1);
			InternetAddress[] addresses=new InternetAddress[emploees.size()];
			for(int i=0; i<emploees.size(); i++)
				addresses[i]=new InternetAddress(emploees.get(i).getEmail());
			return sendMessage(message,title,addresses);
		} catch (NamingException e) {
			return false;
		}
		 catch (AddressException e) {
			return false;
		}
		
	}
	
	public static boolean sendToOneMessage(String message,String title,String recipient) {
		InternetAddress[] addresses=new InternetAddress[1];
		try {
			addresses[0]=new InternetAddress(recipient);
			return sendMessage(message,title,addresses);
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	private static boolean sendMessage(String message,String title,InternetAddress[] recipient) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        int SMTPport = 465;
        String from = "parkmaszynejb@gmail.com";
        
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        // Setup mail server
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", SMTPport);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        try {
            // Get session
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {

                        @Override
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            String login = "parkmaszynejb";
                            String password = "parkmaszyn";
                            System.out.println(login + " " + password);
                            javax.mail.PasswordAuthentication pa = new javax.mail.PasswordAuthentication(login, password);

                            return pa;
                        }
                    });

            MimeMessage mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(from));
            mess.addRecipients(Message.RecipientType.TO,recipient);
            mess.setSubject(title);
            MimeBodyPart messageBodyPart =  new MimeBodyPart();
            messageBodyPart.setText(message);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            mess.setContent(multipart);
            Transport.send(mess);
            return true;
        } catch (Exception ex) {
        	ex.printStackTrace();
            return false;
        }
    }

	public static boolean sendReservationRejectInformation(Rezerwation r) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void sendReservationExpirationInfo(Rezerwation[] r) {
		// TODO Auto-generated method stub
		
	}

	public static void sendReservationExpirationInfo(Rezerwation[] r, int i) {
		// TODO Auto-generated method stub
		
	}
}
