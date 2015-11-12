package demos.basic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL;
//import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FirstStepSwing implements GLEventListener {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FirstStepSwing();
			}
		});
	}

	public FirstStepSwing() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2)); //(2)
		caps = new GLCapabilities(GLProfile.getMinimum(true)); //(2)
		caps = new GLCapabilities(GLProfile.getMaxFixedFunc(true));
		caps = new GLCapabilities(GLProfile.getMaxProgrammable(false));
		JFrame frame = new JFrame(); //(3)
		frame.setTitle("First demo (Swing)"); //(4)

		frame.addWindowListener(new WindowAdapter() { //(6)
        	    @Override
	            public void windowClosing(WindowEvent e) {
        	    	System.exit(0);
            	    }
        	});

		GLCanvas canvas = new GLCanvas(caps);
//
		System.out.println("autoswap:" + canvas.getAutoSwapBufferMode());
		canvas.setAutoSwapBufferMode(true);
		canvas.swapBuffers();

		canvas.setPreferredSize(new Dimension(300, 300)); //(5)

		canvas.addGLEventListener(this); //(7)

		frame.add(canvas, BorderLayout.CENTER);
		frame.setLocation(500, 300);
		frame.pack();
		frame.setVisible(true);//(9)
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		showGLInfo(drawable);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int z, int h) {}

	@Override
	public void display(GLAutoDrawable drawable) {}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	private static void showGLInfo(GLAutoDrawable drawable) {
		System.err.println("利用可能なプロファイルのリスト");
		for(String prof : GLProfile.GL_PROFILE_LIST_ALL) {
			System.err.println(prof);
		}
		System.err.println();
		System.err.println("選択されたGLCapabilities: " + drawable.getChosenGLCapabilities());
		GL gl = drawable.getGL();
		System.err.println("INIT GL IS: " + gl.getClass().getName());
		System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
		System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
		System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
//		System.err.println("GLSL VERSION: " + gl.glGetString(GL2.GL_SHADING_LANGUAGE_VERSION));
	}
}
