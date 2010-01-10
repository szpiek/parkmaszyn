package DataRepository;

import java.io.Serializable;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;

public class MachineFinderCriteria extends ISortable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1393387137732965333L;
	public String os=null;
	public String architecture=null;
	public String processor=null;
	public String IP=null;
	public String login=null;
	public String password=null;
	public Integer memory=null;
	public Integer bits=null;
	public Integer ID=null;
	public Boolean isBook=null;
	
	public Criteria getStrictCriteria()
	{
		Criteria criteria=CriteriaFactory.createCriteria("Machine");
		if(os!=null) criteria.add( Restrictions.eq("os", os ) );
		if(architecture!=null) criteria.add( Restrictions.eq("architecture", architecture.toString()) );
		if(processor!=null) criteria.add( Restrictions.eq("processor", processor.toString()) );
		if(isBook!=null) criteria.add( Restrictions.eq("isBook", isBook) );
		addSortOrder(criteria);
		return criteria;
	}
}
