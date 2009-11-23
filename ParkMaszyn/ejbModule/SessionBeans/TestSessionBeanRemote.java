package SessionBeans;
import javax.ejb.Remote;

@Remote
public interface TestSessionBeanRemote {
	
	public void test();
}
