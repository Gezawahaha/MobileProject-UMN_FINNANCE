package umn.ac.id.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.source.FileSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.UUID;

public class Payment extends AppCompatActivity {

    ImageView imgShow;
    Button submit;
    Button addImg;
    private Uri imageUrl;
    static final int CAMERA = 1;
    static final int GALLERY = 2;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference reference;
    String userId;
    TextView price, due;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        reference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        price = findViewById(R.id.kurangbayar);
        due = findViewById(R.id.duedate);

        loading = findViewById(R.id.loading);
        addImg = findViewById(R.id.btnAdd);

        final DocumentReference documentReference = fStore.collection("Mahasiswa").document(userId).collection("Tagihan").document("Gasal1920");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                long kurang = documentSnapshot.getLong("Semester 5").intValue();
                price.setText("Tagihan : Rp. "+kurang+" ,-");

                due.setText("Tenggat Waktu : "+documentSnapshot.getString("waktu"));
            }
        });





        final String[] pilih = new String[]{"Camera", "Gallery"};
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, pilih);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setAdapter(arr_adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pilihan) {
                if(pilihan == 0){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, CAMERA);
                    /*File file = new File(Environment.getExternalStorageDirectory(),
                            "image_picker/img_" +
                                    String.valueOf(System.currentTimeMillis())+ ".jpg");
                    imageUrl = Uri.fromFile(file);

                    try{
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, CAMERA);
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                    /*dialog.cancel();*/
                }else if(pilihan == 1){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    /*intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);*/

                    startActivityForResult(Intent.createChooser(intent, "Pilih Aplikasi"), GALLERY);
                }
            }
        });
        final AlertDialog dialog = builder.create();

        imgShow = findViewById(R.id.gambarHasil);

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        submit =findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgShow.setDrawingCacheEnabled(true);
                imgShow.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgShow.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                String namaFile = UUID.randomUUID()+".jpg";
                String pathImage = "gambar/"+namaFile;

                UploadTask uploadTask = reference.child(pathImage).putBytes(bytes);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        loading.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                        Toast.makeText(Payment.this, "Pengunggahan Bukti Pembayaran Berhasil!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                        Toast.makeText(Payment.this, "Gagal Mengunggah!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        submit.setVisibility(View.GONE);
                        loading.setVisibility(View.VISIBLE);
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        loading.setProgress((int) progress);
                    }
                });
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        Bitmap bitmap = null;
        String path = "";

        if (requestCode == GALLERY) {
            imageUrl = data.getData();
            imgShow.setImageURI(imageUrl);
            /*if (path == null) {
                path = imageUrl.getPath();
            } else {
                *//*bitmap = BitmapFactory.decodeFile(path);*//*
                imgShow.setImageURI(Uri.parse(path));
            }*/
        }
        if (requestCode == CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap imagebitmap = (Bitmap) extras.get("data");
            imgShow.setImageBitmap(imagebitmap);


            /*path = imageUrl.getPath();*/

            /*bitmap = BitmapFactory.decodeFile(path);
            imgShow.setImageBitmap(bitmap);*/
        }

    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
