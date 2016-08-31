package study.pmoreira.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.COLUMN_NAME;
import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.TABLE_DRINK;

/**
 * Created by pedrofelipemm on 26/08/16.
 */
public class DrinkCategoryActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            StarbuzzDatabaseHelper databaseHelper = new StarbuzzDatabaseHelper(this);
            db = databaseHelper.getReadableDatabase();
            cursor = db.query(TABLE_DRINK,
                    new String[]{"_id", COLUMN_NAME},
                    null, null, null, null, null);

            CursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_1,
                    cursor, new String[]{COLUMN_NAME}, new int[]{android.R.id.text1}, 0);

            getListView().setAdapter(adapter);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
