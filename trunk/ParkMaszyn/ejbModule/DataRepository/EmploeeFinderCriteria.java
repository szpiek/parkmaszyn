package DataRepository;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;

public class EmploeeFinderCriteria {
    public String firstName=null;
    public String lastName=null;
    public String email=null;
    public String department=null;
    
    public Criteria getStrictCriteria()
	{
		Criteria criteria=CriteriaFactory.createCriteria("Emploee");
		if(firstName!=null) criteria.add( Restrictions.eq("firstName", firstName ) );
		if(lastName!=null) criteria.add( Restrictions.eq("lastName", lastName) );
		if(email!=null) criteria.add( Restrictions.eq("email", email) );
		if(department!=null) criteria.add( Restrictions.eq("department", department) );
		return criteria;
	}
}
