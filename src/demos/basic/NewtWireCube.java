package demos.basic;

import static com.jogamp.opengl.GL.GL_LINES;

import com.jogamp.opengl.GL;
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

public class NewtWireCube implements GLEventListener {
	public static void main(String[] args){
		new NewtWireCube();
	}

	float[][] vertex = {
		{ 0.0f, 0.0f, 0.0f}, /* A */
		{ 1.0f, 0.0f, 0.0f}, /* B */
		{ 1.0f, 1.0f, 0.0f}, /* C */
		{ 0.0f, 1.0f, 0.0f}, /* D */
		{ 0.0f, 0.0f, 1.0f}, /* E */
		{ 1.0f, 0.0f, 1.0f}, /* F */
		{ 1.0f, 1.0f, 1.0f}, /* G */
		{ 0.0f, 1.0f, 1.0f} /* H */
	};

	int[][] edge = {
		{ 0, 1}, /* ア (A-B) */
		{ 1, 2}, /* イ (B-C) */
		{ 2, 3}, /* ウ (C-D) */
		{ 3, 0}, /* エ (D-A) */
		{ 4, 5}, /* オ (E-) */
		{ 5, 6}, /* カ (-G) */
		{ 6, 7}, /* キ (G-H) */
		{ 7, 4}, /* ク (H-E) */
		{ 0, 4}, /* ケ (A-E) */
		{ 1, 5}, /* コ (B-) */
		{ 2, 6}, /* サ (C-G) */
		{ 3, 7} /* シ (D-H) */
	};

	private final GLU glu;

	public NewtWireCube() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		GLWindow glWindow = GLWindow.create(caps);
		glu = new GLU();
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
		//背景を白く塗りつぶす.
		gl.glClearColor(1f, 1f, 1f, 1.0f);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
//		gl.glViewport(0f, 0f, width, height); //(3) Jogl内部で実行済みなので,不要.(APIDOCに書いてある)
//		gl.glOrtho(-2.0, 2.0, -2.0, 2.0, -2.0, 2.0);
		glu.gluPerspective(30.0, (double)width / (double)height, 1.0, 300.0);
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		glu.gluLookAt(3.0f, 4.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
//		gl.glMatrixMode(GL_PROJECTION);
//		gl.glLoadIdentity();
//		System.out.printf("x:%d, y:%d, w:%d, h:%d, %n", x, y, width, height);
		//これによりウィンドウをリサイズしても中の図形は大きさが維持される.
		//また,第3,第4引数を入れ替えることによりGLWindowの座標系(左上隅が原点)とデバイス座標系(左下隅が原点)の違いを吸収している.
//		gl.glOrthof(x, x + width, y + height, y, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x/300f, (x + width)/300f, (y + height)/300f, y/300f, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x/400f, (x + width)/400f, (y + height)/400f, y/400f, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x/400f, (x + width)/400f, (y + height)/400f, y/400f, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x/300f, (x/300) + width, (y/300) + height, y/300f, -1.0f, 1.0f); //(4)
//		gl.glOrthof(x , x + width/300f, y + height/300f, y, -1.0f, 1.0f); //(4)

//		gl.glMatrixMode(GL_MODELVIEW);
//		gl.glLoadIdentity();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 // 図形の描画
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glBegin(GL_LINES);
		for (int i = 0; i < 12; i++) {
			gl.glVertex3fv(vertex[edge[i][0]], 0);
			gl.glVertex3fv(vertex[edge[i][1]], 0);
		}
		gl.glEnd();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

}
