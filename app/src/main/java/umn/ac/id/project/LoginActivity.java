package umn.ac.id.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPass;
    private Button btnLogin;
    FirebaseAuth fAuth;
    String userID;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.emailSpace);
        etPass = (EditText) findViewById(R.id.pwSpace);
        btnLogin = (Button) findViewById(R.id.buttonLogin);


        fAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email is Required!");
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    etPass.setError("Password is Required!");
                    return;
                }

                if(etPass.length() < 6){
                    etPass.setError("Password Must Be Greater or Equal 6 Characters!");
                }



                //auth user

                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userID = fAuth.getCurrentUser().getUid();
                            Log.d("Success", "Login Berhasil" + userID);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else{
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
