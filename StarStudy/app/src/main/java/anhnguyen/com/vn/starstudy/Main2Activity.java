package anhnguyen.com.vn.starstudy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import anhnguyen.com.vn.starstudy.chuongtrinh.DanhGia;
import anhnguyen.com.vn.starstudy.chuongtrinh.Home;
import anhnguyen.com.vn.starstudy.chuongtrinh.LoiKhuyen;
import anhnguyen.com.vn.starstudy.chuongtrinh.OnTap;
import anhnguyen.com.vn.starstudy.chuongtrinh.ThiThuTracNghiem;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    ImageView imgAvt;
    TextView txtvTen, txtvThanhPho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        callFragment(new Home());
    }

    public void setAvt(String gt) {
        imgAvt = (ImageView)findViewById(R.id.imgAavatar);
        if(gt.equalsIgnoreCase("Nam")){
            imgAvt.setBackgroundResource(R.drawable.boy);
        }else if (gt.equalsIgnoreCase("Nữ")){
            imgAvt.setBackgroundResource(R.drawable.girl);
        }

    }
    public void setThongTin(String ten,String thanhpho){
        txtvTen = (TextView) findViewById(R.id.tvHoTen);
        txtvThanhPho = (TextView)findViewById(R.id.textViewTP);
        txtvTen.setText(ten);
        txtvThanhPho.setText(thanhpho);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(Main2Activity.this);

        //Custom title for alertdialog named alBuilder
        LayoutInflater inflater = (Main2Activity.this).getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_view_alertdialog, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText("THOÁT HẲN ỨNG DỤNG");
        alBuilder.setCustomTitle(view);

        alBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_book) {
            // Handle the camera action
            callFragment(new OnTap());
        } else if (id == R.id.nav_test) {
            callFragment(new ThiThuTracNghiem());

        } else if (id == R.id.nav_chart) {
            callFragment(new DanhGia());

        } else if (id == R.id.nav_comment) {
            callFragment(new LoiKhuyen());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void callFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main2, fragment);
        transaction.commit();
    }
}
