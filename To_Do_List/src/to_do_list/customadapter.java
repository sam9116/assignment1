package to_do_list;

import java.util.ArrayList;

import com.example.to_do_list.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
//this adapter was inspired by the extended baseadapter example on android developer website
//http://developer.android.com/guide/topics/ui/layout/gridview.html
class customadapter extends BaseAdapter// the adapter used for displaying items in the two gridviews from current task and archived task
{
	ArrayList<todoitems> da_list;//the archived/current task currently loaded in this gridview, i could of just referenced those lists since they are global
								// but i think this makes my adapter more portable
	Context context;
	
	customadapter(Context context,ArrayList<todoitems> provided)
	{
		this.context = context; //initializing methode providing the context and the list of todoitems we will work with
		da_list = provided;
		
		fileinfileout.setContext(context);//give the context to my fileio method so it can properly function
		fileinfileout.filein();

		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return da_list.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return da_list.get(position); //returns the item in position,usually used to reference a single item toggled in a gridview cell
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		ViewHolder holder = null;
		if(row == null)
		{
			
			row = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, parent,false);
			
			holder = new ViewHolder(row);
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		todoitems temp = da_list.get(position);
		holder.selecteditem.setText(da_list.get(position).todoname);
		if(temp.done == true)// check task item's attributes and assign notices to user
		{
			holder.selecteditem.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.checkon));
			//if the item is already done, give it a checkmark so user don't get confused
		}	//the deprecated function was used here so the program would run on my slightly outdated phone,which is what i used to test my code majority of the time,avd is just too slow on my laptop 
		else
		{
			holder.selecteditem.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.checkoff));
		}
		if(temp.selected==true)
		{
			//when items are selected as a group, for delete/email,i change the color so user can know what is selected and what is not
			holder.selecteditem.setTextColor(Color.parseColor("#0000FF"));
		}	//blue for selected and red for not,all item starts out as not selected
		else if(temp.selected==false)
		{
			holder.selecteditem.setTextColor(Color.parseColor("#FF0000"));
		}
		holder.selecteditem.setTag(temp);//sets tag so this item can be later retrieved with gettag()
		return row;
	}
	public void updatelist(ArrayList<todoitems> provided)//refreshes the gridview
	{
		this.da_list = provided;
		notifyDataSetChanged(); //updates view
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
