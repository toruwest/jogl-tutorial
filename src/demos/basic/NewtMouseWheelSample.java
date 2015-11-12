package demos.basic;

import static com.jogamp.opengl.GL.*;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class NewtMouseWheelSample implements GLEventListener, KeyListener { //(1)
	private static final char KEY_ESC = 0x1b;
	private static final float INIT_SCALE = 20f;
	private static final int HEIGHT = 300;
	private static final int WIDTH = 300;

	public static void main(String[] args) {
		new NewtMouseWheelSample();
	}

	private float scale = INIT_SCALE;

	public NewtMouseWheelSample() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		final GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Mouse wheel sample (Newt)");
		glWindow.setSize(WIDTH, HEIGHT);

		glWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent arg0) {
				System.exit(0);
			}
		});

		glWindow.addGLEventListener(this);
		glWindow.addMouseListener(new com.jogamp.newt.event.MouseAdapter() {
			@Override
			public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent e) {
				float[] rot = e.getRotation();
				scale *= (rot[1] > 0 ? 1.005f : 0.995f);
				System.out.println("scale:" + scale);
			}
		});

		glWindow.addKeyListener(this);

		FPSAnimator animator = new FPSAnimator(10);
		animator.add(glWindow);
		animator.start();
		glWindow.setPosition(500, 500);
		glWindow.setVisible(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		//showGLInfo(drawable);
		GL gl = drawable.getGL();
		//背景を白く塗りつぶす。
		gl.glClearColor(1f, 1f, 1f, 1.0f);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
//		gl.glMatrixMode(GL_PROJECTION);//透視変換行列を指定
//		gl.glLoadIdentity();//透視変換行列を単位行列にする
		System.out.printf("x:%d, y:%d, w:%d, h:%d, %n", x, y, width, height);
		//これによりウィンドウをリサイズしても中の図形は大きさが維持される。
		//また、第3、第4引数を入れ替えることによりGLWindowの座標系(左上隅が原点)とデバイス座標系(左下隅が原点)の違いを吸収している。
		gl.glOrthof(x, x + width, y, y + height, -1.0f, 1.0f);

//		gl.glMatrixMode(GL_MODELVIEW);//モデルビュー変換行列を指定
//		gl.glLoadIdentity();//モデルビュー変換行列を単位行列にする
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glColor3f(1.0f, 0.0f,0.0f); // 赤
		gl.glBegin(GL_LINE_LOOP);
		gl.glVertex2f(WIDTH/2 - scale, HEIGHT/2 - scale);
		gl.glVertex2f(WIDTH/2 + scale, HEIGHT/2 - scale);
		gl.glVertex2f(WIDTH/2 + scale, HEIGHT/2 + scale);
		gl.glVertex2f(WIDTH/2 - scale, HEIGHT/2 + scale);
		gl.glEnd();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}


	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char keyChar = e.getKeyChar();

		if( keyChar == ' ' ) {
			scale = INIT_SCALE;
			System.out.println("scale:" + scale);
		}
		if(keyChar == 'q' || keyChar == KEY_ESC) System.exit(0);
	}
}
