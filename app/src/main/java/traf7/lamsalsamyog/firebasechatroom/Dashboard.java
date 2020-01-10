package traf7.lamsalsamyog.firebasechatroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    EditText message;
    Button sendMessage;
    SharedPreferences mPreferences;
    String username;
    ListView listView;
    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mPreferences = getSharedPreferences("traf7.lamsalsamyog.firebasechatroom", MODE_PRIVATE);
        username = mPreferences.getString("username", "test");

        message = findViewById(R.id.messageEditText);
        sendMessage = findViewById(R.id.sendMessageButton);

        displayChatMessages();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChatMessage cm = new ChatMessage(message.getText().toString(), username);
                FirebaseDatabase.getInstance().getReference().child("messages").push().setValue(cm);
                message.setText("");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void displayChatMessages() {
        ListView listOfMessages = findViewById(R.id.listView);
        adapter = new FirebaseListAdapter<ChatMessage>(new FirebaseListOptions.Builder<ChatMessage>().setLayout(R.layout.message).setQuery(FirebaseDatabase.getInstance().getReference()
                .child("messages"), ChatMessage.class).build()) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);


                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
        System.out.println("done");
    }

}
