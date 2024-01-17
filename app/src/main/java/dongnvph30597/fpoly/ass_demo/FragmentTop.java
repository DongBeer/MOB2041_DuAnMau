package dongnvph30597.fpoly.ass_demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import dongnvph30597.fpoly.ass_demo.Adapter.Adapter_Top;
import dongnvph30597.fpoly.ass_demo.DAO.ThongKeDAO;
import dongnvph30597.fpoly.ass_demo.model.Top;


public class FragmentTop extends Fragment {

    private ListView lvTop;
    private Adapter_Top adapter;
    private ThongKeDAO thongKeDAO;
    private ArrayList<Top> arr = new ArrayList<>();
    private ImageView imgmenuTop;

    public FragmentTop() {
        // Required empty public constructor
    }


    public static FragmentTop newInstance(String param1, String param2) {
        FragmentTop fragment = new FragmentTop();
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
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvTop = view.findViewById(R.id.top_listview);
        imgmenuTop = view.findViewById(R.id.iconhomeTop);
        imgmenuTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        thongKeDAO = new ThongKeDAO(getContext());
        arr = thongKeDAO.getTop();
        adapter = new Adapter_Top(getContext());
        adapter.setDate(arr);
        lvTop.setAdapter(adapter);
    }
}