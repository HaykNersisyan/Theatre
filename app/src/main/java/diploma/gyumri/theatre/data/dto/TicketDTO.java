package diploma.gyumri.theatre.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by root on 8/24/17.
 */
@Data
public class TicketDTO {

    @SerializedName("state")
    String state;



    @SerializedName("seat_group")
    private int row;
    @SerializedName("grp")
    private int seat;
    @SerializedName("ticket_id")
    private int id;
    @SerializedName("price")
    private int price;
}
