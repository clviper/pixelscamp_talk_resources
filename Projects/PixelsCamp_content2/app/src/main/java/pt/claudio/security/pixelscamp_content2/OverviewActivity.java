package pt.claudio.security.pixelscamp_content2;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import pt.claudio.security.pixelscamp_content2.R;
import pt.claudio.security.pixelscamp_content2.contentprovider.MyContentProvider;
import pt.claudio.security.pixelscamp_content2.database.Table;


@SuppressLint("NewApi")
public class OverviewActivity extends ListActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter adapter;
    public static final int MENU_SECURE = Menu.FIRST;
    public static final int MENU_NEW = Menu.FIRST+1;


    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        fillData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch(item.getItemId())
        {
            case MENU_NEW:
                Intent i = new Intent(this, DetailActivity.class);
                startActivity(i);
                return true;

            case MENU_SECURE:
                Intent i1 = new Intent(this, Secure.class);
                startActivity(i1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Opens the activity create/edit note
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, DetailActivity.class);
        Uri noteUri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
        i.putExtra(MyContentProvider.CONTENT_ITEM_TYPE, noteUri);

        startActivity(i);
    }


    private void fillData() {

        String[] from = new String[] { Table.COLUMN_SUMMARY };
        int[] to = new int[] { R.id.list };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.activity_overview, null, from, to, 0);

        setListAdapter(adapter);
    }





    @SuppressLint("NewApi")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { Table.COLUMN_ID, Table.COLUMN_SUMMARY };
        CursorLoader cursorLoader = new CursorLoader(this,
                MyContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @SuppressLint("NewApi")
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public void securenotes(View view) {
        Intent i = new Intent(this, Secure.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(Menu.NONE, MENU_SECURE, Menu.NONE, "Secure");
        menu.add(Menu.NONE, MENU_NEW, Menu.NONE, "Add");
        return true;
    }

}