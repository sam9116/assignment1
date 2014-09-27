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
// archivedtask is almost a duplicate of current task minus some apabilites
public class ArchivedTask extends Fragment {
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.updatelist(da_items.Global_archived); //updates the view when user finish things like dialog
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		fileinfileout.setContext(getActivity());//gets the taskholder class to use some of its methods
		//fileinfileout.settype(items);
		fileinfileout.getstatus("archive");//saving the archive file here might be a bit redundant but i was paranoid about file io for a period of time, i left it here just for good measure
		fileinfileout.filein();
		grid.setAdapter(adapter);// draws everything on the screen for the first time
	}

	private FragmentActivity parentactivity;//most of these were explained in currenttask.java since they are almost identical
	private RelativeLayout archivelayout;// i understand this repetition suggest poor oo design,but for me, it was functionality first,i wanted to implement as many functions as i can from the spec
										//i guess this is cutting corners...
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
		archivelayout = (RelativeLayout) inflater.inflate(R.layout.fragment_archived, container, false);// loads the acrhived task fragment layout 
		adapter = new customadapter(parentactivity.getApplicationContext(),da_items.Global_archived);//information about that list is passed to populate the grid
		grid = (GridView)archivelayout.findViewById(R.id.the_list_archive);
		grid.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id)
			//when an archived item is long pressed,the menu for handling archived item is activted
			{
				Intent intent = new Intent(parentactivity.getApplicationContext(),Singleitem_dialog_archive.class);
				ViewHolder holder = (ViewHolder) view.getTag();
				todoitems item = (todoitems) holder.selecteditem.getTag();
				//out.println(item.todoname);
				intent.putExtra("id", position); //tells the menu which archived item to be processed
				intent.putExtra("todoname",item.todoname.toString());//passing the detail of the task to menu
				startActivity(intent);// menu starts
				
				// TODO Auto-generated method stub
				return true;
				
			}
		});
		grid.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,//even though they are archived items i still put the ability to toggle done/not done
					int position, long id) {
				// TODO Auto-generated method stub
				ViewHolder holder = (ViewHolder) view.getTag();
				TextView highlight = holder.selecteditem;
				todoitems item = (todoitems) holder.selecteditem.getTag();//single archived item
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
