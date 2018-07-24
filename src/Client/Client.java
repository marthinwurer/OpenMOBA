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
    private static Logger log = LogManager.getLogger(Client.class.getName());

    private static SpriteSheet spriteSheet;

    public static final int SPRITE_SIZE = 32;
    public static final int TICKS_PER_SECOND = 60;


    SpriteSheetWrapper sheetWrapper;

    int imagex = 0;
    int imagey = 0;

    float view_x = 200;
    float view_y = 200;

    float dest_x = 400;
    float dest_y = 400;

    Entity character;

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

        character  = new Entity("Archer",
                sheetWrapper.getSprite(SpriteID.ARCHER).getScaledCopy(2),
                200, 200, 200);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {

        character.update_position();

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
        g.drawImage(character.getSprite(), character.getX() - offset, character.getY() - offset);
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
        character.setDest(x, y);
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