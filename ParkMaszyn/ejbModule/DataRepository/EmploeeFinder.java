package DataRepository;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import EntityBeans.Emploee;

public class EmploeeFinder {
	@SuppressWarnings("unchecked")
	public static ArrayList<Emploee> getAllEmploees(EntityManager em)
	{
		return (ArrayList<Emploee>)(em.createQuery("SELECT OBJECT(emploee) FROM Emploee emploee").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Emploee> getEmploeesByStrictCriteria(EntityManager em, EmploeeFinderCriteria criteria)
	{
		Query query = criteria.getStrictCriteria().prepareQuery(em);
		return (ArrayList<Emploee>)query.getResultList();
	} 
	
	
}
