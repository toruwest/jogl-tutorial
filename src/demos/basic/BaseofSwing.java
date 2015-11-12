package demos.basic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.util.FPSAnimator;
//import com.jogamp.newt.event.WindowAdapter;
//import com.jogamp.newt.event.WindowEvent;

//import demos.plain.GL3ShaderPlainDrawElementsWithTextureSwing;

//public abstract class AbstractDemoSwing

public class BaseofSwing extends JFrame implements GLEventListener {//, MouseListener, MouseWheelListener, MouseMotionListener {

//	private static final int JOGL_PANEL_HEIGHT = 500;
//	private static final int JOGL_PANEL_WIDTH = 500;
//	private static final int BUTTON_PANEL_WIDTH = 100;
//	private static final int WIN_HEIGHT = 500;

//    private Dimension dim;
    private final GLJPanel joglPanel;
//    private JPanel statusPanel;

//    private JLabel statusMessageLabel;

	protected static float scale;
	protected static float angleX = 0;
	protected static float angleY = 0;
	protected float moveX;
	protected float moveY;

	private Point prevMouse;
	protected float azim;
	protected float evel;
	private Point mouseClickedPoint;

//	private final boolean showAngle;
//	private final boolean showScale;

	private FPSAnimator animator;
	NumberFormat form = NumberFormat.getInstance();

	private boolean doAnimation;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
//				try {
					new BaseofSwing();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		});
	}

	public BaseofSwing() {
//		this.dim = dim;
//		this.showAngle = showAngle;
//		this.showScale = showScale;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                runExit();
            }
        });
//        addMouseListener(this);
//		addMouseWheelListener(this);
//		addMouseMotionListener(this);

		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL3));
        joglPanel = new GLJPanel(caps);
        joglPanel.setFocusable(true);
        joglPanel.setPreferredSize(new Dimension(500, 200));
        add(joglPanel, BorderLayout.CENTER);
        setLocation(500, 300);
        pack();
        setVisible(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		showGLInfo(drawable, drawable.getGL().getGL3());
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int z, int h) {}

	@Override
	public void display(GLAutoDrawable drawable) {}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

//	private void startAnimation() {
////		initComponents();
//		doAnimation = true;
//		animator = new FPSAnimator(joglPanel, 60, true);
//		animator.start();
//	}

//	public void setButtonPanel(JPanel buttonPanel) {
////		this.buttonPanel = buttonPanel;
//		initComponents();
////		initComplete();
//	}

//	public void setDimension(Dimension dim) {
//		this.dim = dim;
//		repaint();
//	}

	private void runExit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
            	if(doAnimation) {
            		animator.stop();
            	}
                System.exit(0);
            }
        }).start();
    }

//	public void setGLEventListener(GLEventListener listener) {
//      joglPanel.addGLEventListener(listener);
//	}

//	@Override
//	public void mouseDragged(MouseEvent e) {
//		Point p = e.getPoint();
//		if(prevMouse != null) {
//			moveX = p.x - prevMouse.x;
//			moveY = p.y - prevMouse.y;
//
//			angleX -= 1.0f * moveY;//なぜかxとyを逆にしないとまともに動かない。
//			if(angleX > 360)angleX = 0;
//			if(angleX < 0)  angleX = 0;
//
//			angleY += 1.0f * moveX;
//			if(angleY > 360)angleY = 0;
//			if(angleY < 0)  angleY = 0;
//
////			if(showAngle) {
////				System.out.println("angleX:" + angleX + ", angleY:" + angleY);
////			}
//			repaint();
//		}
//		// 現在のマウスの位置を保存
//		prevMouse = p;
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		prevMouse = e.getPoint();
//	}
//
//	@Override
//	public void mouseWheelMoved(MouseWheelEvent e) {
//		int step = e.getWheelRotation();
//		if(step < 0) {
//			scale *= 1.05;
//		} else if(0 < step) {
//			scale *= 0.95;
//		}
////		if(showScale) {
////	        System.out.println("scale:" + scale);
////		}
//		repaint();
//	}
//
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		mouseClickedPoint = e.getPoint();
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}


	private static void showGLInfo(GLAutoDrawable drawable, GL3 gl) {
		System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
		System.err.println("INIT GL IS: " + gl.getClass().getName());
		System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
		System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
		System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
		System.err.println("GLSL VERSION: " + gl.glGetString(GL3.GL_SHADING_LANGUAGE_VERSION));
	}

}
