package projectofinalcomputacaografica;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 *
 * @Autor ed
 * Free Use - Livre Uso
 */
public class JanelaPrincipal extends JFrame {
    private static int WIDTH_JANELA = 1200;
    private static int HEIGHT_JANELA = 600;
    final int TIMELINE_WIDTH = 1200;
    final int TIMELINE_HEIGTH = 100;
    
    private Color corDeDesenho;
    private final JToolBar barraFerramentasDeDesenho = new JToolBar();
    private final JToolBar barraFerramentasDeFuncionalidades = new JToolBar();
    private final JButton buttonMao = new JButton("Mão");
    private final JButton buttonLapis = new JButton("Lapis");
    private final JButton buttonColor = new JButton("Cor");
    private final JButton buttonQuadrado = new JButton("Quadrado");
    private final JButton buttonQuadradoB = new JButton("QuadradoB");
    private final JButton buttonCirculo = new JButton("Circulo");
    private final JButton buttonTriangulo = new JButton("Triangulo");
    private final JButton buttonLine = new JButton("Linha");
    private final JButton buttonApagador = new JButton("Apagador");
    private final JCheckBox checkBoxRotacao = new JCheckBox("Rotação");
    private final JButton buttonPlay = new JButton("Play");
    private final JButton buttonSetKeyFrame = new JButton("Set Keyframe");
    private final JButton buttonSetBlankKeyFrame = new JButton("Blank Keyframe");
    private final JButton buttonSetFrameAnimation = new JButton("Set Animation");
    private int inicioAnimacao, fimAnimacao;
    private int setandoInicioEFim = 1;
    private boolean settingAnimationStartFrame = false;
    
    private final Timeline timeline;
    
    private int actualFrameIndex = 0;
    private Thread thread;
    
    private final PainelDeDesenho painelDeDesenho;

    public JanelaPrincipal() {
        super.setTitle("Flayers");
        super.setLayout(null);
        super.setBackground(Color.BLACK);
        
        inicializarBarraDeFerramentasDeDesenho();
        inicializarBarraDeFerramentasDeFuncionalidades();
        
        timeline = new Timeline();
        JScrollPane scrollPane = new JScrollPane(timeline);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 460, TIMELINE_WIDTH, TIMELINE_HEIGTH);
        scrollPane.setBackground(Color.BLACK);
        
        
        final int PAINEL_DE_DESENHO_WIDTH = 850;
        painelDeDesenho = new PainelDeDesenho();
        painelDeDesenho.setBackground(Color.WHITE);
        painelDeDesenho.setBounds((int)(WIDTH_JANELA / 2) - (int)(PAINEL_DE_DESENHO_WIDTH / 2), 40, 850, 400);
        
        
        super.add(painelDeDesenho);
        super.add(scrollPane);
        super.add(barraFerramentasDeDesenho);
        super.add(barraFerramentasDeFuncionalidades);
        
        OuvidorDeOpcoesEscolhidas escutaDeClickEmBotoes = new OuvidorDeOpcoesEscolhidas();
        
        buttonMao.addActionListener(escutaDeClickEmBotoes);
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
    
