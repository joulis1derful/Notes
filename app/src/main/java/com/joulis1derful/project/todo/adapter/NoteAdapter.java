package com.joulis1derful.project.todo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.joulis1derful.project.todo.R;
import com.joulis1derful.project.todo.model.Note;

import java.util.List;

import static com.joulis1derful.project.todo.activity.MainActivity.NOTES;


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

    CustomViewHolder(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.title);
        body = (TextView) itemView.findViewById(R.id.body);
    }

    void bind(final Note note) {
        title.setText(note.getTitle());
        body.setText(note.getBody());
    }
}
}

