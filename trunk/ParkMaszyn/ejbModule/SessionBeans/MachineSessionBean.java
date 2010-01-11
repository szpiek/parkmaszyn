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
		Machine m2=getById(mach.getID());
		if(m2==null) m2=new Machine();
		m2.copyFromMachine(mach);
		em.persist(m2);
	}
	
	public void removeMachine(Machine mach)
	{
		Machine machineToRemove=getById(mach.getID());
		DataOperations.removeMachine(em, machineToRemove);
	}
	
	public void releaseMachine(Machine mach, Rezerwation res)
	{
		Rezerwation r2=getRezerwationById(res.getID());
		Machine toRem=null;
		for(Machine m:r2.getMachine())
		{
				if(m.getID().equals(mach.getID()))
				{
					toRem=m;
					break;
				}
		}
		toRem.getRezerwation().remove(r2);
		r2.getMachine().remove(toRem);
		if(r2.getMachine().isEmpty()) DataOperations.removeRezervation(em, r2);
	}
	
	public ArrayList< ArrayList<Date[]> > getMachinesTimeUsage(ArrayList<Machine> machs)
	{
		System.out.println("MACHS: "+machs.size());
		ArrayList<ArrayList<Date[]> > ret=new ArrayList<ArrayList<Date[]> >();
		for(Machine m:machs)
		{
			System.out.println("BY ID="+m.getID());
			Machine mach=getById(m.getID());
			System.out.println(mach+" "+mach.getRezerwation());
			if(mach.getRezerwation()!=null && mach.getRezerwation().size()>0)
			{
				System.out.println("ADDING: "+mach.getRezerwation().size()+" RESERWATIONS");
				ret.add(new ArrayList<Date[]>());
				for(Rezerwation rezerwation:mach.getRezerwation())
					ret.get(ret.size()-1).add(new Date[]{ rezerwation.getCreateDate(),rezerwation.getReturnDate()});
			}		
		}
		return ret;
	}
	
	public Machine getById(Integer id)
	{
		if(id==null) return null;
		MachineFinderCriteria mfc=new MachineFinderCriteria();
		mfc.ID=id;
		ArrayList<Machine> results=MachineFinder.getMachinesByStrictCriteria(em, mfc);
		return results==null?null:results.get(0);
	}
	
	public Rezerwation getRezerwationById(int id)
	{
		RezerwationFinderCriteria mfc=new RezerwationFinderCriteria();
		mfc.ID=id;
		return RezerwationFinder.getRezerwationsByStrictCriteria(em, mfc).get(0);
	}
	
}
