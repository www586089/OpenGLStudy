package com.airhockey.android.objects;

import com.airhockey.android.data.VertexArray;
import com.airhockey.android.programs.TextureShaderProgram;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static com.airhockey.android.Constants.BYTES_PER_FLOAT;

public class Table {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private float theta = -0;
    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, S, T
            // Triangle Fan
               0f,    0f, 0.5f, 0.5f,
            -0.5f, -0.8f,   0f, 1f,
             0.5f, -0.8f,   1f, 1f,
             0.5f,  0.8f,   1f, 0f,
            -0.5f,  0.8f,   0f, 0f,
            -0.5f, -0.8f,   0f, 1f };

    private final VertexArray vertexArray;
    public Table() {
        setRotation(theta);
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    private void setRotation(float theta) {
        VERTEX_DATA[2] = 0.5F;
        VERTEX_DATA[3] = 0.5F;

        VERTEX_DATA[6] = -0.5f;
        VERTEX_DATA[7] =  0.5F;

        VERTEX_DATA[10] = 0.5F;
        VERTEX_DATA[11] = 0.5f;

        VERTEX_DATA[14] =  0.5F;
        VERTEX_DATA[15] = -0.5F;

        VERTEX_DATA[18] = -0.5f;
        VERTEX_DATA[19] = -0.5F;

        VERTEX_DATA[22] = -0.5F;
        VERTEX_DATA[23] =  0.5f;

        float piTheta = (float) Math.toRadians(theta);

        float itemPITheta = (float) Math.atan2(VERTEX_DATA[7], VERTEX_DATA[6]);
        float delta = itemPITheta - piTheta;
        float cos = (float) Math.cos(delta);
        float sin = (float) Math.sin(delta);
        float radius = VERTEX_DATA[6] / (float) Math.cos(itemPITheta);
        VERTEX_DATA[6] = radius * cos + 0.5f;
        VERTEX_DATA[7] = radius * sin + 0.5f;

        computePointRotation(10, 11, piTheta, radius);
        computePointRotation(14, 15, piTheta, radius);
        computePointRotation(18, 19, piTheta, radius);
        computePointRotation(22, 23, piTheta, radius);
    }


    private void computePointRotation(int indexX, int indexY, float theta, float radius) {
        float itemPITheta = (float) Math.atan2(VERTEX_DATA[indexY], VERTEX_DATA[indexX]);
        float delta = itemPITheta - theta;
        float cos = (float) Math.cos(delta);
        float sin = (float) Math.sin(delta);
        VERTEX_DATA[indexX] = radius * cos + 0.5f;
        VERTEX_DATA[indexY] = radius * sin + 0.5f;
    }

    public void bindData(TextureShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
