/**
 * 
 */
package com.coign.cp.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**
 * @author Ragvesh
 *
 */
public class TransactionMenu extends Activity {
	
	Button five_trans,all_trans,trans_dates;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_menu);
        
        all_trans = (Button)findViewById(R.id.all_tran);
        all_trans.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TransactionMenu.this, Transaction.class);
		        startActivity(intent);
			}
		});
        
        five_trans = (Button)findViewById(R.id.five_tran);
        five_trans.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TransactionMenu.this, Last_5_Transaction.class);
		        startActivity(intent);
			}
		});
        
        trans_dates = (Button)findViewById(R.id.tran_dates);
        trans_dates.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TransactionMenu.this, Transaction_between_two_dates.class);
		        startActivity(intent);
			}
		});
	}
}