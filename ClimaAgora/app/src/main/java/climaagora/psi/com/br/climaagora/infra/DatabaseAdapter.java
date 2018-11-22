package climaagora.psi.com.br.climaagora.infra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import climaagora.psi.com.br.climaagora.repository.SensorRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseAdapter.
 */
public class DatabaseAdapter extends SQLiteOpenHelper {
	/** The Constant TAG. */
	private static final String TAG = "GenericDAO";
	
	/** The Constant KEY_ID. */
	public static final String KEY_ID = "_id";
	
	/** The sql. */
	private static String sql;
	
	// Database adapter instance
	/** The db adapter. */
	private static DatabaseAdapter dbAdapter;

	// Database object
	/** The db. */
	private static SQLiteDatabase db;

	// Database filename
	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "sendor-data.db";

	// Database version
	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 5;

	/**
	 * Instantiates a new database adapter.
	 *
	 * @param context the context
	 * @param name the name
	 * @param factory the factory
	 * @param version the version
	 */
	public DatabaseAdapter(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		Log.i(TAG, "Creating or opening database [ " + DATABASE_NAME + " ].");
	}
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
	
		Log.i(TAG, "Trying to create database table if it isn't existed [ "	+ sql + " ].");
		
		try {
		
			String[] statements = new String[] { 
					
					//Table for create Expense Repository

					SensorRepository.SQL_CREATE_TABLE
			};

			// then
			for (String sql : statements) {
				db.execSQL(sql);
			}
		
		} catch (SQLException se) {
			Log.e(TAG,
					"Cound not create the database table according to the SQL statement [ "
							+ sql + " ].", se);
		}
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(getClass().getSimpleName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		
		try {
			
			String[] statements = new String[] { 
					//Table for create Expense Repository
					SensorRepository.SQL_DROP_TABLE
				};

			// then
			for (String sql : statements) {
				db.execSQL(sql);
			}
		
		} catch (SQLException se) {
			Log.e(TAG,
					"Cound not create the database table according to the SQL statement [ "
							+ sql + " ].", se);
		}
		// db.execSQL("DROP TABLE IF EXISTS " + Owner.SQL_TABLE_NAME);
		onCreate(db);
	}

	/**
	 * Initialize.
	 *
	 * @param context the context
	 */
	private static void initialize(Context context) {
		
		if (dbAdapter == null) {
			dbAdapter = new DatabaseAdapter(context, DATABASE_NAME, null, DATABASE_VERSION);
			db = dbAdapter.getWritableDatabase();
		}
	}

	/**
	 * Gets the single instance of DatabaseAdapter.
	 *
	 * @param context the context
	 * @return single instance of DatabaseAdapter
	 */
	public static final DatabaseAdapter getInstance(Context context) {
		initialize(context);
		return dbAdapter;
	}

	/**
	 * Gets the database.
	 *
	 * @return the database
	 */
	public static SQLiteDatabase getDatabase() {
		return db;
	}

	/**
	 * Insert.
	 *
	 * @param table the table
	 * @param values the values
	 * @return the long
	 */
	public long insert(String table, ContentValues values) {
		return db.insert(table, "", values);
	}
	
	/**
	 * Gets the raw login.
	 *
	 * @param table the table
	 * @param columns the columns
	 * @param restrictUser the restrict user
	 * @param restrictValuesUser the restrict values user
	 * @param restrictPasswd the restrict passwd
	 * @param restrictValuesPasswd the restrict values passwd
	 * @return the raw login
	 */
	public Cursor getRawLogin(String table, String[] columns,
			String restrictUser, String restrictValuesUser,
			String restrictPasswd, String restrictValuesPasswd) {

		// WHERE clause
		String selection = restrictUser + "=? AND " + restrictPasswd + "=?";
		// WHERE clause arguments
		String[] selectionArgs = { restrictValuesUser, restrictValuesPasswd };

		return db.query(true, table, columns, selection, selectionArgs, null,
				null, null, null);
	}

	/**
	 * Gets the raw.
	 *
	 * @param table the table
	 * @param columns the columns
	 * @param selection the selection
	 * @param selectionArgs the selection args
	 * @return the raw
	 */
	public Cursor getRaw(String table, String[] columns,
			String selection, String[] selectionArgs) {

			return db.query(true, table, columns, selection, selectionArgs, null,	null, null, null);
	}
	
	/**
	 * Gets the raw.
	 *
	 * @param table the table
	 * @param columns the columns
	 * @param restrict the restrict
	 * @param restrictValues the restrict values
	 * @return the raw
	 */
	public Cursor getRaw(String table, String[] columns, String restrict, String restrictValues) {

		return db.query(true, table, columns, restrict + " LIKE '"
				+ restrictValues + "'", null, null, null, null, null);
	}
	
