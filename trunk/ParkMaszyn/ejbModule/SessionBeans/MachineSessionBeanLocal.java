package SessionBeans;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Local;

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
	public ArrayList< ArrayList<Date[]> > getMachinesTimeUsage(ArrayList<Machine> machs);

}
