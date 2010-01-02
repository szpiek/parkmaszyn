package MessageDrivenBeans;

import java.util.Properties;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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

    /**
     * Default constructor. 
     */
    public MDBTopic() {
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message_) {

    	System.out.println("Dostalem wiadomosc tekstowa od klienta webowego");
    	try 
    	{	
	    	ConnectionFactory connectionFactory = null;
	        Connection connection = null;
	        Session session = null;
	        Destination destination = null;
	        MessageProducer messageProducer = null;
	        TextMessage message = null;
	        System.out.println("Getting ConnectionFactory for JNDI");
	        connectionFactory = getJmsConnectionFactory();
			connection = connectionFactory.createConnection();
	        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        destination = getJmsDestination();
	        messageProducer = session.createProducer(destination);
	        message = session.createTextMessage("Wyslalem wiadomosc!!!");
	        messageProducer.send(message);
	        System.out.println("Message sent");
	        messageProducer.close();
	        session.close();
	        connection.close();
    	} catch (JMSException e) {
			// TODO Auto-generated catch block
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
	
	private Properties getContextProp()
	{
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		properties.put("java.naming.factory.url.pkgs","=org.jboss.naming:org.jnp.interfaces");
		properties.put("java.naming.provider.url","localhost:1099");
		return properties;
	}

}
