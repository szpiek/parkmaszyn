package SessionBeans;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import DataRepository.MachineFinder;
import DataRepository.MachineFinderCriteria;
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
	
	public void persistMachine(Machine mach)
	{
		em.persist(mach);
	}
	
	public void removeMachine(Machine mach)
	{
		em.remove(mach);
	}
	
	public void releaseMachine(Machine mach, Rezerwation res)
	{
		res.getMachine().remove(mach);
		em.persist(res);
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
