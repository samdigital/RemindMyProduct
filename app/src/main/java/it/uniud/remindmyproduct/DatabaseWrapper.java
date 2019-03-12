package it.uniud.remindmyproduct;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

public class DatabaseWrapper {

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private static final String DATABASE_TABLE = "prodotti";

    public static final String PRODUCT_ID = "id_prodotto";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_EXPIREDATE = "expiredate";
    public static final String PRODUCT_INSERTED = "insertdate";
    public static final String PRODUCT_QUANTITY = "quantity";
    public static final String PRODUCT_VALUE = "value";
    public static final String PRODUCT_ISOPEN = "iopen";
    public static final String PRODUCT_CATEGORY = "category";

    public DatabaseWrapper(Context context) {
        this.context = context;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "remindmyproduct.db";
        private static final int DATABASE_VERSION = 1;

        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " ("
                + PRODUCT_ID + " integer primary key autoincrement, "
                + PRODUCT_NAME + " text not null, "
                + PRODUCT_DESCRIPTION + " text, "
                + PRODUCT_EXPIREDATE + " date, "
                + PRODUCT_INSERTED + " date , "
                + PRODUCT_QUANTITY + " integer, "
                + PRODUCT_VALUE + " double, "
                + PRODUCT_ISOPEN + " boolean, "
                + PRODUCT_CATEGORY + " integer);";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }



        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(database);
        }
    }

    public DatabaseWrapper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String nome, String descrizione, Integer categoria, Integer quantita, Date scadenza, Double valore) {
        ContentValues values = new ContentValues();

        values.put(PRODUCT_NAME, nome);
        values.put(PRODUCT_DESCRIPTION, descrizione);
        values.put(PRODUCT_CATEGORY, categoria);
        values.put(PRODUCT_QUANTITY, quantita);
        //values.put(PRODUCT_EXPIREDATE, scadenza);
        //values.put(PRODUCT_INSERTED, )
        values.put(PRODUCT_VALUE, valore);
        values.put(PRODUCT_ISOPEN, true);
        Log.d("DB ACT", "ho i valori");
        return  values;
    }

    public long createProduct(String nome, String descrizione, Integer categoria, Integer quantita, Date scadenza, Double valore) {
        Log.d("DB ACT", "prima di initialvalues");
        ContentValues initialValues = createContentValues(nome, descrizione, categoria, quantita, scadenza, valore);
        Log.d("DB ACT", "prima delle query");
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    public boolean updateProduct(long id_prodotto, String nome, String descrizione, Integer categoria, Integer quantita, Date scadenza, Double valore) {
        ContentValues updateValues = createContentValues(nome, descrizione, categoria, quantita, scadenza, valore);
        return database.update(DATABASE_TABLE, updateValues, PRODUCT_ID+"="+id_prodotto, null)>0;
    }

    public boolean deleteProduct(long id_prodotto) {
        return database.delete(DATABASE_TABLE, PRODUCT_ID+"="+id_prodotto, null)>0;
    }

    public Cursor getProductList(int category, String filter) {
        if(category==0){
            return database.query(DATABASE_TABLE, new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_EXPIREDATE, PRODUCT_QUANTITY, PRODUCT_CATEGORY}, PRODUCT_NAME + " like '%" + filter + "%'", null, null, null, PRODUCT_NAME);
        } else {
            return database.query(DATABASE_TABLE, new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_EXPIREDATE, PRODUCT_QUANTITY, PRODUCT_CATEGORY}, PRODUCT_NAME + " like '%" + filter + "%' AND " + PRODUCT_CATEGORY + "=" + category, null, null, null, PRODUCT_NAME);
        }
    }

    public Cursor getProductListInScadenza(int category, String filter) {
        if(category==0){
            return database.query(DATABASE_TABLE, new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_EXPIREDATE, PRODUCT_QUANTITY, PRODUCT_CATEGORY}, PRODUCT_NAME + " like '%" + filter + "%'", null, null, null, PRODUCT_EXPIREDATE);
        } else {
            return database.query(DATABASE_TABLE, new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_EXPIREDATE, PRODUCT_QUANTITY, PRODUCT_CATEGORY}, PRODUCT_NAME + " like '%" + filter + "%' AND " + PRODUCT_CATEGORY + "=" + category, null, null, null, PRODUCT_EXPIREDATE);
        }
    }

    public Cursor getProduct(long id_prodotto) {
        Log.d("DB QUERY", "ID PRODOTTO IN QUERY"+id_prodotto);
        return database.query(true, DATABASE_TABLE, new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_EXPIREDATE, PRODUCT_INSERTED, PRODUCT_QUANTITY, PRODUCT_VALUE, PRODUCT_ISOPEN}, PRODUCT_ID + "=" + id_prodotto, null, null, null, null, null);
    }


}
