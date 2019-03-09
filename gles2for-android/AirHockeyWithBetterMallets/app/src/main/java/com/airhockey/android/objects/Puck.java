package com.airhockey.android.objects;

import com.airhockey.android.data.VertexArray;
import com.airhockey.android.programs.ColorShaderProgram;
import com.airhockey.android.util.Geometry.Point;
import com.airhockey.android.util.Geometry.Cylinder;
import com.airhockey.android.objects.ObjectBuilder.DrawCommand;
import com.airhockey.android.objects.ObjectBuilder.GeneratedData;

import java.util.List;

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius, height;
    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        GeneratedData generatedData = ObjectBuilder.createPuck(new Cylinder(
                new Point(0f, 0f, 0f), radius, height), numPointsAroundPuck);
        this.radius = radius;
        this.height = height;
        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
    public void draw() {
        for (DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
