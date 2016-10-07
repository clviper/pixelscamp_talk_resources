package pt.claudio.security.pixelscamp_content2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.claudio.security.pixelscamp_content2.database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.text.Editable;
import android.text.InputType;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Secure extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure);
        final ListView lista = (ListView) findViewById(R.id.listView1);
        String title = "";
        String content = "";
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        DatabaseHelper helper = new DatabaseHelper(this);

        Cursor curs = helper.getSecure();

        if (curs.moveToFirst()) {
            while (!curs.isAfterLast()) {
                title = curs.getString(curs.getColumnIndex("summary"));
                content = curs.getString(curs.getColumnIndex("description"));
                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("summary", title);
                datum.put("description", content);
                data.add(datum);

                curs.moveToNext();
            }
        }
        curs.close();

        final SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2, new String[] { "summary",
                "description" }, new int[] { android.R.id.text1,
                android.R.id.text2 });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("SecureNote");
        alert.setMessage("Insert your code to access");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Editable value = input.getText();
                if (value.toString().equals("pixelsRules")) {
                    lista.setAdapter(adapter);
                }
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

        alert.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.secure, menu);
        return true;
    }

}

