package dongnvph30597.fpoly.ass_demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.Adapter.Adapter_TimSach;
import dongnvph30597.fpoly.ass_demo.DAO.ThongKeDAO;
import dongnvph30597.fpoly.ass_demo.model.TimSach;


public class FragmentTimSach extends Fragment {

    private ListView lvtimsach;
    private Adapter_TimSach adpater;
    private ThongKeDAO thongKeDAO;
    private ArrayList<TimSach> arr = new ArrayList<>();

    private ImageView imgHometim;
    private EditText edtimsl;
    private Button btnTimsl;

    public FragmentTimSach() {
        // Required empty public constructor
    }


    public static FragmentTimSach newInstance(String param1, String param2) {
        FragmentTimSach fragment = new FragmentTimSach();
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
        return inflater.inflate(R.layout.fragment_tim_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgHometim = view.findViewById(R.id.iconhomeTimSach);
        edtimsl = view.findViewById(R.id.edTimSl);
        btnTimsl = view.findViewById(R.id.btnTim);
        lvtimsach = view.findViewById(R.id.timsach_listview);

        imgHometim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        btnTimsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sl = edtimsl.getText().toString().trim();
                if (sl.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập số lượng trang!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(sl) < 1){
                    Toast.makeText(getContext(), "Số lượng trang sách phải > 0", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    thongKeDAO = new ThongKeDAO(getContext());
//                    arr = thongKeDAO.getSach(Integer.parseInt(sl));
                    adpater = new Adapter_TimSach(getContext());
                    adpater.setData(arr);
                    lvtimsach.setAdapter(adpater);
                }
            }
        });

    }
}