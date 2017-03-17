package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import yourfuture.dictionary.R;
import items.ItemFeature;

/**
 * Created by Dell on 12/11/2016.
 */
public class AdapterFeature extends BaseAdapter {
    private Context context;
    private ArrayList<ItemFeature> listFeature;

    public AdapterFeature(Context context, ArrayList<ItemFeature> listFeature) {
        this.context = context;
        this.listFeature = listFeature;
    }

    @Override
    public int getCount() {
        return listFeature.size();
    }

    @Override
    public Object getItem(int i) {
        return listFeature.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        ItemFeature item = listFeature.get(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_item_feature, null);
            viewHolder = new ViewHolder();
            viewHolder.tvIcon = (TextView) view.findViewById(R.id.tvIcon);
            viewHolder.tvFeature = (TextView) view.findViewById(R.id.tvFeature);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvIcon.setBackgroundResource(item.getIcon());
        viewHolder.tvFeature.setText(item.getName());

        return view;
    }

    private static class ViewHolder {
        public TextView tvIcon;
        public TextView tvFeature;
    }
}
