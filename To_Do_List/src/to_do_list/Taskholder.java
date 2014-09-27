package to_do_list;

import java.net.URLEncoder;
import java.util.ArrayList;

import com.example.to_do_list.R;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Taskholder extends FragmentActivity implements TabListener{
	
	ViewPager currentarchived = null;
	ActionBar current_archived;
	static Boolean tabselected = true;//when application starts,currenttask is selected
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.taskholder, menu);
		return true;
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Toast.makeText(Taskholder.this,"on appilcation stop,the size of list is:"+da_items.Global.size(), Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(Taskholder.this,"on appilcation DESTROY,the size of list is:"+da_items.Global.size(), Toast.LENGTH_SHORT).show();
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.email) 
		{
			
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
			i.putExtra(Intent.EXTRA_SUBJECT, "To Do List");
			ArrayList<String> data = new ArrayList<String>();
			if(tabselected == true)
			{
				for(int x = 0; x<da_items.Global.size();x++)
				{
					data.add(da_items.Global.get(x).todoname);
					data.add("-");
					if(da_items.Global.get(x).done == true)
					{
						data.add("Done");
					}
					else
					{
						data.add("Not Done");
					}
					data.add("\n");
				}
			}
			else
			{
				for(int x = 0; x<da_items.Global_archived.size();x++)
				{
					data.add(da_items.Global_archived.get(x).todoname);
					data.add("-");
					if(da_items.Global_archived.get(x).done == true)
					{
						data.add("Done");
					}
					else
					{
						data.add("Not Done");
					}
					data.add("\n");
				}
			}
			
			String finalmessage =  data.toString().replaceAll("[,]", "");
			i.putExtra(Intent.EXTRA_TEXT   ,finalmessage.substring(1, finalmessage.length()-1));
			try {
			    startActivity(Intent.createChooser(i, "Chose an email client"));
			} catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(Taskholder.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
		}
		else if (id == R.id.emailselection)
		{
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
			i.putExtra(Intent.EXTRA_SUBJECT, "To Do List");
			ArrayList<String> data = new ArrayList<String>();
			
			for(int x = 0; x<da_items.Selections.size();x++)
			{
				data.add(da_items.Selections.get(x).todoname);
				data.add("-");
				if(da_items.Selections.get(x).done == true)
				{
					data.add("Done");
				}
				else
				{
					data.add("Not Done");
				}
				data.add("\n");
			}
			String finalmessage =  data.toString().replaceAll("[,]", "");
			i.putExtra(Intent.EXTRA_TEXT   ,finalmessage.substring(1, finalmessage.length()-1));
			try {
			    startActivity(Intent.createChooser(i, "Chose an email client"));
			} catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(Taskholder.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
			
			
		}
		else if (id == R.id.archiveall)
		{
			
			da_items.Global_archived.addAll(da_items.Global);
			da_items.Global.clear();
			fileinfileout.getstatus("save");
			fileinfileout.saveInFile();
			fileinfileout.getstatus("archive");
			fileinfileout.saveInFile();
			currentarchived.setAdapter(new fragadapter(getSupportFragmentManager()));
		}
		else if (id == R.id.deleteall)
		{
			if(tabselected == true)
			{
				da_items.Global.clear();
				fileinfileout.getstatus("save");
				fileinfileout.saveInFile();
				da_items.Global_archived.clear();
				fileinfileout.getstatus("archive");
				fileinfileout.saveInFile();
				currentarchived.setAdapter(new fragadapter(getSupportFragmentManager()));
			}
			else if(tabselected == false)
			{
				
				currentarchived.setAdapter(new fragadapter(getSupportFragmentManager()));
			}
		
		}
		else if (id == R.id.stats)
		{
			Intent i = new Intent(Taskholder.this,report.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		currentarchived.setCurrentItem(tab.getPosition());
		if(tab.getPosition()== 0)
		{
			tabselected = true;
		}
		else if(tab.getPosition()== 1)
		{
			tabselected = false;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_activity);
		currentarchived =(ViewPager)findViewById(R.id.pager);
		currentarchived.setAdapter(new fragadapter(getSupportFragmentManager()));
		current_archived = getActionBar();
		current_archived.setNavigationMode(current_archived.NAVIGATION_MODE_TABS);
		ActionBar.Tab currenttab = current_archived.newTab();
		ActionBar.Tab archivetab = current_archived.newTab();
		currenttab.setText("Current");
		archivetab.setText("Archived");
		currenttab.setTabListener(this);
		archivetab.setTabListener(this);
		current_archived.addTab(currenttab);
		current_archived.addTab(archivetab);
		
		
		currentarchived.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				current_archived.setSelectedNavigationItem(arg0);

			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
	
			}
			
		});
	}

	

}

