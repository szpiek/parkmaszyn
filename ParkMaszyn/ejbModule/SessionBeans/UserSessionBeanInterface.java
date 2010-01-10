package SessionBeans;

import java.util.List;

import EntityBeans.Emploee;
import Utilities.UserInfo;

public interface UserSessionBeanInterface {
	public UserInfo userLogin(String login, String password);
	public List<Emploee> getAll(int page);
	public boolean persist(Emploee e);
	public boolean remove(Emploee e);
}
