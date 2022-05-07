/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import java.util.ArrayList;
import java.util.function.Predicate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.swing.text.Position;
import pedro.ieslaencanta.com.dawairtemplate.Background;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IDrawable;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;

/**
 *
 * @author Pedro
 */
public class Level implements IDrawable, IWarnClock, IKeyListener {

    public enum Estado {
	PRE_STARTED,
	RUNNING,
	STOPPED,
	PAUSED,
	PRE_END,
	END
    }
    private static String[] msg = {"\"Pulsar una tecla para empezar", "Siguiente nivel..."};
    private String background_path;
    private int speed;
    private int position;
    private int fin;
    private Background background;

    private GraphicsContext bg_ctx;
    private MediaPlayer player;
    private float[] probabilidadenemigos;
    private Size s;

    private Estado estado;
    private Player p;
    private Fighter fighter;
    private AEnemy enemigoprueba;
    private ArrayList<Bullet> balasEnemy;
    private ArrayList<AEnemy> enemigos;
    private int contador = 0;
    private String tipoenemigo;

    public Level(String image_path, String music_path, Size s, int speed, Coordenada start_position, GraphicsContext bg_ctx, float[] probabilidad_enemigos, int fin) {
	this.initFactoryEnemy();
	this.tipoenemigo = FactoryEnemy.getKeyNames().get(1);
	this.background = new Background(image_path, s, speed, start_position);
	this.background.setBg(bg_ctx);
	this.position = 0;
	this.speed = speed;
	this.estado = Estado.PRE_STARTED;
	this.fin = fin;
	this.s = s;
	this.enemigos = new ArrayList<>();
	//crear el avion
	this.fighter = new Fighter(
		3,
		new Size(74, 26),
		new Coordenada(20, s.getHeight() / 2),
		new Rectangle(new Coordenada(0, 0), new Coordenada(s.getWidth(), s.getHeight())));

	this.probabilidadenemigos = probabilidad_enemigos;
	this.initSound(music_path);

	//Enemigo de prueba
	//this.enemigoprueba = new Enemy(3, new Size(912, 800), new Coordenada(1000, 40), new Rectangle(new Coordenada(200, 200), new Coordenada(s.getWidth(), s.getHeight())));
    }

    private void initFactoryEnemy() {
	FactoryEnemy.addEnemy("Enemy1", Enemy1::new);
	FactoryEnemy.addEnemy("Enemy2", Enemy2::new);
    }

    private void crearEnemy() {
	AEnemy tempo;
	this.tipoenemigo = FactoryEnemy.getNext(this.tipoenemigo);

	int cordy = (int) (Math.random() * (this.s.getHeight() - 50));
	System.out.println("Credo");
	tempo = FactoryEnemy.create(this.tipoenemigo);
	tempo.initEnemy(new Coordenada(1000, cordy), new Rectangle(new Coordenada(0, 0), new Coordenada(s.getWidth(), s.getHeight())));
	this.enemigos.add(tempo);
    }

    private void initSound(String music_path) {
	this.player = new MediaPlayer(new Media(getClass().getResource(music_path).toString()));

	player.setOnEndOfMedia(new Runnable() {
	    @Override
	    public void run() {
		player.seek(Duration.ZERO);
	    }
	});

    }

    @Override
    public void draw(GraphicsContext gc) {

	this.background.paint(gc);
	this.fighter.draw(gc);
	this.enemigos.forEach(a -> a.draw(gc));

	if (this.estado == Estado.PRE_STARTED) {
	    gc.setFill(Color.BROWN);
	    gc.setStroke(Color.WHITE);
	    gc.strokeText(Level.msg[0], 100, 200);
	    gc.fillText(Level.msg[0], 100, 200);

	}
    }

    @Override
    public void TicTac() {
	if (this.getEstado() == Estado.RUNNING) {
	    //llamar a tictac de los hijos
	    this.TicTacChildrens();
	    this.detectCollisions();
	    //contador prueba
	    this.contador++;
	    if (contador == 100) {
		this.crearEnemy();
		contador = 0;
	    }

	    //posicion en la que termina
	    if (this.position < this.fin) {
		this.position += this.speed;
	    } //else {
	    //	this.EndLevel();
	    // }
	}
    }

    private void TicTacChildrens() {
	//pintar el fondo
	this.background.TicTac();
	this.fighter.TicTac();
	this.enemigos.forEach(e -> e.TicTac());
    }

    private void detectCollisions() {
	//se mira si las balas del avión le pegan a algún enemigo
	//ademá se borran los que se pasen por el lateral
	this.enemigos.removeIf(e -> e.getPosicion().getX() < e.getInc() || e.hascollided());

	this.fighter.getBalas().forEach(b -> {
	    this.enemigos.forEach(e -> e.isCollision(b));
	});

	this.enemigos.forEach(e -> {
	    e.isCollision(fighter);
	});

    }

    private void moveBullets() {
	//mover las balas
	//comprobar las condiciones para borrar y borrar las balas

    }

    public boolean isEnd() {
	return this.getEstado() == Estado.STOPPED;
    }

    private void EndLevel() {
	this.player.stop();
	this.setEstado(Estado.END);
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
	return estado;
    }

    /**
     * @return the p
     */
    public Player getP() {
	return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(Player p) {
	this.p = p;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
	this.estado = estado;
    }

    @Override
    public void onKeyPressed(KeyCode code) {
	//pasar el key code al avion
	this.fighter.onKeyPressed(code);
    }

    @Override
    public void onKeyReleased(KeyCode code) {

	this.fighter.onKeyReleased(code);
	//para iniciar el juego
	if (this.getEstado() == Level.Estado.PRE_STARTED) {
	    this.setEstado(Level.Estado.RUNNING);
	}
	if (this.getEstado() == Level.Estado.RUNNING) {

	    if (player.getStatus() == MediaPlayer.Status.READY) {
		player.play();
	    }
	}

    }

    public ArrayList<Bullet> getBalasEnemy() {
	return balasEnemy;
    }

    public void setBalasEnemy(ArrayList<Bullet> balasEnemy) {
	this.balasEnemy = balasEnemy;
    }

    public Fighter getFighter() {
	return fighter;
    }

    public void setFighter(Fighter fighter) {
	this.fighter = fighter;
    }

    /**
     * @param tipoenemigo the tipoenemigo to set
     */
    public void setTipoenemigo(String tipoenemigo) {
	this.tipoenemigo = tipoenemigo;
    }

}
