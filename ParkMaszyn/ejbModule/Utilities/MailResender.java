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

	static String from = "parkmaszynejb@gmail.com";
	static Session session = null;
	static String host = "smtp.gmail.com";
    static int SMTPport = 465;
    static String login = "parkmaszynejb";
    static String password = "parkmaszyn";
    
	static{
		Properties props = System.getProperties();
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", SMTPport);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
		session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        
                        javax.mail.PasswordAuthentication pa = new javax.mail.PasswordAuthentication(login, password);

                        return pa;
                    }
                });
		/*Properties props = new Properties();
		InitialContext ictx;
		try {
			ictx = new InitialContext(props);
		
		session = (Session) ictx.lookup("java:/Mail");
		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static boolean sendRezervationAcceptInformation(Rezerwation rez) {
		System.out.println("GOT "+rez.getMachine().size()+" machines in rezerwation");
		String message="Twoja rezerwacja dla celu: "+rez.getNeed()+" zosta³a zaakceptowana.";
		message+="\nOto dane dostêpu do poszczególnych maszyn:";
		String subject="Potwierdzenie akceptacji zamówienia maszyn";
		for(Machine mach:rez.getMachine())
		{
			message+=getMachineMessage(mach);
			message+=getMachineAcceptMessage(mach);
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
        

        //Transport transport;
        try {
        	//transport = session.getTransport("smtps");
            // Get session
            MimeMessage mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(from));
            mess.addRecipients(Message.RecipientType.TO,recipient);
            mess.setSubject(title);
            mess.setText(message);
            //transport.connect(login, password);
    		//transport.sendMessage(mess, mess.getAllRecipients());
            Transport.send(mess);
            //transport.close();
            return true;
        } catch (Exception ex) {
        	ex.printStackTrace();
            return false;
        }
			
    }
	
	private static String getMachineAcceptMessage(Machine mach)
	{
		String message="";
		message+="\nLogin: "+mach.getLogin();
		message+="\nHas³o: "+mach.getPassword();
		message+="\nIP: "+mach.getIP();
		return message;
	}

	private static String getMachineMessage(Machine mach)
	{
		String message="";
		message+="\n\nProcesor: "+mach.getProcessor().getName()+" Bitów: "+mach.getProcessor().getBits()+ " Mhz: "+mach.getProcessor().getClock();
		message+="\nPamiêæ: "+mach.getMemory();
		message+="\nSystem: "+mach.getOs().getName()+" Bitów:"+mach.getOs().getBits()+" Version: "+mach.getOs().getVersion()+" Patch:"+mach.getOs().getPatch();
		return message;
	}
	
	public static boolean sendReservationRejectInformation(Rezerwation rez) {
		String message="Twoja rezerwacja dla celu: "+rez.getNeed()+" zosta³a odrzucona.";
		message+="\nOto dane maszyn na które z³o¿ona by³a rezerwacja:";
		String subject="Potwierdzenie odrzucenia zamówienia maszyn";
		for(Machine mach:rez.getMachine())
			message+=getMachineMessage(mach);
		return sendToOneMessage(message,subject,rez.getEmploee().getEmail());
	}

	public static void sendReservationExpirationInfo(Rezerwation[] r) {
		for(Rezerwation rez: r)
		{
		String message="Twoja rezerwacja dla celu: "+rez.getNeed()+" wygaœnie jutro.";
		message+="\nOto dane maszyny na które dokonana jest rezerwacja:";
		String subject="Powiadomienie o koñcz¹cej siê rezerwacji na maszyny";
		for(Machine mach:rez.getMachine())
		{
			message+=getMachineMessage(mach);
			message+=getMachineAcceptMessage(mach);
		}
		sendToOneMessage(message,subject,rez.getEmploee().getEmail());
		}
	}

	public static void sendReservationExpirationInfo(Rezerwation[] r, int i) {
		for(Rezerwation rez: r)
		{
		String message="Twoja rezerwacja dla celu: "+rez.getNeed()+" wygaœnie za "+i+" dni.";
		message+="\nOto dane maszyny na które dokonana jest rezerwacja:";
		String subject="Powiadomienie o koñcz¹cej siê rezerwacji na maszyny";
		for(Machine mach:rez.getMachine())
		{
			message+=getMachineMessage(mach);
			message+=getMachineAcceptMessage(mach);
		}
		sendToOneMessage(message,subject,rez.getEmploee().getEmail());
		}
	}
}
