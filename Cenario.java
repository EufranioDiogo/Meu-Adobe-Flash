/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package working;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Yuri Domingos 
 * Data   : 14 - 01 - 2021
 * Objectivo : Construir o paint que desnha em tempo real 
 */
public class Cenario extends JPanel  {
    
    
    private ArrayList<Point> lapis = new ArrayList<>();
    private JButton choose ;
    private Color color;
   
    
    public Cenario() {
        
        
        
        choose = new JButton("Seleciona a cor please ");
        
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            
                color = JColorChooser.showDialog(null, "Seleciona a cor ", Color.RED);
                
            }
        });
        
   
        add(choose);
        
        addMouseMotionListener(new MouseMotionListener()
        {
            @Override
            public void mouseDragged(MouseEvent me) {
                
                lapis.add(me.getPoint());
                repaint();
         
            }

            @Override
            public void mouseMoved(MouseEvent me) {
             
            }

            
        });
               
       init();
    }
    
    public void init()
    {
        
        JFrame frame = new JFrame();
        frame.setTitle("Paint ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.getContentPane().add(this);
        frame.setVisible(true);
        
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.clearRect(0, 0, this.getWidth(), this.getHeight());
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(32));
       
        for ( Point lapis_para_desenhar  : lapis )
        {
            graphics2D.fill(new Ellipse2D.Double(lapis_para_desenhar.x, lapis_para_desenhar.y, 10,10));
          
        }
        
        g.dispose(); // clone design cenary 
    }
    
    
    
    
}
