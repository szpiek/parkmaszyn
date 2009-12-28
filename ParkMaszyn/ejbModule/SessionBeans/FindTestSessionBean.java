package SessionBeans;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import DataRepository.EmploeeFinder;
import DataRepository.EmploeeFinderCriteria;
import DataRepository.MachineFinder;
import DataRepository.MachineFinderCriteria;
import EntityBeans.Emploee;
import EntityBeans.Machine;

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
    @SuppressWarnings("unchecked")
	public ArrayList<Emploee> findAllEmploee()
    {
    	return (ArrayList<Emploee>)(em.createQuery("SELECT OBJECT(emploee) FROM Emploee emploee").getResultList());
    }
    
    public ArrayList<Emploee> findEmploee(EmploeeFinderCriteria efc, boolean strict)
    {
    	if(strict) return EmploeeFinder.getEmploeesByStrictCriteria(em, efc);
    	return null;
    }
    
    public ArrayList<Machine> findMachine(MachineFinderCriteria mfc, boolean strict)
    {
    	if(strict) return MachineFinder.getMachinesByStrictCriteria(em, mfc);
    	return null;
    }

}
