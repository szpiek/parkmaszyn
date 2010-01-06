package SessionBeans;

import javax.ejb.Local;

import Utilities.UserInfo;

@Local
public interface UserSessionBeanLocal {

	public UserInfo userLogin(String login, String password);
}
