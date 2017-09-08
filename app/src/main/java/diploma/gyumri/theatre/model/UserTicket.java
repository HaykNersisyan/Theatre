package diploma.gyumri.theatre.model;

import lombok.Data;

/**
 * Created by root on 9/9/17.
 */
@Data
public class UserTicket {
    private String date;
    private int eventId;
    private String name;
    private int price;
    private int row;
    private String seat;
    private int ticketId;
}
