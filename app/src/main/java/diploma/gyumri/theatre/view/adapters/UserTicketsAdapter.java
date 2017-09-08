package diploma.gyumri.theatre.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.UserTicket;
import diploma.gyumri.theatre.view.viewholders.UserTicketsViewHolder;

/**
 * Created by root on 9/9/17.
 */

public class UserTicketsAdapter extends RecyclerView.Adapter<UserTicketsViewHolder> {

    private List<UserTicket> tickets;
    private LayoutInflater inflater;
    private Context context;

    public UserTicketsAdapter(List<UserTicket> tickets, Context context) {
        this.tickets = tickets;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.context = context;

    }

    @Override
    public UserTicketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_tickets_item, parent, false);
        return new UserTicketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserTicketsViewHolder holder, int position) {
        UserTicket userTicket = getItem(position);
        holder.initData(userTicket);
    }

    private UserTicket getItem(int position) {
        if (tickets != null && position >= 0 && tickets.size() > position) {
            return tickets.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        return tickets.size();
    }
}
