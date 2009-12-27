package SessionBeans;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import DataRepository.EmploeeFinder;
import DataRepository.EmploeeFinderCriteria;
import EntityBeans.Emploee;

/**
 * Session Bean implementation class FindTestSessionBean
 */
@Stateless
public class FindTestSessionBean implements FindTestSessionBeanRemote, FindTestSessionBeanLocal {

	@PersistenceContext
	EntityManager em;

	public static final String RemoteJNDIName =  FindTestSessionBean.class.getSimpleName() + "/remote";
	public static final String LocalJNDIName =  FindTestSessionBean.class.getSimpleName() + "/local";
	
    /**
     * Default constructor. 
     */
    public FindTestSessionBean() {
     
    }
    
    public ArrayList<Emploee> findEmploee(EmploeeFinderCriteria efc, boolean strict)
    {
    	if(strict) return EmploeeFinder.getEmploeesByStrictCriteria(em, efc);
    	return null;
    }

}
