package com.example.rujeet.myresturant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Starter_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Starter_Model> starterList ;
//    Starter_Recyclerview_Adapter starter_recyclerview_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starter,container,false);

        recyclerView = view.findViewById(R.id.recyclerview);
//        starter_recyclerview_adapter = new Starter_Recyclerview_Adapter(getContext(),starterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(starter_recyclerview_adapter);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        starterList = new ArrayList<>();

        starterList.add(new Starter_Model(R.drawable.chinese,"Chinese"));
        starterList.add(new Starter_Model(R.drawable.indian,"Indian"));
        starterList.add(new Starter_Model(R.drawable.italian1,"Italian"));
//        starterList.add(new Starter_Model(R.drawable.starter1));
//        starterList.add(new Starter_Model(R.drawable.starter1));
    }
}
