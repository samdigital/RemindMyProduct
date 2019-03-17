package it.uniud.remindmyproduct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class NotificheActivity extends AppCompatActivity {

    Toolbar toolbar;

    private String valsca;
    private String valqua;
    private boolean actsca;
    private boolean actqua;

    EditText scadenza;
    EditText quantita;
    ToggleButton scadenzaact;
    ToggleButton quantitaact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiche);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notifiche");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        scadenza = (EditText) findViewById(R.id.insscadenza);
        quantita = (EditText) findViewById(R.id.insquantita);
        scadenzaact = (ToggleButton) findViewById(R.id.scadenzaon);
        quantitaact = (ToggleButton) findViewById(R.id.quantitaon);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        valsca = scadenza.getText().toString();
        valqua = quantita.getText().toString();
        //Toast.makeText(getApplicationContext(),valsca,Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),valqua,Toast.LENGTH_SHORT).show();
        actsca = scadenzaact.isChecked();
        actqua = quantitaact.isChecked();
        //Toast.makeText(getApplicationContext(),Boolean.toString(actsca),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),Boolean.toString(actqua),Toast.LENGTH_SHORT).show();
    }
}
