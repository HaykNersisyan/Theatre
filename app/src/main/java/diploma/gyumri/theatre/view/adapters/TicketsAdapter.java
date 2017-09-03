package diploma.gyumri.theatre.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.Ticket;
import diploma.gyumri.theatre.view.fragments.ToBuyFragment;
import diploma.gyumri.theatre.view.viewholders.TicketViewHolder;

/**
 * Created by Hayk on 19.08.2017.
 */

public class TicketsAdapter extends RecyclerView.Adapter<TicketViewHolder> implements View.OnClickListener {
    private LayoutInflater inflater;
    private Context context;
    private List<Ticket> ticketList;
    private ToBuyFragment fragment;

    public TicketsAdapter(Context context, List<Ticket> ticketList, ToBuyFragment fragment) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.fragment = fragment;
        this.ticketList = ticketList;
        setHasStableIds(true);
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ticket_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        Ticket ticket = getItem(position);
        holder.initData(ticket);
        holder.delet.setTag(position);
        holder.delet.setOnClickListener(this);
    }

    private Ticket getItem(int position) {
        if (ticketList != null && position >= 0 && ticketList.size() > position) {
            return ticketList.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (ticketList == null) {
            ticketList = new ArrayList<>();
        }
        return ticketList.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        fragment.removeFromList(position);
    }
}
