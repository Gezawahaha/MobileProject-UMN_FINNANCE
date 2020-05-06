package umn.ac.id.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import static umn.ac.id.project.app.CHANNEL_1_ID;
import static umn.ac.id.project.app.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    ViewFlipper v_fliper;

    TextView fullName;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    private NotificationManagerCompat notificationManager;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fullName = findViewById(R.id.Nama);

        //fliper animation
        v_fliper = findViewById(R.id.v_fliper);
        int images[] = {R.drawable.pic1e , R.drawable.pic2e, R.drawable.pic3e, R.drawable.pic4e};
        for (int i=0; i <images.length; i++){
            flipperImages(images[i]);
        }

        //Firebase Data

        //inisiasi authorize sama firestore
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //ngambil userId
        userId = fAuth.getCurrentUser().getUid();

        //nyetak nama
        final DocumentReference documentReference = fStore.collection("Mahasiswa").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                fullName.setText(documentSnapshot.getString("nama"));

            }
        });


        //Notif
        notificationManager = NotificationManagerCompat.from(this);

    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_fliper.addView(imageView);
        v_fliper.setFlipInterval(4000);
        v_fliper.setAutoStart(true);

        //animasi
        v_fliper.setInAnimation(this, android.R.anim.fade_in);
        v_fliper.setOutAnimation(this, android.R.anim.fade_out);


    }

    public void profile(View view) {
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void Payment(View view) {
        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void PaymentMethod(View view) {
        Intent intent = new Intent(this, Paymentmethod.class);
        startActivity(intent);
    }

    public void StudentSupport(View view) {
        Intent intent = new Intent(this, Studentsup.class);
        startActivity(intent);
    }

    public void Email(View view) {
        Intent intent = new Intent(this, email.class);
        startActivity(intent);
    }

    public void History(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void Calender(View view) {
        Intent intent = new Intent(this, Calender.class);
        startActivity(intent);
    }

    public void Elearning(View view) {
        Intent intent = new Intent(this, Elearning.class);
        startActivity(intent);
    }

    public void sendOnChannel1(View v) {
        String title = ("Jatuh Tempo");
        String message = ("Blablalbalbal");



        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(1, notification);
    }



}
