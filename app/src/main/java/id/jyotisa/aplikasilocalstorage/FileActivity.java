package id.jyotisa.aplikasilocalstorage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnNew, btnOpen, btnSave;
    EditText editContent, editTitle;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.btnNew);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnSave = (Button) findViewById(R.id.btnSave);
        editContent = (EditText) findViewById(R.id.editContent);
        editTitle = (EditText) findViewById(R.id.editTitle);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnNew:
                newFile();
                break;
            case R.id.btnOpen:
                openFile();
                break;
            case R.id.btnSave:
                saveFile();
                break;
        }
    }

    public void newFile() {
        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        editContent.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    private void openFile() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select file to open");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();

        alert.show();
    }

    public void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title required", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + editTitle.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }
}
