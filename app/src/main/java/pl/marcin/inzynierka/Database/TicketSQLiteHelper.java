package pl.marcin.inzynierka.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Marcin on 14.11.2016.
 */

public class TicketSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tickets.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TICKETS = "tickets";

    public static final String KEY = "primaryKey";
    public static final String QUEUE_ID = "queueId";
    public static final String TICKET_ID = "ticketId";
    public static final String DATE = "date";
    public static final String IS_ACTIVE = "isActive";

    private static final String DATABASE_CREATE =
            "create table "  + TABLE_TICKETS + "( " + KEY  + " integer primary key autoincrement, "
                    + QUEUE_ID + " integer, "
                    + TICKET_ID + " integer, "
                    + DATE + " text not null, "
                    + IS_ACTIVE + " integer);";

    public TicketSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TicketSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        onCreate(db);
    }
}
