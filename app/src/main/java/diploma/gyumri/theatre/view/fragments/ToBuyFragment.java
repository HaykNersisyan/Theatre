package diploma.gyumri.theatre.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.Ticket;
import diploma.gyumri.theatre.view.HallView;
import diploma.gyumri.theatre.view.adapters.TicketsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ToBuyFragment extends Fragment {
    private Unbinder unbinder;
    private TicketsAdapter adapter;
    private List<Ticket> ticketList;
    @BindView(R.id.hallView)
    HallView hallView;
    @BindView(R.id.tickets_list)
    RecyclerView recyclerView;
    @BindView(R.id.tickets_list_desc)
    LinearLayout ticketsListDescription;


    public ToBuyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_buy, container, false);
        unbinder = ButterKnife.bind(this, view);
        int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        ticketsListDescription.setVisibility(View.GONE);
        adapter = new TicketsAdapter(getActivity(), new ArrayList<Ticket>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        hallView.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        return view;
    }

    @OnTouch(R.id.hallView)
    boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Ticket ticket = hallView.select(event.getX(), event.getY());
            if (ticket != null) {
                if (ticket.getState() == Ticket.State.AVAILABLE) {
                    if (ticketList == null) {
                        ticketList = new ArrayList<>();
                    }
                    ticketsListDescription.setVisibility(View.VISIBLE);
                    ticketList.add(ticket);
                    recyclerViewSetAdapter(ticket, Ticket.State.SELECTED);
                } else if (ticket.getState() == Ticket.State.SELECTED) {
                    if (ticketList.indexOf(ticket) != -1) {
                        removeFromList(ticketList.indexOf(ticket));
                        recyclerViewSetAdapter(ticket, Ticket.State.AVAILABLE);
                    }
                }

                hallView.invalidate();
                return true;
            }
        }
        return false;
    }

    private void recyclerViewSetAdapter(Ticket ticket, Ticket.State state) {
        adapter = new TicketsAdapter(getActivity(), ticketList, this);
        recyclerView.setAdapter(adapter);
        ticket.setState(state);
    }

    public void removeFromList(int position) {
        Ticket ticket = null;
        lab:
        for (int i = 0; i < hallView.tickets.length; i++) {
            for (int j = 0; j < hallView.tickets[i].size(); j++) {
                if (hallView.tickets[i].get(j).getSeat() == ticketList.get(position).getSeat()
                        && hallView.tickets[i].get(j).getRow() == ticketList.get(position).getRow()) {
                    ticket = ticketList.remove(position);
                    if (ticketList.size() == 0) {
                        ticketsListDescription.setVisibility(View.GONE);
                    }
                    break lab;
                }
            }
        }
        if (ticket != null) {
            if (ticket.getState() == Ticket.State.SELECTED) {
                if (ticketList == null) {
                    ticketList = new ArrayList<>();
                }
                ticket.setState(Ticket.State.AVAILABLE);
            } else if (ticket.getState() == Ticket.State.AVAILABLE) {
                ticket.setState(Ticket.State.SELECTED);
            }
            hallView.invalidate();
        }
        adapter = new TicketsAdapter(getActivity(), ticketList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
