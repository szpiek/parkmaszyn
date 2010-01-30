package SessionBeans;
import java.util.ArrayList;
import java.sql.Date;

import javax.ejb.Remote;

import DataRepository.MachineFinderCriteria;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;

@Remote
public interface MachineSessionBeanRemote {
	public ArrayList<Machine> getAllMachines();
	public ArrayList<Machine> getBookableMachines();
	public ArrayList<Machine> getUnbookableMachines();
	public boolean persistMachine(Machine mach);
	public boolean updateMachine(Machine mach);
	public void removeMachine(Machine mach);
	public void releaseMachine(Machine mach, Rezerwation res);
	@SuppressWarnings("unchecked")
	public ArrayList< ArrayList<Date[]> > getMachinesTimeUsage(ArrayList machs);
	public ArrayList<Machine> getByCriteria(MachineFinderCriteria mfc);
}
