package DataRepository;

public class FinderFunctions {
	public static String getLikeQuery(String query)
	{
		if(query!=null && !query.isEmpty())
		return query.replaceAll("\\*", "%").replaceAll("\\?", "_");
		return query;
	}
	
	public static String getLikeFitQuery(String query)
	{
		if(query!=null && !query.isEmpty())
		return "%"+getLikeQuery(query)+"%";
		return query;
	}
}
