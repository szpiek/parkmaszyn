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
		System.out.println("ADDING "+rez.getID()+" MACH: "+mach.getID());
		rez.getMachine().add(mach);
	}
	
	public static void removeMachine(EntityManager em, Machine mach)
	{
		em.merge(mach);
		em.remove(mach);
		for( Rezerwation rez:RezerwationFinder.getRezerwationsByMachine(em, mach))
		{
			rez.getMachine().remove(mach);
		}
	}
	
	public static void removeRezervation(EntityManager em, Rezerwation rez)
	{
				em.merge(rez);
				em.remove(rez);
	}
	
	public static void removeEmploee(EntityManager em, Emploee emp)
	{
		em.merge(emp);
		em.remove(emp);
	}
}
