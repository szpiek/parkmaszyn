package SessionBeans;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import EntityBeans.Emploee;
import EntityBeans.MachRez;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;

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
    	
    	Emploee e=new Emploee();
    	e.setDepartment("Woskowy");
    	e.setEmail("e.mai.com");
    	e.setFirstName("Ala");
    	e.setLastName("Kot");
    	e.setManager("Olchawski");
    	e.setPhone(123456);
    	em.persist(e);
    	
    	MachRez mr=new MachRez();
    	mr.setMachID(1);
    	mr.setRezID(1);
    	em.persist(mr);
    	
    	System.out.println("JEA2");
    }

    
    
}
