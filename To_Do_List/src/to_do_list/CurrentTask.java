package to_do_list;
import static java.lang.System.out;

import java.util.ArrayList;

import com.example.to_do_list.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class CurrentTask extends Fragment{

	EditText enteritem;
	private FragmentActivity parentactivity;
	private RelativeLayout tasklayout;
	GridView grid;
	todoitems x = new todoitems("");
	static customadapter adapter;
	ArrayList<todoitems> items = new ArrayList<todoitems>();
	public CurrentTask() {
		// Required empty public constructor
		da_items.intialize();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		parentactivity=super.getActivity();
		
		tasklayout = (RelativeLayout)inflater.inflate(R.layout.activity_the_l_ist, container,false);
		Toast.makeText(parentactivity.getApplicationContext(), "in oncreateview"+da_items.Global.size(),  Toast.LENGTH_LONG).show();
		adapter = new customadapter(parentactivity.getApplicationContext(),da_items.Global);
		grid = (GridView)tasklayout.findViewById(R.id.the_list);
		enteritem = (EditText)tasklayout.findViewById(R.id.body);
		grid.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id)
			{
				Intent intent = new Intent(parentactivity.getApplicationContext(),Singleitem_dialog.class);
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
				Toast.makeText(parentactivity.getApplicationContext(), "in onclick:"+da_items.Global.size(),  Toast.LENGTH_LONG).show();
				ViewHolder holder = (ViewHolder) view.getTag();
				TextView highlight = holder.selecteditem;
				todoitems item = (todoitems) holder.selecteditem.getTag();
				if(item.done==false)
				{
					//highlight.setBackgroundColor(Color.parseColor("#0000FF"));
					highlight.setBackgroundDrawable(getResources().getDrawable(R.drawable.checkon));
					item.done=true;
					adapter.updatelist(da_items.Global); 
				}
				else
				{
					//highlight.setBackgroundColor(Color.parseColor("#FF0000"));
					highlight.setBackgroundDrawable(getResources().getDrawable(R.drawable.checkoff));
					item.done=false;
					adapter.updatelist(da_items.Global); 
				}
				
			}
			
		});
		enteritem.setOnEditorActionListener(new OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) 
			{
				if(actionId==EditorInfo.IME_ACTION_DONE)
				{
					InputMethodManager imm = (InputMethodManager)parentactivity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(enteritem.getWindowToken(), 0);
				    da_items.Global.add(new todoitems(enteritem.getText().toString()));
				    //fileinfileout.settype(items);
				    fileinfileout.getstatus("save");
				    fileinfileout.saveInFile();
					enteritem.getText().clear();
					adapter.updatelist(da_items.Global);
				}
				// TODO Auto-generated method stub
				return true;
			}
		});
		return tasklayout;
		
	}

	

	

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Toast.makeText(parentactivity.getApplicationContext(), "onstart in currentTASKonstart "+da_items.Global.size(),  Toast.LENGTH_SHORT).show();
		grid.setAdapter(adapter);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(parentactivity.getApplicationContext(), "onstart in currenttaskonresume"+da_items.Global.size(),  Toast.LENGTH_SHORT).show();
		adapter.updatelist(da_items.Global); 
	}
	
	

}



