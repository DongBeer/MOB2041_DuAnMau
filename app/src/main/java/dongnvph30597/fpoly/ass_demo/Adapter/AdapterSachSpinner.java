package dongnvph30597.fpoly.ass_demo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.R;
import dongnvph30597.fpoly.ass_demo.model.Sach;

public class AdapterSachSpinner extends ArrayAdapter<Sach> {
    private Context context;
    private ArrayList<Sach> objects;
    TextView tvspnma,tvspnnsx;
    public AdapterSachSpinner(@NonNull Context context, ArrayList<Sach> objects) {
        super(context, 0,objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.layout_spiner_sach,null);

        }
        final Sach obj = objects.get(position);
        if (obj != null){
            tvspnma = holder.findViewById(R.id.item_spn_thanhvien_ma);
            tvspnma.setText(String.valueOf(obj.maSach));
            tvspnnsx = holder.findViewById(R.id.item_spn_thanhvien_nsx);
            tvspnnsx.setText(obj.tenSach);
        }
        return holder;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.layout_spiner_sach,null);

        }
        final Sach obj = objects.get(position);
        if (obj != null){
            tvspnma = holder.findViewById(R.id.item_spn_thanhvien_ma);
            tvspnma.setText(String.valueOf(obj.maSach));
            tvspnnsx = holder.findViewById(R.id.item_spn_thanhvien_nsx);
            tvspnnsx.setText(obj.tenSach);
        }
        return holder;
    }
}
