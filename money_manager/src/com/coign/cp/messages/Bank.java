package com.coign.cp.messages;

import java.util.ArrayList;
import java.util.Date;

public class Bank{
	public String Card_number;
	public String type;
	public String Bank_name;
	public float available_balance;
	public ArrayList<transaction_detail> trans;
	public float Total_spent;
	public String Action;
	public String spent_date; 
	
	public Bank()
	{
		Card_number = "";
		Bank_name = "";
		available_balance = -1.0F;
		trans = new ArrayList<transaction_detail>();
		Total_spent = -1.0F;
		Action ="";
		spent_date = "";
		type = "CARD NUMBER :";
	}
};


