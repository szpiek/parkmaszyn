package SessionBeans;

import java.util.List;

import javax.ejb.Local;

import EntityBeans.Emploee;
import Utilities.UserInfo;

@Local
public interface UserSessionBeanLocal
{
	public UserInfo userLogin(String login, String password);
	public List<Emploee> getAll(int page);
	public boolean persist(Emploee e);
	public boolean edit(Emploee e);
	public boolean remove(Emploee e);
}
