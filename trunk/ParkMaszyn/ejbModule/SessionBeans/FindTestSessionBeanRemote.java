package SessionBeans;
import java.util.ArrayList;

import javax.ejb.Remote;

import DataRepository.EmploeeFinderCriteria;
import EntityBeans.Emploee;

@Remote
public interface FindTestSessionBeanRemote {
	public ArrayList<Emploee> findEmploee(EmploeeFinderCriteria efc, boolean strict);
}
