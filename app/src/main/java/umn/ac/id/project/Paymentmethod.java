package umn.ac.id.project;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.barteksc.pdfviewer.PDFView;

public class Paymentmethod extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    PDFView pdfView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentmethod);

        String[] bankNames={"Bank Account","CIMBI NIAGA","BRI"};
        int logo[] = {R.drawable.expmini,R.drawable.cimb, R.drawable.bri};

        pdfView = (PDFView)findViewById(R.id.pdfView);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),logo,bankNames);
        spin.setAdapter(customAdapter);



    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i==0 ){

        }else if (i == 1){
            pdfView.fromAsset("cimb.pdf").load();
        }else if (i== 2){
            pdfView.fromAsset("bri.pdf").load();
        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


