package SessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    }
}
