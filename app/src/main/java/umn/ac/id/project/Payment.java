package umn.ac.id.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class Payment extends AppCompatActivity {

    ImageView imgShow;
    Button submit;
    private Uri imageUrl;
    private static int CAMERA = 1;
    private static int GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final String[] pilih = new String[]{"Camera", "Gallery"};
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, pilih);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setAdapter(arr_adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pilihan) {
                if(pilihan == 0){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(),
                            "image_picker/img_" +
                            String.valueOf(System.currentTimeMillis())+ ".jpg");
                    imageUrl = Uri.fromFile(file);

                    try{
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, CAMERA);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    /*dialog.cancel();*/
                }else if(pilihan == 1){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Pilih Aplikasi"), GALLERY);
                }
            }
        });
        final AlertDialog dialog = builder.create();

        imgShow = findViewById(R.id.gambarHasil);

        imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        submit = findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            path = getRealPath(imageUrl);

            if (path == null) {
                path = imageUrl.getPath();
            } else {
                bitmap = BitmapFactory.decodeFile(path);
            }
        } else {
            path = imageUrl.getPath();
            bitmap = BitmapFactory.decodeFile(path);
        }
        imgShow.setImageBitmap(bitmap);
    }

    public String getRealPath(Uri contentUri){
        String path = null;
        String[] image_data = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(contentUri, image_data, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
