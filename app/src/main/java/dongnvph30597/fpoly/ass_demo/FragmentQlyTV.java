package dongnvph30597.fpoly.ass_demo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.Adapter.Adapter_ThanhVien;
import dongnvph30597.fpoly.ass_demo.DAO.ThanhVienDAO;
import dongnvph30597.fpoly.ass_demo.DAO.ThongKeDAO;
import dongnvph30597.fpoly.ass_demo.model.ThanhVien;

public class FragmentQlyTV extends Fragment {

    private RecyclerView recyclerTV;
    private FloatingActionButton floatAddTV;

    private EditText edTenTV, edNamSinh, edsoTKTV;
    private Button btnThemTV, btnCancleThemTV;

    private ThanhVienDAO thanhVienDAO;
    private Adapter_ThanhVien adapter;
    private ArrayList<ThanhVien> arr = new ArrayList<>();
    private ImageView imgmenuTV;

    private EditText edTimkiemTV;
    private ImageView imgTimkiemTV;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_qly_thanh_vien,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerTV = view.findViewById(R.id.recyclerListTV);
        floatAddTV = view.findViewById(R.id.floatAddTV);

        edTimkiemTV = view.findViewById(R.id.edTimkiemTV);
        imgTimkiemTV = view.findViewById(R.id.imgTimkiemTV);
        imgTimkiemTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nam = edTimkiemTV.getText().toString().trim();
            }
        });

        imgmenuTV = view.findViewById(R.id.icon_menuTV);
        imgmenuTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        floatAddTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_dialog_add_tv);
                edTenTV = dialog.findViewById(R.id.edTenTV);
                edNamSinh = dialog.findViewById(R.id.edNamSinh);
                edsoTKTV = dialog.findViewById(R.id.edsoTKTV);
                btnThemTV = dialog.findViewById(R.id.btnThemTV);
                btnCancleThemTV = dialog.findViewById(R.id.btncancleAddTV);



                btnThemTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = edTenTV.getText().toString().trim();
                        String year = edNamSinh.getText().toString().trim();
                        String t = edsoTKTV.getText().toString().trim();

                        String check = "\\d+";
                        if(ten.isEmpty() || year.isEmpty() || t.isEmpty()){
                            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            if(year.length() != 4){
                                Toast.makeText(getContext(), "Nhập sai định dạng năm!", Toast.LENGTH_SHORT).show();
                                return;

                            }

                            if(!t.matches(check)){
                                Toast.makeText(getContext(), "Nhập sai định dạng số!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ThanhVien thanhVien = new ThanhVien();
                            thanhVien.setHoTen(ten);
                            thanhVien.setNamSinh(year);
                            thanhVien.setSoTK(Integer.parseInt(t));
                            thanhVienDAO.insert(thanhVien);
                            fillListTV();
                            Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });

                btnCancleThemTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });

        fillListTV();
    }

    public void fillListTV(){
        thanhVienDAO = new ThanhVienDAO(getContext());
        arr = thanhVienDAO.getAllTV();
        adapter = new Adapter_ThanhVien(arr,getContext());
        adapter.setData(arr);
        recyclerTV.setAdapter(adapter);
    }
}
