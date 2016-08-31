package study.pmoreira.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.COLUMN_DESCRIPTION;
import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.COLUMN_FAVORITE;
import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.COLUMN_IMAGE_RESOURCE_ID;
import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.COLUMN_NAME;
import static study.pmoreira.starbuzz.StarbuzzDatabaseHelper.TABLE_DRINK;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNO = "drinkNo";

    private SQLiteDatabase db = null;
    private Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = getIntent().getExtras().getInt(EXTRA_DRINKNO);

        try {
            StarbuzzDatabaseHelper databaseHelper = new StarbuzzDatabaseHelper(this);
            db = databaseHelper.getWritableDatabase();
            cursor = db.query(TABLE_DRINK,
                    new String[]{COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_IMAGE_RESOURCE_ID, COLUMN_FAVORITE},
                    "_id = ?",
                    new String[]{Integer.toString(drinkId)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);
                String description = cursor.getString(1);
                int imageResourceId = cursor.getInt(2);
                boolean isFavorite = cursor.getInt(3) == 1;

                ImageView imageTextView = (ImageView) findViewById(R.id.photo);
                imageTextView.setImageResource(imageResourceId);
                imageTextView.setContentDescription(name);

                TextView nameTextView = (TextView) findViewById(R.id.name);
                nameTextView.setText(name);

                TextView descriptionTextView = (TextView) findViewById(R.id.description);
                descriptionTextView.setText(description);

                CheckBox favoriteCheckbox = (CheckBox) findViewById(R.id.favorite);
                favoriteCheckbox.setChecked(isFavorite);
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        } finally {
            cursor.close();
            db.close();
        }
    }

    public void onFavoriteClicked(View view) {
        new UpdateDrinkTask().execute(getIntent().getExtras().getInt(EXTRA_DRINKNO));
    }

    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {

        private ContentValues contentValues;

        @Override
        protected void onPreExecute() {
            CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
            contentValues = new ContentValues();
            contentValues.put("FAVORITE", favorite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkNo = drinks[0];

            try{
                SQLiteDatabase db = new StarbuzzDatabaseHelper(DrinkActivity.this).getWritableDatabase();
                db.update(TABLE_DRINK, contentValues, "_id = ?", new String[] {String.valueOf(drinkNo)});
                db.close();
                return true;
            }catch (SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(!success) Toast.makeText(DrinkActivity.this, "Database Unavailable", Toast.LENGTH_SHORT).show();
        }
    }
}
