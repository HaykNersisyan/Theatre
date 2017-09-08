package diploma.gyumri.theatre.data.mappers;

import java.util.ArrayList;
import java.util.List;

import diploma.gyumri.theatre.data.dto.UserTicketDTO;
import diploma.gyumri.theatre.data.dto.UserTicketsDTO;
import diploma.gyumri.theatre.model.UserTicket;

/**
 * Created by root on 9/9/17.
 */

public class UserTicketsMapper {
    public static List<UserTicket> userTickets(UserTicketsDTO userTicketsDTO) {
        List<UserTicket> userTickets = new ArrayList<>(userTicketsDTO.getUsertickets().size());
        for (int i = 0; i < userTicketsDTO.getUsertickets().size(); i++) {
            userTickets.add(getUserTicket(userTicketsDTO.getUsertickets().get(i)));
        }
        return userTickets;
    }


    private static UserTicket getUserTicket(UserTicketDTO userTicketDTO) {
        UserTicket userTicket = new UserTicket();
        userTicket.setRow(userTicketDTO.getRow());
        userTicket.setDate(EventMapper.getDate(userTicketDTO.getDate()));
        userTicket.setPrice(userTicketDTO.getPrice());
        userTicket.setEventId(userTicketDTO.getEventId());
        userTicket.setName(userTicketDTO.getName());
        userTicket.setEventId(userTicketDTO.getEventId());
        userTicket.setSeat(userTicketDTO.getSeat());
        return userTicket;
    }
}
