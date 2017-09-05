package diploma.gyumri.theatre.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by root on 8/24/17.
 */
@Data
public class TicketsSeatDTO {
    @SerializedName("seats")
    private List<TicketDTO> ticketsSeat;
}
