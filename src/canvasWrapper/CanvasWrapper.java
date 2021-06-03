package canvasWrapper;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CanvasWrapper {
    private final Canvas canvas;

    public CanvasWrapper(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawLine(int fromX, int fromY, int toX, int toY) {
        if (outOfBounds(fromX, fromY, toX, toY)) return;
        this.getContext2D().strokeLine(fromX, fromY, toX, toY);
    }

    public void drawRect(int startX, int startY, int width, int height) {
        if (outOfBounds(startX, startY, startX + width, startY + height)) return;

        this.getContext2D().fillRect(startX, startY, width, height);
    }

    public void setFillColor(Color col) {
        this.getContext2D().setFill(col);
    }

    public void setLineColor(Color col) {
        this.getContext2D().setStroke(col);
    }

    public void setLineWidth(double y) {
        if (y <= 0)
            return;
        this.getContext2D().setLineWidth(y);
    }

    public void setWidth(double y) {
        if (y <= 0)
            return;

        this.canvas.setWidth(y);
    }

    public void setHeight(double x) {
        if (x <= 0)
            return;

        this.canvas.setHeight(x);
    }

    public GraphicsContext getContext2D() {
        return this.canvas.getGraphicsContext2D();
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private boolean outOfBounds(int fromX, int fromY, int toX, int toY) {
        boolean ret = false;
        if (fromX < 0 || fromY < 0 || fromX > this.canvas.getWidth()
                || fromY > this.canvas.getHeight()) {
            System.out.println("Drawing object from invalid " +
                    "start coordinates that are not within bounds.");
            ret = true;
        } else if (toX < 0 || toY < 0 || toX > this.canvas.getWidth()
                || toY > this.canvas.getHeight()) {
            System.out.println("Drawing object to invalid end coordinates that " +
                    "are not within bounds.");
            ret = true;
        }

        return ret;
    }
}
