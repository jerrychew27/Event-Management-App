package com.fit2081.a1_2081_32837259;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private ArrayList<Event> eventData;
    private String eventName;

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_card_layout, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event events = eventData.get(position);
        holder.tvEventId.setText(events.getEventID());
        holder.tvEventName.setText(events.getEventName());
        holder.tvEventCategoryId.setText(events.getEventCategoryID());
        holder.tvTickets.setText(String.valueOf(events.getTickets()));
        if (events.isActive()) {
            holder.tvEventsActive.setText("Active");
        } else {
            holder.tvEventsActive.setText("Inactive");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 eventName = events.getEventName();
                 Intent intent = new Intent(view.getContext(), EventGoogleResult.class);
                 intent.putExtra("event_name", eventName);
                 view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this.eventData != null) { // if data is not null
            return this.eventData.size(); // then return the size of ArrayList
        }

        // else return zero if data is null
        return 0;
    }
    public void setData(ArrayList<Event> eventData) {
        this.eventData = eventData;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventId;
        public TextView tvEventName;
        public TextView tvEventCategoryId;
        public TextView tvTickets;
        public TextView tvEventsActive;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventId = itemView.findViewById(R.id.tv_event_id);
            tvEventName = itemView.findViewById(R.id.tv_event_name);
            tvEventCategoryId = itemView.findViewById(R.id.tv_event_category_id);
            tvTickets = itemView.findViewById(R.id.tv_tickets);
            tvEventsActive = itemView.findViewById(R.id.event_active);
        }

    }
}
