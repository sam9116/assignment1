package to_do_list;

import java.util.ArrayList;
import static java.lang.System.out;
import android.util.Log;
import android.widget.Toast;

public class da_items {
	// these are my global objects that gets called by everybody
	static ArrayList<todoitems> Global;// the list that holds all current task items,there can't be an item in both archive and current task list
	static ArrayList<todoitems> Global_archived;// the list that holds all archived items
	static ArrayList<todoitems> Selections;//the list that holds all selected items, for email/deletion..etc,they can be either current task items or archived

	public static void intialize()
	{
		Global_archived = new ArrayList<todoitems>(); //intializes all 3 lists
		Global = new ArrayList<todoitems>();
		Selections = new ArrayList<todoitems>();
		
	}
	static String domath()
	{
		String report = new String();//the summery of number as requested in the specs
		int y = Global.size();//i stored size of both array lists in these variables just so they don't have to be called too often
		int z = Global_archived.size();
		int count1=0,count2=0,count3=0,count4=0,count5=0;// these are explained at the bottom the method,when the report is generated
		if(y!=0)
		{
			for(int x = 0; x<y;x++)
			{
				if(Global.get(x).done == true)
				{
					count1++;
				}
			}
		}
		if(z!=0)
		{
			for(int j= 0; j<z;j++)
			{
				if(Global_archived.get(j).done == true)
				{
					count4++;
				}
			}
			//out.println(count4);
		}
		
		
		count2 = y - count1;
		count3 = z;
		count5 = z - count4;
		//not the best variable names but they are quite self explanatory now
		report = " total number of TODO items checked: "+count1+"\n"+" total number of TODO items left unchecked:"+count2+"\n"+" total number of archived TODO items:"+count3+"\n"+" total number of checked archived TODO items:"+count4+"\n"+" total number of unchecked archived TODO items:"+count5+"\n";
		return report;
	}
}
