package com.eBusiness;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

public class SessionHandler {

	private FlexSession mySession;
	
	public SessionHandler()
	{
		mySession= FlexContext.getFlexSession();
	}
	
	public String getUserName()
	{
		String userName = null;
		userName = (String)mySession.getAttribute("userName");
		return userName;
	}
	
	public void setUser(String userName)
	{
		mySession.setAttribute("userName", userName);
	}
	
	public void delUser(String userName)
	{
		mySession.removeAttribute("userName");
	}
}
