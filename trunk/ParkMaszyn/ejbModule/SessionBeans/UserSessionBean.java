package SessionBeans;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
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
    	System.out.println(login);
    	System.out.println(password);
    	checkUser.setParameter(1, login);
    	checkUser.setParameter(2, password);
    	List <Emploee> l = checkUser.getResultList();
    	if(!l.isEmpty())
    	{
    		return new UserInfo(l.get(0));
    	}
    	return null;
    }
    
    @SuppressWarnings("unchecked")
	public List<Emploee> getAll(int page)
    {
    	return em.createNamedQuery("getAllEmployes").getResultList();
    }

	@Override
	public boolean edit(Emploee e) {
		try
		{
			em.merge(e);
		}
		catch(EntityExistsException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
    
    
	@Override
	public boolean persist(Emploee e) {
		try
		{
			String pass = PasswordGenerator.genPass();
			pass = "123456";
			e.setID(null);
			e.setPassword(PasswordGenerator.generatePassword(pass));
			em.persist(e);
		}
		catch(EntityExistsException ex)
		{
			System.out.println(ex.getMessage());
			return false;
		} catch (NoSuchAlgorithmException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean remove(Emploee e) {
		try
		{
			Emploee emp = em.merge(e);
			em.remove(emp);
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

//	@Override
//	public void pies(boolean test) {
//
//		if(test)
//		{
//			UserTransaction et = sc.getUserTransaction();
//			try {
//				et.begin();
//			} catch (NotSupportedException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			} catch (SystemException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//			Emploee e = em.find(Emploee.class, 11);
//			e.setFirstName("Buc");
//			em.persist(e);
//			em.flush();
//			System.out.println(e.getFirstName());
//			try {
//				Thread.sleep(20000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			if(test)
//				try {
//					System.out.println("rollback");
//					et.commit();
//					//et.rollback();
//				} catch (IllegalStateException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				} catch (SecurityException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				} catch (SystemException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				} catch (HeuristicRollbackException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				} catch (RollbackException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				} catch (HeuristicMixedException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//		}
//		else
//		{
//			Connection con = null;
//			try {
//				con = dataSource.getConnection();
//			} catch (SQLException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
////			try {
////				con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
////			} catch (SQLException e2) {
////				// TODO Auto-generated catch block
////				e2.printStackTrace();
////			}
//			Emploee e = em.find(Emploee.class, 11);
//			try {
//				PreparedStatement ps = con.prepareStatement("SELECT * FROM emploee where ID=11");
//				ResultSet rs = ps.executeQuery();
//				while(rs.next())
//				{
//					System.out.println(rs.getString("firstName"));
//				}
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//			System.out.println(e.getFirstName());
//			try {
//				con.close();
//			} catch (SQLException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
////		} 
//	}

}
