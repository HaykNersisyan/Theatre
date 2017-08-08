package diploma.gyumri.theatre.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.view.fragments.ContactUsFragment;
import diploma.gyumri.theatre.view.fragments.MainFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        regBtn = (Button) ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.mainRegBtn);
        getSupportFragmentManager().beginTransaction().add(R.id.container, MainFragment.newInstance(getSupportFragmentManager()), "MainFragment").commit();
        regBtn.setOnClickListener(this);
        regBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonColor, null));
                        ((Button) v).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_pressed, null));
                        return false;
                    case MotionEvent.ACTION_UP:
                        ((Button) v).setTextColor(Color.WHITE);
                        Toast.makeText(MainActivity.this, "asdasd", Toast.LENGTH_SHORT).show();
                        ((Button) v).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                        return false;
                }

                return false;
            }
        });
    }

//    private void abdul(){
//        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
//        startActivity(intent);
//    }
//

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (getSupportFragmentManager().findFragmentByTag("map") != null
                && getSupportFragmentManager().findFragmentByTag("map").isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment(getSupportFragmentManager()), null).commit();
            return;
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            String uri = "facebook://https://www.facebook.com/gyumritheatre/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.contact_us) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ContactUsFragment(), "map")
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}
