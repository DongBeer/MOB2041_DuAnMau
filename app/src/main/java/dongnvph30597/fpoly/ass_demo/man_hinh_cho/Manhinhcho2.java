package dongnvph30597.fpoly.ass_demo.man_hinh_cho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import dongnvph30597.fpoly.ass_demo.MainActivity;
import dongnvph30597.fpoly.ass_demo.R;

public class Manhinhcho2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhcho2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        },2000);
    }
}