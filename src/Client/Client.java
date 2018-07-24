package Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.newdawn.slick.*;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static java.lang.Math.*;

public class Client extends BasicGame
{
    public static Logger log = LogManager.getLogger(Client.class.getName());

    public static SpriteSheet spriteSheet;

    public static int SPRITE_SIZE = 32;
    public static int TICKS_PER_SECOND = 60;


    SpriteSheetWrapper sheetWrapper;

    int imagex = 0;
    int imagey = 0;

    float view_x = 200;
    float view_y = 200;

    float dest_x = 400;
    float dest_y = 400;

    public Client()
    {
        super("OpenMOBA");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        log.info("in init");
        spriteSheet = new SpriteSheet("assets/ProjectUtumno_full.png", SPRITE_SIZE, SPRITE_SIZE);
        sheetWrapper = new SpriteSheetWrapper(spriteSheet);

        Input input = gc.getInput();
        input.enableKeyRepeat();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {

        // move towards the destination
        float speed = 2000.0f;

        float dx = view_x - dest_x;
        float dy = view_y - dest_y;
        float hyp = (float) sqrt(dx * dx + dy * dy);

        if (abs(hyp) > 1) {
            log.info("differences: x: " + dx + ", dy: " + dy + " hyp: " + hyp);

            float xratio = dx/hyp;
            float yratio = dy/hyp;

            float angle = (float) Math.atan2(dy, dx);

//            view_x -= speed * cos(angle) / TICKS_PER_SECOND;
//            view_y -= speed * sin(angle) / TICKS_PER_SECOND;
            view_x -= speed * xratio / TICKS_PER_SECOND;
            view_y -= speed * yratio / TICKS_PER_SECOND;
        }

    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        g.drawString("(" + imagex + ", " + imagey + ")", 50, 50);
        float scale = 2.0f;
        int scaled_size = (int)(SPRITE_SIZE * scale);
        int offset = scaled_size / 2;
        int basex = 100;
        int basey = 100;
        g.drawImage(sheetWrapper.getSprite(SpriteID.ARCHER).getScaledCopy(scale), view_x - offset, view_y - offset);
//        for (int xx = imagex; xx < min(imagex + 6, spriteSheet.getHorizontalCount()); xx++){
//            for (int yy = imagey; yy < min(imagey + 6, spriteSheet.getVerticalCount()); yy++) {
//                g.drawImage(spriteSheet.getSubImage(xx, yy).getScaledCopy(scale), basex + (xx - imagex) * scaled_size, basey + (yy - imagey) * scaled_size);
//            }
//        }

    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key){
            case Input.KEY_UP:
                imagey = max(0, imagey - 1);
                imagey = min(spriteSheet.getVerticalCount() - 1, imagey);
                break;
            case Input.KEY_DOWN:
                imagey = max(0, imagey + 1);
                imagey = min(spriteSheet.getVerticalCount() - 1, imagey);
                break;
            case Input.KEY_LEFT:
                imagex = max(0, imagex - 1);
                imagex = min(spriteSheet.getHorizontalCount() - 1, imagex);
                break;
            case Input.KEY_RIGHT:
                imagex = max(0, imagex + 1);
                imagex = min(spriteSheet.getHorizontalCount() - 1, imagex);
                break;
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        this.dest_x = x;
        this.dest_y = y;
    }

    public static void main(String[] args)
    {
        LoggerContext context = LoggerContext.getContext();
//        context.setConfigLocation();

        System.out.println(log.getLevel());
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Client());
            appgc.setDisplayMode(640, 480, false);
            appgc.setTargetFrameRate(120);
            appgc.setVSync(true);
            appgc.start();
        }
        catch (SlickException ex)
        {
            log.error(ex);
        }
    }
}