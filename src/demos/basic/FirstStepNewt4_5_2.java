package demos.basic;

import static com.jogamp.opengl.GL2.GL_POLYGON;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class FirstStepNewt4_5_2 implements GLEventListener {
	public static void main(String[] args){
		new FirstStepNewt4_5_2();
	}

	public FirstStepNewt4_5_2() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("First demo (Newt)");
		glWindow.setSize(300, 300);
		glWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent arg0) {
				System.exit(0);
			}
		});
		glWindow.addGLEventListener(this);
		FPSAnimator animator = new FPSAnimator(10); //(2)
		animator.add(glWindow);
		animator.start();
		glWindow.setVisible(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		//ウィンドウを青く塗りつぶす。
		gl.glClearColor(0f, 0f, 1.0f, 1.0f);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		//gl.glColor3f(1.0f, 0.0f, 0.0f); //ここは削除

		gl.glBegin(GL_POLYGON);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // 赤
		gl.glVertex2f(-0.9f,-0.9f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // 緑
		gl.glVertex2f(0.9f, -0.9f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // 青
		gl.glVertex2f(0.9f, 0.9f);
		gl.glColor3f(1.0f, 1.0f, 0.0f); // 黄
		gl.glVertex2f(-0.9f, 0.9f);
		gl.glEnd();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

}
