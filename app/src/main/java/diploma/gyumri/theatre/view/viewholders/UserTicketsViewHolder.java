package diploma.gyumri.theatre.view.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.UserTicket;

/**
 * Created by root on 9/9/17.
 */

public class UserTicketsViewHolder extends RecyclerView.ViewHolder {
    TextView eventName;
    TextView eventDate;
    TextView ticketRow;
    TextView ticketSeat;
    TextView ticketPrice;

    public UserTicketsViewHolder(View itemView) {
        super(itemView);
        eventDate = (TextView) itemView.findViewById(R.id.user_ticket_date);
        eventName = (TextView) itemView.findViewById(R.id.user_ticket_event);
        ticketRow = (TextView) itemView.findViewById(R.id.user_ticket_row);
        ticketPrice = (TextView) itemView.findViewById(R.id.user_ticket_price);
        ticketSeat = (TextView) itemView.findViewById(R.id.user_ticket_seat);
    }

    public void initData(UserTicket ticket) {
        eventDate.setText(ticket.getDate());
        eventName.setText(ticket.getName());
        ticketRow.setText("Շարք։ " + ticket.getRow());
        ticketSeat.setText( "Տեղ։ "+ ticket.getSeat());
        ticketPrice.setText("Գին։ "+ ticket.getPrice());
    }
}
