package SessionBeans;

import java.util.List;

import EntityBeans.Rezerwation;

public interface RezervationSessionBeanInterface {
	public List<Rezerwation> getAll(int page);
	public boolean remove(Rezerwation r);
	public List<Rezerwation> getMyRezervations(int userID, int page);
	public boolean persist(Rezerwation r);
	public boolean releaseRezervation(Rezerwation r);
}
