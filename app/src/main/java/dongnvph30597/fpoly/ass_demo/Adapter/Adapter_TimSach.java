package dongnvph30597.fpoly.ass_demo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.R;
import dongnvph30597.fpoly.ass_demo.model.TimSach;

public class Adapter_TimSach extends BaseAdapter {
    private Context context;
    private ArrayList<TimSach> arr = new ArrayList<>();

    public Adapter_TimSach(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<TimSach> arrTimsach){
        arr = arrTimsach;
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
        TimsachViewHolder holder;
        TimSach timSach = arr.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_item_timsach,null,false);
            holder = new TimsachViewHolder();
            holder.tvTenSachTim = convertView.findViewById(R.id.item_timtensach);
            holder.tvTSoluongtrang = convertView.findViewById(R.id.item_tim_sltrang);
            convertView.setTag(holder);
        }else {
            holder = (TimsachViewHolder) convertView.getTag();
        }
        holder.tvTenSachTim.setText(String.valueOf(timSach.tenSachtim));
        holder.tvTSoluongtrang.setText(String.valueOf(timSach.sotrangSachtim));
        return convertView;
    }

    public static class TimsachViewHolder{
        private TextView tvTenSachTim, tvTSoluongtrang;
    }
}
