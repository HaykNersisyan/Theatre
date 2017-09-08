package diploma.gyumri.theatre.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * Created by root on 9/9/17.
 */
@Data
public class UserTicketsDTO {
    @SerializedName("tickets")
    List<UserTicketDTO> usertickets;
}
