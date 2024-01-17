package dongnvph30597.fpoly.ass_demo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import dongnvph30597.fpoly.ass_demo.Adapter.Adapter_PhieuMuon;
import dongnvph30597.fpoly.ass_demo.DAO.PhieuMuonDAO;
import dongnvph30597.fpoly.ass_demo.DAO.SachDAO;
import dongnvph30597.fpoly.ass_demo.DAO.ThanhVienDAO;
import dongnvph30597.fpoly.ass_demo.DAO.ThongKeDAO;
import dongnvph30597.fpoly.ass_demo.model.PhieuMuon;
import dongnvph30597.fpoly.ass_demo.model.Sach;
import dongnvph30597.fpoly.ass_demo.model.ThanhVien;

public class FragmentQlyPhieuMuon extends Fragment {

    private RecyclerView recyclerListPM;
    private FloatingActionButton floatAddPM;
    private PhieuMuonDAO phieuMuonDAO;
    private Adapter_PhieuMuon adapter;
    private ArrayList<PhieuMuon> arr = new ArrayList<>();

    private ArrayList<ThanhVien> arrTV = new ArrayList<>();
    private ThanhVienDAO thanhVienDAO;
    private ArrayList<Sach> arrSach = new ArrayList<>();
    private SachDAO sachDAO;
    private ArrayList<String> check = new ArrayList();

    private Spinner spnPMaddSach, spnPMaddTV;
    private EditText edPMaddNgaythue, edPMaddgiathue;
    private Button btnPMadd, btnPMcancleAdd;
    private String maSach, maTV;
    private Calendar myCalendar = Calendar.getInstance();

    private ImageView imghomepm;

    private EditText edTimkiemPM;
    private ImageView imgTimkiemPM;

    public FragmentQlyPhieuMuon() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_qly_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerListPM =view.findViewById(R.id.recyclerListPM);
        floatAddPM = view.findViewById(R.id.floatAddPM);

        edTimkiemPM = view.findViewById(R.id.edTimkiemPM);
        imgTimkiemPM = view.findViewById(R.id.imgTimKiemPM);
        imgTimkiemPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        imghomepm = view.findViewById(R.id.icon_menupm);
        imghomepm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        thanhVienDAO = new ThanhVienDAO(getContext());
        arrTV = thanhVienDAO.getAllTV();
        sachDAO = new SachDAO(getContext());
        arrSach = sachDAO.getAllSach();

        getAllPM();

        floatAddPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrSach.size() == 0 ){
                    check.add("Sách");
                }
                if(arrTV.size() == 0){
                    check.add("Thành viên");
                }
                if(check.isEmpty()){
                    Dialog dialog =  new Dialog(getContext());
                    dialog.setContentView(R.layout.layout_dialog_add_pm);
                    spnPMaddSach = dialog.findViewById(R.id.spn_PMAddsach);
                    spnPMaddTV = dialog.findViewById(R.id.spn_PMAddTV);
                    edPMaddNgaythue = dialog.findViewById(R.id.edAddpmNgaythue);
                    edPMaddgiathue = dialog.findViewById(R.id.edAddPMGiathue);
                    btnPMadd = dialog.findViewById(R.id.btnThemPM);
                    btnPMcancleAdd = dialog.findViewById(R.id.btncancleAddPM);

                    spnPMaddSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, Object> hashMap = (HashMap<String, Object>) spnPMaddSach.getSelectedItem();
                            maSach = String.valueOf(hashMap.get("maSach"));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spnPMaddTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, Object> hashMap = (HashMap<String, Object>) spnPMaddTV.getSelectedItem();
                            maTV = String.valueOf(hashMap.get("maTV"));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    edPMaddNgaythue.setFocusable(false);
                    edPMaddNgaythue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDatePickerDialog(edPMaddNgaythue);
                        }
                    });

                    btnPMadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String ngay = edPMaddNgaythue.getText().toString().trim();
                            String gia = edPMaddgiathue.getText().toString().trim();
                            if(ngay.isEmpty() || gia.isEmpty()){
                                Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                PhieuMuon pm = new PhieuMuon();
                                pm.setMaSach(Integer.parseInt(maSach));
                                pm.setMaTV(Integer.parseInt(maTV));

                                Intent intent = getActivity().getIntent();
                                String user = intent.getStringExtra("user");
                                pm.setMaTT(user);
                                pm.setNgay(java.sql.Date.valueOf(ngay));
                                pm.setTienThue(Integer.valueOf(gia));
                                phieuMuonDAO.insert(pm);
                                getAllPM();
                                Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });

                    laylistSachSpiner(spnPMaddSach);
                    laylistTVSpiner(spnPMaddTV);

                    dialog.show();

                }else {
                    Toast.makeText(getContext(), "Bạn chưa thêm: "+check, Toast.LENGTH_SHORT).show();
                    check.clear();
                }
            }
        });
    }


    public void getAllPM(){
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        arr = phieuMuonDAO.getAllPM();
        adapter = new Adapter_PhieuMuon(arr,getContext());
        adapter.setData(arr);
        recyclerListPM.setAdapter(adapter);
    }
    private void laylistSachSpiner(Spinner spnSach) {
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getAllSach();
        ArrayList<HashMap<String, Object>> listhm = new ArrayList<>();
        for (Sach sach : list){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maSach", sach.getMaSach());
            hashMap.put("tenSach", sach.getTenSach());
            listhm.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listhm,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1}
        );
        spnPMaddSach.setAdapter(simpleAdapter);
    }

    private void laylistTVSpiner(Spinner spnTV) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> list = thanhVienDAO.getAllTV();
        ArrayList<HashMap<String, Object>> listhm = new ArrayList<>();
        for (ThanhVien tv : list){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maTV", tv.getMaTV());
            hashMap.put("tenTV", tv.getHoTen());
            listhm.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listhm,
                android.R.layout.simple_list_item_1,
                new String[]{"tenTV"},
                new int[]{android.R.id.text1}
        );
        spnPMaddTV.setAdapter(simpleAdapter);
    }
    private void showDatePickerDialog(final EditText editText) {
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        myCalendar.set(Calendar.YEAR, i);
                        myCalendar.set(Calendar.MONTH, i1);
                        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                        Date selectedDate = myCalendar.getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String time = (dateFormat.format(selectedDate));
                        editText.setText(time);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}
