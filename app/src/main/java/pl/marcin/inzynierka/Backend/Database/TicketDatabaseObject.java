package pl.marcin.inzynierka.Backend.Database;

/**
 * Created by Marcin on 15.11.2016.
 */

public class TicketDatabaseObject {

    private long key;
    private int queue_id;
    private int ticket_id;
    private String date;
    private int isActive;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getQueue_id() {
        return queue_id;
    }

    public void setQueue_id(int queue_id) {
        this.queue_id = queue_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return date;
    }

}
