package to_do_list;

import java.util.ArrayList;
import static java.lang.System.out;
import android.util.Log;
import android.widget.Toast;

public class da_items {
	static ArrayList<todoitems> Global;
	static ArrayList<todoitems> Global_archived;
	static ArrayList<todoitems> Selections;

	public static void intialize()
	{
		Global_archived = new ArrayList<todoitems>();
		Global = new ArrayList<todoitems>();
		Selections = new ArrayList<todoitems>();
		
	}
	static String domath()
	{
		String report = new String();
		int y = Global.size();
		int z = Global_archived.size();
		int count1=0,count2=0,count3=0,count4=0,count5=0;
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
		report = " total number of TODO items checked: "+count1+"\n"+" total number of TODO items left unchecked:"+count2+"\n"+" total number of archived TODO items:"+count3+"\n"+" total number of checked archived TODO items:"+count4+"\n"+" total number of unchecked archived TODO items:"+count5+"\n";
		return report;
	}
	public static ArrayList<todoitems> return_list()
	{
		return Global;
	}
}
