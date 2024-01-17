package dongnvph30597.fpoly.ass_demo;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.Adapter.Adapter_Thuthu;
import dongnvph30597.fpoly.ass_demo.DAO.ThuThuDAO;
import dongnvph30597.fpoly.ass_demo.model.ThuThu;


public class FragmentThuthu extends Fragment {
    private RecyclerView recyclerTT;
    private Adapter_Thuthu adapter;
    private ArrayList<ThuThu> arr = new ArrayList<>();
    private ImageView imgmenuTT;
    private FloatingActionButton addTT;
    private ThuThuDAO thuThuDAO;

    private EditText edAddmaTT, edAddtenTT, edAddmk;
    private Button btnThemTT, btnCancleTT;

    public FragmentThuthu() {
        // Required empty public constructor
    }


    public static FragmentThuthu newInstance(String param1, String param2) {
        FragmentThuthu fragment = new FragmentThuthu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thuthu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerTT = view.findViewById(R.id.recyclerThuthu);
        imgmenuTT = view.findViewById(R.id.icon_menuTT);

        loadData();
        imgmenuTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        addTT = view.findViewById(R.id.floatAddTT);
        addTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog mDialog = new Dialog(getContext());
                mDialog.setContentView(R.layout.layout_dialog_add_thuthu);
                edAddmaTT = mDialog.findViewById(R.id.edAddmaTT);
                edAddtenTT = mDialog.findViewById(R.id.edAddtenTT);
                edAddmk = mDialog.findViewById(R.id.edAddMk);
                btnThemTT = mDialog.findViewById(R.id.btnThemTT);
                btnCancleTT = mDialog.findViewById(R.id.btncancleAddTT);

                btnThemTT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String matt = edAddmaTT.getText().toString().trim();
                        String ten = edAddtenTT.getText().toString().trim();
                        String mk = edAddmk.getText().toString().trim();
                        if(matt.isEmpty() || ten.isEmpty() || mk.isEmpty()){
                            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        arr = thuThuDAO.getALLTT();
                        for (int i = 0; i < arr.size(); i++) {
                            if (arr.get(i).getMaTT().equals(matt)) {
                                Toast.makeText(getContext(), "Đã tồn tại mã thủ thư: " + matt, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                                ThuThu tt = new ThuThu();
                                tt.setMaTT(matt);
                                tt.setHoTen(ten);
                                tt.setMatKhau(mk);

                                if(thuThuDAO.insert(tt) > 0){
                                    Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                    loadData();
                                    mDialog.dismiss();
                                }else {
                                    Toast.makeText(getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                                    mDialog.dismiss();
                            }
                    }
                });
                btnCancleTT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });
    }

    public void loadData(){
        thuThuDAO =  new ThuThuDAO(getContext());
        arr = thuThuDAO.getALLTT();
        adapter = new Adapter_Thuthu(getContext());
        adapter.setData(arr);
        recyclerTT.setAdapter(adapter);
    }
}