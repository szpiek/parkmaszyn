package SessionBeans;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Remote;

import DataRepository.MachineFinderCriteria;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;

@Remote
public interface MachineSessionBeanRemote {
	public ArrayList<Machine> getAllMachines();
	public ArrayList<Machine> getBookableMachines();
	public ArrayList<Machine> getUnbookableMachines();
	public void persistMachine(Machine mach);
	public void removeMachine(Machine mach);
	public void releaseMachine(Machine mach, Rezerwation res);
	public ArrayList< ArrayList<Date[]> > getMachinesTimeUsage(ArrayList<Machine> machs);
	public ArrayList<Machine> getByCriteria(MachineFinderCriteria mfc);
}
