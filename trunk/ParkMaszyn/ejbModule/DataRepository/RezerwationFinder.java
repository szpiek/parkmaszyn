package DataRepository;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import EntityBeans.Machine;
import EntityBeans.Rezerwation;

public class RezerwationFinder {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Rezerwation> getAllRezerwations(EntityManager em)
	{
		return (ArrayList<Rezerwation>)(em.createQuery("SELECT OBJECT(rezerwation) FROM Rezerwation rezerwation").getResultList());
	}
	
	public static void removeRezervation(EntityManager em, Rezerwation rez)
	{
				for(Machine mach:rez.getMachine())
					mach.getRezerwation().remove(rez);
				em.remove(rez);
	}
}
