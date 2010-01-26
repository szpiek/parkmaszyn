import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import DataRepository.EmploeeFinderCriteria;
import DataRepository.ISortable;
import DataRepository.MachineFinderCriteria;
import EntityBeans.Emploee;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;
import SessionBeans.DataProviderBeanRemote;
import SessionBeans.FindTestSessionBeanRemote;
import SessionBeans.MachineSessionBeanRemote;
import SessionBeans.RezervationSessionBeanRemote;
import SessionBeans.RezervationTimerSessionBeanRemote;
import SessionBeans.TestSessionBeanRemote;


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
	
	public static void acceptTest(Context context) throws NamingException
	{
		RezervationSessionBeanRemote dpbr=(RezervationSessionBeanRemote) context.lookup("RezervationSessionBean/remote");
		System.out.println(dpbr.acceptRequest(19, "pies", "pies2", "pies3", true));
	}
	
	public static void timerTest(Context context) throws NamingException
	{
		RezervationTimerSessionBeanRemote dpbr=(RezervationTimerSessionBeanRemote) context.lookup("RezervationTimerSessionBean/remote");
		dpbr.startTimer();
		dpbr.isTimerSet();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dpbr.cancelTimer();
	}
	
	public static void findEmploeeTest(Context context) throws NamingException
	{
		FindTestSessionBeanRemote dpbr=(FindTestSessionBeanRemote) context.lookup("FindTestSessionBean/remote");
		EmploeeFinderCriteria efc=new EmploeeFinderCriteria();
		efc.firstName="*i*tr";
		efc.setSortType(ISortable.SORT_DESC);
		efc.setSortProperty("lastName");
		ArrayList<Emploee> emploees=dpbr.findEmploee(efc, true);
		for(Emploee emp:emploees)
			System.out.println(emp);
	}
	
	public static void findMachineTest(Context context) throws NamingException
	{
		FindTestSessionBeanRemote dpbr=(FindTestSessionBeanRemote) context.lookup("FindTestSessionBean/remote");
		MachineFinderCriteria mfc=new MachineFinderCriteria();
		mfc.setSortType(ISortable.SORT_DESC);
		mfc.setSortProperty("os");
		ArrayList<Machine> emploees=dpbr.findMachine(mfc, true);
		for(Machine emp:emploees)
			System.out.println(emp);
	}
	
	public static void rezerwationTest(Context context) throws NamingException
	{
		RezervationSessionBeanRemote dpbr=(RezervationSessionBeanRemote) context.lookup("RezervationSessionBean/remote");
		MachineSessionBeanRemote m=(MachineSessionBeanRemote) context.lookup("MachineSessionBean/remote");
		ArrayList<Machine> all = m.getAllMachines();
		Date d1 = new Date(1264259793500L);
		Date d2 = new Date(1264289793500L);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		System.out.println(sd.format(d1));
		System.out.println(sd.format(d2));
		Rezerwation r = new Rezerwation(d1,d2 , true);
		Set<Machine> s = new HashSet<Machine>();
		for(Machine ma : all)
		{
			if(ma.getID()==11)
				s.add(ma);
		}
		r.setMachine(s);
		dpbr.persist(r, 8);
	}	
	
	public static void mailTest(Context context)throws NamingException
	{
		TestSessionBeanRemote dpbr=(TestSessionBeanRemote) context.lookup("TestSessionBean/remote");
		dpbr.test();
	}
	
	public static void MachineTest(Context context) throws NamingException
	{
		MachineSessionBeanRemote dpbr=(MachineSessionBeanRemote) context.lookup("MachineSessionBean/remote");
		FindTestSessionBeanRemote ftsbr=(FindTestSessionBeanRemote) context.lookup("FindTestSessionBean/remote");
		
		ArrayList<Machine> machines;
		System.out.println("START GET ALL MACHINES");
		machines=dpbr.getAllMachines();
		for(Machine m:machines)
			System.out.println(m);
		System.out.println("STOP GET ALL MACHINES");
		System.out.println("START GET BOOKABLE MACHINES");
		machines=dpbr.getBookableMachines();
		for(Machine m:machines)
			System.out.println(m);
		System.out.println("STOP GET BOOKABLE MACHINES");
		System.out.println("START GET UNBOOKABLE MACHINES");
		machines=dpbr.getUnbookableMachines();
		for(Machine m:machines)
			System.out.println(m);
		System.out.println("STOP GET UNBOOKABLE MACHINES");
		System.out.println("START PERSIST MACHINE");
		Machine m1=new Machine("sys","123","123","123","234","234",123,12,false);
		dpbr.persistMachine(m1);
		System.out.println(m1);
		MachineFinderCriteria mfc=new MachineFinderCriteria();
		mfc.IP="123";
		Machine m=dpbr.getByCriteria(mfc).get(0);
		System.out.println(m);
		m.setLogin("LONGIN");
		dpbr.persistMachine(m);
		System.out.println(dpbr.getAllMachines().get( dpbr.getAllMachines().size()-1 ) );
		System.out.println("STOP PERSIST MACHINE");
		System.out.println("START REMOVE MACHINE");
		System.out.println("REMOVING: "+m);
		System.out.println("LEFT:");
		dpbr.removeMachine(m);
		machines=dpbr.getAllMachines();
		for(Machine m2:machines)
			System.out.println(m2);
		System.out.println("STOP REMOVE MACHINE");
		System.out.println("START RELEASE MACHINE");
		EmploeeFinderCriteria efc=new EmploeeFinderCriteria();
		efc.firstName="Piotr";
		efc.lastName="Olchawski";
		Emploee empl=ftsbr.findEmploee(efc, true).get(0);
		Rezerwation rezerw=new Rezerwation();
		System.out.println("BEFORE");
		for(Rezerwation r:empl.getRezerwation())
		{
			for(Machine machine:r.getMachine())
					{
						if(machine.getOs().equals("Mac OS Coconut"))
							rezerw=r;
						System.out.println(machine);
					}
		}
		mfc=new MachineFinderCriteria();
		mfc.login="brzeczyszczykiewicz4";
		Machine mach=ftsbr.findMachine(mfc, true).get(0);
		
		System.out.println("RELEASE: "+mach+" FROM REZERWATION: "+rezerw);
		dpbr.releaseMachine(mach,rezerw);
		System.out.println("AFTER");
		ArrayList<Machine> machinesToTiming=new ArrayList<Machine>();
		empl=ftsbr.findEmploee(efc, true).get(0);
		for(Rezerwation r:empl.getRezerwation())
		{
			for(Machine machine:r.getMachine())
					{
							machinesToTiming.add(machine);
							System.out.println(machine);
					}
		}
		
		System.out.println("STOP RELEASE MACHINE");
		System.out.println("START TIME USAGE MACHINES");
			ArrayList<ArrayList<Date[]>> dates=dpbr.getMachinesTimeUsage(machinesToTiming);
			for(ArrayList<Date[]> al:dates)
			{
				for(Date[] d: al)
				{
					System.out.print(Arrays.deepToString(d)+";");
				}
				System.out.println();
			}
		System.out.println("STOP TIME USAGE MACHINES");
		
	}
	
	
	
	public static void main(String[] args)
	{
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		properties.put("java.naming.factory.url.pkgs","=org.jboss.naming:org.jnp.interfaces");
		properties.put("java.naming.provider.url","localhost:1099");
		try {
			Context context = new InitialContext(properties);
//			timerTest(context);
//			rezerwationTest(context);
//			clearTest(context);
			addTest(context);
//			findMachineTest(context);
//			MachineTest(context);
			//clearTest(context);
			//addTest(context);
			//findMachineTest(context);
			//MachineTest(context);
//			mailTest(context);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
