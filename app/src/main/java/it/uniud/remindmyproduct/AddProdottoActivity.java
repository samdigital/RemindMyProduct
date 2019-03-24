package it.uniud.remindmyproduct;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddProdottoActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner;
    Button conferma;
    EditText nome;
    EditText descrizione;
    EditText giorno;
    EditText mese;
    EditText anno;
    EditText quantita;
    EditText prezzo;

    //private DatabaseWrapper dbWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prodotto);

        toolbar = (Toolbar) findViewById(R.id.toolbaradd);
        setSupportActionBar(toolbar);
        setTitle("Aggiungi prodotto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //dbWrapper = new DatabaseWrapper(this);

        popolaSpinnerCategorie();

        nome = (EditText) findViewById(R.id.editNome);
        descrizione = (EditText) findViewById(R.id.editDesc);
        giorno = (EditText) findViewById(R.id.editTextgiorno);
        mese = (EditText) findViewById(R.id.editTextmese);
        anno = (EditText) findViewById(R.id.editTextanno);
        quantita = (EditText) findViewById(R.id.editQuan);
        prezzo = (EditText) findViewById(R.id.editPrezzo);
        conferma = (Button) findViewById(R.id.confermaaggiunta);

        //dbWrapper.open();


        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNome = nome.getText().toString().isEmpty();
                boolean isGiorno = giorno.getText().toString().isEmpty();
                boolean isMese = mese.getText().toString().isEmpty();
                boolean isAnno = anno.getText().toString().isEmpty();
                boolean isQuantita = quantita.getText().toString().isEmpty();
                boolean isPrezzo = prezzo.getText().toString().isEmpty();
                //Integer valQuantita = Integer.parseInt(quantita.getText().toString());
                //Double valPrezzo = Double.parseDouble(prezzo.getText().toString());

                if (isNome){
                    nome.setError("Inserisci nome");
                    nome.requestFocus();
                }
                if (isGiorno){
                    giorno.setError("Inserisci giorno di scadenza");
                    giorno.requestFocus();
                }
                if (isMese){
                    mese.setError("Inserisci mese di scadenza");
                    mese.requestFocus();
                }
                if (isAnno){
                    anno.setError("Inserisci anno di scadenza");
                    anno.requestFocus();
                }
                if (isQuantita){
                    quantita.setError("Inserisci quantità");
                    quantita.requestFocus();
                }
                if (isPrezzo){
                    prezzo.setError("Inserisci prezzo");
                    prezzo.requestFocus();
                }
                if (!(isNome || isGiorno || isMese || isAnno || isQuantita || isPrezzo)){
                    //dbWrapper.createProduct(nome.getText().toString(),descrizione.getText().toString(), 0, valQuantita, (long) 0,valPrezzo);
                    //dbWrapper.close();
                    Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentHome);
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

}
