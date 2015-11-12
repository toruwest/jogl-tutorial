package demos.fingers;

import com.jogamp.opengl.GL2;

import lombok.Getter;
import lombok.Setter;

public class Bone {
	@Getter
	private float x;
	@Getter
	private float y;
	@Getter
	private float z;

	@Getter
	private float axisX;
	@Getter
	private float axisY;
	@Getter
	private float axisZ;

	@Getter
	@Setter
	private float rotationAngle;

	@Setter
	@Getter
	private float length;

//	@Getter
	private final Bone parent;

	private float[] color;

	public Bone(Bone parent) {
		this.parent = parent;
//		setupData();
	}

//	public void setParent(Bone parentBone) {
//		this.parent = parentBone;
//	}

//	public float[] getPosition() {
//		return new float[]{this.x, this.y, this.z};
//	}

	public void setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	//glRotatef()のパラメーターと同じ意味にする
	public void setRotation(float angle, float u0, float u1, float u2) {
		this.rotationAngle = angle;
		this.axisX = u0;
		this.axisY = u1;
		this.axisZ = u2;
	}

	public void setRotationAxis(float u0, float u1, float u2) {
		this.axisX = u0;
		this.axisY = u1;
		this.axisZ = u2;
	}

	public void computePosition() {
		if(parent != null) {
			float[] parentTopPosition = parent.getTopPosition();
//			this.x = parentTopPosition[0];
//			this.y = parentTopPosition[1];
//			this.z = parentTopPosition[2];
		}
	}

	float[] getTopPosition() {
		//FIXME
		return null;
	}

	public void compute() {
//		FloatUtil.multMatrix(a, b, result);
		//TODO 自分自身の先端の位置を計算
	}

	// ボーンを描画する
	protected void render(GL2 gl2, float viewScale) {
		// ボーンの初期位置における根元と先端の位置を求める

		gl2.glTranslatef(x, y, z);
		gl2.glRotatef(rotationAngle, axisX, axisY, axisZ);
		gl2.glScalef(viewScale, viewScale, viewScale);

		gl2.glColor3f(color[0], color[1], color[2]);
		gl2.glBegin(GL2.GL_LINE_LOOP);

		for (int j = 0; j < boneEdge.length; j++) {
			gl2.glVertex3fv(boneVertex[boneEdge[j]], 0);
		}
		gl2.glEnd();
	}

//	private FloatBuffer boneVertexBuf;
//	private IntBuffer indicesBuffer;
//
//	private void setupData(){
//
//		boneVertexBuf = Buffers.newDirectFloatBuffer(boneVertex.length * boneVertex[0].length);
//		for(int i = 0; i < boneVertex.length; i++) {
//			for(int j = 0; j < boneVertex[i].length; j++) {
//				boneVertexBuf.put(boneVertex[i][j]);
//			}
//		}
//		boneVertexBuf.rewind();
//		indicesBuffer = Buffers.newDirectIntBuffer(boneEdge);
//
//	}

	/* ボーンの図形データ */
	static float boneVertex[][] = {
		{  0.0f,  0.0f,  0.0f },
		{  0.1f,  0.0f,  0.1f },
		{  0.0f,  0.1f,  0.1f },
		{ -0.1f,  0.0f,  0.1f },
		{  0.0f, -0.1f,  0.1f },
		{  0.0f,  0.0f,  1.0f },
//		{  0.0f,  0.0f,  0.0f,  1.0f },
//		{  0.1f,  0.0f,  0.1f,  1.0f },
//		{  0.0f,  0.1f,  0.1f,  1.0f },
//		{ -0.1f,  0.0f,  0.1f,  1.0f },
//		{  0.0f, -0.1f,  0.1f,  1.0f },
//		{  0.0f,  0.0f,  1.0f,  1.0f },
	};

	static int boneEdge[] = {
		0, 1, 5, 3, 0, 2, 5, 4, 1, 2, 3, 4,
	};

	public void setColor(float[] color) {
		this.color = color;
	}

}
