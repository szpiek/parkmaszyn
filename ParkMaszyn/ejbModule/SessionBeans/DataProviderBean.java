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
    	emploees.add(new Emploee("Piotr","Olchawski","olchawski@gmail.com","Development",123,"Micha³ Swatowski","123456",false));
    	emploees.add(new Emploee("Micha³","Swatowski","szpieg@gmail.com","Development",456,"Uknown","123456",false));
    	emploees.add(new Emploee("Piotr","Wiêcek","reset@gmail.com","Development",789,"Micha³ Swatowski","123456",false));
    	emploees.add(new Emploee("Jan","Rokita","rokita@gmail.com","Development",234,"Jan Kowalski","123456",false));
    	emploees.add(new Emploee("Stephen","King","s.king@gmail.com","Main Plot Coding",159,"Jan Kowalski","123456",false));
    	for(Emploee emp:emploees) em.persist(emp);
    	
    	Processor proc=new Processor("AMD64X2",64,"x86-64",1700,2);
    	Processor proc4=new Processor("Core 2 Duo",64,"x86-64",2400,2);
    	Processor proc2=new Processor("Power6",64,"PPC",1400,2);
    	Processor proc3=new Processor("RISC",64,"SPARC",1200,2);
    	OS os=new OS("Windows 7","6.1","12",64);
    	OS os2=new OS("RHEL","5","12",64);
    	OS os4=new OS("SLES","11","12",64);
    	OS os3=new OS("Solaris","10","0",64);
//    	em.persist(proc);
//    	em.persist(proc2);
//    	em.persist(proc3);
//    	em.persist(proc4);
//    	em.persist(os);
//    	em.persist(os2);
//    	em.persist(os3);
//    	em.persist(os4);
    	ArrayList<Machine> machines=new ArrayList<Machine>();
    	machines.add(new Machine(os,proc4, "192.168.140.1","brzeczyszczykiewicz1","maslo",2048,64,true));
    	machines.add(new Machine(os2, proc4,"192.168.140.2","brzeczyszczykiewicz2","maslo",2048,64,true));
    	machines.add(new Machine(os, proc,"192.168.140.3","brzeczyszczykiewicz3","maslo",1024,32,true));
    	machines.add(new Machine(os2, proc,"192.168.140.4","brzeczyszczykiewicz4","maslo",512,32,true));
    	machines.add(new Machine(os3, proc3,"192.168.140.5","brzeczyszczykiewicz5","maslo",256,64,true));
    	machines.add(new Machine(os2, proc2,"192.168.140.6","brzeczyszczykiewicz6","maslo",4096,32,true));
    	//ghost machines:
    	machines.add(new Machine(os4, proc2,"192.168.140.6","brzeczyszczykiewicz7","maslo",4096,32,false));
    	machines.add(new Machine(os4, proc4,null,null,null,4096,32,false));
    	
    	for(Machine mach:machines){
    		em.persist(mach);
    	}
    	
    	ArrayList<Rezerwation> rezerwations=new ArrayList<Rezerwation>();
    	long day = 1000*60*60*24;
    	Rezerwation r = new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()), new Date(Calendar.getInstance().getTimeInMillis()+2*day), true);
    	r.setAccepted(1);
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()), new Date(Calendar.getInstance().getTimeInMillis()+day), true));
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()+2*day), new Date(Calendar.getInstance().getTimeInMillis()+3*day), false));
    	rezerwations.add(r);
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()+3*day), new Date(Calendar.getInstance().getTimeInMillis()+5*day), true));
    	rezerwations.add(new Rezerwation(new Date(Calendar.getInstance().getTimeInMillis()+day), new Date(Calendar.getInstance().getTimeInMillis()+10*day), false));
    	
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
    	for(Rezerwation r:RezerwationFinder.getAllRezerwations(em))
    		DataOperations.removeRezervation(em, r);
    	for(Machine m:MachineFinder.getAllMachines(em))
    		DataOperations.removeMachine(em, m);
    	for(Emploee e:EmploeeFinder.getAllEmploees(em))
    		DataOperations.removeEmploee(em, e);
    	System.out.println("-==DATABASE CLEARING==- STOP");
    }
    
}
