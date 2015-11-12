package demos.fingers;

import com.jogamp.opengl.GL2;

public class Finger {
	private static final float[][] fingerVertex = {{-1f, 0f}, {+1f, 0f}, {+1f, 1f}, {-1f, 1f}};

	private final float positionX;
	private final float positionY;
	private final float length;
	private float rotationAngle;
	private final float[] color;

	public Finger(Finger parent, float length, float rotationAngle, float[] color) {
		this.positionX = parent.getJointX();
		this.positionY = parent.getJointY();
		this.length = length;
		this.rotationAngle = rotationAngle;
		this.color = color;
	}

	public Finger(float x, float y, float length, float rotationAngle, float[] color) {
		this.positionX = x;
		this.positionY = y;
		this.length = length;
		this.rotationAngle = rotationAngle;
		this.color = color;
	}

	float getJointY() {
		return length;
	}

	float getJointX() {
		return 0;
	}

	public void updateRotation(float angle) {
		this.rotationAngle += angle;
	}

	// 指を描画する
	protected void render(GL2 gl2) {
		gl2.glTranslatef(positionX, positionY, 0f);
		gl2.glRotatef(rotationAngle, 0f, 0f, 1f);

		gl2.glPushMatrix();
		gl2.glScalef(1f, length, 1f);
		gl2.glColor3fv(color, 0);
		gl2.glBegin(GL2.GL_LINE_LOOP);
		for (int i = 0; i < fingerVertex.length; i++) {
			gl2.glVertex3fv(fingerVertex[i], 0);
		}
		gl2.glEnd();

		gl2.glPopMatrix();
	}
}
