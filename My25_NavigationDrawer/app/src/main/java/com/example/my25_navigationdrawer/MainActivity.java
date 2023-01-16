package com.example.my25_navigationdrawer;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "main:MainActivity";

    Toolbar toolbar;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    FloatingActionButton fab;
    NavigationView nav_view;
    DrawerLayout draw_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_view = findViewById(R.id.nav_view);
        // implement Listener 할때는 반드시 아래와 같이 정의한다
        nav_view.setNavigationItemSelectedListener(this);

        // toolbar 적용 : res->theme->NoActionBar로 변경
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // drawerLayout을 찾아서 토클 리스너를 붙인다
        draw_layout = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, draw_layout, toolbar,
                R.string.draw_open, R.string.draw_close );
        draw_layout.addDrawerListener(toggle);
        toggle.syncState();

        // 프래그먼트 객체를 생성하고 프래임레이아웃에 초기화를 시킨다
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment1).commit();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 클릭한 아이템의 id를 얻는다
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                onFragmentSelected(0);
                break;
            case R.id.nav_gallery:
                onFragmentSelected(1);
                break;
            case R.id.nav_slideshow:
                onFragmentSelected(2);
                break;
            case R.id.nav_tools:
                onFragmentSelected(3);
                break;
            case R.id.nav_share:
                onFragmentSelected(4);
                break;
            case R.id.nav_send:
                onFragmentSelected(5);
                break;
        }
        // 메뉴 선택 후 드로어가 사라지게 한다
        draw_layout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void onFragmentSelected(int position){
        Log.d(TAG, "onFragmentSelected: " + position);
        Fragment selFragment = null;

        if(position == 0){
            selFragment = fragment1;
            toolbar.setTitle("첫번째 화면");
        }else if(position == 1){
            selFragment = fragment2;
            toolbar.setTitle("두번째 화면");
        }else if(position == 2){
            selFragment = fragment3;
            toolbar.setTitle("세번째 화면");
        }else if(position == 3){
            selFragment = fragment1;
            toolbar.setTitle("네번째 화면");
        }else if(position == 4){
            selFragment = fragment2;
            toolbar.setTitle("다섯번째 화면");
        }else if(position == 5){
            selFragment = fragment3;
            toolbar.setTitle("여섯번째 화면");
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, selFragment).commit();
    }


}