package to_do_list;

import com.example.to_do_list.R;

import android.view.View;
import android.widget.TextView;

class ViewHolder
{
	TextView selecteditem;
	ViewHolder(View feed)
	{
		selecteditem = (TextView) feed.findViewById(R.id.singleitem_brief);
	}
}