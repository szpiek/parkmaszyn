package SessionBeans;

import javax.ejb.Remote;

@Remote
public interface RezervationTimerSessionBeanRemote {

	public boolean startTimer();
	public boolean isTimerSet();
	public boolean cancelTimer();
}
