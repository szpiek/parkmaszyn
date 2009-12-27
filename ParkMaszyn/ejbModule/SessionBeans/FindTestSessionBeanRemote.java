package SessionBeans;
import java.util.ArrayList;

import javax.ejb.Remote;

import DataRepository.EmploeeFinderCriteria;
import DataRepository.MachineFinderCriteria;
import EntityBeans.Emploee;
import EntityBeans.Machine;

@Remote
public interface FindTestSessionBeanRemote {
	public ArrayList<Emploee> findEmploee(EmploeeFinderCriteria efc, boolean strict);
	public ArrayList<Machine> findMachine(MachineFinderCriteria mfc, boolean strict);
}
