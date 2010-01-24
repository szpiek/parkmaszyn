package SessionBeans;

import javax.ejb.Local;

@Local
public interface RezervationTimerSessionBeanLocal {

	public boolean startTimer();
	public boolean isTimerSet();
	public boolean cancelTimer();
	
}
