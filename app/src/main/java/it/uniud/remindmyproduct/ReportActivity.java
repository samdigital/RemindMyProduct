package it.uniud.remindmyproduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    private int num_of_product;
    private double value_of_product;
    TextView num_of_product_text;
    TextView value_of_product_text;
    TextView suggestion_text;
    TextView emoticon_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_drawer);

        num_of_product=5;
        value_of_product=0;

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.title_activity_report);

        num_of_product_text=(TextView) findViewById(R.id.reportNumeroProdotti);
        value_of_product_text=(TextView) findViewById(R.id.reportValoreEuro);
        suggestion_text=(TextView) findViewById(R.id.reportSuggest);
        emoticon_text=(TextView) findViewById(R.id.reportEmoticon);

        num_of_product_text.setText(String.valueOf(num_of_product));
        value_of_product_text.setText(String.format("%.2f", value_of_product)+getString(R.string.currency));

        if(num_of_product>0) {
            suggestion_text.setText(getString(R.string.report_suggest_bad));
            emoticon_text.setText(getEmojiByUnicode(0x1F625));
        } else {
            suggestion_text.setText(getString(R.string.report_suggest_good));
            emoticon_text.setText(getEmojiByUnicode(0x1F60A));
        }


        drawer=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_home:
                        //Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                        //startActivity(intentHome);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_report:
                        Intent intentReport = new Intent(getApplicationContext(), ReportActivity.class);
                        startActivity(intentReport);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_dispensa:
                        Intent intentDispensa = new Intent(getApplicationContext(), ViewItemActivity.class);
                        intentDispensa.putExtra("product_id", 87978);
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

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
