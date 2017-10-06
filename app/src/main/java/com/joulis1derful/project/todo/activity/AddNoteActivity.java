package com.joulis1derful.project.todo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.joulis1derful.project.todo.R;
import com.joulis1derful.project.todo.model.Note;
import com.joulis1derful.project.todo.service.MessageSenderService;

import static com.joulis1derful.project.todo.activity.MainActivity.NOTES;

public class AddNoteActivity extends AppCompatActivity {

    public static final String REQUIRED = "Required";
    private int mPriority;

    EditText addTtl;
    EditText addBody;
    Button saveBtn;

    private BroadcastReceiver mMessageReceiver;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        FirebaseAuth.getInstance();

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String title = intent.getExtras().getString("title");
                String body = intent.getExtras().getString("body");
                Toast.makeText(AddNoteActivity.this, title+"\n"+body, Toast.LENGTH_LONG).show();
            }
        };

        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyData")
        );
    };

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    };

    private void initUI() {
        addTtl = (EditText)findViewById(R.id.addTitle);
        addBody = (EditText)findViewById(R.id.addBody);
        saveBtn = (Button)findViewById(R.id.btnSave);
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(addTtl.getText())) {
                    addTtl.setError(REQUIRED);
                    return;
                }
                if (TextUtils.isEmpty(addBody.getText())) {
                    addBody.setError(REQUIRED);
                    return;
                }

                View view = getCurrentFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromInputMethod(view != null ? view.getWindowToken() : null, 0);

                setEditingEnabled(false);
                saveBtn.setEnabled(false);

                postNote(addTtl.getText().toString(), addBody.getText().toString(), mPriority);
                Toast.makeText(AddNoteActivity.this,"Posting...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postNote(String title, String body, int priority) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child(NOTES).push().getKey();

        Note noteToSave = new Note(key, title, body, priority);
        mDatabase.child(NOTES).child(key)
                .setValue(noteToSave, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                saveBtn.setEnabled(true);

                if (databaseError == null) {
                    Toast.makeText(AddNoteActivity.this, "Successfully posted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_SYNC, null, AddNoteActivity.this, MessageSenderService.class);
                    startService(intent);
                    startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(AddNoteActivity.this, "Note was not posted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }

   private void setEditingEnabled(boolean state) {
        addTtl.setEnabled(state);
        addBody.setEnabled(state);
    }
}
