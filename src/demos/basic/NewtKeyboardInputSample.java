package demos.basic;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class NewtKeyboardInputSample implements GLEventListener {
	private static final char KEY_ESC = 0x1b;

	public static void main(String[] args) {
		new NewtKeyboardInputSample2();
	}

	private final GLWindow glWindow;

	public NewtKeyboardInputSample() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		glWindow = GLWindow.create(caps);
		glWindow.setTitle("Keyboard input demo (Newt)");
		glWindow.setSize(300, 300);

		glWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent arg0) {
				System.out.println("デストロイされた");
				System.exit(0);
			}
		});

		glWindow.addGLEventListener(this);
		glWindow.addKeyListener(new com.jogamp.newt.event.KeyAdapter() {                   //(2)
			@Override
			public void keyPressed(KeyEvent e) {
				char keyChar = e.getKeyChar();
				printCharType(keyChar, "pressed");

				if(e.isAltDown()) {
					System.out.println("ALT key pressed");
				}
				if(e.isShiftDown()) {
					System.out.println("Shift key pressed");
				}
				if(e.isControlDown()) {
					System.out.println("Ctrl key pressed");
				}
				if(e.isAltGraphDown()) {
					System.out.println("AltGraph key pressed");
				}
				if(e.isMetaDown()) {
					System.out.println("Meta key pressed");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				char keyChar = e.getKeyChar();
				printCharType(keyChar, "released");

				if(e.isAltDown()) {
					System.out.println("ALT key released");
				}
				if(e.isShiftDown()) {
					System.out.println("Shift key released");
				}
				if(e.isControlDown()) {
					System.out.println("Ctrl key released");
				}
				if(e.isAltGraphDown()) {
					System.out.println("AltGraph key released");
				}
				if(e.isMetaDown()) {
					System.out.println("Meta key released");
				}
					if(keyChar == KEY_ESC || keyChar == 'q' || keyChar == 'Q') { //KeyEvent.VK_Qは大文字。小文字?
					glWindow.destroy();
				}
			}
		});
		FPSAnimator animator = new FPSAnimator(10);
		animator.add(glWindow);
		animator.start();
		glWindow.setVisible(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		//背景を白く塗りつぶす。
		gl.glClearColor(1f, 1f, 1f, 1.0f);
		showGLInfo(drawable);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

	@Override
	public void display(GLAutoDrawable drawable) {}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		System.out.println("dispose()");
	}

	private static void showGLInfo(GLAutoDrawable drawable) {
		for(String prof : GLProfile.GL_PROFILE_LIST_ALL) {
			System.err.println(prof);
		}
		System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());

		GL gl = drawable.getGL();
		System.err.println("INIT GL IS: " + gl.getClass().getName());
		System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
		System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
		System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
	}

//	@Override
//	public void keyPressed(KeyEvent e) {
//		char keyChar = e.getKeyChar();
//		printCharType(keyChar, "pressed");
//
//		if(e.isAltDown()) {
//			System.out.println("ALT key pressed");
//		}
//		if(e.isShiftDown()) {
//			System.out.println("Shift key pressed");
//		}
//		if(e.isControlDown()) {
//			System.out.println("Ctrl key pressed");
//		}
//		if(e.isAltGraphDown()) {
//			System.out.println("AltGraph key pressed");
//		}
//		if(e.isMetaDown()) {
//			System.out.println("Meta key pressed");
//		}
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		char keyChar = e.getKeyChar();
//		printCharType(keyChar, "released");
//
//		if(e.isAltDown()) {
//			System.out.println("ALT key released");
//		}
//		if(e.isShiftDown()) {
//			System.out.println("Shift key released");
//		}
//		if(e.isControlDown()) {
//			System.out.println("Ctrl key released");
//		}
//		if(e.isAltGraphDown()) {
//			System.out.println("AltGraph key released");
//		}
//		if(e.isMetaDown()) {
//			System.out.println("Meta key released");
//		}
//			if(keyChar == KEY_ESC || keyChar == 'q' || keyChar == 'Q') { //KeyEvent.VK_Qは大文字。小文字?
//			glWindow.destroy();
//		}
//	}

	private void printCharType(char keyChar, String type) {
		if(Character.isISOControl(keyChar)) {
			System.out.println(Integer.valueOf(keyChar) + type);
		} else {
			System.out.println(keyChar + type);
		}
	}
}
