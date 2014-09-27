package to_do_list;

import java.util.ArrayList;

import com.example.to_do_list.R;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class ArchivedTask extends Fragment {
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.updatelist(da_items.Global_archived); 
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		fileinfileout.setContext(getActivity());
		//fileinfileout.settype(items);
		fileinfileout.getstatus("archive");
		fileinfileout.filein();
		grid.setAdapter(adapter);
	}

	private FragmentActivity parentactivity;
	private RelativeLayout archivelayout;
	GridView grid;
	todoitems x = new todoitems("");
	static customadapter adapter;
	ArrayList<todoitems> items = new ArrayList<todoitems>();
	
	public ArchivedTask() {
		// Required empty public constructor
		da_items.intialize();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		// Inflate the layout for this fragment
		parentactivity = super.getActivity();
		archivelayout = (RelativeLayout) inflater.inflate(R.layout.fragment_archived, container, false); 
		adapter = new customadapter(parentactivity.getApplicationContext(),da_items.Global_archived);
		grid = (GridView)archivelayout.findViewById(R.id.the_list_archive);
		grid.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id)
			{
				Intent intent = new Intent(parentactivity.getApplicationContext(),Singleitem_dialog_archive.class);
				ViewHolder holder = (ViewHolder) view.getTag();
				todoitems item = (todoitems) holder.selecteditem.getTag();
				//out.println(item.todoname);
				intent.putExtra("id", position);
				intent.putExtra("todoname",item.todoname.toString());
				startActivity(intent);
				
				// TODO Auto-generated method stub
				return true;
				
			}
		});
		grid.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ViewHolder holder = (ViewHolder) view.getTag();
				TextView highlight = holder.selecteditem;
				todoitems item = (todoitems) holder.selecteditem.getTag();
				if(item.done==false)
				{
					highlight.setBackgroundDrawable(getResources().getDrawable(R.drawable.checkon));
					
					item.done=true;
					adapter.updatelist(da_items.Global_archived); 
				}
				else
				{
					highlight.setBackgroundDrawable(getResources().getDrawable(R.drawable.checkoff));
					item.done=false;
					adapter.updatelist(da_items.Global_archived); 
				}
				
			}
			
		});
		
		return archivelayout;
	}

}
