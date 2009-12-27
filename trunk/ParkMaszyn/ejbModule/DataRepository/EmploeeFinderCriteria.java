package DataRepository;

import java.io.Serializable;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;

public class EmploeeFinderCriteria extends ISortable implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8558333151206044458L;
	public String firstName=null;
    public String lastName=null;
    public String email=null;
    public String department=null;
    
    public Criteria getStrictCriteria()
	{
    	Criteria criteria=CriteriaFactory.createCriteria("Emploee");
		if(firstName!=null) criteria.add( Restrictions.like("firstName",FinderFunctions.getLikeQuery(firstName) ) );
		if(lastName!=null) criteria.add( Restrictions.like("lastName", FinderFunctions.getLikeQuery(lastName)) );
		if(email!=null) criteria.add( Restrictions.like("email", FinderFunctions.getLikeQuery(email)) );
		if(department!=null) criteria.add( Restrictions.like("department",FinderFunctions.getLikeQuery(department)) );
		addSortOrder(criteria);
		return criteria;
	}
    
    public Criteria getCriteria()
	{
		Criteria criteria=CriteriaFactory.createCriteria("Emploee");
		if(firstName!=null) criteria.add( Restrictions.like("firstName",FinderFunctions.getLikeFitQuery(firstName) ) );
		if(lastName!=null) criteria.add( Restrictions.like("lastName", FinderFunctions.getLikeFitQuery(lastName)) );
		if(email!=null) criteria.add( Restrictions.like("email", FinderFunctions.getLikeFitQuery(email)) );
		if(department!=null) criteria.add( Restrictions.like("department",FinderFunctions.getLikeFitQuery(department)) );
		addSortOrder(criteria);
		return criteria;
	}
    
}
