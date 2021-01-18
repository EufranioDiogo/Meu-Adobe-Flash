package projectofinalcomputacaografica;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @Autor ed
 * Free Use - Livre_Uso
 */
public class JanelaPrincipal extends JFrame {
    private Color corDeDesenho;
    private JToolBar barraFerramentasDeDesenho = new JToolBar();
    private JToolBar barraEfeitosExtras = new JToolBar();
    private JButton buttonLapis = new JButton("Lapis");
    private JButton buttonColor = new JButton("Cor");
    private JButton buttonQuadrado = new JButton("Quadrado");
    private JButton buttonQuadradoB = new JButton("QuadradoB");
    private JButton buttonCirculo = new JButton("Circulo");
    private JButton buttonTriangulo = new JButton("Triangulo");
    private JButton buttonLine = new JButton("Linha");
    private JButton buttonApagador = new JButton("Apagador");
    private JCheckBox checkBoxRotacao = new JCheckBox("Rotação");
    
    
    private PainelDeDesenho painelDeDesenho = new PainelDeDesenho();

    
    public JanelaPrincipal() {
        super.setTitle("Flayers");
        
        painelDeDesenho.setBackground(Color.WHITE);        
        buttonLapis.setBackground(Color.WHITE);
        barraFerramentasDeDesenho.add(buttonLapis);
        barraFerramentasDeDesenho.add(buttonColor);
        barraFerramentasDeDesenho.add(buttonQuadrado);
        barraFerramentasDeDesenho.add(buttonQuadradoB);
        barraFerramentasDeDesenho.add(buttonCirculo);
        barraFerramentasDeDesenho.add(buttonLine);
        barraFerramentasDeDesenho.add(buttonTriangulo);
        barraFerramentasDeDesenho.add(buttonApagador);
        barraFerramentasDeDesenho.add(checkBoxRotacao);
        barraFerramentasDeDesenho.setBackground(new Color(33, 33, 33));
        
        
        add(barraFerramentasDeDesenho, BorderLayout.PAGE_START);
        add(barraEfeitosExtras, BorderLayout.PAGE_END);
        add(painelDeDesenho, BorderLayout.CENTER);
        add(new JPanel(), BorderLayout.SOUTH);
        
        OuvidorDeOpcoesEscolhidas escutaDeClickEmBotoes = new OuvidorDeOpcoesEscolhidas();
        
        buttonLapis.addActionListener(escutaDeClickEmBotoes);
        buttonColor.addActionListener(escutaDeClickEmBotoes);
        buttonQuadrado.addActionListener(escutaDeClickEmBotoes);
        buttonQuadradoB.addActionListener(escutaDeClickEmBotoes);
        buttonCirculo.addActionListener(escutaDeClickEmBotoes);
        buttonLine.addActionListener(escutaDeClickEmBotoes);
        buttonTriangulo.addActionListener(escutaDeClickEmBotoes);
        buttonApagador.addActionListener(escutaDeClickEmBotoes);
        checkBoxRotacao.addActionListener(escutaDeClickEmBotoes);
    }
    
    
    public class OuvidorDeOpcoesEscolhidas implements ActionListener {
        public int forma;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (e.getSource() == checkBoxRotacao) {
                painelDeDesenho.setRotacao(checkBoxRotacao.isSelected());
            }
            
