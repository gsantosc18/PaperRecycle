package com.e.engapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.Button;

import com.e.engapp.model.Slider;
import com.e.engapp.model.SliderAdapter;

public class MainActivity extends Activity {
    private ViewPager viewPager;
    private SliderAdapter sliderAdapter;
    private Button btnEntrar, btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById( R.id.viewPager );
        btnEntrar = (Button) findViewById( R.id.btnEntrar );
        btnBack = (Button) findViewById( R.id.btnBack );
        btnNext = (Button) findViewById( R.id.btnNext );

        sliderAdapter = new Slider( this ).getAdapter();

        viewPager.setAdapter( sliderAdapter );

        btnNext.setOnClickListener( pressBtnNext() );
        btnBack.setOnClickListener( pressBtnBack() );
        btnEntrar.setOnClickListener( pressBtnEntrar() );
    }

    private View.OnClickListener pressBtnNext() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = viewPager.getCurrentItem()+1,
                    count = sliderAdapter.getCount()-1;

                if( item > count ) item  = 0;

                viewPager.setCurrentItem( item );
            }
        };
    }

    private View.OnClickListener pressBtnBack() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = viewPager.getCurrentItem()-1,
                        count = sliderAdapter.getCount()-1;

                if( item < 0 ) item  = count;

                viewPager.setCurrentItem( item );
            }
        };
    }

    private View.OnClickListener pressBtnEntrar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, LoginActivity.class );
                startActivity( intent );
            }
        };
    }
}
