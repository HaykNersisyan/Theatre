package diploma.gyumri.theatre.model;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import lombok.Data;

/**
 * Created by Hayk on 07.08.2017.
 */

@Data
public class Ticket {
    State state = State.AVAILABLE;


    public enum State {
        AVAILABLE, SOLD, RESERVED, SELECTED
    }

    private int row;
    private int seat;
    private int id;
    private int price;
    private int color;
    private float cX, cY, radius;
    private Canvas canvas;
    private Paint p = new Paint();

    public Ticket(int row, int seat, int price) {
        this.row = row;
        this.seat = seat;
        this.price = price;
    }

    public Ticket() {

    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getSeat() {
        return seat;
    }

    public State getState() {
        return state;
    }

    public int getRow() {
        return row;
    }

    public int getPrice() {
        return price;
    }

    public float getcX() {
        return cX;
    }

    public float getcY() {
        return cY;
    }

    public float getRadius() {
        return radius;
    }


    public void coo(float a) {
        this.cX *= a;
        this.cY *= a;
        this.radius *= a;
    }


    public Ticket(int row, int seat, int id, int price, int color) {
        this.row = row;
        this.seat = seat;
        this.id = id;
        this.price = price;
        this.color = color;
    }

    public void draw(Canvas canvas, float cX, float cY, float radius, int i, float a) {


        this.cX = cX;
        this.cY = cY;
        this.radius = radius;
        this.canvas = canvas;
        switch (state) {
            case AVAILABLE:
                p.setColor(Color.rgb(49, 148, 23));
                break;
            case SOLD:
                p.setColor(Color.BLACK);
                break;
            case RESERVED:
                p.setColor(Color.rgb(255, 170, 15));
                break;
            case SELECTED:
                p.setColor(Color.rgb(179, 22, 30));
        }
        canvas.drawCircle(cX, cY, radius, p);
        p.setColor(Color.WHITE);
        float b = 0.07f * a;
        if (i < 10){
            canvas.drawText(i + "", cX - b, cY + b, p);
        } else {
            canvas.drawText(i + "", cX - 2 * b, cY + b, p);
        }

    }

    public static boolean isSelected = false;


}
