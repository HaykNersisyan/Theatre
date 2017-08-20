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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.Event;
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
    @BindView(R.id.selectedTicketsDescription)
    TextView selectedTicketsDescription;
    @BindView(R.id.eventTitleHall)
    TextView eventTitle;
    Event event;

    public ToBuyFragment(Event event) {
        this.event = event;
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
        eventTitle.setText(event.getName());
        int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        ticketsListDescription.setVisibility(View.GONE);
        adapter = new TicketsAdapter(getActivity(), new ArrayList<Ticket>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        hallView.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        selectedTicketsDescription.setVisibility(View.GONE);
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
                if (ticketList.size() != 0) {
                    selectedTicketsDescription();
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
        if (ticketList.get(position).getState() == Ticket.State.SELECTED) {
            ticketList.get(position).setState(Ticket.State.AVAILABLE);
        } else if (ticketList.get(position).getState() == Ticket.State.AVAILABLE) {
            ticketList.get(position).setState(Ticket.State.SELECTED);
        }
        hallView.invalidate();
        ticketList.remove(position);
        if (ticketList.size() == 0) {
            selectedTicketsDescription.setVisibility(View.GONE);
            ticketsListDescription.setVisibility(View.GONE);
        }else {
            selectedTicketsDescription();
        }
        adapter = new TicketsAdapter(getActivity(), ticketList, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    private void selectedTicketsDescription() {
        selectedTicketsDescription.setVisibility(View.VISIBLE);
        selectedTicketsDescription.setText("Ընտրված է " + ticketList.size() + " տոմս " +
                selectedTicketsPrice() + " ՀՀ դրամ ընդհանուր արժողությամբ։");
    }


    private int selectedTicketsPrice() {
        int price = 0;
        for (int i = 0; i < ticketList.size(); i++) {
            price += ticketList.get(i).getPrice();
        }
        return price;
    }
}
