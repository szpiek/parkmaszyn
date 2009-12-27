package DataRepository;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import EntityBeans.Machine;

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

}
