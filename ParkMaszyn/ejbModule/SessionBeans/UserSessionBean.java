package SessionBeans;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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
    
    @SuppressWarnings("unchecked")
	public UserInfo userLogin(String login, String password)
    {
    	try {
			password = PasswordGenerator.generatePassword(password);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
    	Query checkUser = em.createQuery("from Emploee e where email=? and password=?");
    	checkUser.setParameter(1, login);
    	checkUser.setParameter(2, password);
    	List <Emploee> l = checkUser.getResultList();
    	if(!l.isEmpty())
    	{
    		return new UserInfo(l.get(0));
    	}
    	return null;
    }

}