            if (e.getSource() == buttonLapis) {
                forma = 0;
            } else if (e.getSource() == buttonColor) {
                Color color = JColorChooser.showDialog(null, "Seleciona a cor", Color.RED);
                System.out.println(color);
                if (color != null) {
                    corDeDesenho = color;
                }
                forma = painelDeDesenho.getForma();
            } else if (e.getSource() == buttonQuadrado) {
                forma = 1;
                System.out.println("Entrou Quadrado");
            } else if (e.getSource() == buttonQuadradoB) {
                forma = 2;
            } else if (e.getSource() == buttonCirculo) {
                forma = 3;
            } else if (e.getSource() == buttonLine) {
                forma = 4;
            } else if (e.getSource() == buttonTriangulo) {
                forma = 5;
            } else if (e.getSource() == buttonApagador) {
                forma = 6;
            }
            painelDeDesenho.setForma(forma, corDeDesenho);
        }
    }
    
    
    
    public class PainelDeDesenho extends JPanel implements MouseMotionListener, MouseListener, Runnable{
        int posXInicialMouse, posXFinalMouse, posYFinalMouse, posYInicialMouse;
        int formaParaSerDesenhada = 3;
        boolean redesenharTodosObjectos = false;
        Color corDoDesenho = Color.BLACK;
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        ArrayList<FiguraGeometrica> listaDeObjectosDesenhadosNaTela;
        private boolean rotacao;
        Thread thread;
        private float rotacaoObjectos = 0;
        
        
        public PainelDeDesenho() {
            listaDeObjectosDesenhadosNaTela = new ArrayList<FiguraGeometrica>();
            super.setLayout(null);
            setBackground(Color.WHITE);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            thread = new Thread(this);
            thread.start();
        }

        public void setForma(int forma, Color corDoDesenho) {
            formaParaSerDesenhada = forma;
            this.corDoDesenho = corDoDesenho;
        }
        
        public int getForma() {
            return this.formaParaSerDesenhada;
        }
        
        public void setRotacao(boolean value) {
            this.rotacao = value;
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform afine = g2d.getTransform();
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (this.redesenharTodosObjectos) {
               listaDeObjectosDesenhadosNaTela.forEach((objecto) -> {
                    AffineTransform affineAux = g2d.getTransform();
                    desenharObjecto(objecto, g);
                    g2d.setTransform(affineAux);
                }); 
            } else {
                
            }
            g2d.setTransform(afine);
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
           
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            posXInicialMouse = e.getX();
            posYInicialMouse = e.getY();
            this.redesenharTodosObjectos = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            posXFinalMouse = e.getX();
            posYFinalMouse = e.getY();
            
            int posXInicialDesenho = posXInicialMouse;
            int posYInicialDesenho = posYInicialMouse;
            int largura;
            int altura;
            
            if (posXFinalMouse < posXInicialMouse) {
                posXInicialDesenho = posXFinalMouse;
                posXFinalMouse = posXInicialMouse;
            }
            largura = posXFinalMouse - posXInicialDesenho;
            
            if (posYFinalMouse < posYInicialMouse) {
                posYInicialDesenho = posYFinalMouse;
                posYFinalMouse = posYInicialMouse;
            }
            altura = posYFinalMouse - posYInicialDesenho;
            
            FiguraGeometrica novaFigura = new FiguraGeometrica
            (posXInicialDesenho, posYInicialDesenho, altura, largura,
            corDoDesenho, formaParaSerDesenhada);
            
            novaFigura.setRotacionando(rotacao);
            listaDeObjectosDesenhadosNaTela.add(novaFigura);
            this.redesenharTodosObjectos = true;
            super.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }

        private void desenharObjecto(FiguraGeometrica objecto, Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            
            g2d.setColor(objecto.getCorFigura());
            if (objecto.isRotacionando()) {
                g2d.rotate(objecto.getGrauRotacao(), objecto.getCenterX(), objecto.getCenterY());
            }
            
            switch (objecto.getForma()) {
                case 1:
                    g2d.fillRect(objecto.getxPos(), objecto.getyPos(), objecto.getLargura(), objecto.getAltura());
                    break;
                case 2:
                    g2d.fillRoundRect(objecto.getxPos(), objecto.getyPos(), objecto.getLargura(), objecto.getAltura(), 10, 10);
                    break;
                case 3:
                    g2d.fillOval(objecto.getxPos(), objecto.getyPos(), objecto.getLargura(), objecto.getAltura());
                    break;
                case 4:
                    g2d.drawLine(objecto.getxPos(), objecto.getyPos(), objecto.getxPos() + objecto.getLargura(), objecto.getyPos() + objecto.getAltura());
                    break;
                case 5:
                    g2d.fillRect(objecto.getxPos(), objecto.getyPos(), objecto.getLargura(), objecto.getAltura());
                    break;
                case 6:
                    g2d.fillRect(objecto.getxPos(), objecto.getyPos(), objecto.getLargura(), objecto.getAltura());
                    break;
                case 7:
                    // Apagador
                    break;
            }
        }

        @Override
        public void run() {
            try {
                while(true) {
                    repaint();
                    Thread.sleep(1000/24);
                }
            } catch (Exception e) {
            }
        }
    }
    
}