	/**
	 * Gets the raw.
	 *
	 * @param table the table
	 * @param columns the columns
	 * @param restrict the restrict
	 * @param id the id
	 * @return the raw
	 */
	public Cursor getRaw(String table, String[] columns, String restrict, long id) {
		return db.query(true, table, columns, restrict + " = "	+ id, null, null, null, null, null);
	}
	
	/**
	 * Gets the raw by description.
	 *
	 * @param table the table
	 * @param columns the columns
	 * @param restrict1 the restrict1
	 * @param restrict2 the restrict2
	 * @param id the id
	 * @param description the description
	 * @return the raw by description
	 */
	public Cursor getRawByDescription(String table, String[] columns, String restrict1, String restrict2, long id, String description) {
		return db.query(true, table, columns, restrict1 + " = " + id + " AND " + restrict2 + " LIKE '%" + description + "%'", null, null, null, null, null);
	}
	
	public Cursor getRawByAnotherId(String table, String[] columns, String restrict1, String restrict2, long id, long anotherId) {
		return db.query(true, table, columns, restrict1 + " = " + id + " AND " + restrict2 + " = " + anotherId, null, null, null, null, null);
	}
	
/*	query(true, SQL_TABLE_NAME,
            new String[] { Users._ID, Users.WS_ID, Users.USER,
			Users.PASSWORD }, Users._ID + "=" + id,
            null, null, null, null, null);
*/
	/**
	 * Gets the raw by id.
	 *
	 * @param table the table
	 * @param columns the columns
	 * @param restrict the restrict
	 * @param restrictValues the restrict values
	 * @return the raw by id
	 */
	public Cursor getRawById(String table, String[] columns, String restrict,
			int restrictValues) {

		return db.query(true, table, columns, restrict + " = '"
				+ restrictValues + "'", null, null, null, null, null);
	}

	/**
	 * Gets the.
	 *
	 * @param table the table
	 * @param columns the columns
	 * @return the cursor
	 */
	public Cursor get(String table, String[] columns) {
		return db.query(table, columns, null, null, null, null, null);
	}
	
	

	/**
	 * Gets the max.
	 *
	 * @param sql the sql
	 * @return the max
	 */
	public Cursor getMax(String sql) {
		return db.rawQuery(sql, null);
	}
	
	/**
	 * Gets the raw query.
	 *
	 * @param sql the sql
	 * @param id the id
	 * @return the raw query
	 */
	public Cursor getRawQuery(String sql, long id) {
		return db.rawQuery(sql, new String[] { String.valueOf(id) });
	}
	
	/**
	 * Gets the.
	 *
	 * @param table the table
	 * @param columns the columns
	 * @param id the id
	 * @return the cursor
	 */
	public Cursor get(String table, String[] columns, long id) {
		Cursor cursor = db.query(true, table, columns, KEY_ID + "=" + id, null,
				null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	/**
	 * Delete.
	 *
	 * @param table the table
	 * @return the int
	 */
	public int delete(String table) {
		return db.delete(table, "1", null);
	}

	/**
	 * Delete.
	 *
	 * @param table the table
	 * @param id the id
	 * @return the int
	 */
	public int delete(String table, long id) {
		return db.delete(table, KEY_ID + "=" + id, null);
	}
	
	/**
	 * Delete.
	 *
	 * @param table the table
	 * @param where the where
	 * @param whereArgs the where args
	 * @return the int
	 */
	public int delete(String table, String where, String[] whereArgs) {
		return db.delete(table, where, whereArgs);
	}

	/**
	 * Update.
	 *
	 * @param table the table
	 * @param id the id
	 * @param values the values
	 * @return the int
	 */
	public int update(String table, long id, ContentValues values) {
		return db.update(table, values, KEY_ID + "=" + id, null);
	}

	/**
	 * Update.
	 *
	 * @param table the table
	 * @param values the values
	 * @param where the where
	 * @param whereArgs the where args
	 * @return the int
	 */
	public int update(String table, ContentValues values, String where, String[] whereArgs) {
		return db.update(table, values, where, whereArgs);
	}
	
	// Busca um ExpenseType utilizando as configura??????es definidas no
		// SQLiteQueryBuilder
		// Utilizado pelo Content Provider de ExpenseType
		/*public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection,
				String selection, String[] selectionArgs, String groupBy,
				String having, String orderBy) {
			Cursor c = queryBuilder.query(this.db, projection, selection,
					selectionArgs, groupBy, having, orderBy);
			return c;
		}*/
	
	/*
	 * @Override protected void finalize() { db.close(); }
	 */
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#close()
	 */
	public void close() {
		if (dbAdapter != null) {
			db.close();
			dbAdapter = null;
		}
	}

}
