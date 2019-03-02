package it.uniud.remindmyproduct;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class ViewItemActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewitem_drawer);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Dettaglio Prodotto");

        drawer = (DrawerLayout) findViewById(R.id.viewitem_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_home:
                        Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentHome);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_report:
                        Intent intentReport = new Intent(getApplicationContext(), ReportActivity.class);
                        startActivity(intentReport);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_dispensa:
                        //Intent intentDispensa = new Intent(getApplicationContext(), ViewItemActivity.class);
                        //startActivity(intentDispensa);
                        drawer.closeDrawers();
                        return true;

                    default:
                        return false;
                }
            }
        });


        Button consumaTutto;
        Button consumaInParte;

        consumaTutto = (Button) findViewById(R.id.buttonConsumaTutto);
        consumaInParte = (Button) findViewById(R.id.buttonConsumaInParte);

        consumaTutto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = consuma(0);
                dialog.show();
            }
        });

        consumaInParte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = consuma(10);
                dialog.show();
            }
        });


    }

    private AlertDialog consuma(int quantity) {
        if (quantity == 0) {
            AlertDialog consumaTutto = new AlertDialog.Builder(this)
                    .setTitle(R.string.consuma_tutto)
                    .setMessage(R.string.domanda_vuoi_rimuovere_prodotto)
                    .setIcon(R.drawable.icon_check)

                    .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //codice per eliminare da DB
                            dialog.dismiss();
                            finish();
                        }
                    })

                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            return consumaTutto;
        } else {
            final NumberPicker numberPicker = new NumberPicker(this);
            numberPicker.setMaxValue(quantity);
            numberPicker.setMinValue(1);
            AlertDialog consumaInParte = new AlertDialog.Builder(this)
                    .setView(numberPicker)
                    .setTitle(R.string.consuma_in_parte)
                    .setMessage(R.string.seleziona_prodotto_consumato)
                    .setIcon(R.drawable.icon_check)

                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            int numero = numberPicker.getValue();

                            Toast.makeText(getApplicationContext(), String.valueOf(numero), Toast.LENGTH_LONG).show();
                            //codice per eliminare da DB
                            dialog.dismiss();
                            finish();
                        }

                    })

                    .setNegativeButton(R.string.undo, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            return consumaInParte;
        }
    }

}
