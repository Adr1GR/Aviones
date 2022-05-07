/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import com.sun.javafx.font.directwrite.RECT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IMove;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author User
 */
public abstract class AEnemy extends SpriteMove implements IWarnClock, ICollision {

    private boolean colision;

    public AEnemy() {
    }

    /**
     *
     * @param inc incremento del movimiento
     * @param s tamaño del avión
     * @param p coordenadas iniciales
     * @param board rectangulo con las dimensiones del juego para no salirse
     *
     * public Enemy(int inc, Size s, Coordenada p, Rectangle board) { super(inc,
     * s, p, true, true, board); this.img = new
     * Image(getClass().getResourceAsStream("/" + Enemy.pathurl)); }
     *
     * /**
     * dibujar, es algo más complejo al moverse las alas
     *
     * @param gc
     */
    //movimiento del enemigo
    private void move() {
	this.move(IMove.Direction.LEFT);
    }

    /**
     * cada vez que se recibe un tictac se mueve, faltan las balas del fighter
     */
    @Override
    public void TicTac() {
	this.move();
	//mover las balas 
    }

    public void initEnemy(Coordenada c, Rectangle board) {
	super.init(3, new Size(74, 26), c, true, true, board);
    }

    @Override
    public int getX() {
	return this.getPosicion().getX();
    }

    @Override
    public int getY() {
	return this.getPosicion().getY();
    }

    @Override
    public int getHeight() {
	return this.getSize().getHeight();
    }

    @Override
    public int getWidht() {
	return this.getSize().getWidth();
    }

    @Override
    public boolean hascollided() {
	return this.colision;
    }

    @Override
    public void setColision() {
	this.colision = true;
    }

    @Override
    public void setFree() {
	this.colision = false;
    }
}
