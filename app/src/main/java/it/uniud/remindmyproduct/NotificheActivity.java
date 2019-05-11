package it.uniud.remindmyproduct;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

public class NotificheActivity extends AppCompatActivity {

    Toolbar toolbar;

    int id_notifiche;
    boolean notifiche_scadenza;
    boolean notifiche_quantita;
    int valore_notifiche_scadenza;
    int valore_notifiche_quantita;

    EditText scadenza;
    EditText quantita;
    ToggleButton scadenzabool;
    ToggleButton quantitabool;

    private DatabaseWrapper dbWrapper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiche);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notifiche");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbWrapper = new DatabaseWrapper(this);

        dbWrapper.open();

        scadenza = (EditText) findViewById(R.id.insscadenza);
        quantita = (EditText) findViewById(R.id.insquantita);
        scadenzabool = (ToggleButton) findViewById(R.id.scadenzaon);
        quantitabool = (ToggleButton) findViewById(R.id.quantitaon);

        cursor = dbWrapper.getNotifiche();
        cursor.moveToFirst();

        id_notifiche = cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.NOTIFICHE_ID));
        notifiche_scadenza = (cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.NOTIFICHE_SCADENZA))) == 1;
        notifiche_quantita = (cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.NOTIFICHE_QUANTITY))) == 1;
        valore_notifiche_scadenza = cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.VALORE_NOTIFICHE_SCADENZA));
        valore_notifiche_quantita = cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.VALORE_NOTIFICHE_QUANTITY));

        scadenzabool.setChecked(notifiche_scadenza);
        quantitabool.setChecked(notifiche_quantita);
        scadenza.setText(Integer.toString(valore_notifiche_scadenza));
        quantita.setText(Integer.toString(valore_notifiche_quantita));

        cursor.close();

        /*
        scadenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valore_notifiche_scadenza = Integer.valueOf(scadenza.getText().toString());
                dbWrapper.updateNotifiche(id_notifiche, notifiche_quantita, notifiche_scadenza, valore_notifiche_quantita, valore_notifiche_scadenza);
            }
        });


        quantita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valore_notifiche_quantita = Integer.valueOf(quantita.getText().toString());
                dbWrapper.updateNotifiche(id_notifiche, notifiche_quantita, notifiche_scadenza, valore_notifiche_quantita, valore_notifiche_scadenza);
            }
        });
        */

        scadenzabool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifiche_scadenza = scadenzabool.isChecked();
                dbWrapper.updateNotifiche(id_notifiche, notifiche_quantita, notifiche_scadenza, valore_notifiche_quantita, valore_notifiche_scadenza);
            }
        });

        quantitabool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifiche_quantita = quantitabool.isChecked();
                dbWrapper.updateNotifiche(id_notifiche, notifiche_quantita, notifiche_scadenza, valore_notifiche_quantita, valore_notifiche_scadenza);
            }
        });
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
        valore_notifiche_scadenza = Integer.valueOf(scadenza.getText().toString());
        valore_notifiche_quantita = Integer.valueOf(quantita.getText().toString());
        dbWrapper.updateNotifiche(id_notifiche, notifiche_quantita, notifiche_scadenza, valore_notifiche_quantita, valore_notifiche_scadenza);
    }

}
