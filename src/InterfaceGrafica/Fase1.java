/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGrafica;

import SimuladorDeDados.Dice;
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
    
    private TiledMap mapaAtual;
    private final String mapa1 = "images/mapa1.tmx";
    
    private SpriteSheet spriteSheet;
    private Animation link, movingUp, movingDown, movingLeft, movingRight;
    
    private boolean trocado = false;
    
    private int x, y;
    
    Fase1(int play) {
    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        container.setVSync(true);
        container.setTargetFrameRate(60);
        container.setMaximumLogicUpdateInterval(10);
        
        mapaAtual = new TiledMap(mapa1);
        
        spriteSheet = new SpriteSheet("images/oorjG.png", 90, 90);        
                
        x = 1;
        y = 18;
        
        Image[] walkLeft = {
            //spriteSheet.getSubImage(0, 0),
            spriteSheet.getSubImage(1, 0),
            spriteSheet.getSubImage(2, 0),
            spriteSheet.getSubImage(3, 0),
            spriteSheet.getSubImage(4, 0),
        };
        
        Image[] walkRight = {
            spriteSheet.getSubImage(0, 1),
            spriteSheet.getSubImage(1, 1),
            spriteSheet.getSubImage(2, 1),
            spriteSheet.getSubImage(3, 1),
            spriteSheet.getSubImage(4, 1),
        };
        
        Image[] walkUp = {
            spriteSheet.getSubImage(0, 2),
            spriteSheet.getSubImage(1, 2),
            spriteSheet.getSubImage(2, 2),
            spriteSheet.getSubImage(3, 2),
            spriteSheet.getSubImage(4, 2),
        };
        
        Image[] walkDown = {
            spriteSheet.getSubImage(0, 3),
            spriteSheet.getSubImage(1, 3),
            spriteSheet.getSubImage(2, 3),
            spriteSheet.getSubImage(3, 3),
            spriteSheet.getSubImage(4, 3),
        };
        
        movingUp = new Animation(walkUp, x, false);
        movingDown = new Animation(walkDown, x, false);
        movingLeft = new Animation(walkLeft, x, false);
        movingRight = new Animation(walkRight, x, false);
        
        link = movingUp;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        mapaAtual.render(0, 0);
        link.draw(x * tileSize - (spriteSize - tileSize)/2,
                y * tileSize - (spriteSize - tileSize),
                spriteSize,
                spriteSize);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        int objectLayer = mapaAtual.getLayerIndex("Objetos");
        int transitionLayer = mapaAtual.getLayerIndex("Transicao");
        mapaAtual.getTileId(0, 0, objectLayer);

        if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
            link = movingUp;
            if (mapaAtual.getTileId(x, y-1, objectLayer) == 0){
                link.update(delta);
                y--;
            }
            this.trocaMapa(mapaAtual, transitionLayer, game);
        }
        
        if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S)) {
            link = movingDown;
            if (mapaAtual.getTileId(x, y+1, objectLayer) == 0){
                                link.update(delta);
                y++;
            }
            this.trocaMapa(mapaAtual, transitionLayer, game);
        }
        
        if (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_A)) {
            link = movingLeft;
            if (mapaAtual.getTileId(x-1, y, objectLayer) == 0){
                link.update(delta);
                x--;
            }
            this.trocaMapa(mapaAtual, transitionLayer, game);
        }
        
        if (input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_D)) {
            link = movingRight;
            if (mapaAtual.getTileId(x+1, y, objectLayer) == 0){
                link.update(delta);
                x++;
            }
            this.trocaMapa(mapaAtual, transitionLayer, game);
        }
        
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(0);
        }
    }
    
    private void trocaMapa(TiledMap mapaAtual, int transitionLayer, StateBasedGame game)
            throws SlickException{
        if (mapaAtual.getTileId(x, y, transitionLayer) != 0){
            game.enterState(4);
        } else {
            //int entrada = Dice.rolagem(5);
            //if (entrada == 1){
              //  game.addState(new Batalha(7));
                //game.getState(7).init(null, game);
            //}
        }
    }
}
