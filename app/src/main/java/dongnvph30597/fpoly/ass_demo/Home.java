package dongnvph30597.fpoly.ass_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.DAO.ThuThuDAO;
import dongnvph30597.fpoly.ass_demo.model.ThuThu;

public class Home extends AppCompatActivity {

//    private ImageView imgbtnLoaiSach, imgbtnSach, imgbtnTV, imgbtnPM;

    public static DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView tvuser,tvuserma;
    private View view;
    private ThuThuDAO thuThuDAO;
    private ArrayList<ThuThu> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new FragmentQlyPhieuMuon()).commit();
        navigationView.setItemIconTintList(null);

        view = navigationView.getHeaderView(0);
        tvuser = view.findViewById(R.id.tvuser);
        tvuserma = view.findViewById(R.id.tvuserma);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new FragmentQlyPhieuMuon()).commit();
                        drawerLayout.close();
                        break;

                    case R.id.ic_librarian:
                        if(user.equals("admin")){
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentThuthu()).commit();
                        drawerLayout.close();
                        break;
                        }else {
                            Toast.makeText(Home.this, "Xin lỗi! Bạn không có quyền quản lý người dùng!", Toast.LENGTH_SHORT).show();
                            break;
                        }

                    case R.id.ic_kind_of_book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new FragmentQlyLoaiSach()).commit();
                        drawerLayout.close();
                        break;

                    case R.id.ic_book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentQlySach()).commit();
                        drawerLayout.close();
                        break;

                    case R.id.ic_member:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentQlyTV()).commit();
                        drawerLayout.close();
                        break;

                    case R.id.ic_top_book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentTop()).commit();
                        drawerLayout.close();
                        break;

                    case R.id.ic_revenue:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentDoanhThu()).commit();
                        drawerLayout.close();
                        break;

                    case R.id.ic_exit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentDangXuat()).commit();
                        drawerLayout.close();
                        break;

//                    case R.id.ic_tim_sach:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentTimSach()).commit();
//                        drawerLayout.close();
//                        break;

                    case R.id.ic_change_pass:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentDoimatkhau()).commit();
                        drawerLayout.close();
                        break;
                }
                return true;
            }
        });

        thuThuDAO = new ThuThuDAO(Home.this);
        arr = thuThuDAO.getALLTT();
        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i).getMaTT().equals(user)){
                tvuser.setText("Xin chào "+ arr.get(i).getHoTen());
                tvuserma.setText(arr.get(i).getMaTT());
                return;
            }
        }

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát khỏi ứng dụng không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Không",null);
        builder.show();
    }
}