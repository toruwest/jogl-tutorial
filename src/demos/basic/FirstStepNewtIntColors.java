package demos.basic;

import static com.jogamp.opengl.GL2.GL_LINE_LOOP;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

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

public class FirstStepNewtIntColors implements GLEventListener {
	public static void main(String[] args){
		new FirstStepNewtIntColors();
	}

	public FirstStepNewtIntColors() {
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
		//背景を白く塗りつぶす。
		gl.glClearColor(1f, 1f, 1f, 1.0f);
		System.out.println("byte max:" + Byte.MAX_VALUE);
		System.out.println("short max:" + Short.MAX_VALUE);
		System.out.println("int max:" + Integer.MAX_VALUE);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

 		gl.glColor3f(1f, 0, 0);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(-0.95f,-0.95f);
		gl.glVertex2f(0.95f, -0.95f);
		gl.glVertex2f(0.95f, 0.95f);
		gl.glVertex2f(-0.95f, 0.95f);
		gl.glEnd();

		gl.glColor3f(1f, -1f, -1f);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(-0.97f,-0.97f);
		gl.glVertex2f(0.97f, -0.97f);
		gl.glVertex2f(0.97f, 0.97f);
		gl.glVertex2f(-0.97f, 0.97f);
		gl.glEnd();

 		gl.glColor3b(Byte.MAX_VALUE, (byte)0, (byte)0);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(-0.9f,-0.9f);
		gl.glVertex2f(0.9f, -0.9f);
		gl.glVertex2f(0.9f, 0.9f);
		gl.glVertex2f(-0.9f, 0.9f);
		gl.glEnd();

		//この上下はどちらも同じ結果(全て赤色)になる。負の数は0として扱われるようだ。

 		gl.glColor3b(Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(-0.8f,-0.8f);
		gl.glVertex2f(0.8f, -0.8f);
		gl.glVertex2f(0.8f, 0.8f);
		gl.glVertex2f(-0.8f, 0.8f);
		gl.glEnd();

 		gl.glColor3s((Short.MAX_VALUE), (short)0, (short)0);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(-0.7f,-0.7f);
		gl.glVertex2f(0.7f, -0.7f);
		gl.glVertex2f(0.7f, 0.7f);
		gl.glVertex2f(-0.7f, 0.7f);
		gl.glEnd();

 		gl.glColor3s((Short.MAX_VALUE), Short.MIN_VALUE, Short.MIN_VALUE);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(-0.6f,-0.6f);
		gl.glVertex2f(0.6f, -0.6f);
		gl.glVertex2f(0.6f, 0.6f);
		gl.glVertex2f(-0.6f, 0.6f);
		gl.glEnd();

		gl.glColor3i(Integer.MAX_VALUE, 0, 0);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(-0.5f,-0.5f);
		gl.glVertex2f(0.5f, -0.5f);
		gl.glVertex2f(0.5f, 0.5f);
		gl.glVertex2f(-0.5f, 0.5f);
		gl.glEnd();

		gl.glColor3i(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(-0.4f,-0.4f);
		gl.glVertex2f(0.4f, -0.4f);
		gl.glVertex2f(0.4f, 0.4f);
		gl.glVertex2f(-0.4f, 0.4f);
		gl.glEnd();

		//以下のように、ByteBuffer/ShortBuffer/IntBufferやbyte/short/intの配列も指定できるが、メソッド名が変わる。

		FloatBuffer floatBuf = FloatBuffer.allocate(4);
		gl.glColor3fv(floatBuf);
		DoubleBuffer doubleBuf = DoubleBuffer.allocate(4);
		gl.glColor3dv(doubleBuf);
		IntBuffer intBuf = IntBuffer.allocate(4);
		gl.glColor3iv(intBuf);
		ByteBuffer byteBuf = ByteBuffer.allocate(4);
		gl.glColor3bv(byteBuf);
		ShortBuffer shortBuf = ShortBuffer.allocate(4);
		gl.glColor3sv(shortBuf);

		float[] floatArray = new float[4];
		gl.glColor3fv(floatArray, 0);
		double[] doubleArray = new double[4];
		gl.glColor3dv(doubleArray, 0);
		int[] intArray = new int[3];
		gl.glColor3iv(intArray, 0);
		byte[] byteArray = new byte[3];
		gl.glColor3bv(byteArray, 0);
		short[] shortArray = new short[3];
		gl.glColor3sv(shortArray, 0);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

}
