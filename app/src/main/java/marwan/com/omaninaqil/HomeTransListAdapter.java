package marwan.com.omaninaqil;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import marwan.com.omaninaqil.R;

public class HomeTransListAdapter extends ArrayAdapter<String> {
    public final Activity context;
    public final String[] trans_type;
    public final String[] trans_from;
    public final String[] trans_to;

    public HomeTransListAdapter(Activity context, String[] trans_type, String[] trans_from, String[]
            trans_to) {
        super(context, R.layout.custom_trans_layout,trans_type);
        this.context = context;
        this.trans_type = trans_type;
        this.trans_from = trans_from;
        this.trans_to = trans_to;
    }
    public View getView(int position , View view , ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_trans_layout,null,true);

        ImageView pointView = rowView.findViewById(R.id.custom_point);
        TextView type_cus = (TextView)rowView.findViewById(R.id.custom_type);
        TextView from_cus = (TextView)rowView.findViewById(R.id.custom_from);
        TextView to_cus = (TextView)rowView.findViewById(R.id.custom_to);
        ImageView morebtn = rowView.findViewById(R.id.custom_more);
        ImageView from_icon = rowView.findViewById(R.id.custom_from_icon);
        ImageView to_icon = rowView.findViewById(R.id.custom_to_icon);

        type_cus.setText(trans_type[position]);
        from_cus.setText(trans_from[position]);
        to_cus.setText(trans_to[position]);
        pointView.setImageResource(R.drawable.pointlist);
        morebtn.setImageResource(R.drawable.details);
        from_icon.setImageResource(R.drawable.from);
        to_icon.setImageResource(R.drawable.to);

        return rowView;
    }
}
