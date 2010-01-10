package SessionBeans;

import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import EntityBeans.Emploee;
import Utilities.PasswordGenerator;
import Utilities.UserInfo;

/**
 * Session Bean implementation class UserSessionBean
 */
@Stateless
public class UserSessionBean implements UserSessionBeanRemote, UserSessionBeanLocal {
	
	@PersistenceContext
	EntityManager em;	

	public UserSessionBean() {}
    
    public UserInfo userLogin(String login, String password)
    {
    	try {
			password = PasswordGenerator.generatePassword(password);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
    	Query checkUser = em.createNamedQuery("from emploee where email=? and password=?");
    	checkUser.setParameter(1, login);
    	checkUser.setParameter(2, password);
    	Emploee e = (Emploee)checkUser.getSingleResult();
    	if(e!=null)
    	{
    		return new UserInfo(e);
    	}
    	return null;
    }

}
