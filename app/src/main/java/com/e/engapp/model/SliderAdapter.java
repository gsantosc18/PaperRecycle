package com.e.engapp.model;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.engapp.R;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private int images[];
    private String texts[];
    private String titles[];


    public SliderAdapter(Context context, int images[], String texts[], String titles[]) {
        this.context = context;
        this.images = images;
        this.texts = texts;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view = layoutInflater.inflate( R.layout.card_apresentation, container, false );

        ImageView imageView = (ImageView) view.findViewById( R.id.imgApresentation );
        TextView textView = (TextView) view.findViewById( R.id.textAprentation );
        TextView titleView = (TextView) view.findViewById( R.id.titleApresentation);

        imageView.setImageResource( images[position] );
        textView.setText( texts[position] );
        titleView.setText( titles[position] );

        container.addView( view );

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView( (View) object );
    }


}
