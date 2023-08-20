package com.example.todolist.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import org.jetbrains.annotations.NotNull;

public class ErrorDialogFragment extends DialogFragment {
    public static final String messageId = "message";

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments == null)
            return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage(arguments.getString(messageId))
                .setNeutralButton("ok", (dialog, which) -> {});
        return builder.create();
    }
}
