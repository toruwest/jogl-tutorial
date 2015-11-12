package demos.texture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureSample implements GLEventListener {
	private static final String IMAGE_FILE = "nehe.png";
	private final Animator animator;
	private Texture texture;

	public static void main(String[] args) {
		new TextureSample();
	}

	public TextureSample() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		GLWindow glWindow = GLWindow.create(caps);

		glWindow.setTitle("Texture sample (Newt)");
		glWindow.setSize(500, 500);
		glWindow.addGLEventListener(this);

		glWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDestroyed(WindowEvent evt) {
				quit();
			}
		});

		glWindow.addKeyListener(new KeyAdapter() {
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
		});

		glWindow.setVisible(true);
		animator = new Animator(glWindow);
		animator.start();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl2 = drawable.getGL().getGL2();
		drawable.setGL(new DebugGL2(gl2));
		gl2.glClearColor(1, 1, 1, 1);

		try {
//			InputStream resourceStream = this.getClass().getResourceAsStream(IMAGE_FILE); //(3)
			File file = new File("src/demos/texture", IMAGE_FILE);
			InputStream resourceStream = new FileInputStream(file);//.this.getClass().getResourceAsStream(IMAGE_FILE); //(3)

			texture = TextureIO.newTexture(resourceStream, false, TextureIO.PNG);  //(4)
//			texture.setMustFlipVertically(true);
//			texture = TextureIO.newTexture(texture.getTextureObject(gl2), 0, 1, 1, texture.getImageWidth(), texture.getImageHeight(), true);

//			TextureData data = TextureIO.newTextureData(gl2.getGLProfile(), resourceStream, false, "png");  //(4)
//			data.setMustFlipVertically(true);
////			texture = TextureIO.newTexture(data);
//			texture = new Texture(gl2, data);
		} catch (GLException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { }

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl2 = drawable.getGL().getGL2();
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl2.glLoadIdentity();
		gl2.glScalef(0.9f, 0.9f, 0.9f);

		texture.enable(gl2);
//		texture.bind(gl2);

		gl2.glBegin(GL2.GL_QUADS);
		gl2.glTexCoord2f(0.0f, 0.0f);
//		gl2.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl2.glVertex2f(-1f, -1f);
		gl2.glTexCoord2f(1.0f, 0.0f);
//		gl2.glVertex3f(1.0f, -1.0f, 1.0f);
		gl2.glVertex2f(1.0f, -1f);
		gl2.glTexCoord2f(1.0f, 1.0f);
//		gl2.glVertex3f(1.0f, 1.0f, 1.0f);
		gl2.glVertex2f(1.0f, 1.0f);
		gl2.glTexCoord2f(0.0f, 1.0f);
//		gl2.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl2.glVertex2f(-1f, 1.0f);
		gl2.glEnd();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		if(texture != null) {
			final GL2 gl2 = drawable.getGL().getGL2();
			texture.destroy(gl2);
		}
	}

	private void quit() {
		animator.stop();
		System.exit(0);
	}
}
