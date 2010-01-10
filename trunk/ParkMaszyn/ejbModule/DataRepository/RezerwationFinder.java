package DataRepository;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import EntityBeans.Machine;
import EntityBeans.Rezerwation;

public class RezerwationFinder {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Rezerwation> getAllRezerwations(EntityManager em)
	{
		return (ArrayList<Rezerwation>)(em.createQuery("SELECT OBJECT(rezerwation) FROM Rezerwation rezerwation").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Rezerwation> getRezerwationsByStrictCriteria(EntityManager em, RezerwationFinderCriteria criteria)
	{
		Query query = criteria.getStrictCriteria().prepareQuery(em);
		return (ArrayList<Rezerwation>)query.getResultList();
	}
}
