package diploma.gyumri.theatre.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.model.User;
import diploma.gyumri.theatre.view.fragments.AnnalsFragment;
import diploma.gyumri.theatre.view.fragments.ContactUsFragment;
import diploma.gyumri.theatre.view.fragments.MainFragment;
import diploma.gyumri.theatre.view.fragments.UserTicketsFragment;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Button regBtn;
    private Button loginButton;
    private LinearLayout loginnedLayout;
    private TextView userName;
    private TextView userSurname;
    private LinearLayout un;
    private MenuItem logOut;


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
        logOut = navigationView.getMenu().findItem(R.id.log_out);

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
                    case MotionEvent.ACTION_CANCEL:
                        ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                        ((Button) v).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                        return false;
                    case MotionEvent.ACTION_UP:
                        ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                        ((Button) v).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                        return false;
                }
                return false;
            }
        });
        loginButton = (Button) ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.mainLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonColor, null));
                        ((Button) v).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_pressed, null));
                        return false;
                    case MotionEvent.ACTION_CANCEL:
                        ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                        ((Button) v).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                        return false;
                    case MotionEvent.ACTION_UP:
                        ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                        ((Button) v).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                        return false;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i("in", "onCreateOptionsMenu: "+logOut );
        un = (LinearLayout) ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.unlogin_header);
        loginnedLayout = (LinearLayout) ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.logined_header);

        if (Constants.USER != null) {
            un.setVisibility(View.INVISIBLE);
            loginnedLayout.setVisibility(View.VISIBLE);
            userName = (TextView) ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.user_name);
            userName.setVisibility(View.VISIBLE);
            userName.setText(Constants.USER.getName());
            userSurname = (TextView) ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.user_surname);
            userSurname.setVisibility(View.VISIBLE);
            userSurname.setText(Constants.USER.getSurname());
            logOut.setVisible(true);
            Log.i("TAGIK", "onResume: ." + Constants.USER.getToken());
        } else {
            logOut.setVisible(false);

            un.setVisibility(View.VISIBLE);
            loginnedLayout.setVisibility(View.GONE);
        }
        super.onResume();
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
        if (getSupportFragmentManager().findFragmentByTag("MainFragment") != null &&
                getSupportFragmentManager().findFragmentByTag("MainFragment").isVisible()) {
            finish();
        }
        if (getSupportFragmentManager().findFragmentByTag("map") != null
                && getSupportFragmentManager().findFragmentByTag("map").isVisible()) {
            switchFragment(MainFragment.newInstance(getSupportFragmentManager()), "MainFragment");
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

        if (id == R.id.representation) {
            if (getSupportFragmentManager().findFragmentByTag("MainFragment") != null &&
                    getSupportFragmentManager().findFragmentByTag("MainFragment").isVisible()) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);

                }
            } else {
                switchFragment(MainFragment.newInstance(getSupportFragmentManager()), "MainFragment");
            }


        } else if (id == R.id.tickets) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new UserTicketsFragment(), "annals").addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_slideshow) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new AnnalsFragment(), "annals").addToBackStack(null)
                    .commit();
        } else if (id == R.id.contact_us) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ContactUsFragment(), "map")
                    .commit();
        } else if (id == R.id.log_out) {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<User> drugses = realm.where(User.class).findAll();
            realm.beginTransaction();
            drugses.deleteAllFromRealm();
            realm.commitTransaction();
            Constants.USER = null;
            un.setVisibility(View.VISIBLE);
            loginnedLayout.setVisibility(View.GONE);
//            item.setVisible(false);
            logOut.setVisible(false);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    private void switchFragment(Fragment fragment, String tag) {
        if (fragment instanceof MainFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, tag).addToBackStack(null).commit();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//        super.onCreateOptionsMenu(menu);
//        logOut = menu.findItem(R.id.log_out);
//        Log.i("in", "onCreateOptionsMenu: "+logOut );
//        return super.onCreateOptionsMenu(menu);
//    }
}
