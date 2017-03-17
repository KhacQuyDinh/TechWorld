package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import items.ItemWord;
import yourfuture.dictionary.R;

/**
 * Created by Dell on 13/11/2016.
 */
public class AdapterWord extends BaseAdapter implements Filterable {
    private ListView lvResult;
    private Context context;
    private ArrayList<ItemWord> arrWord;
    private GeneralFilter generalFilter = new GeneralFilter();
    public AdapterWord(Context context, ArrayList<ItemWord> arrWord, ListView lvResult) {
        this.context = context;
        this.arrWord = arrWord;
        this.lvResult = lvResult;
        //Muc dich cua orig la luu lai mang ban dau va khong thay doi gia tri trong mang do.
    }

    @Override
    public int getCount() {
        return arrWord.size();
    }

    @Override
    public Object getItem(int i) {
        return arrWord.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemWord itemWord = arrWord.get(i);
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_item_word, null);
            viewHolder = new ViewHolder();
//            viewHolder.tvIcon = (TextView) view.findViewById(R.id.tvIcon);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tvContent);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
//        viewHolder.tvIcon.setBackgroundResource(itemWord.getIcon());
        viewHolder.tvContent.setText(itemWord.getContent());
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

    private class GeneralFilter extends Filter {
            //Muc dich cua orig la luu lai mang ban dau va khong thay doi gia tri trong mang do.
            private ArrayList<ItemWord> orig;
            private String tmpConstraint;

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //Phai tu viet code nay.
                //Giu ket qua bo loc bang cach dung .values public Object values. trong protected static class nestedclass
                lvResult.setVisibility(View.VISIBLE);

                final FilterResults oReturn = new FilterResults();
                //Bien tam cho luu vao values cua oReturn.
                final ArrayList<ItemWord> results = new ArrayList<>();

                if (orig == null) {
                    orig = new ArrayList<>();
                    for (ItemWord i : arrWord) {
                        orig.add(i);
                    }
                }
                //Khi da nhap chuoi.
//                System.out.println(constraint);  ??? when I type first letter, it does not recognize. Why is it?
                if (!constraint.equals("")) {
                    //Khi List ton tai va co du lieu trong List.
                    if (orig.size() > 0) {
                        //Duyet..
                        tmpConstraint = constraint.toString().trim().toLowerCase();
                        for (final ItemWord g : orig) {
                            //Neu co chua thi them vao.
                            if (g.getContent().trim().toLowerCase().startsWith(tmpConstraint)) {

                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;

                } else {
                    lvResult.setVisibility(View.GONE);
                    arrWord.clear();
                    for (ItemWord i : orig) {
                        arrWord.add(i);
                    }
                    //chú ý notifyDataSetChanged không thể gọi phương thức getView... khi có setVisibility.
                    //và điều ngược lại cx đúng.

                    oReturn.values = null;
                    orig = null;//giúp cập nhập lại orig trên.
                }

                return oReturn; // is results.
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                if (results.values != null) {
                    arrWord.clear();
                    for (ItemWord i : (ArrayList<ItemWord>) results.values) {
                        arrWord.add(i);
                    }
                    notifyDataSetChanged();
                }
            }
    }

    private static class ViewHolder {
        public TextView tvIcon;
        public TextView tvContent;
    }
}
