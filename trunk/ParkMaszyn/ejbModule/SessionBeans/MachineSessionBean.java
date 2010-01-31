package SessionBeans;

import java.sql.Date;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import DataRepository.DataOperations;
import DataRepository.MachineFinder;
import DataRepository.MachineFinderCriteria;
import DataRepository.ProcessorFinder;
import DataRepository.ProcessorFinderCriteria;
import DataRepository.RezerwationFinder;
import EntityBeans.Machine;
import EntityBeans.OS;
import EntityBeans.Processor;
import EntityBeans.Rezerwation;
import Utilities.FlexToJavaConverter;

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

	@SuppressWarnings("unchecked")
	private ArrayList<Processor> getAllProcessors()
	{
		return (ArrayList<Processor>) em.createNamedQuery("getAllProcessors").getResultList();
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
	
	
	public boolean updateMachine(Machine mach)
	{
		System.out.println("updateMachine MACHINE ID: " + mach.getID());
		System.out.println("persistMachine MACHINE - OS NAME: " + mach.getOs().getName());
		try
		{
			mach.setOs(em.merge(mach.getOs()));
			mach.setProcessor(em.merge(mach.getProcessor()));
			mach=em.merge(mach);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean persistMachine(Machine mach)
	{
		System.out.println("persistMachine MACHINE ID: " + mach.getID());
		System.out.println("persistMachine MACHINE - OS NAME: " + mach.getOs().getName());
		try
		{
			mach.setID(null);
			mach.getOs().setID(null);
			mach.getProcessor().setId(null);
			em.persist(mach);
		}
		catch(EntityExistsException ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			return false;
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public void removeMachine(Machine mach)
	{
		mach=em.merge(mach);
		DataOperations.removeMachine(em, mach);
	}
	
	public void releaseMachine(Machine mach, Rezerwation res)
	{
		res.fixForFlex();
		res=em.merge(res);
		Machine toRem=null;
		for(Machine m:res.getMachine())
		{
				if(m.getID().equals(mach.getID()))
				{
					toRem=m;
					break;
				}
		}
		res.getMachine().remove(toRem);
		if(res.getMachine().isEmpty()) DataOperations.removeRezervation(em, res);
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList< ArrayList<Date[]> > getMachinesTimeUsage(ArrayList machs)
	{
		ArrayList<ArrayList<Date[]> > ret = new ArrayList<ArrayList<Date[]> >();
		ArrayList<Machine> test = (ArrayList<Machine>)machs;
		System.out.println("MAMY PROCESORÓW BEFORE: "+getAllProcessors());
		for(Machine m:test)
		{
			Processor p=m.getProcessor();
			System.out.println("MAMY PROCESORÓW INSIDE BEFORE PROC MERGE: "+getAllProcessors());
			Processor proc=ProcessorFinder.getProcessorByExample(em,p);
			if(proc!=null) p=proc;
			m.setProcessor(p);
			System.out.println("MAMY PROCESORÓW INSIDE BEFORE PROC MERGE: "+getAllProcessors());
			
			System.out.println("MAMY PROCESORÓW INSIDE BEFORE MACH MERGE: "+getAllProcessors());
			m=em.merge(m);
			System.out.println("MAMY PROCESORÓW INSIDE AFTER  MACH MERGE: "+getAllProcessors());
			
			ArrayList<Rezerwation> rez = RezerwationFinder.getRezerwationsByMachine(em, m);
			if(rez!=null && rez.size()>0)
			{
				ret.add(new ArrayList<Date[]>());
				for(Rezerwation rezerwation:rez)
					ret.get(ret.size()-1).add(new Date[]{ rezerwation.getCreateDate(),rezerwation.getReturnDate()});
			}
		}
		System.out.println("MAMY PROCESORÓW AFTER: "+getAllProcessors());
		
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
}
