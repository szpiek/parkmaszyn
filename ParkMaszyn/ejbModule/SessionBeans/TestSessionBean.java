package SessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import EntityBeans.Machine;

/**
 * Session Bean implementation class TestSessionBean
 */
@Stateless
public class TestSessionBean implements TestSessionBeanRemote, TestSessionBeanLocal {

	@PersistenceContext
	EntityManager em;

	public static final String RemoteJNDIName =  TestSessionBean.class.getSimpleName() + "/remote";
	public static final String LocalJNDIName =  TestSessionBean.class.getSimpleName() + "/local";
	
    public TestSessionBean() {
        // TODO Auto-generated constructor stub
    }

    public void test()
    {
    	System.out.println("JEA");
    	Machine m=new Machine();
    	m.setArchitecture("x86-64");
    	m.setBits(8);
    	m.setIP("192.168.140.1");
    	m.setLogin("hehe");
    	m.setMemory(2048);
    	m.setOs("Mac OS");
    	m.setPassword("hihi");
    	m.setProcessor("Intel Core 2 Duo Secundo");
    	em.persist(m);
    	System.out.println("JEA2");
    }
}
