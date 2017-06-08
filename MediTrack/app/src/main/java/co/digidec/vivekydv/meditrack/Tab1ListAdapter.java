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
public class Tab1ListAdapter extends ArrayAdapter<Medicine> {

    private Context context;
    private ArrayList<Medicine> medicines = new ArrayList<Medicine>();
    private int pos=0;

    public Tab1ListAdapter(Context context, int layoutId, ArrayList<Medicine> medicines) {
        super(context, layoutId, medicines);
        this.context = context;
        this.medicines = medicines;
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
    public Medicine getItem(int position) {
        return medicines.get(position);
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

        Medicine currentPosition = getItem(position);

        if(currentPosition != null) {
            TextView name = (TextView) rowView.findViewById(R.id.name);
            name.setText(medicines.get(position).getName().toString());
        }
        return rowView;
    }

    @Override
    public int getCount() {
        return medicines.size();
    }
}
