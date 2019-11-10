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
            "O Projeto Visa a coleta de papel de cada setor da instituição com o intuito de " +
                    "promover a conscientização da comunidade acadêmica, ensinando a educação " +
                    "ambiental e a reciclagem do papel na instituição.",
            "O NEA (Núcleo de Estudos Ambientais) é um programa de ações de gerenciamento de " +
                    "resídos criado em junho de 2017, com intituito de conscientizar a comunidade " +
                    "acadêmica, além de coletar papel e destinar para empresas de reutilização e " +
                    "reciclagem.",
            "Alunos: \n" +
                    "Aida Serrão,\n" +
                    "Carlos Hideo Kajitani,\n" +
                    "Luane Nascimento\n" +
            "Professora: \n" +
                    "Msc. Elane Lemos",
            "Estudantes do curso de Bacharelado em Engenharia Ambiental e Sanitária e integrantes" +
                    " do NEA."
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
