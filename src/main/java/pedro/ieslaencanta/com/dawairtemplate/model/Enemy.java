/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IMove;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author User
 */
public class Enemy extends SpriteMove implements IWarnClock {

    private Image img;
    //path para la imagen
    private static String pathurl = "enemigos/e1.png";
    //para la animación
    private int original_height;

    /**
     *
     * @param inc incremento del movimiento
     * @param s tamaño del avión
     * @param p coordenadas iniciales
     * @param board rectangulo con las dimensiones del juego para no salirse
     */
    public Enemy(int inc, Size s, Coordenada p, Rectangle board) {
	super(inc, s, p, true, true, board);
	this.img = new Image(getClass().getResourceAsStream("/" + Enemy.pathurl));
    }

    /**
     * dibujar, es algo más complejo al moverse las alas
     *
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
	gc.drawImage(img, 0,0 , this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
		this.getPosicion().getX(), this.getPosicion().getY(),
		this.getSize().getWidth(), this.getSize().getHeight());
    }

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
}
