package traf7.lamsalsamyog.firebasechatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText username, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.registerUsernameEditText);
        password = findViewById(R.id.registerPasswordEditText);
        register = findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (username.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_LONG).show();
                }
                else if (password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_LONG).show();
                }
                else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("users").child(username.getText().toString()).setValue(password.getText().toString());
                    startActivity(new Intent(Register.this, Login.class));
                }

            }
        });
    }
}
