package DataRepository;

import java.io.Serializable;
import java.util.Date;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.CriteriaFactory;
import net.bzdyl.ejb3.criteria.restrictions.Restrictions;



public class RezerwationFinderCriteria extends ISortable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3385101416110515465L;
	public Date createDate=null;
	public Date createDateFrom=null;
	public Date createDateTo=null;
	public Date returnDate=null;
	public Date returnDateFrom=null;
	public Date returnDateTo=null;
	public Boolean isBook=null;
	public Integer ID;
	
	public Criteria getStrictCriteria()
	{
		Criteria criteria=CriteriaFactory.createCriteria("Rezerwation");
		if(ID!=null)criteria.add( Restrictions.like("ID",ID ) );
		if(createDate!=null) criteria.add( Restrictions.eq("createDate", createDate) );
		if(returnDate!=null) criteria.add( Restrictions.eq("returnDate", returnDate) );
		if(isBook!=null) criteria.add( Restrictions.eq("isBook", isBook) );
		addSortOrder(criteria);
		return criteria;
	}
}
