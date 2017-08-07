package diploma.gyumri.theatre.model;


import android.graphics.Canvas;
import android.graphics.Paint;

import lombok.Data;

/**
 * Created by Hayk on 07.08.2017.
 */

@Data
public class Ticket{

    State state = State.AVAILABLE;

    enum State {
        AVAILABLE, SOLD, RESERVED
    }

    private int row;
    private int seat;
    private int id;
    private int price;
    private int color;

    public Ticket(int row, int seat, int id, int price, int color) {
        this.row = row;
        this.seat = seat;
        this.id = id;
        this.price = price;
        this.color = color;
    }

    public void draw(Canvas canvas, float cX, float cY, float radius, Paint p){
        canvas.drawCircle(cX, cY, radius, p);
    }


}
