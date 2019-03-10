package it.uniud.remindmyproduct;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewDispensaActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner;

    Boolean viewInScadenza;

    private static final String TAG = "ViewDispensaActivity";
    private ArrayList<String> nomi = new ArrayList<>();
    private ArrayList<String> descrizioni = new ArrayList<>();
    private ArrayList<String> pezzi = new ArrayList<>();
    private ArrayList<String> scadenze = new ArrayList<>();
    private ArrayList<Integer> icone = new ArrayList<Integer>();
    private ArrayList<Integer> id_prodotto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdispensa);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.la_mia_dispensa_minuscolo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        viewInScadenza = getIntent().getBooleanExtra("in_scadenza", false);
        if(viewInScadenza) {
            //ordinamento diverso credo
            Toast.makeText(getApplicationContext(), "in scadenza", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "normale", Toast.LENGTH_LONG).show();
        }

        popolaSpinnerCategorie();
        aggiungiListenerSpinner();

        Log.d(TAG, "oncreate started");
        popolaLista();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void popolaSpinnerCategorie() {
        spinner = (Spinner) findViewById(R.id.spinnerCategorie);
        CategorieProdotti categorie = new CategorieProdotti();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorie.getListaCategorie());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void aggiungiListenerSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void popolaLista() {
        Log.d(TAG, "partita lista");
        nomi.add("test0");
        descrizioni.add("desci0");
        pezzi.add("0");
        scadenze.add("12/12/2018");
        icone.add(0);
        id_prodotto.add(0);

        nomi.add("test1");
        descrizioni.add("desci1");
        pezzi.add("1");
        scadenze.add("12/12/2018");
        icone.add(1);
        id_prodotto.add(1);

        nomi.add("test2");
        descrizioni.add("desci2");
        pezzi.add("2");
        scadenze.add("12/12/2018");
        icone.add(2);
        id_prodotto.add(2);

        nomi.add("test3");
        descrizioni.add("desci3");
        pezzi.add("3");
        scadenze.add("12/12/2018");
        icone.add(3);
        id_prodotto.add(3);

        nomi.add("test4");
        descrizioni.add("desci4");
        pezzi.add("4");
        scadenze.add("12/12/2018");
        icone.add(4);
        id_prodotto.add(4);

        nomi.add("test5");
        descrizioni.add("desci5");
        pezzi.add("5");
        scadenze.add("12/12/2018");
        icone.add(5);
        id_prodotto.add(5);


        nomi.add("test6");
        descrizioni.add("desci6");
        pezzi.add("6");
        scadenze.add("12/12/2018");
        icone.add(6);
        id_prodotto.add(6);

        initRecycleView();
    }

    private void initRecycleView () {
        Log.d(TAG, "init recycle");
        RecyclerView recyclerView = findViewById(R.id.listaProdotti);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nomi, descrizioni, pezzi, scadenze, icone, id_prodotto);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
