package traf7.lamsalsamyog.firebasechatroom;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login, register;
    Boolean accountFoundFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences.Editor editor = getSharedPreferences("traf7.lamsalsamyog.firebasechatroom", MODE_PRIVATE).edit();

        username = findViewById(R.id.loginUsernameEditText);
        password = findViewById(R.id.loginPasswordEditText);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.goToRegisterButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (username.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_LONG).show();
                }
                else if (password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_LONG).show();
                }
                else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if (ds.getKey().equals(username.getText().toString()) && ds.getValue().equals(password.getText().toString())) {
                                    //goto dashboard
                                    startActivity(new Intent(Login.this, Dashboard.class));
                                    editor.putString("username", username.getText().toString()).apply();
                                    accountFoundFlag = true;
                                }
                            }

                            if (!accountFoundFlag) {
                                Toast.makeText(getApplicationContext(), "Account not found", Toast.LENGTH_LONG).show();
                                accountFoundFlag = false;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}
