package DataRepository;

import java.util.Date;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;



public class RezerwationFinderCriteria {
	public Date createDate=null;
	public Date createDateFrom=null;
	public Date createDateTo=null;
	public Date returnDate=null;
	public Date returnDateFrom=null;
	public Date returnDateTo=null;
	public Boolean isBook=null;
	
	public Criteria getStrictCriteria()
	{
		Criteria criteria=CriteriaFactory.createCriteria("Rezerwation");
		if(createDate!=null) criteria.add( Restrictions.eq("createDate", createDate) );
		if(returnDate!=null) criteria.add( Restrictions.eq("returnDate", returnDate) );
		if(isBook!=null) criteria.add( Restrictions.eq("isBook", isBook) );
		return criteria;
	}
}
