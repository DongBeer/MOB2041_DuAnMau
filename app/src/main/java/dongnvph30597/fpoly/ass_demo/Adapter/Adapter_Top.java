package dongnvph30597.fpoly.ass_demo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.R;
import dongnvph30597.fpoly.ass_demo.model.LoaiSach;
import dongnvph30597.fpoly.ass_demo.model.Top;

public class Adapter_Top extends BaseAdapter {
    private Context context;
    private ArrayList<Top> arr = new ArrayList<>();
    private int temp = 0;

    public Adapter_Top(Context context) {
        this.context = context;
    }

    public void setDate(ArrayList<Top> arrTop){
        arr = arrTop;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(arr != null){
            return arr.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TopViewHolder holder;
        Top top = arr.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            temp++;
            convertView = inflater.inflate(R.layout.layout_item_top,null,false);
            holder = new TopViewHolder();
            holder.tvTopitem = convertView.findViewById(R.id.item_top);
            holder.tvTopSach = convertView.findViewById(R.id.item_top_sach);
            holder.tvTopSoluong = convertView.findViewById(R.id.item_top_sl);
            convertView.setTag(holder);
        }else {
            holder = (TopViewHolder) convertView.getTag();
        }
        holder.tvTopitem.setText(String.valueOf(temp));
        holder.tvTopSach.setText(String.valueOf(top.tenSach));
        holder.tvTopSoluong.setText(String.valueOf(top.soLuong));
        return convertView;

    }
    public static class TopViewHolder{
        private TextView tvTopitem, tvTopSach, tvTopSoluong;
    }
}
