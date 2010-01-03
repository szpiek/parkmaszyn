package SessionBeans;
import javax.ejb.Remote;

import EntityBeans.Emploee;

@Remote
public interface TestSessionBeanRemote {
	
	public void test();

	public String sayHello();
	
	public Emploee getEmploee();
	
	public void displayEmploee(Emploee emploee);
	
	public void setTimer();
}
