package umn.ac.id.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Studentsup extends AppCompatActivity {


    DownloadManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsup);
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void whatsapp(View view) {
        String url = "https://api.whatsapp.com/send?phone=62081310286151&text=Halo,%20saya%20ingin%20bertanya%20?%20";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void studentsup(View view) {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:622154220808"));
        startActivity(intent);
    }

    public void email(View view) {
        

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","student.support@umn.ac.id", null)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "UMN CLICK");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
}
