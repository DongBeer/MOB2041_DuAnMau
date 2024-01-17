package dongnvph30597.fpoly.ass_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import dongnvph30597.fpoly.ass_demo.DAO.ThuThuDAO;

public class MainActivity extends AppCompatActivity {

    private ImageView imglogo;
    private EditText edTendangnhap, edPass;
    private Button btnLogin, btnCancleLogin;
    private CheckBox ckbSavepass;

    private ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imglogo = findViewById(R.id.imglogo);
        edTendangnhap = findViewById(R.id.edTendangnhap);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btn_login);
        btnCancleLogin = findViewById(R.id.btn_cancel_login);
        ckbSavepass = findViewById(R.id.ckbSavepass);

        thuThuDAO = new ThuThuDAO(MainActivity.this);

        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edTendangnhap.setText(pref.getString("USERNAME",""));
        edPass.setText(pref.getString("PASSWORD",""));
        ckbSavepass.setChecked(pref.getBoolean("REMEMBER",false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkLogin();
            }
        });
    }
    public void checkLogin() {
        String strUser = edTendangnhap.getText().toString();
        String strPass = edPass.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
        if ((thuThuDAO.checkLogin(strUser, strPass) > 0)) {
                Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, ckbSavepass.isChecked());

                Intent intent = new Intent(MainActivity.this, Home.class);
                intent.putExtra("user",strUser);
                intent.putExtra("pass",strPass);
                startActivity(intent);
                finish();
        }else {
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
        }
    }

    public void rememberUser(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status){
            editor.clear();
        }else {
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }
}