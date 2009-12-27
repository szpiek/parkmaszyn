package DataRepository;

import java.io.Serializable;

import net.bzdyl.ejb3.criteria.Criteria;
import net.bzdyl.ejb3.criteria.Order;

public class ISortable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -18787506551443931L;
	public static String SORT_ASC="ASC";
	public static String SORT_DESC="DESC";
	public static String SORT_NONE="NONE";
	private String sortType=SORT_NONE;
	private String sortProperty=null;
	
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getSortProperty() {
		return sortProperty;
	}
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}
	
	public void addSortOrder(Criteria criteria)
	{
		if( isSortNeeded() )
		{
			if( getSortType().equals(SORT_ASC)) criteria.addOrder(Order.asc(getSortProperty()));
			if( getSortType().equals(SORT_DESC) ) criteria.addOrder(Order.desc(getSortProperty()));
		}
	}
	
	public boolean isSortNeeded()
	{
		return ( sortType.equals(SORT_ASC) || sortType.equals(SORT_DESC) ) && sortProperty!=null;
	}
	
}
