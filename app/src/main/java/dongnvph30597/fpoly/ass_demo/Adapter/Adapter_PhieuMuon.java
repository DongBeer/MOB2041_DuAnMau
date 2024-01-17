package dongnvph30597.fpoly.ass_demo.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import dongnvph30597.fpoly.ass_demo.DAO.PhieuMuonDAO;
import dongnvph30597.fpoly.ass_demo.DAO.SachDAO;
import dongnvph30597.fpoly.ass_demo.DAO.ThanhVienDAO;
import dongnvph30597.fpoly.ass_demo.R;
import dongnvph30597.fpoly.ass_demo.database.DB_ThuVien;
import dongnvph30597.fpoly.ass_demo.model.PhieuMuon;
import dongnvph30597.fpoly.ass_demo.model.Sach;
import dongnvph30597.fpoly.ass_demo.model.ThanhVien;

public class Adapter_PhieuMuon extends RecyclerView.Adapter<Adapter_PhieuMuon.MyPMViewHolder>{

    private ArrayList<PhieuMuon> arr = new ArrayList<>();
    private Context context;
    private DB_ThuVien dbThuVien;
    private PhieuMuonDAO phieuMuonDAO;
    private ThanhVienDAO thanhVienDAO;
    private SachDAO sachDAO;
    private ArrayList<Sach> arrSach = new ArrayList<>();
    private ArrayList<ThanhVien> arrTV = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private EditText edPMSuaNgaythue, edPMSuaGiathue;
    private Spinner spnPMSuaSach, spnPMSuaTV;
    private CheckBox ckbTrangthai;
    private Button btnSuaPM, btnXoaPM;
    private String maSach1, maTV1;


    private AdapterSachSpinner adapterSachSpinner;
    private AdapterTVSpinner adapterTVSpinner;

    private Calendar myCalendar = Calendar.getInstance();



    public Adapter_PhieuMuon(ArrayList<PhieuMuon> arr, Context context) {
        this.arr = arr;
        this.context = context;
        this.dbThuVien = new DB_ThuVien(context);
        phieuMuonDAO = new PhieuMuonDAO(context);
    }

    public void setData(ArrayList<PhieuMuon> arrPM){
        this.arr = arrPM;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyPMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_pm,parent,false);
        final MyPMViewHolder holder =  new MyPMViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                Dialog mDialog = new Dialog(context);
                mDialog.setContentView(R.layout.layout_dialog_suaxoa_pm);
                edPMSuaNgaythue = mDialog.findViewById(R.id.edPMSuaNGaythue);
                edPMSuaGiathue = mDialog.findViewById(R.id.edPMSuaGiathue);
                spnPMSuaSach = mDialog.findViewById(R.id.spn_PMSuaSach);
                spnPMSuaTV = mDialog.findViewById(R.id.spn_PMSuaTV);
                ckbTrangthai = mDialog.findViewById(R.id.ckbTrangthai);
                btnSuaPM = mDialog.findViewById(R.id.btnPMSua);
                btnXoaPM = mDialog.findViewById(R.id.btnPMXoa);

                edPMSuaNgaythue.setText(String.valueOf(arr.get(index).getNgay()));
                edPMSuaGiathue.setText(String.valueOf(arr.get(index).getTienThue()));


                if(arr.get(index).getTraSach()==1){
                    ckbTrangthai.setChecked(true);
                }else {
                    ckbTrangthai.setChecked(false);
                }
                arrSach = sachDAO.getAllSach();
                adapterSachSpinner = new AdapterSachSpinner(context, arrSach);
                spnPMSuaSach.setAdapter(adapterSachSpinner);

                arrTV = thanhVienDAO.getAllTV();
                adapterTVSpinner = new AdapterTVSpinner(context,arrTV);
                spnPMSuaTV.setAdapter(adapterTVSpinner);


                for (int i = 0; i < arrSach.size(); i++) {
                    if (arr.get(index).getMaSach() == arrSach.get(i).getMaSach()) {
                        spnPMSuaSach.setSelection(i);
                        break;
                    }
                }

