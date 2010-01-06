package Utilities;

public class UserInfo {
	private Integer id = null;
	private boolean admin = false;
	
	public UserInfo(Integer id, boolean ad)
	{
		this.id = id;
		this.admin = ad;
	}
	
	public Integer getId()
	{
		return this.id;
	}
	
	public boolean isAdmin()
	{
		return this.admin;
	}
}
