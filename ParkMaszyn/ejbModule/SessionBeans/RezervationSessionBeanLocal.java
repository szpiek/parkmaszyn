package SessionBeans;

import java.util.List;
import javax.ejb.Local;
import EntityBeans.Rezerwation;

@Local
public interface RezervationSessionBeanLocal {
	public List<Rezerwation> getAll(int page);
	public boolean remove(int rId);
	public List<Rezerwation> getMyRezervations(int userID, int page);
	public boolean persist(Rezerwation r, int eId);
	public boolean request(Rezerwation r, int eId);
}
