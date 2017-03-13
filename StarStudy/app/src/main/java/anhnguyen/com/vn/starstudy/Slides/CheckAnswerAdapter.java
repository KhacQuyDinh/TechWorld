package anhnguyen.com.vn.starstudy.Slides;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import anhnguyen.com.vn.starstudy.R;

/**
 * Created by MyPC on 28/02/2017.
 */
public class CheckAnswerAdapter extends BaseAdapter {
    String[] arrayList;
    LayoutInflater layoutInflater;

    public CheckAnswerAdapter(String[] arrayList, Context context) {
        this.arrayList = arrayList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.length;
    }

    @Override
    public Object getItem(int i) {
        return arrayList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_gridview_list_exam, null);
            viewHolder.tvDapAn = (TextView) view.findViewById(R.id.tvDapAn);
            viewHolder.tvSoThuTu = (TextView) view.findViewById(R.id.tvSoThuTu);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvSoThuTu.setText("Câu " + (position + 1) + ". ");

        String item = arrayList[position];
        if (item != null) {
            viewHolder.tvDapAn.setText(item);
        } else {
            viewHolder.tvDapAn.setText("");
        }
        return view;
    }

    private static class ViewHolder {
        public TextView tvSoThuTu, tvDapAn;
    }
}
