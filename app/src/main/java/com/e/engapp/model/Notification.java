package com.e.engapp.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.e.engapp.R;

public class Notification {
    public static int ERROR = 0;
    public static int SUCCESS = 1;

    private static AlertDialog alertDialog;

    public static void make(Context context, String title, String message, int type) {
        alertDialog = new AlertDialog.Builder( context ).create();
        alertDialog.setTitle( title );
        alertDialog.setMessage( message );

        switch (type) {
            case 0:
                alertDialog.setIcon( R.drawable.icon_error );
                break;
            case 1:
                alertDialog.setIcon( R.drawable.icon_success );
                break;
        }

        alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        } );

        alertDialog.show();
    }
}
