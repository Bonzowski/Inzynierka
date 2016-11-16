package pl.marcin.inzynierka.Database;

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

    public Ticket createTicket(int queue_id, int ticket_id, String date, int isActive){
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
        Ticket newTicket = cursorToComment(cursor);
        cursor.close();
        return newTicket;

    }

   /* public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }*/

    public List<Ticket> getAllTickets() {
        List<Ticket> comments = new ArrayList<Ticket>();

        Cursor cursor = database.query(TicketSQLiteHelper.TABLE_TICKETS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ticket ticket = cursorToComment(cursor);
            comments.add(ticket);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }


    private Ticket cursorToComment(Cursor cursor) {
        Ticket ticket = new Ticket();

        ticket.setKey(cursor.getLong(0));
        ticket.setQueue_id(cursor.getInt(1));
        ticket.setTicket_id(cursor.getInt(2));
        ticket.setDate(cursor.getString(3));
        ticket.setIsActive(cursor.getInt(4));

        return ticket;
    }

}
