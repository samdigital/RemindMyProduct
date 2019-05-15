package it.uniud.remindmyproduct;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView prodotto1;
    TextView prodotto2;
    TextView prodotto3;

    Button viewInScadenza;
    Button laMiaDispensa;
    Button addProduct;

    private DatabaseWrapper dbWrapper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.remind_my_product);
        dbWrapper = new DatabaseWrapper(this);

        viewInScadenza = findViewById(R.id.view_all);
        laMiaDispensa = findViewById(R.id.label_lamiadispensa);
        addProduct = findViewById(R.id.label_aggiungi);

        prodotto1 = findViewById(R.id.product_1);
        prodotto2 = findViewById(R.id.product_2);
        prodotto3 = findViewById(R.id.product_3);

        viewInScadenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDispensa = new Intent(getApplicationContext(), ViewDispensaActivity.class);
                intentDispensa.putExtra("in_scadenza", true);
                startActivity(intentDispensa);
            }
        });
        laMiaDispensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDispensa = new Intent(getApplicationContext(), ViewDispensaActivity.class);
                intentDispensa.putExtra("in_scadenza", false);
                startActivity(intentDispensa);
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDispensa = new Intent(getApplicationContext(), AddProdottoActivity.class);
                startActivity(intentDispensa);
            }
        });

        dbWrapper.open();
        cursor = dbWrapper.getNotifiche();
        if(!cursor.moveToNext()){
            dbWrapper.createNotifiche(false,false, 3, 3);
        }
        cursor.close();
        dbWrapper.close();

        popolaHome();
        notifiche();

        drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.drawer_report:
                        Intent intentReport = new Intent(getApplicationContext(), ReportActivity.class);
                        startActivity(intentReport);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_dispensa:
                        Intent intentDispensa = new Intent(getApplicationContext(), ViewDispensaActivity.class);
                        intentDispensa.putExtra("in_scadenza", false);
                        startActivity(intentDispensa);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_notifica:
                        Intent intentNotifiche = new Intent(getApplicationContext(), NotificheActivity.class);
                        startActivity(intentNotifiche);
                        drawer.closeDrawers();
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void popolaHome() {
        dbWrapper.open();
        cursor = dbWrapper.getProductListInScadenza(0, "");
        if (cursor.moveToNext()) {
            prodotto1.setText("- " + cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_NAME)));
        } else {
            prodotto1.setText(getString(R.string.no_product_expiring));
        }
        if (cursor.moveToNext()) {
            prodotto2.setText("- " + cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_NAME)));
        } else {
            prodotto2.setText("");
        }
        if (cursor.moveToNext()) {
            prodotto3.setText("- " + cursor.getString(cursor.getColumnIndex(DatabaseWrapper.PRODUCT_NAME)));
        } else {
            prodotto3.setText("");
        }
    }

    private void notifiche(){
        cursor = dbWrapper.getNotifiche();
        cursor.moveToFirst();
        int id_notifiche = cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.NOTIFICHE_ID));
        int quantita = cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.VALORE_NOTIFICHE_QUANTITY));
        int scadenza = cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.VALORE_NOTIFICHE_SCADENZA));
        boolean bool_scadenza = (cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.NOTIFICHE_SCADENZA))) == 1;
        boolean bool_quantita = (cursor.getInt(cursor.getColumnIndex(DatabaseWrapper.NOTIFICHE_QUANTITY))) == 1;

        if (bool_scadenza || bool_quantita){

            Date data_locale = new Date();
            int giorno_locale = data_locale.getDate();
            Date data = new Date();
            try {
                data = (getDate(cursor.getLong(cursor.getColumnIndex(DatabaseWrapper.NOTIFICHE_DATE))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int giorno = data.getDate();


            if(giorno != giorno_locale) {

                String CHANNEL_ID = "Canale";

                // Creazione canale notifiche
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = getString(R.string.channel_name);
                    String description = getString(R.string.channel_description);
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                    channel.setDescription(description);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

                int notificationId = 1;

                if (bool_scadenza){

                    Long data_confronto = data_locale.getTime() + (scadenza * 24 * 60 * 60 * 1000);
                    Cursor cursorS = dbWrapper.getProductNotificheScadenza(data_confronto);

                    while (cursorS.moveToNext()) {

                        notificationId += 1;

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                        String nome = cursorS.getString(cursorS.getColumnIndex(DatabaseWrapper.PRODUCT_NAME));
                        String valore = getDateString(cursorS.getLong(cursorS.getColumnIndex(DatabaseWrapper.PRODUCT_EXPIREDATE)));


                        String textTitle = getString(R.string.notify_expiring_title) + " " + nome;
                        String textContent = getString(R.string.notify_expiring_text) + " " + valore;

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.icon_notifica)
                                .setContentTitle(textTitle)
                                .setContentText(textContent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                        notificationManager.notify(notificationId, builder.build());
                    }
                    cursorS.close();
                }

                if(bool_quantita){

                    Cursor cursorQ = dbWrapper.getProductNotificheQuantita(quantita);

                    while (cursorQ.moveToNext()) {

                        notificationId += 1;

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                        String nome = cursorQ.getString(cursorQ.getColumnIndex(DatabaseWrapper.PRODUCT_NAME));
                        String valore = String.valueOf(cursorQ.getInt(cursorQ.getColumnIndex(DatabaseWrapper.PRODUCT_QUANTITY)));


                        String textTitle = getString(R.string.notify_quantity_title) + nome;
                        String textContent = getString(R.string.notify_quantity_text_1) + " " + valore + " " + getString(R.string.notify_quantity_text_2);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(textTitle)
                                .setContentText(textContent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                        notificationManager.notify(notificationId, builder.build());
                    }

                    cursorQ.close();
                }
            }

            dbWrapper.updateNotificheData(id_notifiche,data_locale.getTime());

        }

        cursor.close();
    }

    public static Date getDate(long milliSeconds) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(milliSeconds));
        Date data = formatter.parse(dateString);
        return data;
    }

    public static String getDateString(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(milliSeconds));
        return dateString;
    }

    @Override
    protected void onResume() {
        super.onResume();
        popolaHome();
    }
}
