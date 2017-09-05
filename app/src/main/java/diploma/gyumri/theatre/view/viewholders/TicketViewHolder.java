package diploma.gyumri.theatre.view.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.Ticket;

/**
 * Created by Hayk on 19.08.2017.
 */

public class TicketViewHolder extends RecyclerView.ViewHolder {
    TextView price;
    TextView row;
    TextView seat;
    public ImageButton delet;

    public TicketViewHolder(View itemView) {
        super(itemView);
        this.price = (TextView) itemView.findViewById(R.id.ticket_price);
        this.row = (TextView) itemView.findViewById(R.id.ticket_row);
        this.seat = (TextView) itemView.findViewById(R.id.ticket_send);
        this.delet = (ImageButton) itemView.findViewById(R.id.ticket_delet);
    }

    public void initData(Ticket ticket) {
        price.setText("" + ticket.getPrice());
        row.setText("" + ticket.getRow());
        seat.setText("" + ticket.getSeat());
    }
}
