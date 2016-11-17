package pl.marcin.inzynierka.Backend.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 15.11.2016.
 */

public class TicketDataSource {

    private SQLiteDatabase database;
    private TicketSQLiteHelper dbHelper;
    private String[] allColumns =
            {TicketSQLiteHelper.KEY, TicketSQLiteHelper.QUEUE_ID, TicketSQLiteHelper.TICKET_ID, TicketSQLiteHelper.DATE, TicketSQLiteHelper.IS_ACTIVE};

    public TicketDataSource(Context context){
        dbHelper = new TicketSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public TicketDatabaseObject createTicket(int queue_id, int ticket_id, String date, int isActive){
        ContentValues values = new ContentValues();

        values.put(TicketSQLiteHelper.QUEUE_ID, queue_id);
        values.put(TicketSQLiteHelper.TICKET_ID, ticket_id);
        values.put(TicketSQLiteHelper.DATE, date);
        values.put(TicketSQLiteHelper.IS_ACTIVE, isActive);

        long insertKey = database.insert(TicketSQLiteHelper.TABLE_TICKETS, null, values);
        Cursor cursor = database.query(TicketSQLiteHelper.TABLE_TICKETS,
                allColumns, TicketSQLiteHelper.KEY + " = " + insertKey, null,
                null, null, null);

        cursor.moveToFirst();
        TicketDatabaseObject newTicketDatabaseObject = cursorToTicket(cursor);
        cursor.close();
        return newTicketDatabaseObject;

    }

    public void deleteTickets() {
        this.open();
        database.delete(TicketSQLiteHelper.TABLE_TICKETS, null, null);
        this.close();
    }

    public void deactivateTicketInDatabase(String date){
        this.open();
        ContentValues value = new ContentValues();
        value.put (TicketSQLiteHelper.IS_ACTIVE, 0);
        database.update(TicketSQLiteHelper.TABLE_TICKETS, value, TicketSQLiteHelper.DATE + " = '" + date + "'", null );
        this.close();

    }

    public List<TicketDatabaseObject> getAllTickets() {
        List<TicketDatabaseObject> tickets = new ArrayList<TicketDatabaseObject>();

        Cursor cursor = database.query(TicketSQLiteHelper.TABLE_TICKETS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TicketDatabaseObject ticketDatabaseObject = cursorToTicket(cursor);
            tickets.add(ticketDatabaseObject);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tickets;
    }


    private TicketDatabaseObject cursorToTicket(Cursor cursor) {
        TicketDatabaseObject ticketDatabaseObject = new TicketDatabaseObject();

        ticketDatabaseObject.setKey(cursor.getLong(0));
        ticketDatabaseObject.setQueue_id(cursor.getInt(1));
        ticketDatabaseObject.setTicket_id(cursor.getInt(2));
        ticketDatabaseObject.setDate(cursor.getString(3));
        ticketDatabaseObject.setIsActive(cursor.getInt(4));

        return ticketDatabaseObject;
    }

}
