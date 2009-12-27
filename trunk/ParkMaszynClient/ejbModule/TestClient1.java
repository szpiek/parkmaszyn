import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import DataRepository.EmploeeFinderCriteria;
import EntityBeans.Emploee;
import SessionBeans.DataProviderBeanRemote;
import SessionBeans.FindTestSessionBeanRemote;


public class TestClient1 {

	public static void clearTest(Context context) throws NamingException
	{
		DataProviderBeanRemote dpbr=(DataProviderBeanRemote) context.lookup("DataProviderBean/remote");
		dpbr.clearDatabase();	
	}
	
	public static void addTest(Context context) throws NamingException
	{
		DataProviderBeanRemote dpbr=(DataProviderBeanRemote) context.lookup("DataProviderBean/remote");
		dpbr.addSimpleData();	
	}
	
	public static void findEmploeeTest(Context context) throws NamingException
	{
		FindTestSessionBeanRemote dpbr=(FindTestSessionBeanRemote) context.lookup("FindTestSessionBean/remote");
		EmploeeFinderCriteria efc=new EmploeeFinderCriteria();
		efc.firstName="*io*";
		ArrayList<Emploee> emploees=dpbr.findEmploee(efc, true);
		for(Emploee emp:emploees)
			System.out.println(emp);
	}
	
	
	
	public static void main(String[] args)
	{
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		properties.put("java.naming.factory.url.pkgs","=org.jboss.naming:org.jnp.interfaces");
		properties.put("java.naming.provider.url","localhost:1099");
		try {
			Context context = new InitialContext(properties);
			clearTest(context);
			addTest(context);
			findEmploeeTest(context);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
