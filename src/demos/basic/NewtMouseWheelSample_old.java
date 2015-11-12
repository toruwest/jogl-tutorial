package demos.basic;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.List;

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

public class NewtMouseWheelSample_old implements GLEventListener, com.jogamp.newt.event.MouseListener, KeyListener { //(1)

	private static final int HEIGHT = 300;
	private static final int WIDTH = 300;

	public static void main(String[] args) {
		new NewtMouseWheelSample();
	}

	private final List<List<Point2D>> pointsList;
	private final List<Point2D> points;
	private float scale = 1f;

	public NewtMouseWheelSample_old() {
		pointsList = new ArrayList<>();
		points = new ArrayList<>();

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
		glWindow.addMouseListener(this);
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
		gl.glMatrixMode(GL_PROJECTION);//透視変換行列を指定
		gl.glLoadIdentity();//透視変換行列を単位行列にする
		System.out.printf("x:%d, y:%d, w:%d, h:%d, %n", x, y, width, height);
		//これによりウィンドウをリサイズしても中の図形は大きさが維持される。
		//また、第3、第4引数を入れ替えることによりGLWindowの座標系(左上隅が原点)とデバイス座標系(左下隅が原点)の違いを吸収している。
		gl.glOrthof(x, x + width, y + height, y, -1.0f, 1.0f);

		gl.glMatrixMode(GL_MODELVIEW);//モデルビュー変換行列を指定
		gl.glLoadIdentity();//モデルビュー変換行列を単位行列にする
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glColor3f(1.0f, 0.0f,0.0f); // 赤
//		gl.glScalef(scale, scale, 1f);
		//現在ドラッグ中の線を描画
		render(gl, points);
		//前の線を描画
		for(List<Point2D> p : pointsList) {
			render(gl, p);
		}
	}
	//TODO スケールを変えると図形が原点を中心に
	private void render(GL2 gl, List<Point2D> p) {
		gl.glBegin(GL_LINES);
		//p1のところで+1しているので、iが範囲を超えないようループ回数を一つ減らしている。
		for(int i = 0; i < p.size() - 1; i++) {
			Point2D.Float p0 = (Float) p.get(i);
			Point2D.Float p1 = (Float) p.get(i + 1);
//			gl.glVertex2d(p0.getX(), p0.getY()); // 今の位置
//			gl.glVertex2d(p1.getX(), p1.getY()); // 次の位置
//			gl.glVertex2d(p0.getX() - scale * WIDTH/2, p0.getY() - scale * HEIGHT/2); // 今の位置
//			gl.glVertex2d(p1.getX() - scale * WIDTH/2, p1.getY() - scale * HEIGHT/2); // 次の位置
//			gl.glVertex2d(scale * p0.getX() - scale * WIDTH/2, scale * p0.getY() - scale * HEIGHT/2); // 今の位置
//			gl.glVertex2d(scale * p1.getX() - scale * WIDTH/2, scale * p1.getY() - scale * HEIGHT/2); // 次の位置
//			gl.glVertex2d(scale * p0.getX(), scale * p0.getY() ); // 今の位置
//			gl.glVertex2d(scale * p1.getX(), scale * p1.getY() ); // 次の位置
//			gl.glVertex2d(p0.getX() + scale * WIDTH/2, p0.getY() + scale * HEIGHT/2); // 今の位置
//			gl.glVertex2d(p1.getX() + scale * WIDTH/2, p1.getY() + scale * HEIGHT/2); // 次の位置
//			gl.glVertex2d(p0.getX() + scale * WIDTH/4, p0.getY() + scale * HEIGHT/4); // 今の位置
//			gl.glVertex2d(p1.getX() + scale * WIDTH/4, p1.getY() + scale * HEIGHT/4); // 次の位置
//			gl.glVertex2d(scale * (p0.getX() - WIDTH/2), scale * (p0.getY() - HEIGHT/2)); // 今の位置
//			gl.glVertex2d(scale * (p1.getX() - WIDTH/2), scale * (p1.getY() - HEIGHT/2)); // 次の位置
			gl.glVertex2d(scale * p0.getX() - WIDTH/2, scale * p0.getY() - HEIGHT/2); // 今の位置
			gl.glVertex2d(scale * p1.getX() - WIDTH/2, scale * p1.getY() - HEIGHT/2); // 次の位置
		}
		gl.glEnd();
	}

	private void dumpThread(String name) {
		Thread th = Thread.currentThread();
		System.out.println(name + ":" + th.getName() + ", " + th.getState());
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

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

	@Override
	public void mouseClicked(com.jogamp.newt.event.MouseEvent e) {}

	@Override
	public void mouseEntered(com.jogamp.newt.event.MouseEvent e) {	}

	@Override
	public void mouseExited(com.jogamp.newt.event.MouseEvent e) {}

	@Override
	public void mousePressed(com.jogamp.newt.event.MouseEvent e) {
	}

	@Override
	public void mouseReleased(com.jogamp.newt.event.MouseEvent e) {
		pointsList.add(new ArrayList<Point2D>(points));
		points.clear();
		scale = 1f;
	}

	@Override
	public void mouseMoved(com.jogamp.newt.event.MouseEvent e) { }

	@Override
	public void mouseDragged(com.jogamp.newt.event.MouseEvent e) {
//		points.add(new Point2D.Float(e.getX() -  scale * WIDTH/2, e.getY() - scale * HEIGHT/2));
		points.add(new Point2D.Float(e.getX(), e.getY()));
	}

	@Override
	public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent e) {
//		float[] rot =
//		System.out.print("rot:");
//		for(float r : e.getRotation()) {
//			System.out.print(r + ", ");
//		}
//		System.out.println();
		float[] rot = e.getRotation();
		scale *= (rot[1] > 0 ? 1.005f : 0.995f);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char keyChar = e.getKeyChar();

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

		if( keyChar == ' ' ) {
			scale = 1f;
			System.out.println("scale:" + scale);
		}
	}
}
