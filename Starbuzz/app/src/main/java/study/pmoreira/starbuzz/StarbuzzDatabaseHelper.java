package study.pmoreira.starbuzz;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    private static final String TABLE_DRINK = "DRINK";

    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_IMAGE_RESOURCE_ID = "IMAGE_RESOURCE_ID";
    private static final String COLUMN_FAVORITE = "FAVORITE";

    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    private void insertDrink(SQLiteDatabase db, String name, String description, int resourceId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMAGE_RESOURCE_ID, resourceId);
        db.insert(TABLE_DRINK, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1: createDB(db); break;
            case 2: updateDBVersion2(db); break;
            default: throw new RuntimeException(); //TODO HANDLE ME
        }
    }

    private void createDB(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRINK ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IMAGE_RESOURCE_ID + " INTEGER);");

        insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
        insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.cappuccino);
        insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
    }

    private void updateDBVersion2(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE " + TABLE_DRINK + " ADD COLUMN " + COLUMN_FAVORITE + " NUMERIC;");
    }
}
