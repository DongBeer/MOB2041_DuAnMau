package dongnvph30597.fpoly.ass_demo.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import dongnvph30597.fpoly.ass_demo.DAO.LoaiSachDAO;
import dongnvph30597.fpoly.ass_demo.DAO.SachDAO;
import dongnvph30597.fpoly.ass_demo.R;
import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.LoaiSach;
import dongnvph30597.fpoly.ass_demo.model.Sach;

public class Adapter_Sach extends RecyclerView.Adapter<Adapter_Sach.MyItemViewHolder>{

    private ArrayList<Sach> arr = new ArrayList<>();
    private Context context;
    private DB_ThuVien dbThuVien;
    private SachDAO sachDAO;

    private EditText edTenSachSua, edGiathueSua, edMaloaisachsua;
    private Spinner spnSuasach;
    private Button btnSuasach, btnXoasach;
    private String idLoai;

    public Adapter_Sach(ArrayList<Sach> arr, Context context) {
        this.arr = arr;
        this.context = context;
        this.dbThuVien = new DB_ThuVien(context.getApplicationContext());
        this.sachDAO = new SachDAO(context);
    }
    public void setData(ArrayList<Sach> arrSach){
        this.arr = arrSach;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sach,parent,false);
        final MyItemViewHolder holder = new MyItemViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = holder.getAdapterPosition();
                Dialog bDialog = new Dialog(context);
                bDialog.setContentView(R.layout.layout_dialog_suaxoa_sach);
                edTenSachSua = bDialog.findViewById(R.id.edTensachSua);
                edGiathueSua = bDialog.findViewById(R.id.edGiathuesachSua);
                spnSuasach = bDialog.findViewById(R.id.spn_loaisachSua);
                btnSuasach = bDialog.findViewById(R.id.btnSuasach);
                btnXoasach = bDialog.findViewById(R.id.btnXoasach);
                edMaloaisachsua = bDialog.findViewById(R.id.edMaloaisachsua);

                edTenSachSua.setText(arr.get(index).getTenSach());
                edGiathueSua.setText(String.valueOf(arr.get(index).getGiaThue()));
                edMaloaisachsua.setText(String.valueOf(arr.get(index).getMaLoai()));

                spnSuasach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) spnSuasach.getSelectedItem();
                        idLoai = String.valueOf(hashMap.get("idLoaii"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnSuasach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = edTenSachSua.getText().toString().trim();
                        String gia = edGiathueSua.getText().toString().trim();


                        if(t.isEmpty() || gia.isEmpty()){
                            Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            Sach sach = new Sach(arr.get(index).getMaSach(),t,Integer.parseInt(gia),Integer.parseInt(idLoai),arr.get(index).getSoluong());
                            sachDAO.update(sach);
                            arr.set(index, sach);
                            notifyDataSetChanged();
                            Toast.makeText(context.getApplicationContext(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            bDialog.dismiss();
                        }

                    }
                });

                btnXoasach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa không?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = arr.get(index).getMaSach();

                                if(sachDAO.delete(String.valueOf(id)) > 0) {
                                    arr = sachDAO.getAllSach();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Mã sách còn tồn tại trong phiếu mượn!", Toast.LENGTH_SHORT).show();
                                }
                                bDialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Không",null);
                        builder.show();
                    }
                });

                layDSSpinerr(spnSuasach);
                bDialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, int position) {
        holder.tvTenSach.setText(arr.get(position).getTenSach());
        holder.tvGiathuesach.setText(String.valueOf(arr.get(position).getGiaThue()));
        holder.imgSach.setImageResource(R.drawable.book_ic);
        holder.tvsoluong.setText(String.valueOf(arr.get(position).getSoluong()));
        holder.tvsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int indix = holder.getAdapterPosition();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_dialog_sltrangsach);
                EditText edTensachsl = dialog.findViewById(R.id.edSLtensach);
                EditText edsl = dialog.findViewById(R.id.edsoluongSua);
                Button btnSuasl = dialog.findViewById(R.id.btnSl);
                Button btnHuysl = dialog.findViewById(R.id.btnhuysl);

                edTensachsl.setText(arr.get(indix).getTenSach());
                edsl.setText(String.valueOf(arr.get(indix).getSoluong()));

                btnSuasl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String ten = edTensachsl.getText().toString();
                        int masach = arr.get(indix).getMaSach();
                        int gia = arr.get(indix).getGiaThue();
                        int ml = arr.get(indix).getMaLoai();
                        String sol = edsl.getText().toString();


                        if(ten.isEmpty() || String.valueOf(gia).isEmpty()){
                            Toast.makeText(context, "Nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            Sach ss = new Sach(masach,ten,gia,ml,Integer.parseInt(sol));
                            sachDAO.update(ss);
                            arr = sachDAO.getAllSach();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

                btnHuysl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSach;
        private TextView tvTenSach, tvGiathuesach, tvLoaisach, tvsoluong;
        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSach = itemView.findViewById(R.id.imgSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGiathuesach = itemView.findViewById(R.id.tvGiathue);
            tvsoluong = itemView.findViewById(R.id.tvsoluongit);
        }
    }

    private void layDSSpinerr(Spinner spnLoai) {
        LoaiSachDAO loaiDAO = new LoaiSachDAO(context);
        ArrayList<LoaiSach> list = loaiDAO.getAllLoaiSach();
        ArrayList<HashMap<String, Object>> listhm = new ArrayList<>();
        for (LoaiSach loai : list){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("idLoaii", loai.getMaLoai());
            hashMap.put("tenLoaii", loai.getTenLoai());
            listhm.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listhm,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoaii"},
                new int[]{android.R.id.text1}
        );
        spnLoai.setAdapter(simpleAdapter);
    }
}
