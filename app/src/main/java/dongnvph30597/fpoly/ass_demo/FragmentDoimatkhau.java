package dongnvph30597.fpoly.ass_demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import dongnvph30597.fpoly.ass_demo.DAO.ThuThuDAO;
import dongnvph30597.fpoly.ass_demo.model.ThuThu;


public class FragmentDoimatkhau extends Fragment {

    private ImageView imghomedoimk;
    private EditText edmkcu, edmkmoi, edmkmoinhaplai;
    private Button btndoimk, btnhuydoimk;

    private ThuThuDAO thuThuDAO;

    public FragmentDoimatkhau() {
        // Required empty public constructor
    }

    public static FragmentDoimatkhau newInstance(String param1, String param2) {
        FragmentDoimatkhau fragment = new FragmentDoimatkhau();
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
        return inflater.inflate(R.layout.fragment_doimatkhau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imghomedoimk = view.findViewById(R.id.iconhomedoimk);
        imghomedoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        edmkcu = view.findViewById(R.id.edMkcu);
        edmkmoi = view.findViewById(R.id.edMkmoi);
        edmkmoinhaplai = view.findViewById(R.id.edMkmoinhaplai);
        btndoimk = view.findViewById(R.id.btnDoimk);
        btnhuydoimk = view.findViewById(R.id.btnHuydoiml);

        btnhuydoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edmkmoi.setText("");
                edmkcu.setText("");
                edmkmoinhaplai.setText("");
            }
        });

        thuThuDAO = new ThuThuDAO(getContext());

        btndoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mkcu = edmkcu.getText().toString().trim();
                String mkmoi = edmkmoi.getText().toString().trim();
                String mkmoi2 = edmkmoinhaplai.getText().toString().trim();

                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME","");
                String oldPasscheck = pref.getString("PASSWORD","");

                if (user.length()==0){
                    Intent intent1 = getActivity().getIntent();
                    String user1 = intent1.getStringExtra("user");
                    user = user1;
                }
                if (oldPasscheck.length()==0){
                    Intent intent = getActivity().getIntent();
                    String oldpass = intent.getStringExtra("pass");
                    oldPasscheck = oldpass;
                }

                if(mkcu.isEmpty() || mkmoi.isEmpty() || mkmoi2.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng không để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!oldPasscheck.equals(mkcu)){
                    Toast.makeText(getContext(), "Nhập sai mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!mkmoi.equals(mkmoi2)){
                    Toast.makeText(getContext(), "Mật khẩu mới không trùng khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ThuThu thuThu = thuThuDAO.getID(user);
                thuThu.setMatKhau(mkmoi);
                if(thuThuDAO.update(thuThu) > 0){
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    edmkcu.setText("");
                    edmkmoi.setText("");
                    edmkmoinhaplai.setText("");
                    Intent mIntent = new Intent(getContext(),MainActivity.class);
                    startActivity(mIntent);
                }else {
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}