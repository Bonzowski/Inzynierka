package pl.marcin.inzynierka.MVPs.TicketDatabaseMenu;

import android.content.Context;

import pl.marcin.inzynierka.Backend.Database.TicketDataSource;

/**
 * Created by Marcin on 16.11.2016.
 */

public class TicketDatabasePresenter {

    private TicketDataSource datasource;
    private TicketDatabaseActivity view;
    private Context context;

    public TicketDatabasePresenter() {}

    public void onTakeView(TicketDatabaseActivity view) {
        this.view = view;
        if (view != null)
            this.context = view.getBaseContext();
    }

    public void deleteAllComments(){
        datasource = new TicketDataSource(context);
        datasource.deleteTickets();
    }

}
