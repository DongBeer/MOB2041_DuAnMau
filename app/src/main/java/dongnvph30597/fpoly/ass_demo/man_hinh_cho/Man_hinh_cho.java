package dongnvph30597.fpoly.ass_demo.man_hinh_cho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.DAO.ThuThuDAO;
import dongnvph30597.fpoly.ass_demo.R;
import dongnvph30597.fpoly.ass_demo.model.ThuThu;

public class Man_hinh_cho extends AppCompatActivity {

    private ThuThuDAO thuThuDAO;
    private ArrayList<ThuThu> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cho);

        thuThuDAO = new ThuThuDAO(Man_hinh_cho.this);
        arr = thuThuDAO.getALLTT();
        if(arr.size() == 0){
            thuThuDAO.insertadmin();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Manhinhcho2.class));
                finish();
            }
        },3000);


    }
}