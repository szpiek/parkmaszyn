package DataRepository;

import javax.persistence.EntityManager;

import EntityBeans.Emploee;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;

public class DataOperations {
	public static void addRezerwationEmploee(EntityManager em,Rezerwation rez, Emploee emp)
	{
		rez.setEmploee(emp);
		emp.getRezerwation().add(rez);
	}
	
	public static void addRezerwationMachine(EntityManager em,Rezerwation rez, Machine mach)
	{
		rez.getMachine().add(mach);
		mach.getRezerwation().add(rez);
	}
	
	public static void removeMachine(EntityManager em, Machine mach)
	{
		for(Rezerwation rez:mach.getRezerwation())
		{
			rez.getMachine().remove(mach);
			if(rez.getMachine().isEmpty())
				removeRezervation(em, rez);
		}
		em.remove(mach);
	}
	
	public static void removeRezervation(EntityManager em, Rezerwation rez)
	{
				for(Machine mach:rez.getMachine())
					mach.getRezerwation().remove(rez);
				em.remove(rez);
	}
	
	public static void removeEmploee(EntityManager em, Emploee emp)
	{
		for(Rezerwation rez:emp.getRezerwation())
			removeRezervation(em, rez);
		em.remove(emp);
	}
}
