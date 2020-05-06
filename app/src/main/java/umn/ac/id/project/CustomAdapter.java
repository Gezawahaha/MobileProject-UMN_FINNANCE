package umn.ac.id.project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends BaseAdapter {
    Context context;
    int logo[];
    String[] bankNames;
    LayoutInflater inflter;


    public CustomAdapter(Context applicationContext, int[] logo, String[] bankNames) {
        this.context = applicationContext;
        this.logo = logo;
        this.bankNames = bankNames;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return logo.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(logo[i]);
        names.setText(bankNames[i]);
        return view;
    }
}
