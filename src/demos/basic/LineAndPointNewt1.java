package demos.basic;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;

import java.nio.FloatBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import com.jogamp.common.nio.Buffers;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class LineAndPointNewt1 implements GLEventListener {
	public static void main(String[] args){
		new LineAndPointNewt1();
	}

	private float[] colors;
	private final short linePattern = 0b111100011001010;

	public LineAndPointNewt1() {
		initColors();
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Line and point (Newt)");
		glWindow.setSize(400, 300);
		glWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent arg0) {
				System.exit(0);
			}
		});
		glWindow.addGLEventListener(this);
		FPSAnimator animator = new FPSAnimator(10);
		animator.add(glWindow);
		animator.start();
		glWindow.setVisible(true);
	}

	private void initColors() {
        colors = new float[8];
        for(int i = 0; i < 8; i++) {
        		colors[i] = 0.3f + (0.1f * i);
        }
	}

	private FloatBuffer glGetBuf;

    @Override
	public void display(GLAutoDrawable gLDrawable) {
        final GL gl = gLDrawable.getGL();
        final GL2 gl2 = gl.getGL2();
        gl2.glClear(GL_COLOR_BUFFER_BIT);

//        gl2.glLoadIdentity();
        for(int i = 0; i < 8; i++) {
        	gl2.glPointSize((i + 1) * 0.5f);
        	System.out.print("point size:" + (i + 1) * 0.5f);
//        	gl2.glGetFloatv(GL.GL_POINT_SIZE, buf);
        	showGlStat(gl2, "点の大きさ", GL.GL_POINT_SIZE, 1);
        	gl2.glColor3f(1.0f, 1.0f, 1.0f);
        	gl2.glBegin(GL2.GL_POINTS);
        	gl2.glVertex2f(-0.9f, (i-7)*(1.6f/7f) + 0.8f);
        	gl2.glEnd();
        }
        //i = 0のときy= -0.8, i = 7のときy = 0.8
        //y = (1.6 / 7) * (i - 7) + 0.8
        for(int i = 0; i < 8; i++) {
        	gl2.glPointSize(2f);
        	gl2.glBegin(GL2.GL_POINTS);
        	gl2.glColor3f(colors[i], colors[i], colors[i]);
        	//-0.8から+0.8の範囲になるよう計算
        	gl2.glVertex2f(-0.8f, (i-7)*(1.6f/7f) + 0.8f);
        	gl2.glEnd();
        }

        for(int i = 1; i < 9; i++) {
        	gl2.glLineWidth(i * 0.5f);
        	showGlStat(gl2, "線の太さ", GL.GL_LINE_WIDTH, 1);
        	gl2.glColor3f(1.0f, 1.0f, 1.0f);
        	gl2.glBegin(GL2.GL_LINES);
        	gl2.glVertex2f(-0.6f + i*0.05f, -0.8f);
        	gl2.glVertex2f(-0.6f + i*0.05f, +0.8f);
        	gl2.glEnd();
        }

        gl2.glLineWidth(1f);
        for(int i = 0; i < 8; i++) {
        	gl2.glColor3f(colors[i], colors[i], colors[i]);
        	gl2.glBegin(GL2.GL_LINES);
        	gl2.glVertex2f(-0.1f + i*0.05f, -0.8f);
        	gl2.glVertex2f(-0.1f + i*0.05f, +0.8f);
        	gl2.glEnd();
        }

        gl2.glEnable(GL2.GL_LINE_STIPPLE);
        for(int i = 0; i < 8; i++) {
        	gl2.glLineStipple(i+1, linePattern );
        	gl2.glColor3f(colors[i], colors[i], colors[i]);
        	gl2.glBegin(GL2.GL_LINES);
        	gl2.glVertex2f(+0.4f + i*0.05f, -0.8f);
        	gl2.glVertex2f(+0.4f + i*0.05f, +0.8f);
        	gl2.glEnd();
        }
        gl2.glDisable(GL2.GL_LINE_STIPPLE);
    }

	private void showGlStat(final GL2 gl2, String paramName, int paramType, int bufSize) {
//		glGetBuf = Buffers.newDirectDoubleBuffer(bufSize);
		glGetBuf = Buffers.newDirectFloatBuffer(bufSize);
//		gl2.glGetDoublev(paramType, glGetBuf);
		gl2.glGetFloatv(paramType, glGetBuf);
		glGetBuf.rewind();

		System.out.print(paramName + ":");
		for(int i = 0; i < bufSize; i++) {
			System.out.print(glGetBuf.get(i) + ", ");
		}
		System.out.println();
	}

    @Override
	public void init(GLAutoDrawable gLDrawable) {
        GL2 gl2 = gLDrawable.getGL().getGL2();
        gl2.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        gl2.glPointSize(3.5f);
        showGlStat(gl2,"COLOR_CLEAR_VALUE", GL2.GL_COLOR_CLEAR_VALUE, 3);
    }

    @Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) { }

	@Override
	public void dispose(GLAutoDrawable arg0) {}
}
