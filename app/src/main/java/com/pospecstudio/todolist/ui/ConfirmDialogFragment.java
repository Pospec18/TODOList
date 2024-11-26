package com.pospecstudio.todolist.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.example.todolist.R;
import org.jetbrains.annotations.NotNull;

public class ConfirmDialogFragment extends DialogFragment {
    public static final String messageId = "message";
    public final DialogInterface.OnClickListener onConfirm;

    public ConfirmDialogFragment(DialogInterface.OnClickListener onConfirm) {
        this.onConfirm = onConfirm;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments == null)
            return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.alert_dialog)
                .setMessage(arguments.getString(messageId))
                .setPositiveButton("confirm", onConfirm)
                .setNegativeButton("cancel", (dialog, which) -> {});
        return builder.create();
    }

    public static void showDialog(String message, DialogInterface.OnClickListener onConfirm, FragmentManager manager) {
        DialogFragment dialogFragment = new ConfirmDialogFragment(onConfirm);
        Bundle b = new Bundle();
        b.putString(InfoDialogFragment.messageId, message);
        dialogFragment.setArguments(b);
        dialogFragment.show(manager, "confirm");
    }
}
