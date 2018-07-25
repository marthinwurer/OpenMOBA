package Client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.Image;

import static Client.Client.TICKS_PER_SECOND;
import static java.lang.Math.*;

public class Entity {
    private static Logger log = LogManager.getLogger(Client.class.getName());

    private Image sprite;
    private float x;
    private float y;
    private float dest_x;
    private float dest_y;
    private float x_offset;
    private float y_offset;
    private float speed;
    private String name;

    public Entity(String name, Image sprite, float startx, float starty, float speed){
        this.name = name;
        this.sprite = sprite;
        this.x = startx;
        this.y = starty;
        this.dest_x = startx;
        this.dest_y = starty;
        this.speed = speed;
    }

    public void update_position(){
        // move towards the destination
        float dx = x - dest_x;
        float dy = y - dest_y;
        float hyp = (float) sqrt(dx * dx + dy * dy);

        if (abs(hyp) > 1) {
//            log.info("differences: x: " + dx + ", dy: " + dy + " hyp: " + hyp);

            float xratio = dx/hyp;
            float yratio = dy/hyp;
            float distance = min(speed / TICKS_PER_SECOND, hyp);

            x -= distance * xratio;
            y -= distance * yratio;
        }
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setDest(float x, float y){
        this.dest_x = x;
        this.dest_y = y;
    }
}
