package dongnvph30597.fpoly.ass_demo.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.DAO.SachDAO;
import dongnvph30597.fpoly.ass_demo.DAO.ThanhVienDAO;
import dongnvph30597.fpoly.ass_demo.R;
import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.Sach;
import dongnvph30597.fpoly.ass_demo.model.ThanhVien;

public class Adapter_ThanhVien extends RecyclerView.Adapter<Adapter_ThanhVien.MyTvViewHolder> {

    private ArrayList<ThanhVien> arr = new ArrayList<>();
    private Context context;
    private DB_ThuVien dbThuVien;
    private ThanhVienDAO thanhVienDAO;

    private EditText edTenTVsua, edNamSinhsua, edMaTVsua;
    private Button btnSuaTV, btnXoaTV;

    public Adapter_ThanhVien(ArrayList<ThanhVien> arr, Context context) {
        this.arr = arr;
        this.context = context;
        this.dbThuVien = new DB_ThuVien(context);
        this.thanhVienDAO = new ThanhVienDAO(context);
    }

    public void setData(ArrayList<ThanhVien> arrTV){
        this.arr = arrTV;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tv,parent,false);
        final MyTvViewHolder holder = new MyTvViewHolder(view);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = holder.getAdapterPosition();
                Dialog gDialog = new Dialog(context);
                gDialog.setContentView(R.layout.layout_dialog_suaxoa_tv);
                edTenTVsua = gDialog.findViewById(R.id.edTenTVSua);
                edNamSinhsua = gDialog.findViewById(R.id.edNamSinhSua);
                edMaTVsua = gDialog.findViewById(R.id.edMaTVsua);
                btnSuaTV = gDialog.findViewById(R.id.btnSuaTV);
                btnXoaTV = gDialog.findViewById(R.id.btnXoaTV);

                edTenTVsua.setText(arr.get(index).getHoTen());
                edNamSinhsua.setText(arr.get(index).getNamSinh());
                edMaTVsua.setText(String.valueOf(arr.get(index).getMaTV()));

                btnSuaTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = edTenTVsua.getText().toString().trim();
                        String y = edNamSinhsua.getText().toString().trim();

                        if(t.isEmpty() || y.isEmpty()){
                            Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {

                            if(y.length() != 4){
                                Toast.makeText(context, "Nhập sai định dạng năm!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            ThanhVien tv = new ThanhVien(arr.get(index).getMaTV(),t,y,arr.get(index).getSoTK());
                            thanhVienDAO.update(tv);
                            arr = thanhVienDAO.getAllTV();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            gDialog.dismiss();
                        }
                    }
                });

                btnXoaTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa không?");
                        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = arr.get(index).getMaTV();

                                if(thanhVienDAO.delete(String.valueOf(id)) > 0){
                                    arr = thanhVienDAO.getAllTV();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                    gDialog.dismiss();
                                }else {
                                    Toast.makeText(context, "Mã thành viên còn tồn tại trong phiếu mượn!", Toast.LENGTH_SHORT).show();
                                    gDialog.dismiss();
                                }

                            }
                        });
                        builder.setPositiveButton("Không",null);

                        builder.show();
                    }
                });

                gDialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTvViewHolder holder, int position) {
        holder.imgTV.setImageResource(R.drawable.icon_member);
        holder.tvTenTV.setText(arr.get(position).getHoTen());
        holder.tvNamSinh.setText(arr.get(position).getNamSinh());
        holder.tvsoTK.setText(String.valueOf(arr.get(position).getSoTK()));

        if (Integer.parseInt(holder.tvsoTK.getText().toString()) %5 == 0){
            holder.tvsoTK.setTypeface(null, Typeface.BOLD);
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyTvViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTV;
        private TextView tvTenTV, tvNamSinh, tvsoTK;
        public MyTvViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTV = itemView.findViewById(R.id.imgTV);
            tvTenTV = itemView.findViewById(R.id.tvTenTV);
            tvNamSinh = itemView.findViewById(R.id.tvNamSinh);
            tvsoTK = itemView.findViewById(R.id.tvsoTK);
        }
    }
}
