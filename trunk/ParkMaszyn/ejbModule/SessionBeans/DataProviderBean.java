package SessionBeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import DataRepository.*;
import EntityBeans.*;


/**
 * Session Bean implementation class DataProviderBean
 */
@Stateless
public class DataProviderBean implements DataProviderBeanRemote, DataProviderBeanLocal {

    @PersistenceContext
	EntityManager em;

	public static final String RemoteJNDIName =  TestSessionBean.class.getSimpleName() + "/remote";
	public static final String LocalJNDIName =  TestSessionBean.class.getSimpleName() + "/local";

    /**
     * Default constructor. 
     */
    public DataProviderBean() {
    }

    public void addSimpleData()
    {
    	System.out.println("-==DATABASE INSERT SIMPLE DATA==- START");
    	ArrayList<Emploee> emploees=new ArrayList<Emploee>();
    	emploees.add(new Emploee("Piotr","Olchawski","olchawski@gmail.com","Development",123,"Yoda","123",false));
    	emploees.add(new Emploee("Micha³","Swatowski","szpieg@gmail.com","Development",456,"Yoda","123",false));
    	emploees.add(new Emploee("Piotr","Wiêcek","reset@gmail.com","Development",789,"Yoda","123",false));
    	emploees.add(new Emploee("Jan","Rokita","rokita@gmail.com","Development",234,"Yoda","123",false));
    	emploees.add(new Emploee("Romuad","Wit","wit@gmail.com","Development",567,"Yoda","123",false));
    	emploees.add(new Emploee("El¿bieta","Richter-W¹s","was@gmail.com","Development",890,"Yoda","123",false));
    	emploees.add(new Emploee("Stephen","King","s.king@gmail.com","Main Plot Coding",159,"Yoda","123",false));
    	for(Emploee emp:emploees) em.persist(emp);
    	
    	Processor proc=new Processor("AMD64X2",64,"x86-64",1700,2);
    	OS os=new OS("Windows 7","6.1","12",64);
    	em.persist(proc);
    	em.persist(os);
    	ArrayList<Machine> machines=new ArrayList<Machine>();
    	machines.add(new Machine("Windows 7","x86-64","Core 2 Duo 2.8 Ghz","192.168.140.1","brzeczyszczykiewicz1","maslo",2048,64,true));
    	machines.add(new Machine("Fedora Core 12","x86-64","Core 2 Quad 3.6 Ghz","192.168.140.2","brzeczyszczykiewicz2","maslo",2048,64,true));
    	machines.add(new Machine("Slackware","x86","Core Duo 1,7 Ghz","192.168.140.3","brzeczyszczykiewicz3","maslo",1024,32,true));
    	machines.add(new Machine("Windows XP SP3","x86","Pentium 4 1,5 Ghz","192.168.140.4","brzeczyszczykiewicz4","maslo",512,32,true));
    	machines.add(new Machine("Red Hat Linux","x86-64","Pentium 133 Mhz","192.168.140.5","brzeczyszczykiewicz5","maslo",256,64,true));
    	machines.add(new Machine("Mac OS Tiger","x86","AMD K6 1.8 Ghz","192.168.140.6","brzeczyszczykiewicz6","maslo",4096,32,true));
    	//ghost machines:
    	machines.add(new Machine("Mac OS Panther","x86","AMD K6 1.8 Ghz","192.168.140.6","brzeczyszczykiewicz7","maslo",4096,32,false));
    	machines.add(new Machine("Mac OS Coconut","x86","AMD K6 1.8 Ghz","192.168.140.6","brzeczyszczykiewicz8","maslo",4096,32,false));
    	
    	for(Machine mach:machines){
    		mach.setProcessor(proc);
    		mach.setOs(os);
    		em.persist(mach);
    	}
    	
    	ArrayList<Rezerwation> rezerwations=new ArrayList<Rezerwation>();
    	
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()), new Date(Calendar.getInstance().getTimeInMillis()), false));
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()), new Date(Calendar.getInstance().getTimeInMillis()), false));
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()), new Date(Calendar.getInstance().getTimeInMillis()), false));
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()), new Date(Calendar.getInstance().getTimeInMillis()), false));
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()), new Date(Calendar.getInstance().getTimeInMillis()), false));
    	
    	for(Rezerwation rez:rezerwations) em.persist(rez);
    	
    	DataOperations.addRezerwationEmploee(em, rezerwations.get(0), emploees.get(1));
    	DataOperations.addRezerwationEmploee(em, rezerwations.get(1), emploees.get(0));
    	DataOperations.addRezerwationEmploee(em, rezerwations.get(2), emploees.get(4));
    	DataOperations.addRezerwationEmploee(em, rezerwations.get(3), emploees.get(0));
    	DataOperations.addRezerwationEmploee(em, rezerwations.get(4), emploees.get(0));
    	
    	DataOperations.addRezerwationMachine(em, rezerwations.get(0), machines.get(1));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(0), machines.get(2));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(0), machines.get(4));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(1), machines.get(3));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(1), machines.get(4));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(2), machines.get(1));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(2), machines.get(2));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(2), machines.get(5));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(2), machines.get(0));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(3), machines.get(6));
    	DataOperations.addRezerwationMachine(em, rezerwations.get(4), machines.get(7));
    	
    	System.out.println("-==DATABASE INSERT SIMPLE DATA==- STOP");
    }

    public void clearDatabase()
    {
    	System.out.println("-==DATABASE CLEARING==- START");
    	for(Machine m:MachineFinder.getAllMachines(em))
    		DataOperations.removeMachine(em, m);
    	for(Emploee e:EmploeeFinder.getAllEmploees(em))
    		DataOperations.removeEmploee(em, e);
    	for(Rezerwation r:RezerwationFinder.getAllRezerwations(em))
    		DataOperations.removeRezervation(em, r);
    	System.out.println("-==DATABASE CLEARING==- STOP");
    }
    
}
