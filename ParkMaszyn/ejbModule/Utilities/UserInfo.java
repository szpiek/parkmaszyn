package Utilities;

import java.io.Serializable;

import EntityBeans.Emploee;

public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
    Integer id;
    boolean admin;
	
	public UserInfo()
	{
	}
	
	public UserInfo(Emploee e)
	{
		this.id = e.getID();
		this.admin = e.isAdmin();
	}
	public UserInfo(Integer id, boolean ad)
	{
		this.id = id;
		this.admin = ad;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public String toString()
	{
		return "id="+id+" admin="+admin;
	}
	

}
