package anhnguyen.com.vn.starstudy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MyPC on 04/03/2017.
 */
public class RevisionKnowledgeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Integer> arrLinkAnh;

    public RevisionKnowledgeAdapter(Context context, ArrayList<Integer> arrLinkAnh) {
        this.context = context;
        this.arrLinkAnh = arrLinkAnh;
    }

    @Override
    public int getCount() {
        return arrLinkAnh.size();
    }

    @Override
    public Object getItem(int i) {
        return arrLinkAnh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_image_knowledge, null);
            viewHolder = new ViewHolder();
            viewHolder.tvImageKnowLedge = (TextView) view.findViewById(R.id.tv_image_knowledge);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvImageKnowLedge.setBackgroundResource(arrLinkAnh.get(i));
        return view;
    }

    private static class ViewHolder {
        TextView tvImageKnowLedge;
    }
}
