package SessionBeans;
import java.util.ArrayList;

import javax.ejb.Local;

import DataRepository.EmploeeFinderCriteria;
import EntityBeans.Emploee;

@Local
public interface FindTestSessionBeanLocal {
	public ArrayList<Emploee> findEmploee(EmploeeFinderCriteria efc, boolean strict);
}
