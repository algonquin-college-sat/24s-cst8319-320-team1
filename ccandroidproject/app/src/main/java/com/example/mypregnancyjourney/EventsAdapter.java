package com.example.mypregnancyjourney;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<Event> events;

    public EventsAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.dateTextView.setText(event.getDate());
        holder.event1TextView.setText(event.getEvent1());
        if (!event.getEvent2().isEmpty()) {
            holder.event2TextView.setText(event.getEvent2());
            holder.event2TextView.setVisibility(View.VISIBLE);
        }
        if (!event.getEvent3().isEmpty()) {
            holder.event3TextView.setText(event.getEvent3());
            holder.event3TextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView event1TextView;
        TextView event2TextView;
        TextView event3TextView;

        ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            event1TextView = itemView.findViewById(R.id.event1TextView);
            event2TextView = itemView.findViewById(R.id.event2TextView);
            event3TextView = itemView.findViewById(R.id.event3TextView);
        }
    }
}
