package demos.fingers;

import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;

public class BoneSample implements GLEventListener, MouseWheelListener, KeyListener {
	private final JFrame frame;
	private final GLCanvas canvas;////GLJPanelだとだめ
	protected GLCapabilities caps;

	// 光源
	// 光源の位置
//	private static FloatBuffer lightpos;
	// 直接光強度
//	private static FloatBuffer lightcol;
	// 影内の拡散反射強度
//	private static float lightdim[] = { 0.2f, 0.2f, 0.2f, 1.0f };
	// 影内の鏡面反射強度
//	private static float lightblk[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	// 環境光強度
//	private static FloatBuffer lightamb;// = { 0.1f, 0.1f, 0.1f, 1.0f };

	private float aspect;
//	private final int[] pointBuffer = new int[1];

	private static final int WIN_HEIGHT = 500;
	private static final int WIN_WIDTH = 500;

	// アニメーションのサイクル
//	private final static int FRAMES = 600;
//	private int bProgram;
//	private int pProgram;
	private boolean isInitDone = false;

	private float viewScale = 0.4f;
	private int prevMouseX = -1;
	//別のシェーダーなので同じ番号を使っても大丈夫。
//	private final int attribPosition = 0;
//	private final int attribBone = 0;
//	private final int attribIndices = 1;
//	private final int attribPoints = 2;

	// ボーン
	private static final int BONES_COUNT = 2;
	private static Bone bone[];
	private final Animator animator;
	private final GLU glu;
	private final float[] blue = {0f, 0f, 1f};
	private final float[] red  = {1f, 0f, 0f};

	public static void main(String[] args) {
		System.out.println("根元のパーツを操作するにはコントロールキーを押しながらドラッグしてください。");
		new BoneSample();
	}

	public BoneSample() {
		GLProfile prof = GLProfile.get(GLProfile.GL2);
		caps = new GLCapabilities(prof);
		glu = new GLU();

		setupBones();

		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);

		frame = new JFrame("Bone example");

		frame.setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
		frame.getContentPane().add(canvas);
		frame.addKeyListener(this);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				prevMouseX = -1;
			}
		});

		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				int x = evt.getX() ;
				float rotDelta = 0;
				if(prevMouseX != -1) {
					rotDelta = (x - prevMouseX);
					if(evt.isControlDown()) {
//						bone[0].setRotation(u0, u1, u2, u3);Angle(-rotDelta);
						bone[0].setRotationAngle(bone[0].getRotationAngle() - rotDelta);
//						System.out.println("0:" + boneAngle[0]);
					} else {
						bone[1].setRotationAngle(bone[1].getRotationAngle() - rotDelta);
//						System.out.println("1:" + boneAngle[1]);
					}
				}
				frame.repaint();

				// 現在のマウスの位置を保存
				prevMouseX = x;
			}

			@Override
			public void mouseMoved(MouseEvent e) {}
		});
		frame.addMouseWheelListener(this);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						quit();
					}
				}).start();
			}
		});

		frame.pack();
		frame.setVisible(true);
		animator = new Animator(canvas);
		animator.start();
	}

	private void setupBones() {
		bone = new Bone[BONES_COUNT];
		bone[0] = new Bone(null);
		bone[1] = new Bone(bone[0]);
		// 一つ目のボーンの設定
		bone[0].setPosition(0.0f, 0.0f, -1.5f);
		bone[0].setRotation(90.0f, 1f, 0f, 1f);
		bone[0].setColor(red );
		bone[0].setLength(1.5f);

		// 二つ目のボーンの設定
		//TODO 一つ目のボーンの先端と一致させるよう計算する。
		bone[1].setPosition(0.0f, 0.0f, 1.5f);
//		bone[1].computePosition();
		bone[1].setRotation(90.0f, 1f, 0f, 1f);
		bone[1].setColor(blue);
		bone[1].setLength(1.5f);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClearColor(1f, 1f, 1f, 1f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL.GL_DEPTH_TEST);
        gl2.glDepthFunc(GL.GL_LEQUAL);

        isInitDone = true;
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl2 = drawable.getGL().getGL2();
		aspect = (float) width / height;
		gl2.glLoadIdentity();
		glu.gluPerspective(80.0, aspect, 0.1f, 300.0);
		glu.gluLookAt(3.0f, 4.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

		gl2.glMatrixMode(GL_MODELVIEW);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		if(!isInitDone) return;

		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl2.glEnable(GL2.GL_DEPTH_TEST);

		gl2.glLoadIdentity();

		// ボーンのアニメーション
		for (int i = 0; i < BONES_COUNT; i++) {
			bone[0].compute();
		}
		for (int i = 0; i < BONES_COUNT; i++) {
			bone[i].render(gl2, viewScale);
		}
//		for (int i = 0; i < BONES_COUNT; i++) {
//			gl2.glTranslatef(bone[i].getX(), bone[i].getY(), bone[i].getZ());
//			gl2.glRotatef(bone[i].getRotationAngle(), bone[i].getAxisX(), bone[i].getAxisY(), bone[i].getAxisZ());
//			gl2.glScalef(viewScale, viewScale, viewScale);
//			bone[i].render(gl2, viewScale);
//		}

	}

	private void quit() {
		animator.stop();
		System.exit(0);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		switch (key.getKeyChar()) {
		case KeyEvent.VK_ESCAPE:
			quit();
			break;

		case 'q':
			quit();
			break;

		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent evt) {
		int step = evt.getWheelRotation();
		if(step < 0) { //前方向への回転 = zoom in
			viewScale *= 1.05;
		} else if(0 < step) {
			viewScale *= 0.95;
		}
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

}
