package diploma.gyumri.theatre.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.data.dto.TicketDTO;
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
    @BindView(R.id.nestedScrollView)
    NestedScrollView scrollView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.buy)
    Button buyButton;


    private IO.Options headers = new IO.Options();


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
        if (Constants.USER != null) {
            headers.forceNew = true;
            headers.query = "token=" + Constants.USER.getToken();
            try {
                mSocket = IO.socket("https://theater-cs50artashes.cs50.io", headers);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else {
            try {
                mSocket = IO.socket("https://theater-cs50artashes.cs50.io");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        mSocket.connect();
        mSocket.emit("tickets", event.getId());
        mSocket.on("tickets", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[0];
                Gson gson = new Gson();
                hallView.setTickets(TicketsMapper.toTickets(gson.fromJson(args[0].toString(), TicketsDTO.class)));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        hallView.invalidate();
                    }
                });
            }
        });


        mSocket.on("ticket", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gson gson = new Gson();
                Ticket ticket = TicketsMapper.initTicket(gson.fromJson(args[0].toString(), TicketDTO.class));
                hallView.getTickets()[ticket.getRow() - 1].set(ticket.getSeat() - 1, ticket);
                int tmp = -1;
                for (int i = 0; i < ticketList.size(); i++) {
                    if (ticketList.get(i).getId() == ticket.getId()) {
                        tmp = i;
                    }
                }

                final int finalTmp = tmp;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (finalTmp != -1) {
                            removeFromList(finalTmp);
                        }
                        hallView.invalidate();
                    }
                });
            }
        });

        ticketList = new ArrayList<>();
        eventTitle.setText(event.getName());
        int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        ticketsListDescription.setVisibility(View.GONE);
        adapter = new TicketsAdapter(getActivity(), ticketList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        scrollView.setEnabled(false);
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

//                    hallView.invalidate();

                    Log.i("Tag", "Action Drag Ended");
                default:
                    Log.i("Tag", "Default");
                    break;
            }
            return true;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mSocket.disconnect();
    }

    @OnClick({R.id.zoomIn, R.id.zoomOut, R.id.buy})
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
            case R.id.buy:
                if (Constants.USER == null) {
                    Toast.makeText(getContext(), "Դուք մուտք եղած չեք", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    for (int i = 0; i < ticketList.size(); i++) {
                        mSocket.emit("buy", ticketList.get(i).getId());
                    }
                    break;
                }
        }

    }


    @OnTouch({R.id.hallView,R.id.buy})
    boolean onTouch(View v, MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
//            scrollView.setNestedScrollingEnabled(false);
//        }
        switch (v.getId()) {
            case R.id.hallView:
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                sX = (int) event.getX();
                sY = (int) event.getY();

                if (hallView.getTickets() == null) {
                    return false;
                }
                Ticket ticket = hallView.select(event.getX(), event.getY());
                if (ticket != null) {
                    if (ticket.getState() == Ticket.State.AVAILABLE) {
                        buyButton.setVisibility(View.VISIBLE);
                        ticketsListDescription.setVisibility(View.VISIBLE);
                        ticketList.add(ticket);
                        recyclerViewDataChanged(ticket, Ticket.State.SELECTED);
                    } else if (ticket.getState() == Ticket.State.SELECTED) {
                        if (ticketList.indexOf(ticket) != -1) {
                            removeFromList(ticketList.indexOf(ticket));
                            recyclerViewDataChanged(ticket, Ticket.State.AVAILABLE);
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
                return false;
            }
            break;
            case R.id.buy:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.buttonColor, null));
                            v.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_pressed, null));
                            return false;
                        case MotionEvent.ACTION_CANCEL:
                            ResourcesCompat.getDrawable(getResources(), R.drawable.button, null);
                            ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                            v.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                            return false;
                        case MotionEvent.ACTION_UP:
                            ResourcesCompat.getDrawable(getResources(), R.drawable.button, null);
                            ((Button) v).setTextColor(ResourcesCompat.getColor(getResources(), R.color.textColor, null));
                            v.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button, null));
                            return false;
                    }
                    break;
            }
        return false;
    }

    private void recyclerViewDataChanged(Ticket ticket, Ticket.State state) {
        adapter.notifyDataSetChanged();
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
            buyButton.setVisibility(View.GONE);
            ticketsListDescription.setVisibility(View.GONE);
        } else {
            selectedTicketsDescription();
        }

//        adapter = new TicketsAdapter(getActivity(), ticketList, this);
//        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
