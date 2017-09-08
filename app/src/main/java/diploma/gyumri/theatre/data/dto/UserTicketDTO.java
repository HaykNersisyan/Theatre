package diploma.gyumri.theatre.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by root on 9/9/17.
 */
@Data
public class UserTicketDTO {
    @SerializedName("date")
    private long date;
    @SerializedName("event_id")
    private int eventId;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private int price;
    @SerializedName("row")
    private int row;
    @SerializedName("seat")
    private String seat;
    @SerializedName("ticket_id")
    private int ticketId;
}
