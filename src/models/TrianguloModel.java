package models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Familia
 */
public class TrianguloModel extends FiguraModel {

	public TrianguloModel() {
		super();
		nombre = "Triangulo";
		puntos = new PointsModel(3);
	}

	@Override
	public void dibujar(Graphics2D g) {

		if (!canDraw()) {
			puntos.dibujar(g);
			return;
		}

		if (isSelected()) {
			g.setStroke(new BasicStroke(5));
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(background);
		}

		g.drawPolygon(puntos.getXPoints(), puntos.getYPoints(), puntos.size());

		g.setStroke(new BasicStroke(2));
		g.setColor(background);
		g.fillPolygon(puntos.getXPoints(), puntos.getYPoints(), puntos.size());

		g.setColor(Color.BLACK);
	}

}
