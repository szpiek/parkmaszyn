package SessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;

import DataRepository.EmploeeFinder;
import DataRepository.MachineFinder;
import DataRepository.MachineFinderCriteria;
import EntityBeans.Emploee;
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
    	
    	Machine m2=new Machine();
    	m2.setArchitecture("x86");
    	m2.setBits(8);
    	m2.setIP("192.168.140.2");
    	m2.setLogin("trallala");
    	m2.setMemory(2048);
    	m2.setOs("Viœta");
    	m2.setPassword("1214");
    	m2.setProcessor("AMD K6");
    	em.persist(m2);
    	em.persist(m);
    	
    	Rezerwation rez=new Rezerwation();
    	rez.getMachine().add(m);
    	rez.getMachine().add(m2);
    	em.persist(rez);
    	
    	m.getRezerwation().add(rez);
    	m2.getRezerwation().add(rez);
    	em.persist(m2);
    	em.persist(m);
    	
    	Emploee emploee=new Emploee();
    	emploee.getRezerwation().add(rez);
    	em.persist(emploee);
    	
    	rez.setEmploee(emploee);
    	em.persist(rez);
    	
    	
    	System.out.println();
    	for(Machine mach:MachineFinder.getAllMachines(em))
    		{
    			System.out.println(mach);
    			for(Rezerwation rezerw:mach.getRezerwation())
    			{
    				System.out.println(rezerw);
    				System.out.println(rezerw.getEmploee());
    			}
    		}
    	System.out.println();
    	
    	Emploee testEmp=em.find(Emploee.class, 1);
    	System.out.println(testEmp);
    	for(Rezerwation rezerw:testEmp.getRezerwation())
    	{
    		System.out.println(rezerw);
    		for(Machine mach:rezerw.getMachine())
    			{
    				System.out.println(mach);
    			}
    	}
    	EmploeeFinder.removeEmploee(em, testEmp);
    	MachineFinderCriteria mfc=new MachineFinderCriteria();
    	mfc.os="Mac OS";
    	
    	
    	for(Machine mach:MachineFinder.getMachinesByStrictCriteria(em, mfc))
		{
			//MachineFinder.removeMachine(em, mach);
    		System.out.println(mach);
		}
    	System.out.println("JEA5");
    }

    
    
}
