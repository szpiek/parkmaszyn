package MessageDrivenBeans;

import java.util.Properties;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import EntityBeans.Machine;
import EntityBeans.Rezerwation;
import SessionBeans.RezervationSessionBeanLocal;

/**
 * Message-Driven Bean implementation class for: MDBTopic
 *
 */
@MessageDriven(  
		activationConfig= {  
		@ActivationConfigProperty(propertyName="destination",propertyValue="topic/testTopic"),  
		@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Topic")  
		}, name="testTopic"  
		)  
public class MDBTopic implements MessageListener {

	@EJB 
	RezervationSessionBeanLocal resSessionBean;
	
    /**
     * Default constructor. 
     */
    public MDBTopic() {
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message_) {
    	try {	
			ObjectMessage omsg = (ObjectMessage)message_;
			Rezerwation res = (Rezerwation) omsg.getObject();
    		
	    	ConnectionFactory connectionFactory = null;
	        Connection connection = null;
	        Session session = null;
	        Destination destination = null;
	        MessageProducer messageProducer = null;
	        TextMessage message = null;
	        connectionFactory = getJmsConnectionFactory();
			connection = connectionFactory.createConnection();
	        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        destination = getJmsDestination();
	        messageProducer = session.createProducer(destination);
	        if(omsg.getStringProperty("action").equals("zaakceptowana"))
	        	message = session.createTextMessage("Rezerwacja na okres\n\n" + res.getCreateDate() + "  -  " + res.getReturnDate() + "\n\nzostala zaakceptowana");
	        else
	        	message = session.createTextMessage("Rezerwacja na okres\n\n" + res.getCreateDate() + "  -  " + res.getReturnDate() + "\n\nzostala odrzucona");
	        message.setIntProperty("user", res.getEmploee().getID());
	        messageProducer.send(message);
	        messageProducer.close();
	        session.close();
	        connection.close();
    	} catch (JMSException e) {
			e.printStackTrace();
		}
    }

	private Destination getJmsDestination() {
		Destination jmsDestination = null;
        try {
            Context ctx = new InitialContext(getContextProp());
            jmsDestination = (Destination) ctx.lookup("topic/testTopic_flex");
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return jmsDestination;
	}

	private ConnectionFactory getJmsConnectionFactory() {
		ConnectionFactory jmsConnectionFactory = null;
        try {
            Context ctx = new InitialContext(getContextProp());
            jmsConnectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return jmsConnectionFactory;
	}
	
	private Properties getContextProp(){
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		properties.put("java.naming.factory.url.pkgs","=org.jboss.naming:org.jnp.interfaces");
		properties.put("java.naming.provider.url","localhost:1099");
		return properties;
	}

}
