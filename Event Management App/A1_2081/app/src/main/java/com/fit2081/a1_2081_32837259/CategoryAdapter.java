package com.fit2081.a1_2081_32837259;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<EventCategory> data;
    private String eventLocation;


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_card_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        EventCategory category = data.get(position);
        holder.tvCategoryID.setText(category.getCategoryID());
        holder.tvCategoryName.setText(category.getCategoryName());
        holder.tvEventCount.setText(String.valueOf(category.getEventCount()));
        if (category.isActive()) {
            holder.tvIsActive.setText("Yes");
        } else {
            holder.tvIsActive.setText("No");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventLocation = category.getEventLocation();

                Intent intent = new Intent(view.getContext(), GoogleMapsActivity.class);
                intent.putExtra("event_location", eventLocation);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (this.data != null) { // if data is not null
            return this.data.size(); // then return the size of ArrayList
        }

        // else return zero if data is null
        return 0;
    }
    public void setData(ArrayList<EventCategory> data) {
        this.data = data;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategoryID;
        public TextView tvCategoryName;
        public TextView tvEventCount;
        public TextView tvIsActive;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryID = itemView.findViewById(R.id.category_id);
            tvCategoryName = itemView.findViewById(R.id.category_name);
            tvEventCount = itemView.findViewById(R.id.category_event_count);
            tvIsActive = itemView.findViewById(R.id.category_active);
        }

    }
}
