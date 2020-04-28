package umn.ac.id.project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Loading extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        final FirebaseUser currentLogin = fAuth.getCurrentUser();
        //updateUI(currentLogin);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI(currentLogin);
            }
        }, SPLASH_TIME_OUT);

        /*userId = fAuth.getCurrentUser().getUid();

        if(userId != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
*/
    }

    private void updateUI(FirebaseUser currentLogin) {
        if(currentLogin != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}