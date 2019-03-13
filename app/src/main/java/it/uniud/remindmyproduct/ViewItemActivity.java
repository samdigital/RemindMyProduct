package it.uniud.remindmyproduct;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewItemActivity extends AppCompatActivity {

    Toolbar toolbar;

    private int product_id;
    private double prezzo;
    private int pezzi;
    private boolean statusProductOpen=false;

    private TextView productName;
    private TextView description;
    private TextView quantity;
    private TextView expireDate;
    private TextView insertedDate;
    private TextView value;


    private DatabaseWrapper dbWrapper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewitem);
        // recupero id che mi è stato passato dalla lista... mi serve per il db
        product_id = getIntent().getIntExtra("id_prodotto", 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.product_detail);
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
                AlertDialog dialog = consuma(pezzi-1);
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
                } else {
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

    private AlertDialog consuma(int quantity) {
        if (quantity == 0) {
            AlertDialog consumaTutto = new AlertDialog.Builder(this)
                    .setTitle(R.string.consuma_tutto)
                    .setMessage(R.string.domanda_vuoi_rimuovere_prodotto)
                    .setIcon(R.drawable.icon_check)

                    .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dbWrapper.open();
                            if(dbWrapper.consumaTutto(product_id)) {
                                Toast.makeText(getApplicationContext(), getString(R.string.updated), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                            }
                            dbWrapper.close();
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
                            int qta = numberPicker.getValue();
                            int pezzi_rimasti = pezzi-qta;
                            Double valore_rimasto = (prezzo/pezzi)*pezzi_rimasti;

                            dbWrapper.open();
                            if(dbWrapper.consumaInParte(product_id, pezzi-qta, valore_rimasto)) {
                                Toast.makeText(getApplicationContext(), getString(R.string.updated), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                            }
                            dbWrapper.close();

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
                        dbWrapper.open();
                        if(dbWrapper.setOpen(product_id)) {
                            buttonView.setClickable(false);
                            Toast.makeText(getApplicationContext(), getString(R.string.updated), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                        dbWrapper.close();
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
        cursor=dbWrapper.getProduct(product_id);

        cursor.moveToNext();
        productName.setText(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_NAME)));
        description.setText(cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_DESCRIPTION)));

        pezzi=cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_QUANTITY));
        quantity.setText(""+pezzi);

        Long long_date;
        long_date= cursor.getLong(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_EXPIREDATE));
        expireDate.setText(getDate(long_date));
        long_date= cursor.getLong(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_INSERTED));
        insertedDate.setText(getDate(long_date));

        prezzo = cursor.getDouble(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_VALUE));
        value.setText(String.format("%.2f", prezzo)+this.getString(R.string.currency));

        statusProductOpen = cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_ISOPEN))>0;

        cursor.close();
        dbWrapper.close();
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(milliSeconds));
        return dateString;
    }

}
