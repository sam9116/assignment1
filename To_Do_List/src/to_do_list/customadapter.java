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

class customadapter extends BaseAdapter
{
	ArrayList<todoitems> da_list;
	Context context;
	
	customadapter(Context context,ArrayList<todoitems> provided)
	{
		this.context = context;
		da_list = provided;
		Toast.makeText(context.getApplicationContext(), "in customadapter:"+da_items.Global.size(),  Toast.LENGTH_LONG).show();
		fileinfileout.setContext(context);
		fileinfileout.filein();
		if(da_list== null)
		{
			for(int i=0; i <=9; i++)
			{
				todoitems x = new todoitems("");
				x.todoname = Integer.toString(i);
				da_list.add(x);
			}
		}
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return da_list.size();
	}
	public ArrayList<todoitems> return_list()
	{
		return da_list;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return da_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		ViewHolder holder = null;
		if(row == null)
		{
			//System.out.println("attempt to inflate");
			row = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, parent,false);
			//System.out.println("inflated");
			holder = new ViewHolder(row);
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		todoitems temp = da_list.get(position);
		holder.selecteditem.setText(da_list.get(position).todoname);
		if(temp.done == true)
		{
			holder.selecteditem.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.checkon));
			
		}
		else
		{
			holder.selecteditem.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.checkoff));
		}
		if(temp.selected==true)
		{
			holder.selecteditem.setTextColor(Color.parseColor("#0000FF"));
		}
		else if(temp.selected==false)
		{
			holder.selecteditem.setTextColor(Color.parseColor("#FF0000"));
		}
		holder.selecteditem.setTag(temp);
		return row;
	}
	public void updatelist(ArrayList<todoitems> provided)
	{
		this.da_list = provided;
		notifyDataSetChanged();
	}
}
