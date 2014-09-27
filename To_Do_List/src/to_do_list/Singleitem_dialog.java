package to_do_list;

import static java.lang.System.out;

import java.util.ArrayList;

import com.example.to_do_list.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//this is almost identical to single_item_archive, except everything is for curremt task items
public class Singleitem_dialog extends Activity {
	String[] settings = new String[] {"Delete","Archive","Add to selection","Remove from selection","Go back"};//all the possible options
	ListView setting_menu;
	int index =0;//the index of the item we expanded this menu for, intialzed to 0
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Detail and Options");
		setContentView(R.layout.one_item);
		int id = 0;
		setting_menu =  (ListView) findViewById(R.id.item_setting);
		Intent intent = getIntent();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Singleitem_dialog.this,android.R.layout.simple_list_item_1, settings);
		setting_menu.setAdapter(adapter);
		setting_menu.setOnItemClickListener(new OnItemClickListener(){
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position== 0)
				{
					
					da_items.Global.remove(index);//removes the file from current item list,but also we wipe the savefile instead
					fileinfileout.getstatus("save");
					fileinfileout.saveInFile();

					finish();
				}
				else if(position== 1)
				{
					da_items.Global_archived.add(da_items.Global.get(index));
					da_items.Global.remove(index);

					fileinfileout.getstatus("archive");
					fileinfileout.saveInFile();
					fileinfileout.getstatus("save");
					fileinfileout.saveInFile();
					
					finish();
				}
				else if(position == 2)
				{
					//add to selections
					da_items.Selections.add(da_items.Global.get(index));//current item selected for email and other activity
					da_items.Global.get(index).selected = true;
					finish();
				}
				else if(position == 3)
				{
					//delete to selection
					da_items.Selections.remove(da_items.Global.get(index));
					da_items.Global.get(index).selected = false;
					finish();
				}
				else
				{
					finish();
				}
			}
		
		});
		

		
		if(intent!=null)
		{
			index = intent.getIntExtra("id", id);//get which element in the list is been optioned
			String todoname = intent.getStringExtra("todoname");
			//out.println(todoname);
			TextView singletext = (TextView)findViewById(R.id.singleitem);
			singletext.setText(todoname);//prints out task detail on screen
			
			
		}

		
	}
	
}
