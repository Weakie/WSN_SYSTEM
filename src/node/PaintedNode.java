package node;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Stroke;

public interface PaintedNode {
	public static final Stroke stroke1 = new BasicStroke(1);
	public static final Stroke stroke2 = new BasicStroke(2);
	public static final Stroke stroke3 = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,new float[]{5.0f},0.0f);
	public void draw(Graphics g,double size);
}