                for (int i = 0; i < arrTV.size(); i++) {
                    if (arr.get(index).getMaTV() == arrTV.get(i).getMaTV()) {
                        spnPMSuaTV.setSelection(i);
                        break;
                    }
                }
                spnPMSuaSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maSach1 = String.valueOf(arrSach.get(position).getMaSach());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spnPMSuaTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maTV1 = String.valueOf(arrTV.get(position).getMaTV());


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                edPMSuaNgaythue.setFocusable(false);
                edPMSuaNgaythue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePicker(edPMSuaNgaythue);
                    }
                });

                btnSuaPM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String n = edPMSuaNgaythue.getText().toString().trim();
                        String d = edPMSuaGiathue.getText().toString().trim();
                        String tt = arr.get(index).getMaTT();

                        if(n.isEmpty() || d.isEmpty()){
                            Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else {
                            if(ckbTrangthai.isChecked()){
                                arr.get(index).setTraSach(1);
                            }else {
                                arr.get(index).setTraSach(0);
                            }
                            PhieuMuon pm = new PhieuMuon(arr.get(index).getMaPm(),tt,Integer.parseInt(maTV1),
                                    Integer.parseInt(maSach1), Date.valueOf(n),Integer.parseInt(d),arr.get(index).getTraSach());
                            phieuMuonDAO.update(pm);
                            arr.set(index,pm);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });

                btnXoaPM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Thông báo");
                        builder.setMessage("Bạn có muốn xóa không?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = arr.get(index).getMaPm();
                                arr.remove(index);
                                notifyItemRemoved(index);
                                phieuMuonDAO.delete(String.valueOf(id));
                                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Không",null);
                        builder.show();
                    }
                });

                mDialog.show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPMViewHolder holder, int position) {

        PhieuMuon pm = arr.get(position);
        holder.tvMaPM.setText(String.valueOf(pm.getMaPm()));

        thanhVienDAO = new ThanhVienDAO(context);
        ThanhVien tv = thanhVienDAO.getID(String.valueOf(pm.getMaTV()));
        holder.tvPMThanhVien.setText(tv.getHoTen());

        sachDAO = new SachDAO(context);
        Sach s = sachDAO.getID(String.valueOf(pm.getMaSach()));
        holder.tvPMTenSach.setText(s.getTenSach());
        holder.tvPMGiathue.setText(String.valueOf(pm.getTienThue()));
        holder.tvPMNgaythue.setText(sdf.format(pm.getNgay()));
        holder.tvPMmaTT.setText(pm.getMaTT());

        if(pm.getTraSach() == 1){
            holder.tvPMTrangthai.setText("Đã trả sách");
            holder.tvPMTrangthai.setTextColor(Color.parseColor("#23B52A"));
        }else {
            holder.tvPMTrangthai.setText("Chưa trả sách");
            holder.tvPMTrangthai.setTextColor(Color.parseColor("#FF5722"));
        }

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyPMViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaPM, tvPMThanhVien, tvPMTenSach, tvPMGiathue, tvPMNgaythue, tvPMmaTT, tvPMTrangthai;
        private ImageView imgPM;
        public MyPMViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPM = itemView.findViewById(R.id.tvMaPM);
            tvPMThanhVien = itemView.findViewById(R.id.tvPMTenTV);
            tvPMTenSach = itemView.findViewById(R.id.tvPMTenSach);
            tvPMGiathue = itemView.findViewById(R.id.tvPMGiathue);
            tvPMNgaythue = itemView.findViewById(R.id.tvPMNgaythue);
            tvPMTrangthai = itemView.findViewById(R.id.tvPMTrangThai);
            tvPMmaTT = itemView.findViewById(R.id.tvPMmaTT);
            imgPM = itemView.findViewById(R.id.imgPm);
        }
    }

    private void showDatePicker(final EditText editText) {
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        myCalendar.set(Calendar.YEAR, i);
                        myCalendar.set(Calendar.MONTH, i1);
                        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                        java.util.Date selectedDate = myCalendar.getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String time = (dateFormat.format(selectedDate));
                        editText.setText(time);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}
