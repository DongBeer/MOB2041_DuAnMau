package dongnvph30597.fpoly.ass_demo.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.DAO.ThuThuDAO;
import dongnvph30597.fpoly.ass_demo.R;
import dongnvph30597.fpoly.ass_demo.model.ThuThu;

public class Adapter_Thuthu extends RecyclerView.Adapter<Adapter_Thuthu.MyTTViewHolder>{
    private Context context;
    private ArrayList<ThuThu> arr = new ArrayList<>();

    private EditText edSuamaTT, edSuatenTT, edSuamkTT;
    private Button btnSuaTT, btnXoaTT;

    private ThuThuDAO thuThuDAO;

    public Adapter_Thuthu(Context context) {
        this.context = context;
        thuThuDAO = new ThuThuDAO(context);
    }

    public void setData(ArrayList<ThuThu> arrTT){
        arr = arrTT;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyTTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_thuthu,parent,false);
        final MyTTViewHolder holder = new MyTTViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                if(index == 0){
                    Toast.makeText(context, "Không thể sửa ADMIN", Toast.LENGTH_SHORT).show();
                }else {
                    Dialog kDialog = new Dialog(context);
                    kDialog.setContentView(R.layout.layout_dialog_suaxoa_tt);
                    edSuamaTT = kDialog.findViewById(R.id.edSuamaTT);
                    edSuatenTT = kDialog.findViewById(R.id.edSuatenTT);
                    edSuamkTT = kDialog.findViewById(R.id.edSuamkTT);
                    btnSuaTT = kDialog.findViewById(R.id.btnSuaTT);
                    btnXoaTT = kDialog.findViewById(R.id.btnXoaTT);
                    edSuamaTT.setFocusable(false);
                    edSuamaTT.setText(arr.get(index).getMaTT());
                    edSuatenTT.setText(arr.get(index).getHoTen());
                    edSuamkTT.setText(arr.get(index).getMatKhau());

                    btnSuaTT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String ma = edSuamaTT.getText().toString().trim();
                            String t = edSuatenTT.getText().toString().trim();
                            String mk = edSuamkTT.getText().toString().trim();

                            if (ma.isEmpty() || t.isEmpty() || mk.isEmpty()){
                                Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            ThuThu tt = new ThuThu(ma,t,mk);
                           if(thuThuDAO.update(tt) > 0) {
                               arr = thuThuDAO.getALLTT();
                               notifyDataSetChanged();
                               Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                               kDialog.dismiss();
                           }
                        }
                    });
                    btnXoaTT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Bạn có muốn xóa không?");
                            builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String id = arr.get(index).getMaTT();
                                    if(thuThuDAO.delete(id) > 0){
                                        arr = thuThuDAO.getALLTT();
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                        kDialog.dismiss();
                                    }else {
                                        Toast.makeText(context, "Mã thủ thư còn tồn tại trong phiếu mượn!", Toast.LENGTH_SHORT).show();
                                        kDialog.dismiss();
                                    }
                                }
                            });
                            builder.setPositiveButton("Không",null);

                            builder.show();
                        }
                    });

                    kDialog.show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTTViewHolder holder, int position) {
        holder.imgTT.setImageResource(R.drawable.ic_librarian);
        holder.tvmaTT.setText(arr.get(position).getMaTT());
        holder.tvtenTT.setText(arr.get(position).getHoTen());
        holder.tvmk.setText(arr.get(position).getMatKhau());
    }

    @Override
    public int getItemCount() {

        return arr.size();
    }

    public class MyTTViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTT;
        private TextView tvmaTT, tvtenTT, tvmk;
        public MyTTViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTT = itemView.findViewById(R.id.imgTT);
            tvmaTT = itemView.findViewById(R.id.tvmaTT);
            tvtenTT = itemView.findViewById(R.id.tvtenTT);
            tvmk = itemView.findViewById(R.id.tvmk);
        }

    }
}
