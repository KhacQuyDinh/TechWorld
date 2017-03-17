package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import yourfuture.dictionary.R;
import items.ItemHistory;
import model_dictionary.ModeDictionary;

/**
 * Created by Dell on 16/11/2016.
*/

public class AdapterHistory extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<ItemHistory> arrHistory;
    private ArrayList<ItemHistory> arrTmpHistory;
    private ArrayList<ItemHistory> orig;//Luu tru gia tri goc cac items
    private GeneralFilter generalFilter = new GeneralFilter();

    public AdapterHistory(Context context, ArrayList<ItemHistory> arrHistory) {
        this.context = context;
        this.arrHistory = arrHistory;

        //Bien tam luu tru du lieu goc khong thay doi du lieu cho viec search.
        arrTmpHistory = new ArrayList<>();
        for (ItemHistory i : arrHistory) {
            arrTmpHistory.add(i);
        }
    }

    @Override
    public int getCount() {
        return arrHistory.size();
    }

    @Override
    public Object getItem(int i) {
        return arrHistory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ItemHistory itemHistory = arrHistory.get(i);
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_item_history, null);
            viewHolder = new ViewHolder();
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tvContent);
            viewHolder.imbtnDel = (ImageView) view.findViewById(R.id.imgDel);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvContent.setText(itemHistory.getTvContent());
        viewHolder.imbtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModeDictionary.getExecDic().deleteAWordOfHistory(itemHistory.getTvContent());
                arrHistory.remove(itemHistory);
                //co luon.
                notifyDataSetChanged();
            }
        });
        return view;
    }

    /**
     *
     * @return 1 đối tượng duy nhất của tìm kiếm.
     */
    @Override
    public Filter getFilter() {
        return generalFilter;
    }

    //Đối tượng chứa hành vi tìm kiếm từ.
    private class GeneralFilter extends Filter {
        private String tmpConstraint;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //Phai tu viet code nay.
            //Giu ket qua bo loc bang cach dung .values public Object values. trong protected static class nestedclass
            final FilterResults oReturn = new FilterResults();

            //Bien tam cho luu vao values cua oReturn.
            final ArrayList<ItemHistory> results = new ArrayList<>();

            if (orig == null) {
                orig = new ArrayList<>();
                for (ItemHistory i : arrHistory) {
                    orig.add(i);
                }
            }

            //Khi da nhap chuoi.
            if (!constraint.equals("")) {
                //Khi List ton tai va co du lieu trong List.
                if (orig.size() > 0) {
                    //Duyet..
                    tmpConstraint = constraint.toString().trim().toLowerCase();
                    for (final ItemHistory g : orig) {
                        if (g.getTvContent().trim().toLowerCase().startsWith(tmpConstraint)) {
                            results.add(g);//Nếu thoả mãn điều kiện trên thì thêm vào results.
                        }
                    }
                }
                oReturn.values = results;
            } else {
                oReturn.values = orig;
                orig = null;
            }
            return oReturn; // là kết quả trong hàm publishResults.
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            arrHistory.clear();
            for (ItemHistory i : (ArrayList<ItemHistory>) results.values) {
                arrHistory.add(i);
            }
            notifyDataSetChanged();
        }
    }

    //nested class để tiện quản lý và tối ưu việc tái sử dụng view.
    private static class ViewHolder {
        public TextView tvContent;
        public ImageView imbtnDel;
    }


}

