package SessionBeans;
import javax.ejb.Remote;

@Remote
public interface DataProviderBeanRemote {
	public void addSimpleData();
	public void clearDatabase();
}
