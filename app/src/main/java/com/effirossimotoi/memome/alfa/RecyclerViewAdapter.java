package com.effirossimotoi.memome.alfa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {
    private List<Note> notes;
    private List<Note> notesFull;
    private Context context;

    public RecyclerViewAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        notesFull = new ArrayList<>(notes);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.text.setText(note.getNote());
        holder.date.setText(note.getModifed_date());
        holder.touch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.sendObject(context, position);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Note> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(notesFull);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (Note note: notesFull) {
                        if (note.getTitle().toLowerCase().contains(filterPattern) ||
                                note.getNote().toLowerCase().contains(filterPattern)) {
                            filteredList.add(note);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notes.clear();
                notes.addAll((List<Note>)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView text;
        private TextView date;
        private RelativeLayout touch_layout;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.date);
            touch_layout = itemView.findViewById(R.id.touch_layout);
        }
    }
}
