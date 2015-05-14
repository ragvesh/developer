package com.coign.cp.messages;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("SimpleDateFormat")
@TargetApi(Build.VERSION_CODES.JELLY_BEAN) public class sms_inbox extends Activity implements OnItemClickListener {
    /** Called when the activity is first created. */
	String colName="";
	int counter=1;
	//public ArrayList<Bank> global.obj=new ArrayList<Bank>();
	boolean flag=false;
	
	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_inbox);
        
        //TextView tView = (TextView)findViewById(R.id.txtView);
        //tView.setTextAppearance(main.this,R.style.WindowTitleBackground);
        
        ContentResolver cr =getContentResolver();
        Uri uri = Uri.parse("content://sms/inbox");
        
//        Context context = getApplicationContext();
//        int duration = Toast.LENGTH_SHORT;
        
        
        Cursor messagesCursor = cr.query(uri, new String[] { "_id","address","body","person","date"}, null,null, null);
        final ArrayList<String> list = new ArrayList<String>();
        final ListView listview = (ListView)findViewById(R.id.listview);
      
        //listview.setBackgroundResource(R.drawable.sms);
        listview.setOnItemClickListener(this);
        listview.setClickable(true);
        listview.setLayerType(listview.LAYER_TYPE_NONE, null);
        
        counter = messagesCursor.getCount();
        
       // toast = Toast.makeText(context, "YOU HAVE "+counter+" MESSAGE IN YOUR INBOX", duration);
       
        
        if(counter <= 0)
        	finish();
        
        if(messagesCursor.getCount() > 0 )
        {
        	while(messagesCursor.moveToNext() && global.update_required)
        	{
        		long sms_time = Long.parseLong(messagesCursor.getString(messagesCursor.getColumnIndex("date")));
        		Calendar date = Calendar.getInstance();
        		date.setTimeInMillis(sms_time);
        		
        		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                String formattedDate = df.format(date.getTime());
        		
                String from = messagesCursor.getString(messagesCursor.getColumnIndex("address"));
                
                if(from.toUpperCase().contains("CITI") || from.toUpperCase().contains("ICICI") || from.toUpperCase().contains("SBI") || from.toUpperCase().contains("CENT") || from.toUpperCase().contains("PNB") || from.toUpperCase().contains("HDFC") || from.toUpperCase().contains("HSBC")  || from.toUpperCase().contains("AXIS") )
                {
                	flag = false;
                	
                	String body =  messagesCursor.getString(messagesCursor.getColumnIndex("body"));
                	String tok[] = body.split(" ");
                	
                	Bank temp = new Bank();
                	transaction_detail tr = new transaction_detail();
                	
                	if(from.toUpperCase().contains("CITI"))
                		temp.Bank_name="CITI BANK";
                	else if(from.toUpperCase().contains("ICICI"))
                		temp.Bank_name="ICICI BANK";
                	else if(from.toUpperCase().contains("SBI"))
                		temp.Bank_name="STATE BANK OF INDIA";
                	else if (from.toUpperCase().contains("CENT"))
                		temp.Bank_name="CENTRAL BANK";
                	else if (from.toUpperCase().contains("PNB"))
                		temp.Bank_name="PUNJAB NATIONAL BANK";
                	else if (from.toUpperCase().contains("HDFC"))
                		temp.Bank_name="HDFC BANK";
                	else if (from.toUpperCase().contains("HSBC"))
                		temp.Bank_name="HSBC BANK";
                	else if (from.toUpperCase().contains("AXIS"))
                		temp.Bank_name="AXIS BANK";
                	
            		for(int loop=0;loop<tok.length-1; loop++)
            		{
            			
            			if(tok[loop].toUpperCase().contains("A/C"))
            			{
            				if(!(tok[loop + 1].toUpperCase().contains("BALANCE")))
            				{
	            				if((tok[loop + 1].toUpperCase().contains("NO")))
	            				{
	            					if(temp.Card_number.equals(""))
	            						temp.Card_number = tok[loop + 2].toUpperCase();
	            					temp.type = "A/C NUMBER : ";
	            					loop+=2;
	            				}
	            				else{
	            					if(temp.Card_number.equals(""))
	            						temp.Card_number = tok[loop + 1].toUpperCase();
	            					temp.type = "A/C NUMBER : ";
	            					loop+=1;
	            				}
            				}
            			}
            			
            			if(tok[loop].toUpperCase().contains("ACCOUNT"))
            			{
            				if(!(tok[loop + 1].toUpperCase().contains("BALANCE")))
            				{
	            				if((tok[loop + 1].toUpperCase().contains("NO")))
	            				{
	            					if(temp.Card_number.equals(""))
	            						temp.Card_number = tok[loop + 2].toUpperCase();
	            					temp.type = "A/C NUMBER : ";
	            					loop+=2;
	            				}
	            				else{
	            					if(temp.Card_number.equals(""))
	            						temp.Card_number = tok[loop + 1].toUpperCase();
	            					//Toast t=Toast.makeText(context, "Card Number : " + temp.Card_number, duration);
	            					//t.show();
	            					temp.type = "A/C NUMBER : ";
	            					loop+=1;
	            				}
            				}
            			}

            			if(tok[loop].toUpperCase().contains("CARD"))
            			{
        				
            				if((tok[loop + 1].toUpperCase().contains("NO")))
            				{
            					if(temp.Card_number.equals(""))
            						temp.Card_number = tok[loop + 2].toUpperCase();
            					loop+=2;
            				}
            				else if((tok[loop + 1].toUpperCase().contains("ENDING")))
            				{
            					if(temp.Card_number.equals(""))
            						temp.Card_number = tok[loop + 2].toUpperCase();
            					loop+=2;
            				}
            				else{
            						if(temp.Card_number.equals(""))
            							temp.Card_number = tok[loop + 1].toUpperCase();
	            					loop+=1;
            				}
            			}
            			
            			
            			if(tok[loop].toUpperCase().contains("YOUR"))
            			{
            				if(tok[loop + 1].toUpperCase().contains("AC"))
                			{
            					if(temp.Card_number.equals(""))
	            					temp.Card_number = tok[loop + 2].toUpperCase();
	            					temp.type = "A/C NUMBER : ";
	            					loop+=2;
                			}
            			}
            			
            			if(tok[loop].toUpperCase().contains("INR"))
            			{
	    					String temp_tok1[] = tok[loop].split("INR");
	    					if(temp_tok1.length>1)
	    						tok[loop + 1] = temp_tok1[1];
            				
            				if(tok[loop + 1].endsWith("."))
            				{
            					tok[loop + 1] = tok[loop + 1].substring(0,tok[loop + 1].lastIndexOf(".")-1);
            				}
            				
            				if(!tok[loop + 1].isEmpty()){
            					if(tok[loop + 1].contains(",")){
	            					String temp_tok[] = tok[loop + 1].split(",");
	            					tok[loop + 1] = temp_tok[0] + temp_tok[1];
            					}
            					if(tok[loop + 1].contains("."))
            					{
            						String temp_tok[] = tok[loop + 1].split(".");
            						if( temp_tok.length > 0 && temp_tok[0].toUpperCase().contains("RS"))
            						{
            							tok[loop + 1]="";
            							for (int i=1;i< temp_tok.length;i++)
            							{
            								tok[loop + 1] = tok[loop + 1] + temp_tok[i];
            							}
            						}
            					}
                					
            					try{
            						tr.transaction_amount = Float.valueOf(tok[loop + 1]);
            						//Toast t=Toast.makeText(context, "Card Number : " + temp.Card_number+"\n"+"Account Balance : " + tr.transaction_amount, duration);
	            					//t.show();
            						loop+=1;
            					}catch (Exception e) {
            						tr.transaction_amount = -1.0F;
								}
            				}
            			}
            			
            			
            			if(tok[loop].toUpperCase().contains("RS"))
            			{
            				if(tok[loop + 1].endsWith("."))
            				{
            					tok[loop + 1] = tok[loop + 1].substring(0,tok[loop + 1].lastIndexOf(".")-1);
            				}
            				
            				if(!tok[loop + 1].isEmpty()){
            					
            					if(tok[loop + 1].contains(",")){
	            					String temp_tok[] = tok[loop + 1].split(",");
	            					if(temp_tok.length>1)
	            						tok[loop + 1] = temp_tok[0] + temp_tok[1];
            					}
            					if(tok[loop].contains("."))
            					{
            						String temp_tok[] = tok[loop].split(".");
            						if(temp_tok.length > 0 && temp_tok[0].toUpperCase().contains("RS"))
            						{
            							tok[loop + 1]="";
            							for (int i=1;i< temp_tok.length;i++)
            							{
            								tok[loop + 1] = tok[loop + 1] + temp_tok[i];
            							}
            						}
            					}
            					
            					try{
            						tr.transaction_amount = Float.valueOf(tok[loop + 1]);
            						loop+=1;
            					}catch (Exception e) {
									// TODO: handle exception
            						tr.transaction_amount = -1.0F;
								}
            				}
            			}
            			
            			if(tok[loop].toUpperCase().contains("BALANCE") )
            			{
            				if(tok[loop + 1].toUpperCase().contains("IS"))
            				{
            					if(tok[loop + 2].toUpperCase().contains("NOW"))
            					{
            						loop += 3;
            					}
            					else
            						loop += 2;
            						
            						if(tok[loop].toUpperCase().contains("RS"))
                        			{
                        				if(tok[loop + 1].endsWith("."))
                        				{
                        					tok[loop + 1] = tok[loop + 1].substring(0,tok[loop + 1].lastIndexOf(".")-1);
                        				}
                        				
                        				if(!tok[loop + 1].isEmpty()){
                        					
                        					if(tok[loop + 1].contains(",")){
            	            					String temp_tok[] = tok[loop + 1].split(",");
            	            					tok[loop + 1] = temp_tok[0] + temp_tok[1];
                        					}
                        					else if(tok[loop].contains("."))
                        					{
                        						String temp_tok[] = tok[loop].split(".");
                        						if(temp_tok.length > 0 && temp_tok[0].toUpperCase().contains("RS"))
                        						{
                        							tok[loop + 1]="";
                        							for (int i=1;i< temp_tok.length;i++)
                        							{
                        								tok[loop + 1] = tok[loop + 1] + temp_tok[i];
                        							}
                        						}
                        					}
                        					
                        					try{
                        						tr.balance_avl = Float.valueOf(tok[loop + 1]);
                        						loop+=1;
                        					}catch (Exception e) {
            									// TODO: handle exception
                        						tr.balance_avl = -1.0F;
            								}
                        				}
                        			}
            						else if(tok[loop].toUpperCase().contains("INR") )
                        			{
            	    					String temp_tok1[] = tok[loop].split("INR");
            	    					String temp_amnt=tok[loop].toUpperCase();
            	    					if(tok[loop].matches(".*\\d.*") && temp_tok1.length>1)
            	    						temp_amnt = temp_tok1[1];
            	    					else
            	    						temp_amnt=tok[loop+1].toUpperCase();
                        				
                        				if(temp_amnt.endsWith("."))
                        				{
                        					temp_amnt = temp_amnt.substring(0,temp_amnt.lastIndexOf(".")-1);
                        				}
                        				
                        				if(!temp_amnt.isEmpty()){
                        					if(temp_amnt.contains(",")){
            	            					String temp_tok[] = temp_amnt.split(",");
            	            					temp_amnt = temp_tok[0] + temp_tok[1];
                        					}
                        					if(temp_amnt.contains("."))
                        					{
                        						String temp_tok[] = temp_amnt.split(".");
                        						if( temp_tok.length > 0 && temp_tok[0].toUpperCase().contains("RS"))
                        						{
                        							temp_amnt="";
                        							for (int i=1;i< temp_tok.length;i++)
                        							{
                        								temp_amnt = temp_amnt + temp_tok[i];
                        							}
                        						}
                        					}
                            					
                        					try{
                        						tr.balance_avl = Float.valueOf(temp_amnt);
                        						if(tok.length>loop+2)
                        							loop+=1;
                        					}catch (Exception e) {
                        						tr.balance_avl = -1.0F;
            								}
                        				}
                        			}
            				}
            				
            			}
            			
            			if(tok[loop].toUpperCase().contains("BAL") )
            			{
            				loop += 1;
            				if(tok[loop].toUpperCase().contains("RS"))
                			{
            					if(!tok[loop].matches(".*\\d.*") && tok[loop + 1].equals(""))
            						loop += 1;
            					if( tok[loop].matches(".*\\d.*") &&  tok[loop].contains("."))
            					{
            						String temp_tok[] = tok[loop].split(".");
            						if( temp_tok.length > 1 && temp_tok[0].toUpperCase().contains("RS"))
            						{
            							tok[loop + 1]="";
            							for (int i=1;i< temp_tok.length;i++)
            							{
            								tok[loop + 1] = tok[loop + 1] + temp_tok[i];
            							}
            						}
            					}
            					
                				if(tok[loop + 1].endsWith("."))
                				{
                					tok[loop + 1] = tok[loop + 1].substring(0,tok[loop + 1].lastIndexOf(".")-1);
                				}
                				
                				//Toast toast = Toast.makeText(context, "Number Format exception : "+tok[loop + 1], duration);
        					    //toast.show();
                				if(!tok[loop + 1].isEmpty()){
                					
                					if(tok[loop + 1].contains(",")){
    	            					String temp_tok[] = tok[loop + 1].split(",");
    	            					tok[loop + 1] = temp_tok[0] + temp_tok[1];
                					}
                					
                					try{
                						tr.balance_avl = Float.valueOf(tok[loop + 1]);
                						
                						loop+=1;
                					}catch (Exception e) {
                						tr.balance_avl = -1.0F;
                						
    								}
                				}
                			}
    						else if(tok[loop].toUpperCase().contains("INR"))
                			{
    	    					String temp_tok1[] = tok[loop].split("INR");
    	    					if(temp_tok1.length>1)
    	    						tok[loop + 1] = temp_tok1[1];
                				
                				if(tok[loop + 1].endsWith("."))
                				{
                					tok[loop + 1] = tok[loop + 1].substring(0,tok[loop + 1].lastIndexOf(".")-1);
                				}
                				
                				if(!tok[loop + 1].isEmpty()){
                					if(tok[loop + 1].contains(",")){
    	            					String temp_tok[] = tok[loop + 1].split(",");
    	            					tok[loop + 1] = temp_tok[0] + temp_tok[1];
                					}
                					if(temp.spent_date.matches(".*\\d.*") && tok[loop + 1].contains("."))
                					{
                						String temp_tok[] = tok[loop + 1].split(".");
                						if( tok[loop + 1].matches(".*\\d.*") && temp_tok.length > 0 && temp_tok[0].toUpperCase().contains("RS"))
                						{
                							tok[loop + 1]="";
                							for (int i=1;i< temp_tok.length;i++)
                							{
                								tok[loop + 1] = tok[loop + 1] + temp_tok[i];
                							}
                						}
                					}
                    					
                					try{
                						tr.balance_avl = Float.valueOf(tok[loop + 1]);
                						loop+=1;
                					}catch (Exception e) {
                						tr.balance_avl = -1.0F;
    								}
                				}
                			}
            			}
            			
            			if(tok[loop].toUpperCase().contains("SPENT"))
            			{
            				tr.Action = "SPENT";
            			}
            			
            			if(tok[loop].toUpperCase().contains("WITHDRAW"))
            			{
            				tr.Action = "WITHDRAW";
            			}
            			
            			if(tok[loop].toUpperCase().contains("DEBITED"))
            			{
            				tr.Action = "WITHDRAW";
            			}
            			
            			if(tok[loop].toUpperCase().contains("CREDITED"))
            			{
            				tr.Action = "DEPOSIT";
            			}
            			
            			if(tok[loop].toUpperCase().contains("PURCHASE"))
            			{
            				tr.Action = "SPENT";
            			}
            			
            			if(tok[loop].toUpperCase().contains("DRAW"))
            			{
            				tr.Action = "SPENT";
            			}
            			
            			
            			if(tok[loop].toUpperCase().contains("RECEIVED"))
            			{
            				tr.Action = "SPENT ON CREDIT CARD BILL";
            			}
            			
            			if(tok[loop].toUpperCase().contains("ON"))
            			{
    						if(!(tok[loop + 1].toUpperCase().contains("YOUR")))
    						{
    							temp.spent_date = tok[loop + 1].toUpperCase();
    							tr.date = tok[loop + 1].toUpperCase();
    							loop+=1;
    						}
            			}
            			
            		}
            		
            	
					if(tr.Action.equals(""))
						tr.Action = "BALANCE ENQUIRY";   
					boolean date_flg=false;
					
                	if(temp.spent_date == "" || !temp.spent_date.matches(".*\\d.*"))
                	{
                		temp.spent_date = formattedDate;
                		tr.date = formattedDate;
                		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        				long c = cal.getTimeInMillis();
        				
                	}
                	
                	if((!temp.Card_number.isEmpty() && temp.Card_number.matches(".*\\d.*") && tr.transaction_amount > -1.0F))
                	{
                		
                		tr.transaction=body;
                		temp.trans.add(tr);
                		String temp_card_number ="";
                		for(int i=0;i<global.obj.size();i++){
                			if(global.obj.get(i).Card_number.contains("."))
                			{
                				temp_card_number = global.obj.get(i).Card_number.substring(global.obj.get(i).Card_number.length()-5,global.obj.get(i).Card_number.length()-1);
                			}
                			else
                			{
                				if(global.obj.get(i).Card_number.length()>4)
                					temp_card_number = global.obj.get(i).Card_number.substring(global.obj.get(i).Card_number.length()-4);
                			}
                				
	                		if(temp.Card_number.contains(temp_card_number))
	                		{
	                			global.obj.get(i).trans.add(tr);
	                			
	                			if(temp.available_balance > -1.0F)
	                				global.obj.get(i).available_balance = temp.available_balance;

	                			else if(tr.Action.equals("SPENT ON CREDIT CARD BILL"))
	                			{
	                				//global.obj.get(i).Total_spent -= tr.transaction_amount;
	                			}
	                			
	                			flag = true;
	                			break;
	                		}
                		}
                		
                		
                		if(!flag && tr.transaction_amount > -1.0F && temp.Card_number.matches(".*\\d.*"))
                			global.obj.add(temp);

                	}
        		}
        	}
        	
        	global.update_required = false;        	
        	for(int i=0;i<global.obj.size();i++)
        	{
       			int amnt=0;
       			for(int j=0;j<global.obj.get(i).trans.size();j++)
       			{
       				if(!(global.obj.get(i).trans.get(j).Action.contains("DEPOSIT") || global.obj.get(i).trans.get(j).Action.contains("SPENT ON CREDIT CARD BILL") || global.obj.get(i).trans.get(j).Action.contains("BALANCE ENQUIRY")))
       					amnt += global.obj.get(i).trans.get(j).transaction_amount;
       			}
       			
       			if(amnt>0)
       			{
       				colName = "\n";
            		colName = colName + global.obj.get(i).Bank_name + "\n";
            		colName = colName + global.obj.get(i).type +global.obj.get(i).Card_number + "\n";
	       			global.obj.get(i).Total_spent = amnt;
	       			
	       			if(global.obj.get(i).Total_spent > -1.0F)
	       				colName = colName + "TOTAL SPENT RS : " +  global.obj.get(i).Total_spent + "\n";
	       			
	       			if(global.obj.get(i).trans.get(0).balance_avl > -1.0F)
	       				colName = colName + "TOTAL BALANCE RS : " +  global.obj.get(i).trans.get(0).balance_avl + "\n";
	        		list.add(colName);
       			}
        		colName = "";
        	}
        	
        }

	        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, list);
		    listview.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> listview, View view, int position, long id) {
		global.selected_card_position = position;
		Intent intent = new Intent(sms_inbox.this, TransactionMenu.class);
        startActivity(intent);
	}
} 