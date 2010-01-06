package SessionBeans;

import javax.ejb.Stateless;

import Utilities.UserInfo;

/**
 * Session Bean implementation class UserSessionBean
 */
@Stateless
public class UserSessionBean implements UserSessionBeanRemote, UserSessionBeanLocal {
	
    public UserSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    public UserInfo userLogin(String login, String password)
    {
    	if(login.trim().equals("wiecej") && password.trim().equals("qwerty"))
    	{
    		return new UserInfo(666, true);
    	}
    	return null;
    }

}
