package dongnvph30597.fpoly.ass_demo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;

import dongnvph30597.fpoly.ass_demo.Adapter.Adapter_Sach;
import dongnvph30597.fpoly.ass_demo.DAO.LoaiSachDAO;
import dongnvph30597.fpoly.ass_demo.DAO.SachDAO;
import dongnvph30597.fpoly.ass_demo.DAO.ThongKeDAO;
import dongnvph30597.fpoly.ass_demo.model.LoaiSach;
import dongnvph30597.fpoly.ass_demo.model.Sach;

public class FragmentQlySach extends Fragment {

    private EditText edTenSach, edGiathue, edsoluong;
    private Spinner spnLoaisach;
    private Button btnThemSach, btnCancleAddSach;
    private String idLoai;

    private RecyclerView recyclerListSach;
    private FloatingActionButton floatAddSach;

    private Adapter_Sach adapterSach;
    private SachDAO sachDAO;
    private ArrayList<Sach> arr = new ArrayList<>();

    private LoaiSachDAO loaiSachDAO;
    private ArrayList<LoaiSach> arrLoaiSach = new ArrayList<>();

    private ImageView imghomeSach;

    private EditText edTimkiemSach;
    private ImageView imgTimkiemSach;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_qly_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerListSach = view.findViewById(R.id.recyclerListbook);
        floatAddSach = view.findViewById(R.id.floatAddSach);

        edTimkiemSach = view.findViewById(R.id.edTimkiemSach);
        imgTimkiemSach = view.findViewById(R.id.imgTimKiemSach);
        imgTimkiemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String slsach = edTimkiemSach.getText().toString().trim();
                String check = "\\d+";
                if(slsach.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập số lượng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!slsach.matches(check)){
                    Toast.makeText(getContext(), "Nhập sai định dạng số!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SachDAO sachDAO = new SachDAO(getContext());
                arr = sachDAO.timkiemSach(Integer.parseInt(slsach));
                adapterSach = new Adapter_Sach(arr,getContext());
                adapterSach.setData(arr);
                recyclerListSach.setAdapter(adapterSach);
            }
        });

        imghomeSach = view.findViewById(R.id.icon_menuSach);
        imghomeSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        fillToRecycel();


        floatAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSachDAO = new LoaiSachDAO(getContext());
                arrLoaiSach = loaiSachDAO.getAllLoaiSach();
                if(arrLoaiSach.size() == 0){
                    Toast.makeText(getContext(), "Bạn chưa thêm 'Loại sách' ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog kDialog = new Dialog(getContext());
                kDialog.setContentView(R.layout.layout_dialog_add_sach);
                edTenSach = kDialog.findViewById(R.id.edTensach);
                edGiathue = kDialog.findViewById(R.id.edGiathue);
                spnLoaisach = kDialog.findViewById(R.id.spn_loaisach);
                btnThemSach = kDialog.findViewById(R.id.btnThemSach);
                btnCancleAddSach = kDialog.findViewById(R.id.btncancleAddSach);
                edsoluong = kDialog.findViewById(R.id.edsoluong);
                spnLoaisach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) spnLoaisach.getSelectedItem();
                        idLoai = String.valueOf(hashMap.get("idLoai"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnThemSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = edTenSach.getText().toString().trim();
                        String gia = edGiathue.getText().toString().trim();
                        String sl = edsoluong.getText().toString().trim();
                        Sach sach =  new Sach();
                        sach.setTenSach(ten);
                        sach.setGiaThue(Integer.parseInt(gia));
                        sach.setMaLoai(Integer.parseInt(idLoai));
                        sach.setSoluong(Integer.parseInt(sl));

                        sachDAO.insert(sach);
                        fillToRecycel();
                        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        kDialog.dismiss();
                    }
                });
                btnCancleAddSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kDialog.dismiss();
                    }
                });


                layDSSpiner(spnLoaisach);
                kDialog.show();
            }
        });
    }

    public void fillToRecycel(){
        sachDAO = new SachDAO(getContext());
        arr = sachDAO.getAllSach();
        adapterSach = new Adapter_Sach(arr,getContext());
        adapterSach.setData(arr);
        recyclerListSach.setAdapter(adapterSach);
    }
    private void layDSSpiner(Spinner spnLoai) {
        LoaiSachDAO loaiDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiDAO.getAllLoaiSach();
        ArrayList<HashMap<String, Object>> listhm = new ArrayList<>();
        for (LoaiSach loai : list){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("idLoai", loai.getMaLoai());
            hashMap.put("tenLoai", loai.getTenLoai());
            listhm.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listhm,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnLoai.setAdapter(simpleAdapter);
    }
}
