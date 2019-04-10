package apiratehat.androidsamplecode.audioAndVideo.middle.OpenGl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import apiratehat.androidsamplecode.R;

public class OpenGLESActivity extends AppCompatActivity {

    private MyGLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gles);

        mGLSurfaceView = new MyGLSurfaceView(this);

        setContentView(mGLSurfaceView);
    }


    class MyGLSurfaceView extends GLSurfaceView {

        private MyGLRenderer mRenderer;
        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;

        private float mPreviousX;
        private float mPreviousY;


        public MyGLSurfaceView(Context context) {
            super(context);

            setEGLContextClientVersion(2);

            mRenderer = new MyGLRenderer();


            setRenderer(mRenderer);

            //自动旋转的时候取消这个 模式

            setRenderMode(RENDERMODE_WHEN_DIRTY);


        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
//            return super.onTouchEvent(event);
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;
                    if (y > getHeight() / 2) {
                        dx = dx * -1;
                    }

                    if (x < getWidth() / 2) {
                        dy = dy * -1;
                    }
                    mRenderer.setAngle(mRenderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));
                    //请求重新 渲染
                    requestRender();

            }

            mPreviousX = x;
            mPreviousY = y;

            return true;
        }
    }

    static class MyGLRenderer implements GLSurfaceView.Renderer {
        private Triangle mTriangle;

        //相机
        private final float[] mMVPMatrix = new float[16];
        private final float[] mProjectionMatrix = new float[16];
        private final float[] mViewMatrix = new float[16];

        //旋转
        private float[] mRotationMatrix = new float[16];


        //触摸旋转的角度
        public volatile float mAngle;


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            mTriangle = new Triangle();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

            GLES20.glViewport(0, 0, width, height);

            float ratio = (float) width / height;
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

        }

        @Override
        public void onDrawFrame(GL10 gl) {

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

//            mTriangle.draw();
            Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            // Calculate the projection and view transformation
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

            // Draw shape
//            mTriangle.draw(mMVPMatrix);


            float[] scratch = new float[16];
//            long time = SystemClock.uptimeMillis() % 4000L;
//            float angle = 0.090f * ((int) time);
            Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);

            // Combine the rotation matrix with the projection and camera view
            // Note that the mMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

            // Draw triangle
            mTriangle.draw(scratch);
        }

        public static int loadShader(int type, String shaderCode) {
            int shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            return shader;
        }

        public float getAngle() {
            return mAngle;
        }

        public void setAngle(float angle) {
            mAngle = angle;
        }
    }

    static class Triangle {
        private FloatBuffer vertexBuffer;
        static final int COORDS_PER_VERTEX = 3;
        static final float[] triangleCoords = new float[]{
                0.0f, 0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        };

        float[] color = new float[]{0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
        private int mMVPMatrixHandle;

        private final int mProgram;
        private int mPositionHandle;
        private int mColorHandle;
        private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
        //
//        private final String vertexShaderCode = "attribute vec4 vPosition;" +
//                "void main() {" +
//                "gl_Position = vPosition;" +
//                "}";
        private final String vertexShaderCode =
                // This matrix member variable provides a hook to manipulate
                // the coordinates of the objects that use this vertex shader
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "void main() {" +
                        // the matrix must be included as a modifier of gl_Position
                        // Note that the uMVPMatrix factor *must be first* in order
                        // for the matrix multiplication product to be correct.
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}";
        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";

        public Triangle() {

            ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);

            bb.order(ByteOrder.nativeOrder());

            vertexBuffer = bb.asFloatBuffer();

            vertexBuffer.put(triangleCoords);

            vertexBuffer.position(0);

            int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
            int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

            mProgram = GLES20.glCreateProgram();

            GLES20.glAttachShader(mProgram, vertexShader);

            GLES20.glAttachShader(mProgram, fragmentShader);

            GLES20.glLinkProgram(mProgram);
        }

        public void draw(float[] mvpMatrix) {
            // Add program to OpenGL ES environment
            GLES20.glUseProgram(mProgram);

            // get handle to vertex shader's vPosition member
            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(mPositionHandle);

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    vertexStride, vertexBuffer);

            // get handle to fragment shader's vColor member
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

            // Set color for drawing the triangle
            GLES20.glUniform4fv(mColorHandle, 1, color, 0);

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(mPositionHandle);


            //相机
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(mPositionHandle);
        }
    }


    static class Square {
        private FloatBuffer vertexBuffer;
        private ShortBuffer drawListBuffer;

        static final int COORDS_PER_VERTEX = 3;

        static float squareCoords[] = {
                -0.5f, 0.5f, 0.0f,   // top left
                -0.5f, -0.5f, 0.0f,   // bottom left
                0.5f, -0.5f, 0.0f,   // bottom right
                0.5f, 0.5f, 0.0f};

        private short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices

        public Square() {
            ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);

            bb.order(ByteOrder.nativeOrder());

            vertexBuffer = bb.asFloatBuffer();

            vertexBuffer.put(squareCoords);

            vertexBuffer.position(0);

            ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
            dlb.order(ByteOrder.nativeOrder());
            drawListBuffer = dlb.asShortBuffer();
            drawListBuffer.put(drawOrder);
            drawListBuffer.position(0);
        }
    }


}
