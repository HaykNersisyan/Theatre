package diploma.gyumri.theatre.data.mappers;

import java.util.ArrayList;
import java.util.List;

import diploma.gyumri.theatre.data.dto.TicketDTO;
import diploma.gyumri.theatre.data.dto.TicketsDTO;
import diploma.gyumri.theatre.model.Ticket;

/**
 * Created by root on 8/24/17.
 */

public class TicketsMapper {
    public static List<Ticket>[] toTickets(TicketsDTO ticketsDTO) {
        List<Ticket>[] ticketner = new ArrayList[ticketsDTO.getTickets().length];
        int arrLength = ticketsDTO.getTickets().length;
        for (int i = 0; i < arrLength; i++) {
            int size = ticketsDTO.getTickets()[i].getTicketsSeat().size();
            ticketner[i] = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                ticketner[i].add(initTicket(ticketsDTO.getTickets()[i].getTicketsSeat().get(j)));
            }
        }
        return ticketner;
    }

    public static Ticket initTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDTO.getId());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setRow(ticketDTO.getRow());
        ticket.setSeat(ticketDTO.getSeat());
        switch (ticketDTO.getState()) {
            case "available":
                ticket.setState(Ticket.State.AVAILABLE);
                break;
            case "saled":
                ticket.setState(Ticket.State.SOLD);
                break;
            case "reserved":
                ticket.setState(Ticket.State.RESERVED);
                break;
        }
        return ticket;
    }
}