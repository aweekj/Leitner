package com.uos.leitner;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.uos.leitner.helper.DatabaseHelper;
import com.uos.leitner.model.Category;

import java.util.ArrayList;

// 가로로 추가되는 ViewPager 생성
public class MainActivity extends AppCompatActivity implements CategoryView.Communicator {

    private DatabaseHelper db;
    private MyPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    protected int MAX = 5; // 생성 가능한 페이지 수
    protected boolean flag = false; // false-> CategoryView 생성.  true-> MeasureView 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(getApplicationContext());

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.main_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(MAX);

        pagerAdapter.add(new VerticalActivity());   // MainActivity의 Adapter는 VerticalViewPager를 항목으로 가짐.
    }

    //뒤로가기 클릭
    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 0) {

        }

        else viewPager.setCurrentItem(0);
    }

    // CategoryView 인터페이스 구현
    @Override
    public  void initialize(ArrayList<Category> categoryList) {
        ArrayList<Category> cts = db.getAllCategories();

        for(Category c : cts) {
            categoryList.add(c);
        }

        if (!categoryList.isEmpty()) {
            for (int i = 0; i < categoryList.size(); i++) {
                Fragment fragment = VerticalActivity.newInstance(categoryList.get(i).getSubject_ID());
                pagerAdapter.add(fragment);
            }
        }
    }

    @Override
    public void addCategory(long id) {
        if (pagerAdapter.getCount() <= MAX+1) {

            Fragment fragment = VerticalActivity.newInstance(id);
            pagerAdapter.add(fragment);
            pagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refresh_List(ArrayList<Category> categoryList) {
        ArrayList<Category> cts = db.getAllCategories();
        categoryList.clear();

        for(Category c : cts) {
            categoryList.add(c);
        }

        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void refresh_View(ArrayList<Category> categoryList) {
//        pagerAdapter.remove_all(categoryList);
//        VerticalActivity n = new VerticalActivity();
//        n.aaa();
//        for(int i=0; i<categoryList.size(); i++) {
//            long db_id=categoryList.get(i).getSubject_ID();
//            pagerAdapter.add(VerticalActivity.newInstance(db_id));
//        }
//
//        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNext(int position) {
        //position 0번은 메인페이지
        viewPager.setCurrentItem(position + 1, true);
    }

    @Override
    public void delete(int position) {
        viewPager.removeViewAt(position+1);
        pagerAdapter.remove(position+1);
    }
}
