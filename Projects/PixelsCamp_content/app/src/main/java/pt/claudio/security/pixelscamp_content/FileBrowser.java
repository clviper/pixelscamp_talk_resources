package pt.claudio.security.pixelscamp_content;

/**
 * Created by clviper on 30-09-2016.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FileBrowser extends ListActivity {

    private File file;
    public final static String EXTRA_MESSAGE = "";
    private List<String> FFList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);


        String filename = "mysecretfile.txt";
        String string = "Hello friend, I Love Path Transversal too.";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FFList = new ArrayList<String>();

        String root_sd = Environment.getExternalStorageDirectory().toString();
        file = new File( root_sd + "/" ) ;
        File list[] = file.listFiles();


        for( int i=0; i< list.length; i++)
        {
            FFList.add( list[i].getName() );
        }
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FFList ));
    }


    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        File temp_file = new File( file, FFList.get( position ) );

        if( !temp_file.isFile())
        {
            file = new File( file, FFList.get( position ));
            File list[] = file.listFiles();
            FFList.clear();

            for( int i=0; i< list.length; i++)
            {
                FFList.add( list[i].getName() );
            }
            Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_SHORT).show();
            setListAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, FFList ));

        }else{
            Uri file_uri = Uri.parse(accessfile.CONTENT_URI + FFList.get( position ));

            Intent intent = new Intent(this, Content.class);
            intent.setData(file_uri);
            startActivity(intent);
        }

    }

    public void onBackPressed() {

        if(file.getName().equals(("sdcard"))){
            Toast.makeText(getApplicationContext(), "Can't open the parent folder", Toast.LENGTH_SHORT).show();
            return;
        }

        String parent = file.getParent().toString();
        file = new File( parent ) ;
        File list[] = file.listFiles();
        FFList.clear();

        for( int i=0; i< list.length; i++)
        {
            FFList.add( list[i].getName() );
        }

        Toast.makeText(getApplicationContext(), parent, Toast.LENGTH_SHORT).show();
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FFList ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.file_browser, menu);
        return true;
    }

}