package com.winit.iKonnect.dataobject;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NameIDDo implements Serializable
{
	public String strName ="";
	public String strId="";
	public String strType="";
	
	public float factor=1;
	public String UOM="";
	public String parentID="";
	public String chequeNumber="";
	public String chequeAmount="";
	public String SAPContractNumber="";
}
