package io.github.gatimus.mobilenotes;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog.Builder;

public class About extends DialogFragment {

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
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i(TAG, "Ok");
                dialog.dismiss();
            } //onClick
        });
        return builder.create();
    } //onCreateDialog

} //class