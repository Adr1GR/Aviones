/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IMove;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;
import pedro.ieslaencanta.com.dawairtemplate.model.Level;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;

/**
 *
 * @author User
 */
public class Fighter extends SpriteMove implements IKeyListener, IWarnClock , ICollision {

    private boolean[] keys_presed;
    private Image img;
    //path para la imagen
    private static String pathurl = "avion.png";
    //para la animación
    private int original_height;
    private ArrayList<Bullet> balas;
    private boolean colision;

    /**
     *
     * @param inc incremento del movimiento
     * @param s tamaño del avión
     * @param p coordenadas iniciales
     * @param board rectangulo con las dimensiones del juego para no salirse
     */
    public Fighter(int inc, Size s, Coordenada p, Rectangle board) {
	super(inc, s, p, true, true, board);
	this.keys_presed = new boolean[5];
	this.img = new Image(getClass().getResourceAsStream("/" + Fighter.pathurl));
	//cambia al mover arriba y abajo
	this.original_height = s.getHeight();
	this.balas = new ArrayList<Bullet>();
    }

    public void shoot() {
	Bullet tempo = new Bullet(6, new Size(36, 8),
		new Coordenada(this.getPosicion().getX() + 70, this.getPosicion().getY() + 12), this.board);

	balas.add(tempo);
    }

//    public void crearEnemy(){
//	int coordenadaRandom = (int) (Math.random()*480);
//	Enemy tempo = new Enemy(3, new Size(912, 800), 
//		new Coordenada(1000, 40), new Rectangle(new Coordenada(200, 200), new Coordenada(s.getWidth(), s.getHeight())));
//    }
    /**
     * acciones al pulsar las teclas
     *
     * @param code
     */
    @Override
    public void onKeyPressed(KeyCode code) {

	if (code == KeyCode.RIGHT) {
	    this.keys_presed[0] = true;
	}
	if (code == KeyCode.LEFT) {
	    this.keys_presed[1] = true;
	}
	if (code == KeyCode.UP) {
	    this.keys_presed[2] = true;
	    this.getSize().setHeight(40);
	}
	if (code == KeyCode.DOWN) {
	    this.keys_presed[3] = true;
	    this.getSize().setHeight(40);
	}
    }

    /**
     * acciones al soltar el teclado
     *
     * @param code
     */
    @Override
    public void onKeyReleased(KeyCode code) {

	if (code == KeyCode.SPACE || code == KeyCode.A || code == KeyCode.S) {
	    //crear una bala y añadirla
	    this.shoot();
	}
	if (code == KeyCode.RIGHT) {
	    this.keys_presed[0] = false;
	}
	if (code == KeyCode.LEFT) {
	    this.keys_presed[1] = false;
	}
	if (code == KeyCode.UP) {
	    this.keys_presed[2] = false;
	    this.getSize().setHeight(original_height);
	}
	if (code == KeyCode.DOWN) {
	    this.keys_presed[3] = false;
	    this.getSize().setHeight(original_height);
	}
//	if (code == KeyCode.SHIFT) {
//	    
//	}
    }

    /**
     * dibujar, es algo más complejo al moverse las alas
     *
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
	if (keys_presed[2]) {
	    gc.drawImage(img, 163, 7, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
		    this.getPosicion().getX(), this.getPosicion().getY(),
		    this.getSize().getWidth(), this.getSize().getHeight());
	} else {
	    if (keys_presed[3]) {
		gc.drawImage(img, 54, 7, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
			this.getPosicion().getX(), this.getPosicion().getY(),
			this.getSize().getWidth(), this.getSize().getHeight());
	    } else {
		gc.drawImage(img, 105, 8, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
			this.getPosicion().getX(), this.getPosicion().getY(),
			this.getSize().getWidth(), this.getSize().getHeight());
	    }
	}
	this.balas.forEach(a -> a.draw(gc));
    }
    //movimiento del avión

    private void move() {

	if (this.keys_presed[0]) {
	    this.move(IMove.Direction.RIGHT);
	}
	if (this.keys_presed[1]) {
	    this.move(IMove.Direction.LEFT);
	}
	if (this.keys_presed[2]) {
	    this.move(IMove.Direction.UP);
	}
	if (this.keys_presed[3]) {
	    this.move(IMove.Direction.DOWN);
	}
	this.balas.forEach(a -> a.move(Direction.RIGHT));
	this.balas.removeIf(a
		-> a.hascollided() || a.getPosicion().getX() + a.getSize().getWidth() > this.board.getEnd().getX() - this.inc * 5
	);
    }

    /**
     * cada vez que se recibe un tictac se mueve, faltan las balas del fighter
     */
    @Override
    public void TicTac() {
	this.move();
	//mover las balas 
    }

    public ArrayList<Bullet> getBalas() {
	return balas;
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
