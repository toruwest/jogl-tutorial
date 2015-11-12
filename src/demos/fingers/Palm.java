package demos.fingers;

import com.jogamp.opengl.GL2;

public class Palm {
	private static final float[][] palmVertex = {{-1f, 0f}, {+1f, 0f}, {+1f, 1f}, {-1f, 1f}};

	private final float positionX;
	private final float positionY;
	private final float size;
	private final float[] color;

	public Palm(float size, int x, int y, float[] color) {
		this.size = size;
		this.positionX = x;
		this.positionY = y;
		this.color = color;
	}

	// 手のひら？を描画する
	protected void render(GL2 gl2) {
		gl2.glTranslatef(positionX, positionY, 0f);

		gl2.glPushMatrix();
		gl2.glScalef(size, size, 1f);
		gl2.glColor3fv(color, 0);
		gl2.glBegin(GL2.GL_LINE_LOOP);
		for (int i = 0; i < palmVertex.length; i++) {
			gl2.glVertex3fv(palmVertex[i], 0);
		}
		gl2.glEnd();

		gl2.glPopMatrix();
	}
}
