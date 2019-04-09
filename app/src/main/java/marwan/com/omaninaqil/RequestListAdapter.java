package marwan.com.omaninaqil;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RequestListAdapter extends ArrayAdapter<String> {
    public final Activity context;
    public final String[] shipId;
    public final Integer[] shipStatus;

    public RequestListAdapter(Activity context, String[] shipId, Integer[] shipStatus) {
        super(context, R.layout.custom_request_list,shipId);
        this.context = context;
        this.shipId = shipId;
        this.shipStatus = shipStatus;
    }

    public View getView(int position , View view , ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_request_list,null,true);
        TextView shipmentId = rowView.findViewById(R.id.shipment_id_txt);
        ImageView requestStatus = rowView.findViewById(R.id.status_img);

        shipmentId.setText(shipId[position]);
        requestStatus.setImageResource(shipStatus[position]);
        return rowView;
    }
}