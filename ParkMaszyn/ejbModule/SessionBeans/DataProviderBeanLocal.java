package SessionBeans;
import javax.ejb.Local;

@Local
public interface DataProviderBeanLocal {
	public void addSimpleData();
	public void clearDatabase();
}
