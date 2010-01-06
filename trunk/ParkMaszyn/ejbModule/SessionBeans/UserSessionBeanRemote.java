package SessionBeans;

import javax.ejb.Remote;

import Utilities.UserInfo;

@Remote
public interface UserSessionBeanRemote {

	public UserInfo userLogin(String login, String password);
}
