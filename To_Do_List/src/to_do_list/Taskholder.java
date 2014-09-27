package to_do_list;

//Copyright [2014] [Sam Bao]
//Copyright	[2014] [Jeremy Logan]
//Copyright [2014] [Joshua Campbell]
//Copyright	[2013] [Vivek R]
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

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
	
	ViewPager currentarchived = null;//
	ActionBar current_archived; //used for holding the menu button 
	static Boolean tabselected = true;//a boolean to control items on which tab to be email/archive/deleted, when application starts for the first time,currenttask is selected
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.taskholder, menu);//create the menu on the top right of the app
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// email function code grabbed from http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application Sept 23rd/2014
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
			
			da_items.Global_archived.addAll(da_items.Global);//items currently in current task gets moved to archived list
			da_items.Global.clear();//there should be no more item in current task now
			fileinfileout.getstatus("save");//overwrite both files to update the situation
			fileinfileout.saveInFile();
			fileinfileout.getstatus("archive");
			fileinfileout.saveInFile();
			currentarchived.setAdapter(new fragadapter(getSupportFragmentManager()));
			//refreshes the screen once items are moved and shuffled
		}
		else if (id == R.id.deleteall)//i admit the delete all here is a bit unclear, it actually deletes all items under the current selected tab, i.e. either all the current task items or all the archived task items
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
		else if (id == R.id.stats)// all the summary you can eat! :)
		{
			Intent i = new Intent(Taskholder.this,report.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		//swtiches between current task and archived task,tabselected records which tab is selected to make decisions later
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
		currentarchived =(ViewPager)findViewById(R.id.pager);//my viewpager holds 2 fragments - current tasks and archived task, user can check them by swiping left and right
		currentarchived.setAdapter(new fragadapter(getSupportFragmentManager()));
		current_archived = getActionBar();
		current_archived.setNavigationMode(current_archived.NAVIGATION_MODE_TABS);
		ActionBar.Tab currenttab = current_archived.newTab();//not only can user chose with swiping, they can also press tabs
		ActionBar.Tab archivetab = current_archived.newTab();
		currenttab.setText("Current");//sets up tabs and add them to the actionbar
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

