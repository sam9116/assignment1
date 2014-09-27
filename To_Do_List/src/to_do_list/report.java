package to_do_list;
import java.util.ArrayList;

import com.example.to_do_list.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class report extends Activity{
	ListView stat_stuff;//used to hold the various numbers in summary
	ArrayList<Integer> numbers= new ArrayList<Integer>();
	String[] finalreport = {"Go back"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Boring Stats Stuff For Nerds");//i think thats how youtube described their numbers page,before they took that out too for more simplicity
		setContentView(R.layout.one_item);//again,reuses the layout file for selecting items on a grid
		int id = 0;
		stat_stuff=(ListView) findViewById(R.id.item_setting);
		Intent intent = getIntent();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(report.this,android.R.layout.simple_list_item_1, finalreport);//loads the option text on to the menu,displayed on this activity
									   // alot of people suggested using fragments for dialog instead,but i wasn't very confident using fragment at the time so i sticked to activity
		stat_stuff.setAdapter(adapter);//displays the dialog box which the report will sit in
		stat_stuff.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
						// TODO Auto-generated method stub
						finish();//there is only one option so no point implementing different action by different keys
						
			}
			
		});
		if(intent!=null)
		{									  //
			String report = da_items.domath();//where i got the name from https://www.youtube.com/watch?v=p2EA2nA2ar0
			//out.println(todoname);
			TextView singletext = (TextView)findViewById(R.id.singleitem);
			singletext.setTextSize(1,15);//obviously this is too much to display on my tiny phone, so text sizes were reduced,i think the value 1 was for dp
			singletext.setText(report);
			
			
		}
		
	}
	
	

}
