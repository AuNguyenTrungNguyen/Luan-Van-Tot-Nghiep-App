package luanvan.luanvantotnghiep;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseAuth;

import luanvan.luanvantotnghiep.Activity.SignInActivity;
import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Fragment.MainFragment;
import luanvan.luanvantotnghiep.Fragment.PeriodicTableFragment;
import luanvan.luanvantotnghiep.Fragment.PickingClassFragment;
import luanvan.luanvantotnghiep.Fragment.RankFragment;
import luanvan.luanvantotnghiep.Fragment.ReactionFragment;
import luanvan.luanvantotnghiep.Fragment.ReactivitySeriesFragment;
import luanvan.luanvantotnghiep.Fragment.SearchFragment;
import luanvan.luanvantotnghiep.Fragment.SolubilityTableFragment;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;
import luanvan.luanvantotnghiep.Util.Constraint;
import luanvan.luanvantotnghiep.Util.PreferencesManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private Toolbar mToolbarMain;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private ChemistryHelper mChemistryHelper;

    private Fragment mFragmentToSet = null;

    private int mCurrentId = -1;

    private boolean mIsPeriodic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        init();

        setupNavigate();

        //Load MainFragment to MainActivity
        mTransaction.replace(R.id.container, MainFragment.newInstance());
        mTransaction.commit();

    }

    private void setupToolbar() {
        mToolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbarMain);
    }

    private void init() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();

        mChemistryHelper = ChemistrySingle.getInstance(this);

    }

    private void setupNavigate() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                if (mFragmentToSet != null) {
                    mTransaction = mManager.beginTransaction();
                    mTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    mTransaction.replace(R.id.container, mFragmentToSet);
                    mTransaction.commit();
                }
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        mNavigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {

            switchFragment(R.id.nav_main, MainFragment.newInstance());

        } else if (id == R.id.nav_periodic_table) {

            switchFragment(R.id.nav_periodic_table, PeriodicTableFragment.newInstance());

        } else if (id == R.id.nav_solubility_table) {

            switchFragment(R.id.nav_solubility_table, SolubilityTableFragment.newInstance());

        } else if (id == R.id.nav_reactivity_series) {

            switchFragment(R.id.nav_reactivity_series, ReactivitySeriesFragment.newInstance());

        } else if (id == R.id.nav_theory) {

            switchFragment(R.id.nav_theory, PickingClassFragment.newInstance());

        } else if (id == R.id.nav_search) {

            switchFragment(R.id.nav_search, SearchFragment.newInstance());

        } else if (id == R.id.nav_reaction) {

            switchFragment(R.id.nav_reaction, ReactionFragment.newInstance());

        }else if (id == R.id.nav_rank) {

            switchFragment(R.id.nav_rank, RankFragment.newInstance());

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            PreferencesManager preferencesManager = PreferencesManager.getInstance();
            preferencesManager.init(this);
            preferencesManager.saveStringData(Constraint.PRE_KEY_PHONE, "");
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(int id, Fragment fragment) {

//        if (id != R.id.nav_periodic_table) {
//            mIsPeriodic = false;
//        }

        if (mCurrentId == id) {
            mFragmentToSet = null;
        } else {
            mFragmentToSet = fragment;
            mCurrentId = id;
        }
    }

}
