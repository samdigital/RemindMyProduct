package it.uniud.remindmyproduct;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

public class NotificheActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText scadenza;
    EditText quantita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiche_drawer);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notifiche");

        drawer=(DrawerLayout) findViewById(R.id.notifiche_drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.drawer_home:
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_dispensa:
                        Intent intentDispensa = new Intent(getApplicationContext(), ViewItemActivity.class);
                        startActivity(intentDispensa);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_report:
                        Intent intentReport = new Intent(getApplicationContext(), ReportActivity.class);
                        startActivity(intentReport);
                        drawer.closeDrawers();
                        return true;

                    case R.id.drawer_notifica:
                        drawer.closeDrawers();
                        return true;

                    default:
                        return false;
                }
            }
        });

    }
}
