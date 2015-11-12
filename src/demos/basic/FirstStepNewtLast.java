package demos.basic;

import static com.jogamp.opengl.GL2.GL_POLYGON;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class FirstStepNewtLast implements GLEventListener {
	public static void main(String[] args){
		new FirstStepNewtLast();
	}

	public FirstStepNewtLast() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("First demo (Newt)");
		glWindow.setSize(300, 300);
		glWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent arg0) {
				System.exit(0);
			}
		});
		glWindow.addGLEventListener(this);
		FPSAnimator animator = new FPSAnimator(10); //(2)
		animator.add(glWindow);
		animator.start();
		glWindow.setVisible(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		//背景を白く塗りつぶす。
		gl.glClearColor(1f, 1f, 1f, 1.0f);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
//		gl.glViewport(0, 0, width, height); //(3) Jogl内部で実行済みなので、不要。(APIDOCに書いてある)
//		gl.glMatrixMode(GL_PROJECTION);
//		gl.glLoadIdentity();
//		System.out.printf("x:%d, y:%d, w:%d, h:%d, %n", x, y, width, height);
		//これによりウィンドウをリサイズしても中の図形は大きさが維持される。
		//また、第3、第4引数を入れ替えることによりGLWindowの座標系(左上隅が原点)とデバイス座標系(左下隅が原点)の違いを吸収している。
//		gl.glOrthof(x, x + width, y + height, y, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x/300, (x + width)/300, (y + height)/300, y/300, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x/400, (x + width)/400, (y + height)/400, y/400, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x/400, (x + width)/400, (y + height)/400, y/400, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x/300, (x/300) + width, (y/300) + height, y/300, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x , x + width/300, y + height/300, y, -1.0f, 1.0f); //(4)

//		gl.glMatrixMode(GL_MODELVIEW);
//		gl.glLoadIdentity();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
 		//gl.glColor3f(1.0f, 0.0f, 0.0f); //ここは削除
		gl.glRotatef(25f,  0f, 1f, 0f);
		gl.glBegin(GL_POLYGON);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // 赤
		gl.glVertex2f(-0.9f,-0.9f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // 緑
		gl.glVertex2f(0.9f, -0.9f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // 青
		gl.glVertex2f(0.9f, 0.9f);
		gl.glColor3f(1.0f, 1.0f, 0.0f); // 黄
		gl.glVertex2f(-0.9f, 0.9f);
		gl.glEnd();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

}
