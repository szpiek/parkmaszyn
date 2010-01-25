package SessionBeans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import EntityBeans.Rezerwation;
import Utilities.MailResender;

/**
 * Session Bean implementation class RezervationTimerBean
 */
@Stateless
public class RezervationTimerSessionBean implements RezervationTimerSessionBeanRemote, RezervationTimerSessionBeanLocal {

	@Resource TimerService timerService;
	@Resource(mappedName="java:/mySQLDS") DataSource dataSource;
	@PersistenceContext EntityManager em;	
	
	private final String name = "REZ_TIMER";
//	private final long interval = 1000 * 60 * 60 * 24;
	private final long interval = 1000 * 30;

    public RezervationTimerSessionBean() {}
    
    @Override
    public boolean startTimer()
    {
		if(isTimerSet())
		{
			System.out.println("Timer already set!");
			return false;
		}
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 1);
		timerService.createTimer(c.getTime(), interval, name);
		System.out.println("Timer created!");
    	return true;
    }
    
    @Override
	public boolean isTimerSet()
    {
    	return getTimer()!=null;
    }
    
    @SuppressWarnings("unchecked")
	private Timer getTimer()
    {
    	Collection<Timer> timersCollection = timerService.getTimers();
		Iterator<Timer> iterator = timersCollection.iterator();

		while (iterator.hasNext()) {
			Timer timer = iterator.next();
			if (timer.getInfo().equals(name)) {
				System.out.println("Timer: " + name + " found.");
			}
			return timer;
		}    	
		return null;
    }
    
    @Override
    public boolean cancelTimer()
    {
    	Timer timer = getTimer();
    	if(timer==null)
    	{
    		System.out.println("Timer is not set");
    		return false;
    	}
    	timer.cancel();
    	System.out.println("Timer has been canceled");
    	return true;
    }
    
    @Timeout
    public void timeout(Timer timer)
    {
    	Connection c = null;
    	try {
    		ArrayList<Integer> expIds = new ArrayList<Integer>();
    		ArrayList<Integer> expIds3 = new ArrayList<Integer>();
    		c = dataSource.getConnection();
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			PreparedStatement ps = c.prepareStatement("SELECT ID FROM REZERWATION WHERE returnDate=?");
			ps.setDate(1, new Date(cal.getTimeInMillis()));
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next())
			{
				expIds.add(rs.getInt(1));
			}
			cal.add(Calendar.DAY_OF_MONTH, 3);
			ps.setDate(1, new Date(cal.getTimeInMillis()));
			ps.execute();
			rs = ps.getResultSet();
			while(rs.next())
			{
				expIds3.add(rs.getInt(1));
			}
			cal.add(Calendar.DAY_OF_MONTH, -4);
			ps = c.prepareStatement("DELETE FROM rez_mach where rez_id in (SELECT ID FROM rezerwation where returnDate=?)");
			ps.setDate(1, new Date(cal.getTimeInMillis()));
			ps.execute();
			ps = c.prepareStatement("DELETE FROM rezerwation  WHERE returnDate=?");
			ps.setDate(1, new Date(cal.getTimeInMillis()));
			ps.execute();
			c.close();
			if(expIds.size()>0)
			{
				Rezerwation r[] = new Rezerwation[expIds.size()];
				int i = 0;
				for(Integer id : expIds)
				{
					r[i] = em.find(Rezerwation.class, id);
					i++;
				}
				MailResender.sendReservationExpirationInfo(r);
			}
			if(expIds3.size()>0)
			{
				Rezerwation r[] = new Rezerwation[expIds3.size()];
				int i = 0;
				for(Integer id : expIds3)
				{
					r[i] = em.find(Rezerwation.class, id);
					i++;
				}
				MailResender.sendReservationExpirationInfo(r, 3);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				c.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
			try {
				c.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	    }
    	
    	System.out.println("Timer "+timer.getInfo());
    }

}
