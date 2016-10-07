package pt.claudio.security.pixelscamp_content2;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import pt.claudio.security.pixelscamp_content2.R;
import pt.claudio.security.pixelscamp_content2.contentprovider.MyContentProvider;
import pt.claudio.security.pixelscamp_content2.database.Table;

public class DetailActivity extends Activity {
    private Spinner mCategory;
    private EditText mTitleText;
    private EditText mBodyText;

    private Uri noteUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);

        mCategory = (Spinner) findViewById(R.id.category);
        mTitleText = (EditText) findViewById(R.id.edit_summary);
        mBodyText = (EditText) findViewById(R.id.edit_description);
        Button confirmButton = (Button) findViewById(R.id.edit_button);

        Bundle extras = getIntent().getExtras();

        noteUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(MyContentProvider.CONTENT_ITEM_TYPE);

        if (extras != null) {
            noteUri = extras
                    .getParcelable(MyContentProvider.CONTENT_ITEM_TYPE);

            fillData(noteUri);
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mTitleText.getText().toString())) {
                    makeToast();
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }

        });
    }

    private void fillData(Uri uri) {
        String[] projection = { Table.COLUMN_SUMMARY,
                Table.COLUMN_DESCRIPTION, Table.COLUMN_CATEGORY };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String category = cursor.getString(cursor
                    .getColumnIndexOrThrow(Table.COLUMN_CATEGORY));

            for (int i = 0; i < mCategory.getCount(); i++) {

                String s = (String) mCategory.getItemAtPosition(i);
                if (s.equalsIgnoreCase(category)) {
                    mCategory.setSelection(i);
                }
            }

            mTitleText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(Table.COLUMN_SUMMARY)));
            mBodyText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(Table.COLUMN_DESCRIPTION)));

            cursor.close();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(MyContentProvider.CONTENT_ITEM_TYPE, noteUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        String category = (String) mCategory.getSelectedItem();
        String summary = mTitleText.getText().toString();
        String description = mBodyText.getText().toString();

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_CATEGORY, category);
        values.put(Table.COLUMN_SUMMARY, summary);
        values.put(Table.COLUMN_DESCRIPTION, description);

        if (noteUri == null) {
            // New
            noteUri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
        } else {
            // Update
            getContentResolver().update(noteUri, values, null, null);
        }
    }

    private void makeToast() {
        Toast.makeText(DetailActivity.this, "Please maintain a summary",
                Toast.LENGTH_LONG).show();
    }
}
