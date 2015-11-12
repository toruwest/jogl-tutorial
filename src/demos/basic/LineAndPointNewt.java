package demos.basic;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class LineAndPointNewt implements GLEventListener {
	public static void main(String[] args){
		new LineAndPointNewt();
	}

	private final GLU glu = new GLU();
    float[] colors;
	private final short linePattern = 0b111100011001010;

	public LineAndPointNewt() {
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
		FPSAnimator animator = new FPSAnimator(60); //(2)
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

    @Override
	public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl2 = gLDrawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl2.glLoadIdentity();
        gl2.glTranslatef(-3f, -2f, -6.0f);
        for(int i = 0; i < 8; i++) {
        	gl2.glPointSize(i * 0.5f);
        	gl2.glColor3f(1.0f, 1.0f, 1.0f);
        	gl2.glTranslatef(0f, 0.5f, 0f);
        	gl2.glBegin(GL2.GL_POINTS);
        	gl2.glVertex2f(0.0f, 0.0f);
        	gl2.glEnd();
        }

        gl2.glLoadIdentity();
        gl2.glPointSize(3f);
        gl2.glTranslatef(-2.5f, -2f, -6.0f);
        for(int i = 0; i < 8; i++) {
        	gl2.glTranslatef(0f, 0.5f, 0f);
        	gl2.glBegin(GL2.GL_POINTS);
//        	gl2.glPointSize(3f);
        	gl2.glColor3f(colors[i], colors[i], colors[i]);
        	gl2.glVertex2f(0.0f, 0.0f);
        	gl2.glEnd();
        }

        gl2.glLoadIdentity();
        gl2.glTranslatef(-2f, -2f, -6.0f);
        for(int i = 0; i < 8; i++) {
        	gl2.glLineWidth(i * 0.05f);
        	gl2.glColor3f(1.0f, 1.0f, 1.0f);
        	gl2.glBegin(GL2.GL_LINES);
        	gl2.glVertex2f(i*0.1f, 0.0f);
        	gl2.glVertex2f(i*0.1f, 4.0f);
        	gl2.glEnd();
        }

        gl2.glLoadIdentity();
        gl2.glTranslatef(-1f, -2.0f, -6.0f);
        for(int i = 0; i < 8; i++) {
        	gl2.glColor3f(colors[i], colors[i], colors[i]);
        	gl2.glBegin(GL2.GL_LINES);
        	gl2.glVertex2f(i*0.2f, 0.0f);
        	gl2.glVertex2f(i*0.2f, 4.0f);
        	gl2.glEnd();
        }

        gl2.glLoadIdentity();
        gl2.glTranslatef(1f, -2.0f, -6.0f);
        gl2.glEnable(GL2.GL_LINE_STIPPLE);
        for(int i = 0; i < 8; i++) {
        	gl2.glLineStipple(i+1, linePattern );
        	gl2.glColor3f(colors[i], colors[i], colors[i]);
        	gl2.glBegin(GL2.GL_LINES);
        	gl2.glVertex2f(i*0.2f, 0.0f);
        	gl2.glVertex2f(i*0.2f, 4.0f);
        	gl2.glEnd();
        }
        gl2.glDisable(GL2.GL_LINE_STIPPLE);
        gl2.glFlush();
    }

    @Override
	public void init(GLAutoDrawable gLDrawable) {
        GL2 gl2 = gLDrawable.getGL().getGL2();
        gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl2.glShadeModel(GL2.GL_SMOOTH);
    }

    @Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
    	System.out.printf("x:%d, y:%d%n", x, y);
        final GL2 gl2 = gLDrawable.getGL().getGL2();

        if (height <= 0) // avoid a divide by zero error!
            height = 1;
        final float h = (float) width / (float) height;
        gl2.glViewport(0, 0, width, height);
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
    }

	@Override
	public void dispose(GLAutoDrawable arg0) {}
}
