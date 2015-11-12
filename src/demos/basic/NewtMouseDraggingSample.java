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

public class NewtMouseDraggingSample implements GLEventListener, com.jogamp.newt.event.MouseListener, KeyListener { //(1)

	public static void main(String[] args) {
		new NewtMouseDraggingSample();
	}

	private final List<List<Point2D>> pointsList;
	private final List<Point2D> points;

	public NewtMouseDraggingSample() {
		pointsList = new ArrayList<>();
		points = new ArrayList<>();

		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		final GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Mouse Drag Sample (Newt)");
		glWindow.setSize(300, 300);

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
		dumpThread("display");
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glColor3f(1.0f, 0.0f,0.0f); // 赤

		//現在ドラッグ中の線を描画
		render(gl, points);
		//前の線を描画
		for(List<Point2D> p : pointsList) {
			render(gl, p);
		}
	}

	private void render(GL2 gl, List<Point2D> p) {
		gl.glBegin(GL_LINES);
		//p1のところで+1しているので、iが範囲を超えないようループ回数を一つ減らしている。
		for(int i = 0; i < p.size() - 1; i++) {
			Point2D.Float p0 = (Float) p.get(i);
			Point2D.Float p1 = (Float) p.get(i + 1);
			gl.glVertex2d(p0.getX(), p0.getY()); // 今の位置
			gl.glVertex2d(p1.getX(), p1.getY()); // 次の位置
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
	}

	@Override
	public void mouseMoved(com.jogamp.newt.event.MouseEvent e) { }

	@Override
	public void mouseDragged(com.jogamp.newt.event.MouseEvent e) {
		dumpThread("dragging");
		points.add(new Point2D.Float(e.getX(), e.getY()));
	}

	@Override
	public void mouseWheelMoved(com.jogamp.newt.event.MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		e.isAltDown();
		int mods = e.getModifiers();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
