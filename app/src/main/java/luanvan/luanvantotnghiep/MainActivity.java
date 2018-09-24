package luanvan.luanvantotnghiep;

import android.content.Context;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import luanvan.luanvantotnghiep.Database.ChemistryHelper;
import luanvan.luanvantotnghiep.Fragment.MainFragment;
import luanvan.luanvantotnghiep.Fragment.PeriodicTableFragment;
import luanvan.luanvantotnghiep.Fragment.PickingClassFragment;
import luanvan.luanvantotnghiep.Fragment.ReactionFragment;
import luanvan.luanvantotnghiep.Fragment.ReactivitySeriesFragment;
import luanvan.luanvantotnghiep.Fragment.SearchFragment;
import luanvan.luanvantotnghiep.Fragment.SolubilityTableFragment;
import luanvan.luanvantotnghiep.Model.Anion;
import luanvan.luanvantotnghiep.Model.Cation;
import luanvan.luanvantotnghiep.Model.ChemicalReaction;
import luanvan.luanvantotnghiep.Model.Chemistry;
import luanvan.luanvantotnghiep.Model.Compound;
import luanvan.luanvantotnghiep.Model.CreatedReaction;
import luanvan.luanvantotnghiep.Model.Element;
import luanvan.luanvantotnghiep.Model.Group;
import luanvan.luanvantotnghiep.Model.ProducedBy;
import luanvan.luanvantotnghiep.Model.ReactWith;
import luanvan.luanvantotnghiep.Model.Solute;
import luanvan.luanvantotnghiep.Model.Type;
import luanvan.luanvantotnghiep.Util.ChemistrySingle;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private Toolbar mToolbarMain;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private NavigationView mNavigationRight;

    private ChemistryHelper mChemistryHelper;

    private Fragment mFragmentToSet = null;

    private MenuItem mMnRight = null;
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
        mNavigationRight = findViewById(R.id.nav_view_right);
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();

        mChemistryHelper = ChemistrySingle.getInstance(this);

        if (mMnRight != null) {
            mMnRight.setVisible(false);
        }
    }

    private void setupNavigate() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationRight.setNavigationItemSelectedListener(this);

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
        mNavigationRight.setItemIconTintList(null);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mNavigationRight);

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
        int idGroup = item.getGroupId();

        //close right navigation when user click right navigation
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mNavigationRight);

        //handle hide and show nav right by item
        if (idGroup == R.id.group_left_knowledge || idGroup == R.id.group_left_my_info || idGroup == R.id.group_left_app) {

            if (id == R.id.nav_periodic_table) {
                mMnRight.setVisible(true);
            } else {
                mMnRight.setVisible(false);
            }

        } else if (idGroup == R.id.group_right_type || idGroup == R.id.group_right_state_matter) {
            mMnRight.setVisible(true);
        }

        //handle load fragment
        if (id == R.id.nav_main) {

            switchFragment(R.id.nav_main, MainFragment.newInstance());

        } else if (id == R.id.nav_periodic_table) {

            if (!mIsPeriodic) {
                mIsPeriodic = true;
                switchFragment(R.id.nav_periodic_table, PeriodicTableFragment.newInstance());
                mNavigationRight.getMenu().getItem(0).setChecked(true);
            }

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

        } else if (id == R.id.nav_all) {

            controlRightNavgate(R.id.nav_all, 0);

        } else if (id == R.id.nav_alkali_metal) {

            controlRightNavgate(R.id.nav_alkali_metal, 1);

        } else if (id == R.id.nav_alkaline_earth_metal) {

            controlRightNavgate(R.id.nav_alkaline_earth_metal, 2);

        } else if (id == R.id.nav_post_transition_metal) {

            controlRightNavgate(R.id.nav_post_transition_metal, 3);

        } else if (id == R.id.nav_metalloid) {

            controlRightNavgate(R.id.nav_metalloid, 4);

        } else if (id == R.id.nav_transition_metal) {

            controlRightNavgate(R.id.nav_transition_metal, 5);

        } else if (id == R.id.nav_nonmetal) {

            controlRightNavgate(R.id.nav_nonmetal, 6);

        } else if (id == R.id.nav_halogen) {

            controlRightNavgate(R.id.nav_halogen, 7);

        } else if (id == R.id.nav_noble_gas) {

            controlRightNavgate(R.id.nav_noble_gas, 8);

        } else if (id == R.id.nav_lanthanide) {

            controlRightNavgate(R.id.nav_lanthanide, 9);

        } else if (id == R.id.nav_actinide) {

            controlRightNavgate(R.id.nav_actinide, 10);

        } else if (id == R.id.nav_unknown_chemical_properties) {

            controlRightNavgate(R.id.nav_unknown_chemical_properties, 11);

        } else if (id == R.id.nav_solid) {

            controlRightNavgate(R.id.nav_solid, 12);

        } else if (id == R.id.nav_liquid) {

            controlRightNavgate(R.id.nav_liquid, 13);

        } else if (id == R.id.nav_gas) {

            controlRightNavgate(R.id.nav_gas, 14);

        } else if (id == R.id.nav_unknown) {

            controlRightNavgate(R.id.nav_unknown, 15);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMnRight = menu.findItem(R.id.mn_right);
        mMnRight.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_right:
                mDrawerLayout.openDrawer(mNavigationRight);
                break;
        }
        return true;
    }

    private void switchFragment(int id, Fragment fragment) {

        if (id != R.id.nav_periodic_table) {
            mIsPeriodic = false;
        }

        if (mCurrentId == id) {
            mFragmentToSet = null;
        } else {
            mFragmentToSet = fragment;
            mCurrentId = id;
        }
    }

    private void controlRightNavgate(int id, int type) {
        if (mCurrentId != id) {
            mCurrentId = id;
            mFragmentToSet = PeriodicTableFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("ID_TYPE", type);
            mFragmentToSet.setArguments(bundle);
        }
    }
}
