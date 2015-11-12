package demos.basic;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_FRONT;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL2GL3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT_AND_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class CubeSample6InvalidNormal implements GLEventListener, MouseListener {

	public static void main(String[] args){
		new CubeSample6InvalidNormal();
	}

	private final float[][] vertex = {
		{ 0.0f, 0.0f, 0.0f}, /* A */
		{ 1.0f, 0.0f, 0.0f}, /* B */
		{ 1.0f, 1.0f, 0.0f}, /* C */
		{ 0.0f, 1.0f, 0.0f}, /* D */
		{ 0.0f, 0.0f, 1.0f}, /* E */
		{ 1.0f, 0.0f, 1.0f}, /* F */
		{ 1.0f, 1.0f, 1.0f}, /* G */
		{ 0.0f, 1.0f, 1.0f} /* H */
	};

	private final int face[][] = {
			{ 0, 1, 2, 3 }, // A-B-C-D を結ぶ面
			{ 1, 5, 6, 2 }, // B-F-G-C を結ぶ面
			{ 5, 4, 7, 6 }, // F-E-H-G を結ぶ面
			{ 4, 0, 3, 7 }, // E-A-D-H を結ぶ面
			{ 4, 5, 1, 0 }, // E-F-B-A を結ぶ面
			{ 3, 2, 6, 7 }  // D-C-G-H を結ぶ面
		};

	private final float color[][] = {
			{ 1.0f, 0.0f, 0.0f }, // 赤
			{ 0.0f, 1.0f, 0.0f }, // 緑
			{ 0.0f, 0.0f, 1.0f }, // 青
			{ 1.0f, 1.0f, 0.0f }, // 黄
			{ 1.0f, 0.0f, 1.0f }, // マゼンタ
			{ 0.0f, 1.0f, 1.0f } // シアン
	};

	private final float normal[][] = {
			{ 0.0f,  0.0f, -1.0f},
			{ 1.0f,  0.0f,  0.0f},
			{ 0.0f,  0.0f,  1.0f},
			{-1.0f,  0.0f,  0.0f},
			{ 0.0f, -1.0f,  0.0f},
			{ 0.0f,  1.0f,  0.0f}
	};

	private final float[] light0pos = {0.0f, 3.0f, 5.0f, 1.0f};
	private final float[] light1pos = {5.0f, 3.0f, 0.0f, 1.0f};
	private final float[] green = {0.0f, 1.0f, 0.0f, 1.0f};
	private final float[] red = {0.8f, 0.2f, 0.2f, 1.0f};//追加

	private final GLU glu;

	private final FPSAnimator animator;

	//回転角
	float r = 0;

	private boolean willAnimatorPause = false;


	public CubeSample6InvalidNormal() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		glu = new GLU();
		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Cube demo (Newt)");
		glWindow.setSize(300, 300);
		glWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent arg0) {
				System.exit(0);
			}
		});
		glWindow.addGLEventListener(this);
		glWindow.addMouseListener(this);
		animator = new FPSAnimator(30);
		animator.add(glWindow);
		animator.start();
		animator.pause();
		glWindow.setVisible(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
//		java.nio.IntBuffer maxlights = Buffers.newDirectIntBuffer(1);
		int[] maxlights = new int[1];
		gl.glGetIntegerv(GL2.GL_MAX_LIGHTS, maxlights, 0);
		System.out.println(maxlights[0]);
		//背景を白く塗りつぶす.
		gl.glClearColor(1f, 1f, 1f, 1.0f);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glEnable(GL_CULL_FACE);
		gl.glCullFace(GL_FRONT);
		gl.glEnable(GL_LIGHTING);
		gl.glEnable(GL_LIGHT0);
		gl.glEnable(GL_LIGHT1);
//		gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, green, 0);
//		gl.glLightfv(GL_LIGHT1, GL_SPECULAR, green, 0);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(30.0, (double)width / (double)height, 1.0, 300.0);

		//視点位置と視線方向
		//glu.gluLookAt(3.0f, 4.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);//コメントアウト

		gl.glMatrixMode(GL_MODELVIEW);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //GL_DEPTH_BUFFER_BITを追加
		gl.glLoadIdentity();

		//視点位置と視線方向
		glu.gluLookAt(3.0f, 4.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

		// 光源の位置設定
//		gl.glLightfv(GL_LIGHT0, GL_POSITION, light0pos, 0);
//		gl.glLightfv(GL_LIGHT1, GL_POSITION, light1pos, 0);

		// 図形の回転
		gl.glTranslatef(0.5f, 0.5f, 0.5f);
		gl.glRotatef(r, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(-0.5f, -0.5f, -0.5f);
		// 図形の色 (赤)
//		gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, red, 0);//追加
//		gl.glColor3f(0.0f, 0.0f, 0.0f);

		// 図形の描画
		gl.glBegin(GL_QUADS);
		for (int j = 0; j < 6; ++j) {
//			gl.glColor3fv(color[j], 0); //追加
//			gl.glNormal3fv(normal[j], 0);
			for (int i = 0; i < 4; ++i) {
				gl.glNormal3fv(vertex[face[j][i]], 0);
				gl.glVertex3fv(vertex[face[j][i]], 0);
			}
		}
		gl.glEnd();

		//一周回ったら回転角を 0 に戻す
		if (r++ >= 360.0f) r = 0;
		System.out.println("anim:" + animator.isAnimating() + ", r:" + r);
		if(willAnimatorPause) {
			animator.pause();
			System.out.println("animoator paused:");
			willAnimatorPause = false;
		}
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			animator.resume();
			System.out.println("button 1, left click");
			break;
		case MouseEvent.BUTTON2:
			System.out.println("button 2");
			break;
		case MouseEvent.BUTTON3:
			System.out.println("button 3, right click");
			willAnimatorPause  = true;
			animator.resume();
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		animator.pause();
	}

	@Override
	public void mouseMoved(MouseEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseWheelMoved(MouseEvent e) {}

}
