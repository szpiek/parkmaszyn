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
	public String IP=null;
	public String login=null;
	public String password=null;
	public Integer memory=null;
	public Integer ID=null;
	public Boolean isBook=null;
	
	public Criteria getStrictCriteria()
	{
		Criteria criteria=CriteriaFactory.createCriteria("Machine");
		if(ID!=null) criteria.add( Restrictions.eq("ID", ID ) );
		if(isBook!=null) criteria.add( Restrictions.eq("isBook", isBook) );
		addSortOrder(criteria);
		return criteria;
	}
}
