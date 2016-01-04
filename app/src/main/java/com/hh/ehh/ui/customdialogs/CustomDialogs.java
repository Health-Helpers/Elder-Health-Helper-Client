package com.hh.ehh.ui.customdialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog.Builder;

import com.hh.ehh.R;


public class CustomDialogs {

    public static Builder noInternetConnection(final Context context){
        final Builder licenceDialog = new Builder(context);
        licenceDialog.setTitle(context.getResources().getString(R.string.internet_tittle));
        licenceDialog.setMessage(context.getResources().getString(R.string.internet_body));
        return licenceDialog;
    }

    public static Builder closeDialog(Context context,DialogInterface.OnClickListener accept, DialogInterface.OnClickListener cancel) {
        final Builder closeDialog = new Builder(context);
        closeDialog.setTitle(context.getResources().getString(R.string.close));
        closeDialog.setMessage(context.getResources().getString(R.string.close_body));
        closeDialog.setPositiveButton(context.getResources().getString(R.string.Accept), accept);
        closeDialog.setNegativeButton(context.getResources().getString(R.string.cancel), cancel);
        return closeDialog;
    }

}