    private void inicializarBarraDeFerramentasDeFuncionalidades() {
        barraFerramentasDeFuncionalidades.setBackground(Color.WHITE);
        buttonLapis.setPreferredSize(new Dimension(60, 10));
        barraFerramentasDeFuncionalidades.add(checkBoxRotacao);
        barraFerramentasDeFuncionalidades.add(buttonSetKeyFrame);
        barraFerramentasDeFuncionalidades.add(buttonSetBlankKeyFrame);
        barraFerramentasDeFuncionalidades.add(buttonSetFrameAnimation);
        barraFerramentasDeFuncionalidades.add(buttonPlay);
        barraFerramentasDeFuncionalidades.setOrientation(1);
        barraFerramentasDeFuncionalidades.setBounds(WIDTH_JANELA - 150, 0, 150, 450);
        
        
        buttonSetFrameAnimation.addActionListener((ActionEvent e) -> {
            settingAnimationStartFrame = true;
        });
        
        buttonSetKeyFrame.addActionListener((ActionEvent e) -> {
            try {
                Component component = timeline.getComponent(actualFrameIndex);
                JButton botaoKeyFrame = (Frame) component;
                
                botaoKeyFrame.setBackground(new Color(255, 0, 102));
                Frame auxFrame = (Frame) component;
                
                auxFrame.setKeyFrame(true);
                timeline.getListaDeFrames().get(actualFrameIndex).setKeyFrame(true);
            } catch (Exception evento) {
                System.out.println("Deu pau: " + evento.toString());
            }
            
        });
        
        buttonSetBlankKeyFrame.addActionListener((ActionEvent e) -> {
            try {
                Component component = timeline.getComponent(actualFrameIndex);
                JButton botaoKeyFrame = (Frame) component;
                
                botaoKeyFrame.setBackground(new Color(0, 255, 131));
                timeline.getListaDeFrames().get(actualFrameIndex).setBlankKeyFrame(true);
            timeline.getListaDeFrames().get(actualFrameIndex).getListaDeDesenhosNesteFrame().clear();
            } catch (Exception evento) {
                System.out.println("Deu pau: " + evento.toString());
            }
            painelDeDesenho.repaint();
        });
        
        for (Object elemento : barraFerramentasDeFuncionalidades.getComponents()) {
            if (elemento instanceof JButton) {
                final JButton auxButton = (JButton) elemento;
                
                auxButton.setPreferredSize(new Dimension(50, 10));
                auxButton.setBackground(Color.WHITE);
            }
        }
        
        buttonPlay.addActionListener((ActionEvent e) -> {
            painelDeDesenho.running = true;
            comecarAnimacao();
        });
    }
    
    private void comecarAnimacao() {
        painelDeDesenho.startAnimation();
    }
    
    private void inicializarBarraDeFerramentasDeDesenho() {
        barraFerramentasDeDesenho.setBackground(Color.WHITE);
        buttonLapis.setPreferredSize(new Dimension(60, 10));
        barraFerramentasDeDesenho.add(buttonMao);
        barraFerramentasDeDesenho.add(buttonLapis);
        barraFerramentasDeDesenho.add(buttonColor);
        barraFerramentasDeDesenho.add(buttonQuadrado);
        barraFerramentasDeDesenho.add(buttonQuadradoB);
        barraFerramentasDeDesenho.add(buttonCirculo);
        barraFerramentasDeDesenho.add(buttonLine);
        barraFerramentasDeDesenho.add(buttonTriangulo);
        barraFerramentasDeDesenho.add(buttonApagador);
        barraFerramentasDeDesenho.add(checkBoxRotacao);
        barraFerramentasDeDesenho.add(buttonSetKeyFrame);
        barraFerramentasDeDesenho.add(buttonPlay);
        barraFerramentasDeDesenho.setOrientation(1);
        barraFerramentasDeDesenho.setBounds(0, 0, 150, 450);
        
        for (Object elemento : barraFerramentasDeDesenho.getComponents()) {
            if (elemento instanceof JButton) {
                final JButton auxButton = (JButton) elemento;
                
                auxButton.setPreferredSize(new Dimension(50, 10));
                auxButton.setBackground(Color.WHITE);
            }
        }
    }
    
    
    
    public class OuvidorDeOpcoesEscolhidas implements ActionListener {
        public int forma;
        public boolean maoSelecionada = false;
        public JButton botaoSelecionadoAnteriormente = null;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (e.getSource() == checkBoxRotacao) {
                painelDeDesenho.setRotacao(checkBoxRotacao.isSelected());
            }
            
            JButton botaoSelecionadoActualmente = (JButton) e.getSource();
            
            if (botaoSelecionadoAnteriormente == null) {
                botaoSelecionadoAnteriormente = (JButton) e.getSource();
                botaoSelecionadoActualmente.setBackground(new Color(0, 174, 255));
                botaoSelecionadoAnteriormente.setBackground(new Color(0, 174, 255));
            } else {
                botaoSelecionadoAnteriormente.setBackground(new Color(255, 255, 255));
                botaoSelecionadoAnteriormente = botaoSelecionadoActualmente;
                
            }
            botaoSelecionadoActualmente.setBackground(new Color(0, 174, 255));
            
            
            if (e.getSource() == buttonMao) {
               maoSelecionada = true;
               forma = -1;
            } else if (e.getSource() == buttonLapis) {
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
            
            if (forma != -1) {
                maoSelecionada = false;
            }
            painelDeDesenho.setInformacoesDePainelEsquerdo(forma, corDeDesenho, maoSelecionada);
        }
    }
    
