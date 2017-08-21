package diploma.gyumri.theatre.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by root on 7/30/17.
 */
@Data
public class EventDTO {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String desc;
    @SerializedName("price")
    private String price;
    @SerializedName("stage")
    private String stage;
    @SerializedName("date")
    private String date;
    @SerializedName("imageurl")
    private String imgUrl;
    @SerializedName("videourl")
    private String videoUrl;
}
