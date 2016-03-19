/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

/**
 *
 * @author himon
 */
public class DrawArea extends JComponent {

    private Image image;
    private Graphics2D g;
    private int currentX, currentY, oldX, oldY;
    private int mode;
    private float brushSize;

    public DrawArea() {
        this.brushSize = 1f;
        mode = 5;        
        setDoubleBuffered(false);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                if (g != null && mode >= 1 && mode <= 4) {
                    draw();
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                if (g != null && mode == 5) {
                    drawBrush();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(brushSize,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
            clear();
        }

        graphics.drawImage(image, 0, 0, null);
    }

    public void clear() {
        Color color = g.getColor();
        g.setPaint(Color.white);
        g.fillRect(0, 0, getSize().width, getSize().width);
        repaint();
        g.setPaint(color);
    }

    public void red() {
        g.setPaint(Color.red);
    }

    public void draw() {
        switch (mode) {
            case 1:
                drawLine();
                break;
            case 2:
                drawRectangle();
                break;
            case 3:
                drawCircle();
                break;
            case 4:
                drawTriangle();
                break;
        }
    }

    public void drawRectangle() {
        int rectX, rectY, rectHeight, rectWidth;

        if (currentX > oldX) {
            rectX = oldX;
            rectWidth = currentX - oldX;
        } else {
            rectX = currentX;
            rectWidth = oldX - currentX;
        }

        if (currentY > oldY) {
            rectY = oldY;
            rectHeight = currentY - oldY;
        } else {
            rectY = currentY;
            rectHeight = oldY - currentY;
        }

        g.drawRect(rectX, rectY, rectWidth, rectHeight);

        repaint();
    }

    public void drawCircle() {

        int rectX, rectY, rectHeight, rectWidth;

        if (currentX > oldX) {
            rectX = oldX;
            rectWidth = currentX - oldX;
        } else {
            rectX = currentX;
            rectWidth = oldX - currentX;
        }

        if (currentY > oldY) {
            rectY = oldY;
            rectHeight = currentY - oldY;
        } else {
            rectY = currentY;
            rectHeight = oldY - currentY;
        }

        g.drawOval(rectX, rectY, rectWidth, rectHeight);

        repaint();
    }

    public void drawTriangle() {
        int thirdX, thirdY;

        if (currentX > oldX) {
            thirdX = oldX - Math.abs(currentX - oldX);
        } else {
            thirdX = oldX + Math.abs(currentX - oldX);
        }

        if (currentY > oldY) {
            thirdY = oldY + Math.abs(currentY - oldY);
        } else {
            thirdY = oldY - Math.abs(currentY - oldY);
        }

        int xpoints[] = {oldX, currentX, thirdX};
        int ypoints[] = {oldY, currentY, thirdY};
        int npoints = 3;

        g.drawPolygon(xpoints, ypoints, npoints);

        repaint();
    }

    public void drawLine() {
        g.drawLine(oldX, oldY, currentX, currentY);
        repaint();
    }

    public void drawBrush() {        
        for (int i = 1; i < 2; i++) {
            g.draw(new Line2D.Float(new Point(oldX, oldY), new Point(currentX, currentY)));
        }

        repaint();

        oldX = currentX;
        oldY = currentY;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setColor(Color color) {
        g.setPaint(color);
    }
    
    public void setBrushSize(float size) {
        brushSize = size;
        g.setStroke(new BasicStroke(brushSize,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
    }
}
