import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import SessionBeans.TestSessionBean;
import SessionBeans.TestSessionBeanRemote;


public class TestClient1 {

	public static void main(String[] args)
	{
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		properties.put("java.naming.factory.url.pkgs","=org.jboss.naming:org.jnp.interfaces");
		properties.put("java.naming.provider.url","localhost:1099");
		try {
			Context context = new InitialContext(properties);
			TestSessionBeanRemote tsbr=(TestSessionBeanRemote) context.lookup(TestSessionBean.RemoteJNDIName);
			tsbr.test();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
