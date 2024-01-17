package dongnvph30597.fpoly.ass_demo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.Adapter.Adapter_LoaiSach;
import dongnvph30597.fpoly.ass_demo.DAO.LoaiSachDAO;
import dongnvph30597.fpoly.ass_demo.model.LoaiSach;

public class FragmentQlyLoaiSach extends Fragment {

    private ListView lvLoaiSach;
    private FloatingActionButton flAddLoai;

    private LoaiSachDAO loaiSachDAO;
    private ArrayList<LoaiSach> arr = new ArrayList<>();
    private Adapter_LoaiSach adpter;

    private EditText edTenloaisach, edNhasx;
    private Button btnThemloaisach, btnHuythemloai;

    private EditText edTenloaisachSua, edNhasxSua;
    private Button btnSualoaisach, btnXoaloaisach;

    private ImageView imghomels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_qly_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvLoaiSach = view.findViewById(R.id.lvLoaiSach);
        flAddLoai = view.findViewById(R.id.floatAddLoai);
        imghomels = view.findViewById(R.id.icon_menuls);
        imghomels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        getAllLoaisach();

        flAddLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog =  new Dialog(getContext());
                dialog.setContentView(R.layout.layout_dialog_add_loaisach);
                edTenloaisach = dialog.findViewById(R.id.edTenloaisach);
                edNhasx = dialog.findViewById(R.id.edNhasx);
                btnThemloaisach = dialog.findViewById(R.id.btnThemloai);
                btnHuythemloai = dialog.findViewById(R.id.btncancleAddloai);

                btnThemloaisach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenloai = edTenloaisach.getText().toString().trim();
                        String nhasx = edNhasx.getText().toString().trim();
                        LoaiSach loaiSach = new LoaiSach();
                        loaiSach.setTenLoai(tenloai);
                        loaiSach.setNhaSX(nhasx);
                        loaiSachDAO.insert(loaiSach);
                        getAllLoaisach();
                        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                btnHuythemloai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        lvLoaiSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog aDialog = new Dialog(getContext());
                aDialog.setContentView(R.layout.layout_dialog_suaxoa_loaisach);

                int index = position;

                edTenloaisachSua = aDialog.findViewById(R.id.edTenloaisachSua);
                edNhasxSua = aDialog.findViewById(R.id.edNhasxSua);
                btnSualoaisach = aDialog.findViewById(R.id.btnSuaLoaisach);
                btnXoaloaisach = aDialog.findViewById(R.id.btnXoaloaisach);

                edTenloaisachSua.setText(arr.get(index).getTenLoai());
                edNhasxSua.setText(arr.get(index).getNhaSX());

                btnSualoaisach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = edTenloaisachSua.getText().toString().trim();
                        String n = edNhasxSua.getText().toString().trim();

                        if(t.isEmpty() || n.isEmpty()){
                            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            LoaiSach loaiSach = new LoaiSach();
                            loaiSach.setTenLoai(t);
                            loaiSach.setNhaSX(n);
                            loaiSachDAO.update(loaiSach);
                            adpter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            aDialog.dismiss();
                        }
                    }
                });



                btnXoaloaisach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa không?");
                        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               if(loaiSachDAO.delete(String.valueOf(arr.get(index).getMaLoai())) > 0){
                                   getAllLoaisach();
                                   Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                   aDialog.dismiss();
                               }else {
                                   Toast.makeText(getContext(), "Mã Sách còn tồn tại trong bảng sách!", Toast.LENGTH_SHORT).show();
                                   aDialog.dismiss();
                               }

                            }
                        });
                        builder.setPositiveButton("Không",null);
                        builder.show();
                    }
                });

                aDialog.show();
            }
        });
    }
    public void getAllLoaisach(){
        loaiSachDAO = new LoaiSachDAO(getContext());
        adpter = new Adapter_LoaiSach(getContext());
        arr = loaiSachDAO.getAllLoaiSach();
        adpter.setData(arr);
        lvLoaiSach.setAdapter(adpter);
    }
}
