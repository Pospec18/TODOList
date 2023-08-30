package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.data.*;
import com.example.todolist.ui.InfoDialogFragment;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

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
            lists = (ItemListsHolder) extras.getSerializable("lists");

            if (!creatingList){
                int index = extras.getInt("itemIdx");
                list = lists.getListsData().get(index);
            }
        }

        setContentView(R.layout.list_edit);
        if (creatingList)
            setTitle("ADD LIST");
        else {
            setTitle("EDIT LIST");
            EditText editName = findViewById(R.id.editName);
            editName.setText(list.getListName());
        }

        findViewById(R.id.deleteItem).setVisibility(creatingList ? View.GONE : View.VISIBLE);
        findViewById(R.id.exportToCsv).setVisibility(creatingList ? View.GONE : View.VISIBLE);

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
                            showMessage("Import successful.");
                        } catch (IOException e) {
                            e.printStackTrace();
                            showMessage("Problem in reading from file while importing.");
                            return;
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                            showMessage("Invalid structure of data to import.");
                            return;
                        } catch (Throwable e) {
                            e.printStackTrace();
                            showMessage("Unidentified error occurred while importing.");
                            return;
                        }

                        SaveAndLoad.saveItems(holder, getApplicationContext());
                        if (creatingList)
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
                            e.printStackTrace();
                            showMessage("Problem in writing to file while exporting.");
                        } catch (CsvRequiredFieldEmptyException e) {
                            e.printStackTrace();
                            showMessage("Required field of exporting data is missing.");
                        } catch (CsvDataTypeMismatchException e) {
                            e.printStackTrace();
                            showMessage("Invalid structure of data to export.");
                        } catch (Throwable e) {
                            e.printStackTrace();
                            showMessage("Unidentified error occurred while exporting.");
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
        EditText editName = findViewById(R.id.editName);
        if (editName.getText().length() == 0) {
            showMessage("Name must be filled");
            return false;
        }

        if(creatingList) {
            list = new ListNames(editName.getText().toString(), lists.getNextListName());
            lists.getListsData().add(list);
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
        lists.getListsData().remove(list);
        SaveAndLoad.saveLists(lists, getApplicationContext());
        SaveAndLoad.deleteFile(list.getFileName(), getApplicationContext());
        finish();
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
        EditText editName = findViewById(R.id.editName);
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
