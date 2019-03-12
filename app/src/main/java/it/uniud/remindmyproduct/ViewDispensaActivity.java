package it.uniud.remindmyproduct;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ViewDispensaActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner;
    SearchView barraRicerca;
    int categoriaSelezionata;

    Boolean viewInScadenza;

    private DatabaseWrapper dbWrapper;
    private Cursor cursor;

    private static final String TAG = "ViewDispensaActivity";
    private ArrayList<String> nomi = new ArrayList<>();
    private ArrayList<String> descrizioni = new ArrayList<>();
    private ArrayList<String> pezzi = new ArrayList<>();
    private ArrayList<String> scadenze = new ArrayList<>();
    private ArrayList<Integer> icone = new ArrayList<Integer>();
    private ArrayList<Integer> ids_prodotto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdispensa);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.la_mia_dispensa_minuscolo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbWrapper = new DatabaseWrapper(this);


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
        categoriaSelezionata=0;
        popolaLista(categoriaSelezionata, "");



        barraRicerca = (SearchView) findViewById(R.id.barraRicerca);
        aggiungiListenerSearch();





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        popolaLista(0, "");
        spinner.setSelection(0);
    }

    public void aggiungiListenerSearch() {
        barraRicerca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                popolaLista(categoriaSelezionata, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                popolaLista(categoriaSelezionata, newText);
                return false;
            }
        });
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
                //Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                categoriaSelezionata=position;
                popolaLista(categoriaSelezionata, "");
                barraRicerca.setQuery("", false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void popolaLista(int category, String filter) {
        Log.d(TAG, "partita lista");
        Long id_back;

        nomi.clear();
        descrizioni.clear();
        pezzi.clear();
        scadenze.clear();
        icone.clear();
        ids_prodotto.clear();


/*
        dbWrapper.open();
        Log.d("DB ACT", "sto per lanciare");
        Date today=new Date();

        id_back=dbWrapper.createProduct("Test1", "desc1", 1, 1, today, 11.50);
        Log.d("DB ACT", "id prodotto: "+id_back);
        id_back=dbWrapper.createProduct("Test2", "desc2", 2, 2, today, 22.50);
        Log.d("DB ACT", "id prodotto: "+id_back);
        id_back=dbWrapper.createProduct("Test3", "desc3", 3, 3, today, 33.50);
        Log.d("DB ACT", "id prodotto: "+id_back);
        id_back=dbWrapper.createProduct("Test4", "desc4", 4, 4, today, 44.50);
        Log.d("DB ACT", "id prodotto: "+id_back);
        id_back=dbWrapper.createProduct("Test5", "desc5", 5, 5, today, 55.50);
        Log.d("DB ACT", "id prodotto: "+id_back);
        id_back=dbWrapper.createProduct("Test6", "desc6", 6, 6, today, 66.50);
        Log.d("DB ACT", "id prodotto: "+id_back);

        dbWrapper.close();

*/

        dbWrapper.open();
        if(viewInScadenza) {
            cursor=dbWrapper.getProductListInScadenza(category, filter);
        } else {
            cursor=dbWrapper.getProductList(category, filter);
        }
        while(cursor.moveToNext()) {
            nomi.add(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_NAME)));
            descrizioni.add(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_DESCRIPTION)));
            pezzi.add(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_QUANTITY)));
            scadenze.add(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_EXPIREDATE)));
            icone.add(cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_CATEGORY)));
            ids_prodotto.add(cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_ID)));
        }
        dbWrapper.close();


        initRecycleView();
    }

    private void initRecycleView () {
        Log.d(TAG, "init recycle");
        RecyclerView recyclerView = findViewById(R.id.listaProdotti);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nomi, descrizioni, pezzi, scadenze, icone, ids_prodotto);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
