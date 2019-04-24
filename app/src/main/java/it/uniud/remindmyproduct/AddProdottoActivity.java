package it.uniud.remindmyproduct;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddProdottoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    Spinner spinner;
    Button conferma;
    EditText nome;
    EditText descrizione;
    Spinner giorno;
    Spinner mese;
    Spinner anno;
    EditText quantita;
    EditText prezzo;

    private DatabaseWrapper dbWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prodotto);

        toolbar = (Toolbar) findViewById(R.id.toolbaradd);
        setSupportActionBar(toolbar);
        setTitle("Aggiungi prodotto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbWrapper = new DatabaseWrapper(this);

        popolaSpinnerCategorie();
        popolaSpinnerGiorno();
        popolaSpinnerMese();
        popolaSpinnerAnno();

        nome = (EditText) findViewById(R.id.editNome);
        descrizione = (EditText) findViewById(R.id.editDesc);
        quantita = (EditText) findViewById(R.id.editQuan);
        prezzo = (EditText) findViewById(R.id.editPrezzo);
        conferma = (Button) findViewById(R.id.confermaaggiunta);


        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNome = nome.getText().toString().isEmpty();
                boolean isQuantita = quantita.getText().toString().isEmpty();
                boolean isPrezzo = prezzo.getText().toString().isEmpty();
                boolean isData = false;

                if (isNome){
                    nome.setError("Inserisci nome");
                    nome.requestFocus();
                }
                if (isQuantita){
                    quantita.setError("Inserisci quantità");
                    quantita.requestFocus();
                }
                if (isPrezzo){
                    prezzo.setError("Inserisci prezzo");
                    prezzo.requestFocus();
                }

                if (mese.getSelectedItem().toString().equals("Febbraio") || mese.getSelectedItem().toString().equals("Aprile") || mese.getSelectedItem().toString().equals("Giugno") || mese.getSelectedItem().toString().equals("Settembre") || mese.getSelectedItem().toString().equals("Novembre")) {
                    int giornata = Integer.parseInt(giorno.getSelectedItem().toString());
                    if (mese.getSelectedItem().toString().equals("Febbraio")) {
                        int annata = Integer.parseInt(anno.getSelectedItem().toString());
                        if ((giornata > 28)) {
                            if (((giornata == 29) && (annata%4 == 0))) {
                                isData = true;
                            } else {
                                Toast.makeText(getApplicationContext(), "Inserire la data corretta", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            isData = true;
                        }
                    } else if (giornata == 31) {
                        Toast.makeText(getApplicationContext(), "Inserire la data corretta", Toast.LENGTH_LONG).show();
                    } else {
                        isData = true;
                    }
                } else {
                    isData = true;
                }


                if (isData == true){
                    int giornoscad = Integer.parseInt(giorno.getSelectedItem().toString());
                    int mesescad = mese.getSelectedItemPosition();
                    int annoscad = Integer.parseInt(anno.getSelectedItem().toString());
                    Calendar today = new GregorianCalendar();
                    if (giornoscad < today.get(Calendar.DAY_OF_MONTH) && (mesescad <= today.get(Calendar.MONTH)) && (annoscad <= today.get(Calendar.YEAR))){
                        Toast.makeText(getApplicationContext(), "La data di scadenza è precedente alla data odierna", Toast.LENGTH_LONG).show();
                        isData = false;
                    }
                }


                if (!(isNome || isQuantita || isPrezzo || !isData)){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar scadenza = new GregorianCalendar();
                    try {
                        String data_toParse = giorno.getSelectedItem().toString()+"/"+(mese.getSelectedItemPosition()+1)+"/"+anno.getSelectedItem().toString();
                        scadenza.setTime(dateFormat.parse(data_toParse));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dbWrapper.open();
                    dbWrapper.createProduct(nome.getText().toString(),descrizione.getText().toString(), spinner.getSelectedItemPosition(), Integer.parseInt(quantita.getText().toString()), scadenza.getTimeInMillis(), Double.parseDouble(prezzo.getText().toString()) );
                    dbWrapper.close();
                    Toast.makeText(getApplicationContext(), nome.getText().toString()+" "+getString(R.string.added_to_dispensa), Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    public void popolaSpinnerCategorie() {
        spinner = (Spinner) findViewById(R.id.spinner);
        CategorieProdotti categorie = new CategorieProdotti(getApplicationContext());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorie.getListaCategorie());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void popolaSpinnerGiorno(){
        giorno = (Spinner) findViewById(R.id.spinnergiorno);
        Integer giorni[] = new Integer[31];
        for(int i=0; i<31; i++) {
            giorni[i] = i+1;
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, giorni);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        giorno.setAdapter(dataAdapter);
        giorno.setOnItemSelectedListener(this);
    }

    public void popolaSpinnerMese(){
        mese = (Spinner) findViewById(R.id.spinnermese);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.elencoMesi, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mese.setAdapter(dataAdapter);
        mese.setOnItemSelectedListener(this);
    }

    public void popolaSpinnerAnno(){
        anno = (Spinner) findViewById(R.id.spinneranno);
        Calendar today = new GregorianCalendar();
        Integer anni[] = new Integer[16];
        for(int i=0; i<16; i++) {
            anni[i] = today.get(Calendar.YEAR)+i;
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, anni);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        anno.setAdapter(dataAdapter);
        anno.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
