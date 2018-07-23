package Tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.newdawn.slick.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SpriteExplorer extends BasicGame
{
    public static Logger log = LogManager.getLogger(SpriteExplorer.class.getName());

    public static SpriteSheet spriteSheet;

    public static int SPRITE_SIZE = 32;

    int imagex = 0;
    int imagey = 0;

    public SpriteExplorer()
    {
        super("Sprite Explorer");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        log.info("in init");
        spriteSheet = new SpriteSheet("assets/ProjectUtumno_full.png", SPRITE_SIZE, SPRITE_SIZE);
        log.info("dimensions: " + spriteSheet.getHorizontalCount() + ", " + spriteSheet.getVerticalCount());

        Input input = gc.getInput();
        input.enableKeyRepeat();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
//        log.info("in update");
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        float scale = 2.0f;
        int scaled_size = (int)(SPRITE_SIZE * scale);
        int basex = 0;
        int basey = 0;
        for (int xx = imagex; xx < min(imagex + 10, spriteSheet.getHorizontalCount()); xx++){
            for (int yy = imagey; yy < min(imagey + 7, spriteSheet.getVerticalCount()); yy++) {
                g.drawImage(spriteSheet.getSubImage(xx, yy).getScaledCopy(scale), basex + (xx - imagex) * scaled_size, basey + (yy - imagey) * scaled_size);
            }
        }
        g.drawString("(" + imagex + ", " + imagey + ")", 0, 460);
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

    public static void main(String[] args)
    {
        LoggerContext context = LoggerContext.getContext();
//        context.setConfigLocation();

        System.out.println(log.getLevel());
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new SpriteExplorer());
            appgc.setDisplayMode(640, 480, false);
            appgc.setVSync(true);
            appgc.start();
        }
        catch (SlickException ex)
        {
            log.error(ex);
        }
    }
}