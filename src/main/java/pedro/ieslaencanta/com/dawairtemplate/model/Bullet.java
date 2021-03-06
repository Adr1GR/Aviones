/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IMove;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author User
 */
public class Bullet extends SpriteMove implements IWarnClock, ICollision {

    private Image img;
    //path para la imagen
    private static String pathurl = "bullets/bullet_left.png";
    private boolean colision;

    public Bullet(int inc, Size s, Coordenada p, Rectangle board) {
	super(inc, s, p, true, true, board);
	this.img = new Image(getClass().getResourceAsStream("/" + Bullet.pathurl));
    }

    @Override
    public void draw(GraphicsContext gc) {
	gc.drawImage(img, 0, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
		this.getPosicion().getX(), this.getPosicion().getY(),
		this.getSize().getWidth(), this.getSize().getHeight());
    }

    //movimiento de la bala
    private void move() {
	this.move(IMove.Direction.LEFT);
    }

    @Override
    public void TicTac() {
	this.move();
	//mover las balas 
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
