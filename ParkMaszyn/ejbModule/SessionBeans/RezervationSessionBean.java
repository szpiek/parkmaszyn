package SessionBeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import EntityBeans.Machine;
import EntityBeans.Rezerwation;

/**
 * Session Bean implementation class RezervationSessionBean
 */
@Stateless
public class RezervationSessionBean implements RezervationSessionBeanRemote, RezervationSessionBeanLocal {

	@PersistenceContext
	EntityManager em;	
	
//	@EJB private MachineSessionBeanLocal machineSessionLocal;

	
    public RezervationSessionBean() {}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rezerwation> getAll(int page) {
		return em.createQuery("FROM Rezerwation r").getResultList();
	}

	@Override
	public boolean remove(Rezerwation r) {
		try
		{
			em.remove(r);
		}
		catch(IllegalStateException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		catch(IllegalArgumentException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rezerwation> getMyRezervations(int userID, int page) {
		return em.createNamedQuery("getRezByUser").setParameter("userId", userID).getResultList();
	}

	@Override
	public boolean persist(Rezerwation r) {
		if(!r.getIsBook())
		{
			Machine[] m = r.getMachine().toArray(new Machine[1]);
			em.persist(m[0]);
		}
		try
		{
			em.persist(r);
		}
		catch(EntityExistsException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean releaseRezervation(Rezerwation r) {
		try
		{
			em.remove(r);
		}
		catch(IllegalStateException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		catch(IllegalArgumentException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

}
