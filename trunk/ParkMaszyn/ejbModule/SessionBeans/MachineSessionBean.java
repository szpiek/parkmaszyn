package SessionBeans;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import DataRepository.DataOperations;
import DataRepository.MachineFinder;
import DataRepository.MachineFinderCriteria;
import DataRepository.RezerwationFinder;
import DataRepository.RezerwationFinderCriteria;
import EntityBeans.Machine;
import EntityBeans.Rezerwation;

/**
 * Session Bean implementation class MachineSessionBean
 */
@Stateless
public class MachineSessionBean implements MachineSessionBeanRemote, MachineSessionBeanLocal {

	@PersistenceContext
	EntityManager em;

	public static final String RemoteJNDIName =  MachineSessionBean.class.getSimpleName() + "/remote";
	public static final String LocalJNDIName =  MachineSessionBean.class.getSimpleName() + "/local";
	
    /**
     * Default constructor. 
     */
    public MachineSessionBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public ArrayList<Machine> getAllMachines() {
		return MachineFinder.getAllMachines(em);
	}

	@Override
	public ArrayList<Machine> getBookableMachines() {
		MachineFinderCriteria mfc=new MachineFinderCriteria();
		mfc.isBook=true;
		return MachineFinder.getMachinesByStrictCriteria(em, mfc);
	}

	@Override
	public ArrayList<Machine> getUnbookableMachines() {
		MachineFinderCriteria mfc=new MachineFinderCriteria();
		mfc.isBook=false;
		return MachineFinder.getMachinesByStrictCriteria(em, mfc);
	}
	
	public ArrayList<Machine> getByCriteria(MachineFinderCriteria mfc)
	{
		return MachineFinder.getMachinesByStrictCriteria(em, mfc);
	}
	
	public void persistMachine(Machine mach)
	{
		MachineFinderCriteria mfc=new MachineFinderCriteria();
		mfc.ID=mach.getID();
		Machine m2=MachineFinder.getMachinesByStrictCriteria(em, mfc).get(0);
		m2.copyFromMachine(mach);
		em.persist(m2);
	}
	
	public void removeMachine(Machine mach)
	{
		DataOperations.removeMachine(em, mach);
	}
	
	public void releaseMachine(Machine mach, Rezerwation res)
	{
		RezerwationFinderCriteria mfc=new RezerwationFinderCriteria();
		mfc.ID=mach.getID();
		Rezerwation r2=RezerwationFinder.getRezerwationsByStrictCriteria(em, mfc).get(0);
		r2.getMachine().remove(mach);
		em.persist(r2);
	}
	
	public ArrayList< ArrayList<Date[]> > getMachinesTimeUsage(ArrayList<Machine> machs)
	{
		ArrayList<ArrayList<Date[]> > ret=new ArrayList<ArrayList<Date[]> >();
		for(Machine mach:machs)
		{
			ArrayList<Rezerwation> rez=(ArrayList<Rezerwation>)mach.getRezerwation();
			if(rez!=null && rez.size()>0)
			{
				ret.add(new ArrayList<Date[]>());
				for(Rezerwation rezerwation:rez)
					ret.get(ret.size()-1).add(new Date[]{ rezerwation.getCreateDate(),rezerwation.getReturnDate()});
			}		
		}
		return ret;
	}
	
}
