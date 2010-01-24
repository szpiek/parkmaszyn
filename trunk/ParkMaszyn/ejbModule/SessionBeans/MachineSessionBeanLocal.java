package SessionBeans;
import java.sql.Date;
import java.util.ArrayList;

import javax.ejb.Local;

import DataRepository.MachineFinderCriteria;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;

@Local
public interface MachineSessionBeanLocal {
	public ArrayList<Machine> getAllMachines();
	public ArrayList<Machine> getBookableMachines();
	public ArrayList<Machine> getUnbookableMachines();
	public void persistMachine(Machine mach);
	public void removeMachine(Machine mach);
	public void releaseMachine(Machine mach, Rezerwation res);
	@SuppressWarnings("unchecked")
	public ArrayList< ArrayList<Date[]> > getMachinesTimeUsage(ArrayList machs);
	public ArrayList<Machine> getByCriteria(MachineFinderCriteria mfc);
}
