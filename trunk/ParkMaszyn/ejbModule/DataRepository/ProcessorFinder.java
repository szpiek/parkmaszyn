package DataRepository;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import EntityBeans.Processor;

public class ProcessorFinder {
	@SuppressWarnings("unchecked")
	public static ArrayList<Processor> getAllProcessors(EntityManager em)
	{
		return (ArrayList<Processor>)(em.createQuery("SELECT OBJECT(processor) FROM Processor processor").getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Processor> getProcessorByStrictCriteria(EntityManager em, ProcessorFinderCriteria criteria)
	{
		Query query = criteria.getStrictCriteria().prepareQuery(em);
		return (ArrayList<Processor>)query.getResultList();
	}

	public static Processor getProcessorByExample(EntityManager em, Processor p)
	{
		ProcessorFinderCriteria pfc=new ProcessorFinderCriteria();
		pfc.architecture=p.getArchitecture();
		pfc.bits=p.getBits();
		pfc.clock=p.getClock();
		pfc.cores=p.getCores();
		pfc.name=p.getName();
		ArrayList<Processor> dist=getProcessorByStrictCriteria(em, pfc);
		return (dist!=null && dist.size()>0)?dist.get(0):null;
	}
}
