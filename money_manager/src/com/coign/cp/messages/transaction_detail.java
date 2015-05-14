package com.coign.cp.messages;

public class transaction_detail{
	public String transaction;
	public float transaction_amount;
	public float balance_avl;
	public String Action;
	public String date;
	public transaction_detail()
	{
		transaction="";
		transaction_amount = -1.0F;
		Action = "";
		date = "";
		balance_avl=-1.0F;
	}
};
