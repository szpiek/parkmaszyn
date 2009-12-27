package SessionBeans;
import java.util.ArrayList;

import javax.ejb.Local;

import DataRepository.EmploeeFinderCriteria;
import DataRepository.MachineFinderCriteria;
import EntityBeans.Emploee;
import EntityBeans.Machine;

@Local
public interface FindTestSessionBeanLocal {
	public ArrayList<Emploee> findEmploee(EmploeeFinderCriteria efc, boolean strict);
	public ArrayList<Machine> findMachine(MachineFinderCriteria mfc, boolean strict);
}
