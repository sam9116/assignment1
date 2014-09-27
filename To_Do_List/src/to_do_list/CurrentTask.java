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

	EditText enteritem;//the field where user enter new tasks
	private FragmentActivity parentactivity;
	private RelativeLayout tasklayout;
	GridView grid;// i tried to stand out a bit by using gridview instead of list view to hold all my tasks
	todoitems x = new todoitems("");
	static customadapter adapter;
	ArrayList<todoitems> items = new ArrayList<todoitems>();
	public CurrentTask() {
		// Required empty public constructor
		da_items.intialize(); // initializes/reclear all global lists,used across both current tasks and archived tasks and many other methods
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
		parentactivity=super.getActivity();// gets activity from taskholder,after all, there are alot of things single fragments can't do
		
		tasklayout = (RelativeLayout)inflater.inflate(R.layout.activity_the_l_ist, container,false);//reuses layout from an earlier version of the app, 
		
		adapter = new customadapter(parentactivity.getApplicationContext(),da_items.Global);// adapter for displaying current task items on the gridview
		grid = (GridView)tasklayout.findViewById(R.id.the_list);// grabs the gridview in activity_the_l_ist.xml
		enteritem = (EditText)tasklayout.findViewById(R.id.body);// grabs the input field from activity_the_l_ist.xml
		grid.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id)//displays a dialog menu for user deciding what to do with a single current task item
			{
				Intent intent = new Intent(parentactivity.getApplicationContext(),Singleitem_dialog.class);
				ViewHolder holder = (ViewHolder) view.getTag();
				todoitems item = (todoitems) holder.selecteditem.getTag();
				intent.putExtra("id", position);//tells the dialog which item on the current task list was chosed
				intent.putExtra("todoname",item.todoname.toString());//also redisplay more text on the dialog,since gridview does offer less room for texts
				startActivity(intent);
				// TODO Auto-generated method stub
				return true;
				
			}
		});
		grid.setOnItemClickListener(new OnItemClickListener()//the core functionality of this app: did the user do the task?
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				ViewHolder holder = (ViewHolder) view.getTag();// gets which cell on the gridview is clicked
				TextView highlight = holder.selecteditem;// the actual text of the task item
				todoitems item = (todoitems) holder.selecteditem.getTag();//extracts the todo item from that cell
				if(item.done==false)
				{
					//showes a checkmark picture in the background, im too lazy to implement a prgrammatical checkboxs, but hey, it still functions the same!
					highlight.setBackgroundDrawable(getResources().getDrawable(R.drawable.checkon));
					item.done=true;//item is now done, horray!
					adapter.updatelist(da_items.Global); // update the view so user can see it
				}
				else
				{
					// showes an empty checkbox
					highlight.setBackgroundDrawable(getResources().getDrawable(R.drawable.checkoff));
					item.done=false;
					adapter.updatelist(da_items.Global); 
				}
				
			}
			
		});
		enteritem.setOnEditorActionListener(new OnEditorActionListener()// the text inputfield where user can create new task items
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) 
			{
				if(actionId==EditorInfo.IME_ACTION_DONE)//so instead of creating a savebutton that occupys precious screen realestate on my tiny htc desire c
				{										//i decided to let done button on the soft keyboard do that instead,so the instance user is done typing the new task, it is saved
					InputMethodManager imm = (InputMethodManager)parentactivity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(enteritem.getWindowToken(), 0);//hides the keyboard when user is done
				    da_items.Global.add(new todoitems(enteritem.getText().toString()));//intializes a new todo item and adds it to the current task list
				    fileinfileout.getstatus("save");// saves the list
				    fileinfileout.saveInFile();
					enteritem.getText().clear();//no more text in the input field
					adapter.updatelist(da_items.Global);// update the view
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
		grid.setAdapter(adapter);//refreshes the screen when user comes back from an other app
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(parentactivity.getApplicationContext(), "onstart in currenttaskonresume"+da_items.Global.size(),  Toast.LENGTH_SHORT).show();
		adapter.updatelist(da_items.Global); 
		// refreshes the screen when the user is done with, for example, a dialog
	}
	
	

}



