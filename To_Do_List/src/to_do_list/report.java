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
	ListView stat_stuff;
	ArrayList<Integer> numbers= new ArrayList<Integer>();
	String[] finalreport = {"Go back"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Boring Stats Stuff For Nerds");
		setContentView(R.layout.one_item);
		int id = 0;
		stat_stuff=(ListView) findViewById(R.id.item_setting);
		Intent intent = getIntent();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(report.this,android.R.layout.simple_list_item_1, finalreport);
		stat_stuff.setAdapter(adapter);
		stat_stuff.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
						// TODO Auto-generated method stub
						finish();
						
			}
			
		});
		if(intent!=null)
		{
			String report = da_items.domath();
			//out.println(todoname);
			TextView singletext = (TextView)findViewById(R.id.singleitem);
			singletext.setTextSize(1,15);
			singletext.setText(report);
			
			
		}
		
	}
	
	

}
