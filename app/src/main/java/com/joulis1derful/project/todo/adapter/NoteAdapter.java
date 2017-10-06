package com.joulis1derful.project.todo.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joulis1derful.project.todo.R;
import com.joulis1derful.project.todo.model.Note;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.CustomViewHolder> {

    private List<Note> dataList;
    private Context mContext;

    public NoteAdapter(Context context, List<Note> data) {
        this.dataList = data;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_note, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

class CustomViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView body;
    private TextView priority;

    CustomViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.title);
        body = (TextView) itemView.findViewById(R.id.body);
        priority = (TextView) itemView.findViewById(R.id.priority);
    }

    void bind(final Note note) {
        title.setText(note.getTitle());
        body.setText(note.getBody());
        int prior = note.getPriority();
        GradientDrawable priorityCircle = (GradientDrawable) priority.getBackground();
        priorityCircle.setColor(getPriorityColor(prior));
        priority.setText(String.valueOf(prior));
    }

    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch(priority) {
            case 1: priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2: priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3: priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default: break;
        }
        return priorityColor;
    }
}
}

