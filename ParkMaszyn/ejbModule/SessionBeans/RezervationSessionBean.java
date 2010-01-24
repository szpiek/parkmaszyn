package SessionBeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import EntityBeans.Emploee;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;

/**
 * Session Bean implementation class RezervationSessionBean
 */
@Stateful
@TransactionManagement(TransactionManagementType.BEAN) 
public class RezervationSessionBean implements RezervationSessionBeanRemote, RezervationSessionBeanLocal {

	@PersistenceContext
	EntityManager em;	
	
	@Resource(mappedName="java:/mySQLDS") DataSource dataSource;
	@Resource
	SessionContext sc;
	
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
		m[0].setID(null);
		Machine machine = em.merge(m[0]);	
		Set<Machine> hashSet = new HashSet<Machine>();
		hashSet.add(machine);
		r.setMachine(hashSet);	
		r.setID(null);
		r.setEmploee(em.find(Emploee.class, eId));
		try
		{
			em.merge(r);
		}
		catch(EntityExistsException ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return false;
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean persist(Rezerwation r, int eId) {
		UserTransaction ut = sc.getUserTransaction();
		Connection con = null;
		r.setID(null);
		Emploee e2 = em.find(Emploee.class, eId);
		r.setEmploee(e2);
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
			if(getRezerwationCheck(con, r, ids, false))
			{
				System.out.println("Some machines are already booked");
				con.close();
				return false;
			}
			con.close();
			ut.begin();
			r = em.merge(r);
			con = dataSource.getConnection();
			con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			if(getRezerwationCheck(con, r, ids, false))
			{
				System.out.println("Some machines are already booked (End)");
				ut.rollback();
				con.close();
				return false;
			}
			ut.commit();
			con.close();
			return true;


			
		} catch (SQLException e) {
			e.printStackTrace(); 
		} catch (EntityExistsException ex)	{
			System.out.println(ex.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace(); 
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
	
	private boolean getRezerwationCheck(Connection c, Rezerwation r, String ids, boolean isSecond) throws SQLException
	{
		PreparedStatement ps;
		ps = c.prepareStatement("SELECT createDate as c, returnDate as r, re.id "+
				"FROM rez_mach r "+
				"INNER JOIN rezerwation re on re.id=r.rez_id " +
				"WHERE MACH_ID in ("+ids+") AND " +
				"((createDate<? AND ?<returnDate) OR (?<createDate AND ?<returnDate) OR " +
				"(createDate<? AND ?<returnDate AND returnDate<?) OR (?<createDate and returnDate<?) OR (?=createDate AND ?=returnDate)) " +
				(isSecond?" AND id!="+r.getID()+" ":"")+
				" GROUP by id");
		ps.setDate(1, r.getCreateDate());
		ps.setDate(2, r.getReturnDate());
		ps.setDate(3, r.getCreateDate());
		ps.setDate(4, r.getReturnDate());
		ps.setDate(5, r.getCreateDate());
		ps.setDate(6, r.getCreateDate());
		ps.setDate(7, r.getReturnDate());
		ps.setDate(8, r.getCreateDate());
		ps.setDate(9, r.getReturnDate());	
		ps.setDate(10, r.getCreateDate());
		ps.setDate(11, r.getReturnDate());	
		ps.execute();
		ResultSet rs = ps.getResultSet();
		return rs.next();
	}

}
