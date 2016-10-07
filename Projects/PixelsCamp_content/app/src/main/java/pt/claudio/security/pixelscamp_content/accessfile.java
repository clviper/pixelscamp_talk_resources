package pt.claudio.security.pixelscamp_content;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class accessfile extends ContentProvider {

    private static final int FOLDER = 10;
    private static final int FILE = 20;
    public static final String AUTHORITY = "pt.claudio.security";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/");
    private static final HashMap<String, String> MIME_TYPES = new HashMap<String, String>();
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, "folder/", FOLDER);
        sURIMatcher.addURI(AUTHORITY, "file/", FILE);
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
            throws FileNotFoundException {

        int uriType = sURIMatcher.match(uri);

        if(uriType == 10){
            throw new FileNotFoundException(uri.getPath());
        }

        File f = new File(getContext().getString(R.string._sdcard), uri.getPath());
        Log.d("Px uri.getPath(): ",uri.getPath());

        try{
            Log.d("Px getCanonicalPath",f.getCanonicalPath());
        }catch(Exception e){

        }
        if (f.exists()) {
            return (ParcelFileDescriptor.open(f,
                    ParcelFileDescriptor.MODE_READ_ONLY));
        }
        throw new FileNotFoundException(uri.getPath());

    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        String path = uri.toString();
        for (String extension : MIME_TYPES.keySet()) {
            if (path.endsWith(extension)) {
                return (MIME_TYPES.get(extension));
            }
        }

        return (null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

}