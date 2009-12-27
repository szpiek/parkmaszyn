package DataRepository;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import EntityBeans.Rezerwation;

public class RezerwationFinder {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Rezerwation> getAllRezerwations(EntityManager em)
	{
		return (ArrayList<Rezerwation>)(em.createQuery("SELECT OBJECT(rezerwation) FROM Rezerwation rezerwation").getResultList());
	}
	
}
