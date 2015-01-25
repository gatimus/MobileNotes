package io.github.gatimus.mobilenotes;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class Help extends DialogFragment implements DialogInterface.OnClickListener{

    private static final String TAG = "Help";
    private Builder builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG, "Create");
        super.onCreateDialog(savedInstanceState);
        builder = new Builder(getActivity());
        builder.setTitle(R.string.action_help);
        builder.setIcon(android.R.drawable.ic_menu_help);
        builder.setMessage(R.string.msg_help);
        builder.setNeutralButton("Ok", this);
        return builder.create();
    } //onCreateDialog

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which){
            case DialogInterface.BUTTON_POSITIVE :
                dialog.dismiss();
                break;
            case DialogInterface.BUTTON_NEGATIVE :
                dialog.dismiss();
                break;
            case DialogInterface.BUTTON_NEUTRAL :
                dialog.dismiss();
                break;
            default:
                dialog.dismiss();
                break;
        }
    } //onClick

} //class