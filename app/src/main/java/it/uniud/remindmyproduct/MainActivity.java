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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_drawer);


        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Remind My Product");

        drawer=(DrawerLayout) findViewById(R.id.main_drawer_layout);
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
}
