package umn.ac.id.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class profile extends AppCompatActivity {

    TextView fullName,email,angkatan,fakultas,nim,prodi;
    Button logout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //textview
        fullName = findViewById(R.id.Nama);
        email = findViewById(R.id.email);
        angkatan = findViewById(R.id.angkatan);
        fakultas = findViewById(R.id.fakultas);
        nim = findViewById(R.id.nim);
        prodi = findViewById(R.id.prodi);

        //Button
        logout = findViewById(R.id.logout);

        //Firebase Data

        //inisiasi authorize sama firestore
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //ngambil userId
        userId = fAuth.getCurrentUser().getUid();
        //nyetak
        final DocumentReference documentReference = fStore.collection("Mahasiswa").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                fullName.setText(documentSnapshot.getString("nama"));
                email.setText(documentSnapshot.getString("email"));
                angkatan.setText(documentSnapshot.getString("angkatan"));
                fakultas.setText(documentSnapshot.getString("fakultas"));
                nim.setText(documentSnapshot.getString("nim"));
                prodi.setText(documentSnapshot.getString("prodi"));



            }
        });


        //logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finishAffinity();
            }
        });
        
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
