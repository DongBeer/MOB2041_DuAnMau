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

public class Adapter_LoaiSach extends BaseAdapter {

    private Context context;
    private ArrayList<LoaiSach> arr = new ArrayList<>();

    public Adapter_LoaiSach(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<LoaiSach> arrLoaisach){
        this.arr = arrLoaisach;
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
        LoaiSachHolder holder;
        LoaiSach loaiSach = arr.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_item_loaisach,null,false);
            holder = new LoaiSachHolder();
            holder.tvTenloaisach = convertView.findViewById(R.id.tvTenloaisach);
            holder.tvNhasx = convertView.findViewById(R.id.tvNhasx);
            convertView.setTag(holder);
        }else {
            holder = (LoaiSachHolder) convertView.getTag();
        }
        holder.tvTenloaisach.setText(loaiSach.getTenLoai());
        holder.tvNhasx.setText(loaiSach.getNhaSX());
        return convertView;
    }

    public static class LoaiSachHolder{
        private TextView tvTenloaisach, tvNhasx;
    }
}
