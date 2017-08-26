package diploma.gyumri.theatre.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.data.dto.TicketsDTO;
import diploma.gyumri.theatre.data.mappers.TicketsMapper;
import diploma.gyumri.theatre.model.Event;
import diploma.gyumri.theatre.model.Ticket;
import diploma.gyumri.theatre.view.HallView;
import diploma.gyumri.theatre.view.adapters.TicketsAdapter;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ToBuyFragment extends Fragment {
    private Unbinder unbinder;
    private TicketsAdapter adapter;
    private List<Ticket> ticketList;
    private Socket mSocket;
    private Event event;
    private float sX, sY, mX, mY;
    private boolean isZoomed;
    private int zoom;

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
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;


    private IO.Options headers = new IO.Options();


    public ToBuyFragment(Event event) {
        this.event = event;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        headers.forceNew = true;
//        headers.query = "token=" + "asdasghdsadkasdkk546asd";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_buy, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            mSocket = IO.socket("https://theater-cs50artashes.cs50.io");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();
        mSocket.emit("tickets", 21);
        mSocket.on("tickets", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[0];
                Gson gson = new Gson();
                hallView.setTickets(TicketsMapper.toTickets(gson.fromJson(args[0].toString(), TicketsDTO.class)));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hallView.invalidate();
                    }
                });
            }
        });


        eventTitle.setText(event.getName());
        int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        ticketsListDescription.setVisibility(View.GONE);
        adapter = new TicketsAdapter(getActivity(), new ArrayList<Ticket>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        hallView.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        selectedTicketsDescription.setVisibility(View.GONE);

//        hallView.setOnTouchListener(new MyTouchListener());
//        hallView.setOnDragListener(new MyDragListener());

        return view;
    }


    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    Log.i("Tag", "Action Drag Started");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    Log.i("Tag", "Action Drag Entered");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("Tag", "Action Drag Exited");
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
//                    View view = (View) event.getLocalState();
//                    ViewGroup owner = (ViewGroup) view.getParent();
//                    owner.removeView(view);
//                    LinearLayout container = (LinearLayout) v;
//                    container.addView(view);
//                    view.setVisibility(View.VISIBLE);
//                    List<Ticket>[] tickets = hallView.getTickets();
//                    Ticket ticket;
//                    float cX, cY, mX, mY;
//
//                    mX = event.getX() - sX;
//                    mY = event.getY() - sY;
//
//                    for (int i = 0; i < tickets.length; i++) {
//                        for (int j = 0; j < tickets[i].size(); j++) {
//                            ticket = tickets[i].get(j);
//
//                            cX = ticket.getcX() + mX;
//                            cY = ticket.getcY() + mY;
//                            ticket.move(cX, cY);
//
//                        }
//                    }
//
//                    hallView.invalidate();


                    Log.i("Tag", "Action Drop");

                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    hallView.invalidate();

                    Log.i("Tag", "Action Drag Ended");
                default:
                    Log.i("Tag", "Default");
                    break;
            }
            return true;
        }
    }


//    private final class MyTouchListener implements View.OnTouchListener {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                ClipData data = ClipData.newPlainText("", "");
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//                view.startDrag(data, shadowBuilder, view, 0);
//                view.setVisibility(View.INVISIBLE);
//                sX = motionEvent.getX();
//                sY = motionEvent.getY();
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mSocket.disconnect();
    }

    @OnClick({R.id.zoomIn, R.id.zoomOut})
    void zoom(View view) {
        switch (view.getId()) {
            case R.id.zoomIn:
                hallView.zoomIn();
                zoom++;
                if (zoom > 0) {
                    isZoomed = true;
                }
                break;
            case R.id.zoomOut:
                hallView.zoomOut();
                zoom--;
                if (zoom == 0) {
                    isZoomed = false;
                }
                break;
        }

    }


    @OnTouch(R.id.hallView)
    boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            sX = (int) event.getX();
            sY = (int) event.getY();
//            scrollView.setNestedScrollingEnabled(false);
            if (hallView.getTickets() == null) {
                return false;
            }
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
                Log.i("Tag", sY + "   " + sX);
                return true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (isZoomed) {


                Log.i("Tag", "Action Move ");
                mX = ((int) event.getX()) - sX;
                mY = ((int) event.getY()) - sY;


                hallView.setX(mX);
                hallView.setY(mY);
                Log.i("Tag", mX + " " + mY);

                hallView.invalidate();
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
        } else {
            selectedTicketsDescription();
        }
        adapter = new TicketsAdapter(getActivity(), ticketList, this);
        recyclerView.setAdapter(adapter);

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
