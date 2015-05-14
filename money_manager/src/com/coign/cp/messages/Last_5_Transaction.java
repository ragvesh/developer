package com.coign.cp.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Last_5_Transaction extends Activity implements OnItemClickListener {

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_inbox);
        String colName="";
        final ArrayList<String> list = new ArrayList<String>();
        final ListView listview = (ListView)findViewById(R.id.listview);
        listview.setOnItemClickListener(this);
        listview.setClickable(true);
        listview.setLayerType(listview.LAYER_TYPE_NONE, null);
        
        colName = "\n";
        int no_of_iter = -1;
        if(global.obj.get(global.selected_card_position).trans.size() < 5)
        	no_of_iter = global.obj.get(global.selected_card_position).trans.size();
        else
        	no_of_iter = 5;
		for(int j=0;j<no_of_iter;j++){
			String action = "";
			
			if (global.obj.get(global.selected_card_position).trans.get(j).Action.equals("BALANCE ENQUIRY"))
				action = "AVL";
			else
				action = global.obj.get(global.selected_card_position).trans.get(j).Action;
			
			colName = colName + "DATE : " + global.obj.get(global.selected_card_position).trans.get(j).date + "\n";
			colName = colName + "TRANS TYPE : " + global.obj.get(global.selected_card_position).trans.get(j).Action + "\n";
			colName = colName + action + " AMNT : " + global.obj.get(global.selected_card_position).trans.get(j).transaction_amount + "\n";
			if(global.obj.get(global.selected_card_position).trans.get(j).balance_avl > -1.0F)
				colName = colName + "AVL AMNT : " + global.obj.get(global.selected_card_position).trans.get(j).balance_avl + "\n";
			
			colName = colName + global.obj.get(global.selected_card_position).trans.get(j).transaction.substring(0, 50) + " ..." + "\n";
			
			list.add(colName);
			colName ="\n";
		}
				
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
		        android.R.layout.simple_list_item_1, list);
			    listview.setAdapter(adapter);
	}
	
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	    	this.getView(i, getCurrentFocus(), null).setBackgroundResource(R.drawable.image);
	        mIdMap.put(objects.get(i), i);
	      }
	  }
	}
	
	@Override
	public void onItemClick(AdapterView<?> listview, View view, int position, long id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(null)
		.setCancelable(true);
		

		AlertDialog alertDialog = builder.create();
		//alertDialog.setMessage(Html.fromHtml("<font color='#000000'>" +"<b>"
		//		listview.getItemAtPosition(position).toString()+"</b>""</font>"));
		String colName = "";
		
		String action = "";
		if (global.obj.get(global.selected_card_position).trans.get(position).Action.equals("BALANCE ENQUIRY"))
			action = "AVL";
		else
			action = global.obj.get(global.selected_card_position).trans.get(position).Action;
		
		colName = colName + "DATE : " + global.obj.get(global.selected_card_position).trans.get(position).date + "\n";
		colName = colName + action + " AMNT : " + global.obj.get(global.selected_card_position).trans.get(position).transaction_amount + "\n";
		
		if(global.obj.get(global.selected_card_position).trans.get(position).balance_avl > -1.0F)
			colName = colName + "AVL AMNT : " + global.obj.get(global.selected_card_position).trans.get(position).balance_avl + "\n";
		colName = colName + "TRANS TYPE : " + global.obj.get(global.selected_card_position).trans.get(position).Action + "\n";
		
		colName = colName + "\n";
		colName = colName + "DETAIL : \n" + global.obj.get(global.selected_card_position).trans.get(position).transaction + "\n";

		colName = colName + "---------\n\n";
		alertDialog.setMessage(colName);
		alertDialog.show();
	}

}
