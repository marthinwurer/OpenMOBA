package Client;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class SpriteSheetWrapper {
    private SpriteSheet spriteSheet;


    public SpriteSheetWrapper(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public Image getSprite(SpriteID id) {
        return spriteSheet.getSprite(id.x, id.y);
    }

}
