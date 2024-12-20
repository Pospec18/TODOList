package com.pospecstudio.todolist;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.ItemHolder;
import com.pospecstudio.todolist.data.ItemListsHolder;
import com.pospecstudio.todolist.data.SaveAndLoad;
import com.pospecstudio.todolist.ui.ConfirmDialogFragment;
import com.pospecstudio.todolist.ui.InfoDialogFragment;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.pospecstudio.todolist.data.ListNames;

import java.io.IOException;

public class EditListActivity extends AppCompatActivity {
    private boolean creatingList;
    private ItemListsHolder lists;
    private ListNames list;
    private ActivityResultLauncher<Intent> onImportCSV;
    private ActivityResultLauncher<Intent> onExportCSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            creatingList = extras.getBoolean("creatingList");
            if (Build.VERSION.SDK_INT >= 33)
                lists = extras.getSerializable("lists", ItemListsHolder.class);
            else
                lists = (ItemListsHolder) extras.getSerializable("lists");

            if (!creatingList){
                int index = extras.getInt("itemIdx");
                list = lists.getListToEdit(index);
            }
        }

        setContentView(R.layout.list_edit);
        if (creatingList)
            setTitle("ADD LIST");
        else {
            setTitle("EDIT LIST");
            EditText editName = findViewById(R.id.edit_name);
            editName.setText(list.getListName());
        }

        findViewById(R.id.delete_item).setVisibility(creatingList ? View.GONE : View.VISIBLE);
        findViewById(R.id.export_to_csv).setVisibility(creatingList ? View.GONE : View.VISIBLE);
        findViewById(R.id.to_clipboard).setVisibility(creatingList ? View.GONE : View.VISIBLE);

        onImportCSV = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null || (!saveEdit() && creatingList))
                            return;
                        ItemHolder holder = SaveAndLoad.loadItems(list.getFileName(), getApplicationContext());
                        try {
                            SaveAndLoad.importListFromCSV(getContentResolver(), data.getData(), holder);
                            SaveAndLoad.saveItems(holder, getApplicationContext());
                            showMessage("Import successful.");
                        } catch (IOException e) {
                            Log.e("Import", "Tried to import file", e);
                            showMessage("Problem in reading from file while importing.");
                            return;
                        } catch (IllegalStateException e) {
                            Log.e("Import", "Tried to import file", e);
                            showMessage("Invalid structure of data to import.");
                            return;
                        } catch (Throwable e) {
                            Log.e("Import", "Tried to import file", e);
                            showMessage("Unidentified error occurred while importing:\n" + e.getMessage());
                            return;
                        }

                        finish();
                    }
                }
        );

        onExportCSV = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null)
                            return;

                        ItemHolder holder = SaveAndLoad.loadItems(list.getFileName(), getApplicationContext());
                        try {
                            SaveAndLoad.exportListToCSV(getContentResolver(), data.getData(), holder);
                            showMessage("Export successful.");
                        } catch (IOException e) {
                            Log.e("Export", "Tried to import file", e);
                            showMessage("Problem in writing to file while exporting.");
                        } catch (CsvRequiredFieldEmptyException e) {
                            Log.e("Export", "Tried to import file", e);
                            showMessage("Required field of exporting data is missing.");
                        } catch (CsvDataTypeMismatchException e) {
                            Log.e("Export", "Tried to import file", e);
                            showMessage("Invalid structure of data to export.");
                        } catch (Throwable e) {
                            Log.e("Export", "Tried to import file", e);
                            showMessage("Unidentified error occurred while exporting:\n" + e.getMessage());
                        }
                    }
                }
        );
    }

    private void showMessage(String message) {
        InfoDialogFragment.showMessage(message, getSupportFragmentManager());
    }

    public void applyEdit(View v) {
        if (!saveEdit())
            return;
        finish();
    }

    private boolean saveEdit() {
        EditText editName = findViewById(R.id.edit_name);
        if (editName.getText().length() == 0) {
            showMessage("Name must be filled");
            return false;
        }

        if(creatingList) {
            list = new ListNames(editName.getText().toString(), lists.getNextListName());
            lists.addList(list);
        }
        else
            list.setListName(editName.getText().toString());
        SaveAndLoad.saveLists(lists, getApplicationContext());
        return true;
    }

    public void cancelEdit(View v) {
        finish();
    }

    public void deleteList(View v) {
        ConfirmDialogFragment.showDialog("Delete list?", ((dialog, which) -> {
            lists.removeList(list);
            SaveAndLoad.saveLists(lists, getApplicationContext());
            SaveAndLoad.deleteFile(list.getFileName(), getApplicationContext());
            finish();
        }), getSupportFragmentManager());
    }

    public void listToClipboard(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        StringBuilder text = new StringBuilder();
        ItemHolder holder = SaveAndLoad.loadItems(list.getFileName(), getApplicationContext());
        holder.print(text);
        ClipData clip = ClipData.newPlainText(list.getListName(), text);
        clipboard.setPrimaryClip(clip);
        showMessage("copied to clipboard");
    }

    public void exportToCSV(View v) {
        Intent intent = new Intent()
                .setType("text/csv")
                .setAction(Intent.ACTION_CREATE_DOCUMENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .putExtra(Intent.EXTRA_TITLE, "todo_list_export");
        onExportCSV.launch(intent);
    }

    public void importCSV(View v) {
        EditText editName = findViewById(R.id.edit_name);
        if (editName.getText().length() == 0) {
            showMessage("Name must be filled");
            return;
        }

        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);;
        onImportCSV.launch(intent);
    }
}
