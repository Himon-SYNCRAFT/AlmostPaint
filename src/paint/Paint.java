/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JColorChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author himon
 */
public class Paint implements ChangeListener {

    JButton clearBtn, lineBtn, rectBtn, circleBtn, triangleBtn, brushBtn;
    DrawArea drawArea;
    JColorChooser jColorChooser1;
    JSlider strokeSizeSlider;
    ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearBtn) {
                drawArea.clear();
            } else if (e.getSource() == lineBtn) {
                drawArea.setMode(1);
            } else if (e.getSource() == rectBtn) {
                drawArea.setMode(2);
            } else if (e.getSource() == circleBtn) {
                drawArea.setMode(3);
            } else if (e.getSource() == triangleBtn) {
                drawArea.setMode(4);
            } else if (e.getSource() == brushBtn) {
                drawArea.setMode(5);
            }
        }

    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Paint().show();
    }

    public void show() {
        JFrame frame = new JFrame("Paint");

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        drawArea = new DrawArea();

        content.add(drawArea, BorderLayout.CENTER);

        JPanel controls = new JPanel();

        brushBtn = new JButton("brush");
        brushBtn.addActionListener(actionListener);

        lineBtn = new JButton("line");
        lineBtn.addActionListener(actionListener);

        rectBtn = new JButton("rectangle");
        rectBtn.addActionListener(actionListener);

        circleBtn = new JButton("circle");
        circleBtn.addActionListener(actionListener);

        triangleBtn = new JButton("triangle");
        triangleBtn.addActionListener(actionListener);

        clearBtn = new JButton("clear");
        clearBtn.addActionListener(actionListener);
        
        strokeSizeSlider = new JSlider();
        strokeSizeSlider.setValue(1);
        strokeSizeSlider.addChangeListener(this);
        
        controls.add(clearBtn);
        controls.add(lineBtn);
        controls.add(rectBtn);
        controls.add(circleBtn);
        controls.add(triangleBtn);
        controls.add(brushBtn);
        controls.add(strokeSizeSlider);

        content.add(controls, BorderLayout.NORTH);

        jColorChooser1 = new JColorChooser(Color.BLACK);
        jColorChooser1.getSelectionModel().addChangeListener(this);
        content.add(jColorChooser1, BorderLayout.SOUTH);

        frame.setSize(900, 900);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;

        frame.setVisible(true);
    }

    public Color getColor() {
        return jColorChooser1.getColor();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        drawArea.setColor(getColor());
        drawArea.setBrushSize(strokeSizeSlider.getValue() / 4f + 0.75f);
    }

}
