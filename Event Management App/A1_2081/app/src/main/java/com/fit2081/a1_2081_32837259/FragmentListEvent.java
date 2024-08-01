package com.fit2081.a1_2081_32837259;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.Type;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.a1_2081_32837259.provider.EventCatViewModel;
import com.fit2081.a1_2081_32837259.provider.EventViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class FragmentListEvent extends Fragment {
    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    Gson gson = new Gson();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public FragmentListEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_event, container, false);
        EventViewModel eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventRecyclerView = view.findViewById(R.id.eventRecylerVIew);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        eventAdapter = new EventAdapter();

        eventViewModel.getAllEventsLiveData().observe(getViewLifecycleOwner(), newData -> {
            eventAdapter.setData(new ArrayList<>(newData));
            eventAdapter.notifyDataSetChanged();
        });
        eventRecyclerView.setAdapter(eventAdapter);


        return view;
    }

}