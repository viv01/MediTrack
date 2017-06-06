package co.digidec.vivekydv.meditrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vivekya on 6/6/2017.
 */
public class Tab1ListAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> stringlist = new ArrayList<String>();
    private int pos=0;

    public Tab1ListAdapter(Context context, int layoutId, ArrayList<String> users) {
        super(context, layoutId, users);
        this.context = context;
        this.stringlist = users;
    }

    @Override
    public long getItemId(int position) {
        pos=position;
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public String getItem(int position) {
        return stringlist.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        convertView = null;
        rowView = convertView;
        // Check if an existing view is being reused, otherwise inflate the view
        if (rowView == null) {
            LayoutInflater inflator;
            inflator = LayoutInflater.from(getContext());
            rowView = inflator.inflate(R.layout.tab1_listrow, parent, false);
        }

        String currentPosition = getItem(position);

        if(currentPosition != null) {
            TextView name = (TextView) rowView.findViewById(R.id.name);
            name.setText(stringlist.get(position).toString());
        }
        return rowView;
    }
}
