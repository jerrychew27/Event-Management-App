package com.fit2081.a1_2081_32837259;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.a1_2081_32837259.provider.EventCatViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FragmentListCategory extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    Gson gson = new Gson();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public FragmentListCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);
        EventCatViewModel eventCatViewModel = new ViewModelProvider(this).get(EventCatViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        adapter = new CategoryAdapter();
        eventCatViewModel.getAllEventsCatLiveData().observe(getViewLifecycleOwner(), newData -> {
            adapter.setData(new ArrayList<>(newData));
            adapter.notifyDataSetChanged();
        });
        recyclerView.setAdapter(adapter);


        return view;
    }

}