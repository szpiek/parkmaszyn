package SessionBeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import EntityBeans.Machine;
import EntityBeans.Rezerwation;

/**
 * Session Bean implementation class RezervationSessionBean
 */
@Stateless
public class RezervationSessionBean implements RezervationSessionBeanRemote, RezervationSessionBeanLocal {

	@PersistenceContext
	EntityManager em;	
	
	@Resource(mappedName="java:/mySQLDS") DataSource dataSource;

	
    public RezervationSessionBean() {}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rezerwation> getAll(int page) {
		return em.createQuery("FROM Rezerwation r").getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Rezerwation> getMyRezervations(int userID, int page) {
		return em.createNamedQuery("getRezByUser").setParameter("userId", userID).getResultList();
	}
	

	@Override
	public boolean request(Rezerwation r, int eId)
	{
		Machine[] m = r.getMachine().toArray(new Machine[1]);
		em.merge(m[0]);		
		try
		{
			em.merge(r);
		}
		catch(EntityExistsException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean persist(Rezerwation r, int eId) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			String ids = "";
			boolean first = true;
			for(Machine m:r.getMachine())
			{
				if(!first)
					ids += ",";
				ids += m.getID();
			}
			PreparedStatement ps = con.prepareStatement("SELECT createDate as c, returnDate as r, re.id "+
														"FROM rez_mach r "+
														"INNER JOIN rezerwation re on re.id=r.rez_id " +
														"WHERE MACH_ID in ("+ids+") AND " +
														"((c<? AND ?<e) OR (?<c AND ?<e) OR (c<? AND e<?) OR (?<c and e<?))");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		try
		{
			em.merge(r);
		}
		catch(EntityExistsException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean remove(int rId) {
		try
		{
			Rezerwation r = em.find(Rezerwation.class, rId);
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
