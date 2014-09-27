package to_do_list;

import java.util.ArrayList;

import to_do_list.ViewHolder;
import to_do_list.da_items;

import com.example.to_do_list.R;

import static java.lang.System.out;
import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class The_lIST extends Activity implements AdapterView.OnItemLongClickListener{
	
	EditText enteritem;
	
	GridView grid;
	todoitems x = new todoitems("");
	static customadapter adapter;
	ArrayList<todoitems> items = new ArrayList<todoitems>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_the_l_ist);
		
		
		
		
		da_items.intialize();
		
		da_items.Global = items;
		adapter = new customadapter(this,da_items.Global);
		grid = (GridView) findViewById(R.id.the_list);
		enteritem = (EditText)findViewById(R.id.body);
		
		
		grid.setOnItemLongClickListener(this);
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
					highlight.setBackgroundColor(Color.parseColor("#0000FF"));
					item.done=true;
				}
				else
				{
					highlight.setBackgroundColor(Color.parseColor("#FF0000"));
					item.done=false;
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
					InputMethodManager imm = (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(enteritem.getWindowToken(), 0);
				    da_items.Global.add(new todoitems(enteritem.getText().toString()));
				    //fileinfileout.settype(items);
				    fileinfileout.saveInFile();
					//fileinfileout.saveInFile(item);
					enteritem.getText().clear();
					adapter.updatelist(da_items.Global);
				}
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		fileinfileout.setContext(The_lIST.this);
		//fileinfileout.settype(items);
		fileinfileout.filein();
		grid.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.the_l_ist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onItemLongClick(AdapterView parent, View view,int position, long id)
	{
		Intent intent = new Intent(this,Singleitem_dialog.class);
		ViewHolder holder = (ViewHolder) view.getTag();
		todoitems item = (todoitems) holder.selecteditem.getTag();
		//out.println(item.todoname);
		intent.putExtra("id", position);
		intent.putExtra("todoname",item.todoname.toString());
		startActivity(intent);
		// TODO Auto-generated method stub
		return true;
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getApplicationContext(),"onresume called", 
				   Toast.LENGTH_LONG).show();
		adapter.updatelist(da_items.Global); 
	}
	
}


