package SessionBeans;

import java.util.List;

import javax.ejb.Remote;

import EntityBeans.Emploee;
import Utilities.UserInfo;

@Remote
public interface UserSessionBeanRemote
{
	public UserInfo userLogin(String login, String password);
	public List<Emploee> getAll(int page);
	public boolean persist(Emploee e);
	public boolean edit(Emploee e);
	public boolean remove(Emploee e);
}
