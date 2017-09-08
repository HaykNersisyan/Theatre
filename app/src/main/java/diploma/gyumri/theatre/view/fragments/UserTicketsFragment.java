package diploma.gyumri.theatre.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import diploma.gyumri.theatre.R;
import diploma.gyumri.theatre.constants.Constants;
import diploma.gyumri.theatre.data.dto.UserTicketsDTO;
import diploma.gyumri.theatre.data.mappers.UserTicketsMapper;
import diploma.gyumri.theatre.model.UserTicket;
import diploma.gyumri.theatre.view.adapters.UserTicketsAdapter;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTicketsFragment extends Fragment {


    @BindView(R.id.user_tickets)
    RecyclerView recyclerView;
    private Unbinder mUnbinder;
    private IO.Options headers = new IO.Options();
    private Socket mSocket;
    @BindView(R.id.user_tickets_info)
    TextView info;
    public UserTicketsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        mUnbinder = ButterKnife.bind(this, view);
//        info.setVisibility(View.GONE);
        if (Constants.USER != null) {
            if (Constants.USER != null) {
                headers.forceNew = true;
                headers.query = "token=" + Constants.USER.getToken();
                try {
                    mSocket = IO.socket("https://theater-cs50artashes.cs50.io", headers);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }

            mSocket.connect();
            mSocket.emit("usertickets");
            mSocket.on("usertickets", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Gson gson = new Gson();
                    final List<UserTicket> tickets = UserTicketsMapper.userTickets(gson.fromJson(args[0].toString(), UserTicketsDTO.class));
                    if (tickets != null && tickets.size() > 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UserTicketsAdapter adapter = new UserTicketsAdapter(tickets, getContext());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(adapter);
                                info.setVisibility(View.GONE);
                            }
                        });

                    }else{

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                info.setVisibility(View.VISIBLE);
                                info.setText("Դուք չունեք պատվիրված տոմսեր");
                            }
                        });
                    }
                }
            });
        }else{
            info.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mUnbinder.unbind();
    }
}
