package it.uniud.remindmyproduct;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ViewItemActivity extends AppCompatActivity {

    Toolbar toolbar;

    int product_id;

    private TextView productName;
    private TextView description;
    private TextView quantity;
    private TextView expireDate;
    private TextView insertedDate;
    private TextView value;
    private boolean statusProductOpen=false;

    private DatabaseWrapper dbWrapper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewitem);
        // recupero id che mi è stato passato dalla lista... mi serve per il db
        product_id = getIntent().getIntExtra("id_prodotto", 0);
        Log.d("ID PRODOTTO", "id prodotto: "+product_id);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Dettaglio Prodotto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbWrapper = new DatabaseWrapper(this);

        popolaCampi();


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

        Switch aperto;
        aperto = (Switch) findViewById(R.id.productopened);
        aperto.setChecked(statusProductOpen);
        aperto.setClickable(!statusProductOpen);

        aperto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    AlertDialog dialog = apri(buttonView);
                    dialog.show();
                    //Toast.makeText(getApplicationContext(), "Aperto", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Chiuso", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Toast.makeText(getApplicationContext(), String.valueOf(product_id), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
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
                    .setTitle(R.string.consuma_in_parte_select)
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

    private AlertDialog apri(final CompoundButton buttonView) {
        AlertDialog confermaApri = new AlertDialog.Builder(this)
                .setTitle(R.string.apri_prodotto)
                .setMessage(R.string.conferma_apertura)
                .setIcon(R.drawable.icon_check)

                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        buttonView.setClickable(false);
                        dialog.dismiss();

                    }
                })

                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        buttonView.setChecked(false);
                        dialog.dismiss();
                    }
                })
                .create();
        return confermaApri;
    }

    private void popolaCampi() {
        productName = (TextView) findViewById(R.id.productname);
        description = (TextView) findViewById(R.id.productdescription);
        quantity = (TextView) findViewById(R.id.productquantity_number);
        expireDate = (TextView) findViewById(R.id.productscadenza_date);
        insertedDate = (TextView) findViewById(R.id.productinserted_date);
        value = (TextView) findViewById(R.id.productprice_value);

        dbWrapper.open();
        Log.d("DB VIEW", "APERTA PROCEDURA");
        cursor=dbWrapper.getProduct(product_id);
        Log.d("DB VIEW", "FATTA QUERY");

        cursor.moveToNext();
        productName.setText(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_NAME)));
        description.setText(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_DESCRIPTION)));
        quantity.setText(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_QUANTITY)));
        expireDate.setText(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_EXPIREDATE)));
        insertedDate.setText(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_INSERTED)));
        Double valore = cursor.getDouble(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_VALUE));
        value.setText(String.format("%.2f", valore)+this.getString(R.string.currency));

        statusProductOpen = cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_ISOPEN))>0;




        dbWrapper.close();
    }

}
