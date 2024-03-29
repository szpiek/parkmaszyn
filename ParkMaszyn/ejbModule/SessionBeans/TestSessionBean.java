package SessionBeans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import Utilities.MailResender;

import DataRepository.EmploeeFinder;
import DataRepository.EmploeeFinderCriteria;
import DataRepository.MachineFinder;
import DataRepository.MachineFinderCriteria;
import DataRepository.RezerwationFinder;
import EntityBeans.Emploee;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;

/**
 * Session Bean implementation class TestSessionBean
 */
@Stateless
public class TestSessionBean implements TestSessionBeanRemote, TestSessionBeanLocal, TimedObject {

	@PersistenceContext
	EntityManager em;
	
	@Resource
	TimerService timerService;

	public static final String RemoteJNDIName =  TestSessionBean.class.getSimpleName() + "/remote";
	public static final String LocalJNDIName =  TestSessionBean.class.getSimpleName() + "/local";
	
    public TestSessionBean() {
       
    }
    
    public String sayHello()
    {
    	return "HELLO WEB CLIENT!!!";
    }
    
    public Emploee getEmploee()
    {
    	return new Emploee("Piotr","Olchawski","olchawski@gmail.com","Development",123,"Yoda","123",false);
    }
    
    public void displayEmploee(Emploee emploee)
    {
    	System.out.println(emploee.toString());
    }

    public void test()
    {
    	MailResender.sendToOneMessage("to jest single message", "test1mess", "olchawski@gmail.com");
    	MailResender.sendToAllMessage("to jest multi message", "test2mess");
    	
    	System.out.println("JEA");
    	
    	Rezerwation rez=new Rezerwation();
    	rez.getMachine().add( MachineFinder.getAllMachines(em).get(0) );
    	rez.getMachine().add( MachineFinder.getAllMachines(em).get(1) );
    	em.persist(rez);
    	
    	em.persist(MachineFinder.getAllMachines(em).get(0));
    	em.persist(MachineFinder.getAllMachines(em).get(1));
    	
    	Emploee emploee=new Emploee();
    	emploee.getRezerwation().add(rez);
    	emploee.setFirstName("John");
    	em.persist(emploee);
    	
    	rez.setEmploee(emploee);
    	em.persist(rez);
    	
    	
    	System.out.println();
    	for(Machine mach:MachineFinder.getAllMachines(em))
    		{
    			System.out.println(mach);
    			for(Rezerwation rezerw:RezerwationFinder.getRezerwationsByMachine(em, mach))
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
    	
    	MachineFinderCriteria mfc=new MachineFinderCriteria();
    	mfc.login="brzeczyszczykiewicz4";   	
    	
    	
    	for(Machine mach:MachineFinder.getMachinesByStrictCriteria(em, mfc))
		{
    		System.out.println(mach);
		}
    	
    	EmploeeFinderCriteria efc=new EmploeeFinderCriteria();
    	efc.firstName="John";
    	
    	for(Emploee empl:EmploeeFinder.getEmploeesByStrictCriteria(em, efc))
		{
    		System.out.println(empl);
		}
    	System.out.println("JEA5");
    }

    
	@Override
	public void ejbTimeout(Timer timer) {
		System.out.println("TIMER ecexuted !!! MSG: " + (String)timer.getInfo());
	}

	@Override
	public void setTimer() {
		String msg = "Message from setTimer()";
		for(Object t : timerService.getTimers())
			((Timer)t).cancel();
			
			//timerService.createTimer(5000000, msg);
	}

    
    
}
