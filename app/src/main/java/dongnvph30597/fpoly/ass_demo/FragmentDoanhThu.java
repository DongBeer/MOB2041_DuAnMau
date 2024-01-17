package dongnvph30597.fpoly.ass_demo;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dongnvph30597.fpoly.ass_demo.DAO.ThongKeDAO;


public class FragmentDoanhThu extends Fragment {
    private ImageView imgTungay, imgDenngay, imgHomeDT;
    private EditText edTungay, edDenngay;
    private Button btnDoanhthu;
    private TextView tvDoanhThu;
    private Calendar myCalendar = Calendar.getInstance();
    private ThongKeDAO thongKeDAO;
    private boolean checkk = false;

    public FragmentDoanhThu() {
        // Required empty public constructor
    }


    public static FragmentDoanhThu newInstance(String param1, String param2) {
        FragmentDoanhThu fragment = new FragmentDoanhThu();
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
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgTungay = view.findViewById(R.id.imgTungay);
        imgDenngay = view.findViewById(R.id.imgDenngay);
        edTungay = view.findViewById(R.id.edDTTungay);
        edDenngay = view.findViewById(R.id.edDTDenngay);
        btnDoanhthu = view.findViewById(R.id.btnDoanhthu);
        tvDoanhThu = view.findViewById(R.id.tvDoanhthu);
        imgHomeDT = view.findViewById(R.id.iconhomeDT);
        edTungay.setFocusable(false);
        edDenngay.setFocusable(false);

        imgHomeDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        imgTungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogDT(edTungay);
            }
        });

        imgDenngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogDT(edDenngay);
            }
        });

        btnDoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tungay = edTungay.getText().toString();
                String denngay = edDenngay.getText().toString();
                if(tungay.isEmpty() || denngay.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ từ ngày và đến ngày!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String[] temptungay = tungay.split("-");
                    String[] tempdenngay = denngay.split("-");

                    String newTungay = "";
                    String newdenngay = "";

                    int inttungay = Integer.parseInt(newTungay.concat(temptungay[0]).concat(temptungay[1]).concat(temptungay[2]));
                    int intdenngay = Integer.parseInt(newdenngay.concat(tempdenngay[0]).concat(tempdenngay[1]).concat(tempdenngay[2]));

                    if(inttungay > intdenngay){
                        Toast.makeText(getContext(), "Lỗi! Từ ngày phải bé hơn đến ngày", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    thongKeDAO = new ThongKeDAO(getContext());
                    tvDoanhThu.setText("Doanh thu: "+thongKeDAO.getDoanhThu(tungay,denngay) + " VNĐ");
                }
            }
        });
    }
    private void showDatePickerDialogDT(final EditText editText) {
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
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String time = (dateFormat.format(selectedDate));
                        editText.setText(time);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}