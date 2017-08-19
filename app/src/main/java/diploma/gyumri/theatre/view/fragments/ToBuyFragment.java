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
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.model.Ticket;
import diploma.gyumri.theatre.view.HallView;
import diploma.gyumri.theatre.view.adapters.TicketsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ToBuyFragment extends Fragment {
    private TicketsAdapter adapter;
    private List<Ticket> ticketList;
    private HallView drawView;
    private RecyclerView recyclerView;
    private ScrollView scrollView;

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
        View view =inflater.inflate(R.layout.fragment_to_buy, container, false);
        drawView = (HallView) view.findViewById(R.id.drawView);
        int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_item);
        adapter = new TicketsAdapter(getActivity(), new ArrayList<Ticket>(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        drawView.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        drawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Ticket ticket = drawView.select(event.getX(), event.getY());
                    if (ticket != null) {
                        if (ticket.getState() == Ticket.State.AVAILABLE) {
                            if (ticketList == null) {
                                ticketList = new ArrayList<>();
                            }
                            ticketList.add(ticket);
                            recyclerViewSetAdapter(ticket, Ticket.State.SELECTED);
                        } else if (ticket.getState() == Ticket.State.SELECTED) {
                            if (ticketList.indexOf(ticket) != -1) {
                                removeFromList(ticketList.indexOf(ticket));
                                recyclerViewSetAdapter(ticket, Ticket.State.AVAILABLE);
                            }
                        }

                        drawView.invalidate();
                        return true;
                    }
                }
                return false;
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void recyclerViewSetAdapter(Ticket ticket, Ticket.State state) {
        adapter = new TicketsAdapter(getActivity(), ticketList,this);
        recyclerView.setAdapter(adapter);
        ticket.setState(state);
    }
    public void removeFromList(int position) {
        Ticket ticket = null;
        lab:
        for (int i = 0; i < drawView.tickets.length; i++) {
            for (int j = 0; j < drawView.tickets[i].size(); j++) {
                if (drawView.tickets[i].get(j).getSeat() == ticketList.get(position).getSeat()
                        && drawView.tickets[i].get(j).getRow() == ticketList.get(position).getRow()) {
                    ticket = ticketList.remove(position);
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
            drawView.invalidate();
        }
        adapter = new TicketsAdapter(getActivity(), ticketList,this);
        recyclerView.setAdapter(adapter);
    }

}
