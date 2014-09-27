package to_do_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//this is used to display the fragment 
public class fragadapter extends FragmentPagerAdapter
{

	public fragadapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment=null;
        if(arg0==0)
        {
            fragment=new CurrentTask();//switches between the current task fragment and archived fragment
        }
        if(arg0==1)
        {
            fragment=new ArchivedTask();
        }
      
        return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;//only 2 fragments, so return 2
	}
	
}