package io.github.gatimus.mobilenotes;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog.Builder;

public class About extends DialogFragment implements DialogInterface.OnClickListener{

    private static final String TAG = "About";
    private Builder builder;
    private StringBuilder stringBuilder;
    private String msg;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.v(TAG, "Create");
        super.onCreateDialog(savedInstanceState);
        stringBuilder = new StringBuilder();
        stringBuilder.append("v");
        stringBuilder.append(BuildConfig.VERSION_CODE);
        stringBuilder.append("\n");
        stringBuilder.append("Version: ");
        stringBuilder.append(BuildConfig.VERSION_NAME);
        stringBuilder.append("\n");
        stringBuilder.append(getString(R.string.msg_about));
        msg = stringBuilder.toString();
        builder = new Builder(getActivity());
        builder.setTitle(R.string.action_about);
        builder.setMessage(msg);
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