    public final class Timeline extends JPanel  {
        public ArrayList<Frame> listaDeFrames;
        public ArrayList<JLabel> containerDeLabel;
        public int actualFrame = 0;

        public Timeline() {
            super();
            this.listaDeFrames = inicializarListaDeFrames();
            super.setLayout(new BoxLayout(this, 0));
            this.construirParteVisual();
        }

        public void construirParteVisual() {
            for(int i = 0; i < this.listaDeFrames.size(); i++) {
                final Frame auxButton = new Frame();
                auxButton.setMinimumSize(new Dimension(ProjectoUtils.FRAME_WIDTH, ProjectoUtils.FRAME_HEIGHT));
                auxButton.setMaximumSize(new Dimension(ProjectoUtils.FRAME_WIDTH, ProjectoUtils.FRAME_HEIGHT));
                auxButton.setAlignmentY(TOP_ALIGNMENT);
                auxButton.setFont(new Font("Arial", Font.PLAIN, 10));
                auxButton.id = i;
                auxButton.setText("" + i);
                
                if (i % 5 == 0) {
                    auxButton.setBackground(Color.white);
                }
                
                auxButton.addActionListener((ActionEvent e) -> {
                    painelDeDesenho.repaint();
                    repaint();
                    Frame auxFrame = (Frame) e.getSource();
                    actualFrameIndex = auxFrame.getID();
                    if (settingAnimationStartFrame && auxFrame.isKeyFrame()) {
                        if (setandoInicioEFim == 1) {
                            inicioAnimacao = auxFrame.getID();
                            auxFrame.setInicioAnimation(auxFrame.getID());
                            setandoInicioEFim++;
                        } else {
                            if (inicioAnimacao != auxFrame.getID()) {
                               fimAnimacao = auxButton.getID();
                               auxButton.setFimAnimation(auxButton.getID());
                               settingAnimationStartFrame = false;
                               buildFrameAnimation();
                               setandoInicioEFim = 1;
                            }
                        }
                    }
                    repaint();
                });
                this.add(auxButton);
            }
        }
        
        
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2Graphics = (Graphics2D) g;
            AffineTransform affine = g2Graphics.getTransform();
            
            g2Graphics.setTransform(affine);
            g2Graphics.fillRect(0, 0, getWidth(), getHeight());
            
            construirTimeLineVisual(g2Graphics);
            
            g2Graphics.setTransform(affine);
        }

        public ArrayList<Frame> getListaDeFrames() {
            return listaDeFrames;
        }

        private void construirTimeLineVisual(Graphics2D g2Graphics) {
            AffineTransform affine = g2Graphics.getTransform();
            
            construirListaDeFrames(g2Graphics);
            
            g2Graphics.setTransform(affine);
        }

        private void construirListaDeFrames(Graphics2D g2Graphics) {
            int offsetFrames = 0;
            
            for (int i = 0; i < listaDeFrames.size(); i++) {
                g2Graphics.setStroke(new BasicStroke( 2.0f ));
                g2Graphics.drawRect(offsetFrames, 0, ProjectoUtils.FRAME_WIDTH, ProjectoUtils.FRAME_HEIGHT);
                offsetFrames += ProjectoUtils.FRAME_WIDTH;
            }
        }

        private ArrayList<Frame> inicializarListaDeFrames() {
            ArrayList<Frame> lista = new ArrayList<>();
            
            for (int i = 0; i < 240; i++) {
                lista.add(new Frame());
            }
            return lista;
        }
        
