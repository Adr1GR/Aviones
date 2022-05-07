/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author adria
 */
public class Enemy2 extends AEnemy {

    private Image img;
    //path para la imagen
    private static String pathurl = "enemigos/e2.png";

    public Enemy2() {
	this.img = new Image(getClass().getResourceAsStream("/" + Enemy2.pathurl));
    }

    @Override
    public void initEnemy(Coordenada c, Rectangle board) {
	super.init(3, new Size(200, 80), c, true, true, board);
    }

    @Override
    public void draw(GraphicsContext gc) {
	gc.drawImage(img, 220, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
		this.getPosicion().getX(), this.getPosicion().getY(),
		this.getSize().getWidth(), this.getSize().getHeight());
    }
}
