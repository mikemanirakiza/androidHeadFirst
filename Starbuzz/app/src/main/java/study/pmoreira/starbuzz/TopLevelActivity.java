package study.pmoreira.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.COLUMN_FAVORITE;
import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.COLUMN_NAME;
import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.TABLE_DRINK;

public class TopLevelActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        ListView listView = (ListView) (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);

        try {
            db = new StarbuzzDatabaseHelper(this).getReadableDatabase();
            cursor = db.query(TABLE_DRINK,
                    new String[]{"_id", COLUMN_NAME},
                    COLUMN_FAVORITE + " = 1",
                    null, null, null, null);

            CursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_expandable_list_item_1,
                    cursor,
                    new String[]{COLUMN_NAME},
                    new int[]{android.R.id.text1}, 0);

            listFavorites.setAdapter(adapter);

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            db = new StarbuzzDatabaseHelper(this).getReadableDatabase();
            Cursor cursor = db.query(TABLE_DRINK,
                    new String[]{"_id", COLUMN_NAME},
                    COLUMN_FAVORITE + " = 1",
                    null, null, null, null);
            ListView listFav = (ListView) findViewById(R.id.list_favorites);
            CursorAdapter cursorAdapter = (CursorAdapter) listFav.getAdapter();
            cursorAdapter.changeCursor(cursor);
            this.cursor = cursor;
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
