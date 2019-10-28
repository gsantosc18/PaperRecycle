package com.e.engapp.model;

import android.content.Context;

import com.e.engapp.R;

public class Slider {

    private Context context;

    int images[] = new int[]{
            R.drawable.app3,
            R.drawable.app4,
            R.drawable.app2,
            R.drawable.app1
    };

    String texts[] = new String[] {
            "Conteúdo 1 de 4",
            "Conteúdo 2 de 4",
            "Conteúdo 3 de 4",
            "Conteúdo 4 de 4"
    };

    String titles[] = new String[] {
            "Sobre o projeto",
            "O que é o NEA?",
            "Sobre a equipe",
            "Quem somos?"
    };

    public Slider (Context context) {
        this.context = context;
    }

    public SliderAdapter getAdapter() {
        return new SliderAdapter( context, images, texts, titles );
    }
}
