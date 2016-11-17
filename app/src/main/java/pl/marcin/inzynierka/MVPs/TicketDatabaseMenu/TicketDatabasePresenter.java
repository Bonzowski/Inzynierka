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
    String dateofTicketToDelete = null;

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

    public void longClick(String dateofTicketToDelete){
        this.dateofTicketToDelete = dateofTicketToDelete;
        view.deactivatePopup();
    }

    public void deactivateTicketConfirmed(){
        datasource = new TicketDataSource(context);
        datasource.deactivateTicketInDatabase(dateofTicketToDelete);
        dateofTicketToDelete = null;
    }

    public void openIfActive(Integer i, Integer j, String s){
        view.navigateToTicket(i,j,s);
    }

}
