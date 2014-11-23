/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGrafica;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author pedro_000
 */
public class Fase1 extends BasicGameState {

    private final int spriteSize = 80;
    private final int tileSize = 32;

    private int linkX;
    private int linkY;
    
    private TiledMap mapaAtual;
    private final String mapa1 = "images/mapa1.tmx";

    private SpriteSheet spriteSheet;
    private Animation link, movingUp, movingDown, movingLeft, movingRight;

    private Sound enterStage;
    private int x, y;

    Fase1(int play) {
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        mapaAtual = new TiledMap(mapa1);
        enterStage = new Sound("sound/enterstage.wav");
        spriteSheet = new SpriteSheet("images/oorjG.png", 90, 90);

        x = 1;
        y = 18;
        
        linkX = x*tileSize;
        linkY = y*tileSize;

        Image[] walkLeft = {
            spriteSheet.getSubImage(0, 0),
            spriteSheet.getSubImage(1, 0),
            spriteSheet.getSubImage(2, 0),
            spriteSheet.getSubImage(3, 0),
            spriteSheet.getSubImage(4, 0),};

        Image[] walkRight = {
            spriteSheet.getSubImage(0, 1),
            spriteSheet.getSubImage(1, 1),
            spriteSheet.getSubImage(2, 1),
            spriteSheet.getSubImage(3, 1),
            spriteSheet.getSubImage(4, 1),};

        Image[] walkUp = {
            spriteSheet.getSubImage(0, 2),
            spriteSheet.getSubImage(1, 2),
            spriteSheet.getSubImage(2, 2),
            spriteSheet.getSubImage(3, 2),
            spriteSheet.getSubImage(4, 2),};

        Image[] walkDown = {
            spriteSheet.getSubImage(0, 3),
            spriteSheet.getSubImage(1, 3),
            spriteSheet.getSubImage(2, 3),
            spriteSheet.getSubImage(3, 3),
            spriteSheet.getSubImage(4, 3),};

        movingUp = new Animation(walkUp, 200, false);
        movingDown = new Animation(walkDown, 200, false);
        movingLeft = new Animation(walkLeft, 200, false);
        movingRight = new Animation(walkRight, 200, false);

        link = movingUp;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        mapaAtual.render(0, 0);
        link.draw(linkX - (spriteSize - tileSize)/ 2,
                linkY - (spriteSize - tileSize),
                spriteSize,
                spriteSize);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        
        x = (int)(linkX /32);
        y = (int)(linkY/32);

        int objectLayer = mapaAtual.getLayerIndex("Objetos");
        int transitionLayer = mapaAtual.getLayerIndex("Transicao");
        mapaAtual.getTileId(0, 0, objectLayer);

        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
            link = movingUp;
            link.update(delta);

            if (mapaAtual.getTileId(x, (int) (y - delta * 0.5f), objectLayer) == 0) {
                linkY -= delta * 0.5f;
            }
            this.trocaMapa(mapaAtual, transitionLayer, game);
        }

        if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            link = movingDown;
            link.update(delta);
            
            if (mapaAtual.getTileId(x, (int) (y + delta * 0.5f), objectLayer) == 0) {
                linkY += delta * 0.5f;
            }
            this.trocaMapa(mapaAtual, transitionLayer, game);
        }

        if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
            link = movingLeft;
            link.update(delta);
            
            if (mapaAtual.getTileId((int) (x - delta * 0.5f), y, objectLayer) == 0) {
                linkX -= delta * 0.5f;
            }
            this.trocaMapa(mapaAtual, transitionLayer, game);
        }

        if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            link = movingRight;
            link.update(delta);
            
            if (mapaAtual.getTileId((int) (x + delta * 0.5f), y, objectLayer) == 0) {
                linkX += delta * 0.5f;
            }
            this.trocaMapa(mapaAtual, transitionLayer, game);
        }

        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            game.enterState(0);
        }
    }

    private void trocaMapa(TiledMap mapaAtual, int transitionLayer, StateBasedGame game)
            throws SlickException {
        if (mapaAtual.getTileId((int) x, (int) y, transitionLayer) != 0) {
            enterStage.play();
            x = 1;
            y = 18;
            game.enterState(4);
        }
    }
}