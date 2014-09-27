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
// this has similar structure to the report class,
public class Singleitem_dialog_archive extends Activity {
	String[] settings = new String[] {"Delete","Restore","Add to selection","Remove from selection","Go back"};//all the option text
	ListView setting_menu;
	int index =0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Detail and Options");
		setContentView(R.layout.one_item);
		int id = 0;
		setting_menu =  (ListView) findViewById(R.id.item_setting);
		Intent intent = getIntent();                                                                             //loads the setting text into menu,display the menu in this context
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Singleitem_dialog_archive.this,android.R.layout.simple_list_item_1, settings);
		setting_menu.setAdapter(adapter);
		setting_menu.setOnItemClickListener(new OnItemClickListener(){
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position== 0)
				{
					
					da_items.Global_archived.remove(index);//remove the task from archived list
					fileinfileout.getstatus("save");//wipe that task off the save file
					fileinfileout.saveInFile();
					finish();
				}
				else if(position== 1)
				{
					da_items.Global.add(da_items.Global_archived.get(index));
					da_items.Global_archived.remove(index);//current task list gets hold a copy of that archived item, then archive item wipe the item from itself, and wiped from the save file too
					//fileinfileout.settype(da_items.Global);
					fileinfileout.getstatus("save");//wipe and save both files to ensure task transfer
					fileinfileout.saveInFile();
					fileinfileout.getstatus("archive");
					fileinfileout.saveInFile();
					finish();
				}
				else if(position == 2)
				{
					//add to selections for action like email,turns on the selected flag to display colors so user know its selected
					da_items.Selections.add(da_items.Global_archived.get(index));
					da_items.Global_archived.get(index).selected = true;
					finish();
				}
				else if(position == 3)
				{
					//delete to selection
					da_items.Selections.remove(da_items.Global_archived.get(index));
					da_items.Global_archived.get(index).selected = false;
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
			index = intent.getIntExtra("id", id);
			String todoname = intent.getStringExtra("todoname");
			//out.println(todoname);
			TextView singletext = (TextView)findViewById(R.id.singleitem);
			singletext.setText(todoname);
			
			
		}

		
	}
	
}
