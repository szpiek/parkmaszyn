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
	public boolean edit(Rezerwation r, int eId);
	public boolean request(Rezerwation r, int eId);
	public boolean accept(int rId, boolean accepted);
	public boolean acceptRequest(int rId, String login, String password, String ip, boolean accepted); 
}