        private void buildFrameAnimation() {
            if (settingAnimationStartFrame == false) {
                final int startIndexFrame = inicioAnimacao;
                final int endIndexFrame = fimAnimacao;
                int actualIndexFrame;
                
                Frame startFrame = (Frame) timeline.listaDeFrames.get(startIndexFrame);
                Frame endFrame = (Frame) timeline.listaDeFrames.get(endIndexFrame);
                
                
                final int quantFormasGeometricas = endFrame.listaDeDesenhosNesteFrame.size() >= startFrame.listaDeDesenhosNesteFrame.size() ?
                    startFrame.listaDeDesenhosNesteFrame.size() : endFrame.listaDeDesenhosNesteFrame.size();
                final ArrayList<FiguraGeometrica> listaDeFiguras = endFrame.listaDeDesenhosNesteFrame.size() >= startFrame.listaDeDesenhosNesteFrame.size() ?
                    startFrame.listaDeDesenhosNesteFrame : endFrame.listaDeDesenhosNesteFrame;
                
                
                for (actualIndexFrame = inicioAnimacao + 1; actualIndexFrame < endIndexFrame; actualIndexFrame++) {
                    final Frame frameActual = (Frame) timeline.getComponent(actualIndexFrame);
                    final JButton representacaoVisualFrame = (JButton) timeline.getComponent(actualIndexFrame);
                    final int percentagemDeDeslocamento = (actualIndexFrame * 100) / endIndexFrame;
                    
                    for (int figuraActualIndex = 0; figuraActualIndex < quantFormasGeometricas; figuraActualIndex++) {
                        if (frameActual.listaDeDesenhosNesteFrame.size() <= 0) {
                            frameActual.setListaDeDesenhosNesteFrame(listaDeFiguras);
                        }
                        
                        final FiguraGeometrica figuraActual = frameActual.listaDeDesenhosNesteFrame.get(figuraActualIndex);
                        final int posicaoFinalX = endFrame.listaDeDesenhosNesteFrame.get(figuraActualIndex).getxPos();
                        final int posicaoFinalY = endFrame.listaDeDesenhosNesteFrame.get(figuraActualIndex).getyPos();
                        final int novoPontoX = (percentagemDeDeslocamento * posicaoFinalX) / 100;
                        final int novoPontoY = (percentagemDeDeslocamento * posicaoFinalY) / 100;
                        
                        figuraActual.setNovoPonto(actualIndexFrame, new Point(novoPontoX, novoPontoY));
                        
                        frameActual.listaDeDesenhosNesteFrame.remove(figuraActualIndex);
                        frameActual.listaDeDesenhosNesteFrame.add(figuraActualIndex, figuraActual);
                    }
                    
                    representacaoVisualFrame.setEnabled(false);
                    representacaoVisualFrame.setBackground(new Color(30, 30, 30));
                }
                
            }
        }
        
    }
   
    
    public class PainelDeDesenho extends JPanel implements MouseMotionListener, MouseListener, Runnable{
        int posXInicialMouse, posXFinalMouse, posYFinalMouse, posYInicialMouse;
        int formaParaSerDesenhada = 3;
        boolean redesenharTodosObjectos = false;
        Color corDoDesenho = Color.BLACK;
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        private boolean rotacao;
        Thread thread;
        private float rotacaoObjectos = 0;
        ArrayList<Frame> listaDeFrames;
        private boolean running = false;
        private boolean maoSelecionada = false;
        FiguraGeometrica figuraASerArrastada;
        
        
        public PainelDeDesenho() {
            listaDeFrames = timeline.getListaDeFrames();
            
            super.setLayout(null);
            super.setBackground(Color.WHITE);
            super.addMouseListener(this);
            super.addMouseMotionListener(this);
        }

        public void setInformacoesDePainelEsquerdo(int forma, Color corDoDesenho, boolean maoSelecionada) {
            this.formaParaSerDesenhada = forma;
            this.corDoDesenho = corDoDesenho;
            this.maoSelecionada = maoSelecionada;
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
            
            timeline.listaDeFrames.get(actualFrameIndex % timeline.getListaDeFrames().size())
                 .listaDeDesenhosNesteFrame.forEach((objecto) -> {
                     AffineTransform affineAux = g2d.getTransform();
                     desenharObjecto(objecto, g);
                     g2d.setTransform(affineAux);
                 });
            
            g2d.setTransform(afine);
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
           if (maoSelecionada) {
               if (figuraASerArrastada != null) {
                   figuraASerArrastada.setxPos(e.getPoint().x);
                   figuraASerArrastada.setyPos(e.getPoint().y);
                   painelDeDesenho.repaint();
               }
           } else {
               
           }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
                    
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (!maoSelecionada) {
                posXInicialMouse = e.getX();
                posYInicialMouse = e.getY();
                this.redesenharTodosObjectos = true;  
            } else {
                FiguraGeometrica figuraGeometricaASerMovimentada;
                Point pontoClicadoPeloMouse = e.getPoint();
               
               
                figuraGeometricaASerMovimentada = timeline.getListaDeFrames()
                                                  .get(actualFrameIndex)
                                                  .getFiguraPosicao(pontoClicadoPeloMouse);

                if (figuraGeometricaASerMovimentada != null) {
                    figuraASerArrastada = figuraGeometricaASerMovimentada;
                } else {
                    figuraASerArrastada = null;
                    System.out.println("Está gato");
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!maoSelecionada) {
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
                corDoDesenho, formaParaSerDesenhada, actualFrameIndex);


                inserirNovaFigura(novaFigura);

                novaFigura.setRotacionando(rotacao);
                timeline.listaDeFrames
                        .get(actualFrameIndex % timeline.getListaDeFrames().size())
                        .listaDeDesenhosNesteFrame.add(novaFigura);
                this.redesenharTodosObjectos = true;
                super.repaint();
            } else {
                if (timeline.getListaDeFrames().get(actualFrameIndex).isKeyFrame()) {
                    if (figuraASerArrastada != null) {
                        if (figuraASerArrastada.posicoesObjectoNosFrames.containsKey(actualFrameIndex)) {
                            figuraASerArrastada.posicoesObjectoNosFrames.replace(actualFrameIndex, new Point(e.getPoint().x, e.getPoint().y)); 
                        } else {
                           figuraASerArrastada.posicoesObjectoNosFrames.put(actualFrameIndex, new Point(e.getPoint().x, e.getPoint().y));  
                        }
                    }
                }
                
            }
        }
        
        public void inserirNovaFigura(FiguraGeometrica novaFigura) {
            int i = actualFrameIndex;
            boolean isKeyFrame = false;
            
            while (i < timeline.getListaDeFrames().size() && !isKeyFrame) {
                if (!timeline.getListaDeFrames().get(i).isBlankKeyFrame()) {
                    timeline.getListaDeFrames().get(i).inserirFiguraGeometrica(novaFigura);
                }
                isKeyFrame = timeline.getListaDeFrames().get(i).isKeyFrame();
                i++;
            }
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
            
            int x = objecto.posicoesObjectoNosFrames.get(actualFrameIndex) != null ? (int)objecto.posicoesObjectoNosFrames.get(actualFrameIndex).getX() : objecto.getxPos();
            int y =  objecto.posicoesObjectoNosFrames.get(actualFrameIndex) != null ? (int)objecto.posicoesObjectoNosFrames.get(actualFrameIndex).getY() : objecto.getyPos();;
            
            switch (objecto.getForma()) {
                case 1:
                    g2d.fillRect(x, y, objecto.getLargura(), objecto.getAltura());
                    break;
                case 2:
                    g2d.fillRoundRect(x, y,
                            objecto.getLargura(), objecto.getAltura(), 10, 10);
                    break;
                case 3:
                    g2d.fillOval(x, y,
                            objecto.getLargura(), objecto.getAltura());
                    break;
                case 4:
                    g2d.drawLine(x, y, 
                            objecto.getxPos() + objecto.getLargura(), objecto.getyPos() + objecto.getAltura());
                    break;
                case 5:
                    g2d.fillRect(x, y, 
                            objecto.getLargura(), objecto.getAltura());
                    break;
                case 6:
                    g2d.fillRect(x, y, 
                            objecto.getLargura(), objecto.getAltura());
                    break;
                case 7:
                    // Apagador
                    break;
            }
        }

        @Override
        public void run() {
            try {
                while(running) {
                    repaint();
                    Thread.sleep(1000/24);
                    actualFrameIndex++;
                    
                    if (actualFrameIndex >= 240) {
                        actualFrameIndex = 0;
                        thread.interrupt();
                        running = false;
                        thread.interrupt();
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }

        private void startAnimation() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
}
