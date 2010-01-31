package DataRepository;

import java.io.Serializable;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;

public class ProcessorFinderCriteria extends ISortable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1393387137732965333L;
	public Integer Id=null;
	public Integer clock=null;
	public String name=null;
	public Integer bits=null;
	public String architecture=null;
	public Integer cores=null;
	
	public Criteria getStrictCriteria()
	{
		Criteria criteria=CriteriaFactory.createCriteria("Processor");
		if(Id!=null) criteria.add( Restrictions.eq("ID", Id ) );
		if(clock!=null) criteria.add( Restrictions.eq("clock", clock ) );
		if(name!=null) criteria.add( Restrictions.eq("name", name ) );
		if(bits!=null) criteria.add( Restrictions.eq("bits", bits ) );
		if(architecture!=null) criteria.add( Restrictions.eq("architecture", architecture ) );
		if(cores!=null) criteria.add( Restrictions.eq("cores", cores ) );
		
		addSortOrder(criteria);
		return criteria;
	}
}
