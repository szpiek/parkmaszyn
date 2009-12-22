package DataRepository;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import EntityBeans.Machine;
import EntityBeans.Rezerwation;

public class MachineFinder {
	@SuppressWarnings("unchecked")
	public static ArrayList<Machine> getAllMachines(EntityManager em)
	{
		return (ArrayList<Machine>)(em.createQuery("SELECT OBJECT(machine) FROM Machine machine").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Machine> getMachinesByStrictCriteria(EntityManager em, MachineFinderCriteria criteria)
	{
		Query query = criteria.getStrictCriteria().prepareQuery(em);
		return (ArrayList<Machine>)query.getResultList();
	} 
	
	public static void removeMachine(EntityManager em, Machine mach)
	{
		
		for(Rezerwation rez:mach.getRezerwation())
			{
				rez.getMachine().remove(mach);
				if(rez.getMachine().isEmpty())
					RezerwationFinder.removeRezervation(em, rez);
			}
		em.remove(mach);
	}
	

}
