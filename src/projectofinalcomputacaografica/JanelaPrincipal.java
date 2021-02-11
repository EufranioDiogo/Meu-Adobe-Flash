package projectofinalcomputacaografica;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 *
 * @Autor ed
 * Free Use - Livre Uso
 */
public final class JanelaPrincipal extends JFrame {
    private static final int WIDTH_JANELA = 1200;
    final int TIMELINE_WIDTH = 1200;
    final int TIMELINE_HEIGTH = 100;
    final String PATH_ICONS = "ICON_FIGURAS/";
    
    private Color corDeDesenho;
    private final JToolBar barraFerramentasDeDesenho = new JToolBar();
    private final JToolBar barraFerramentasDeFuncionalidades = new JToolBar();
    
    private final JButton buttonMao = new JButton(new ImageIcon(PATH_ICONS + "mao2.jpeg"));
    private final JButton buttonLapis = new JButton(new ImageIcon(PATH_ICONS + "lapis.png"));
    private final JButton buttonLine = new JButton(new ImageIcon(PATH_ICONS + "linha.png"));
    private final JButton buttonApagador = new JButton(new ImageIcon(PATH_ICONS + "borracha.png"));
    private final JButton buttonQuadrado = new JButton(new ImageIcon(PATH_ICONS + "quadrado.png"));
    private final JButton buttonCirculo = new JButton(new ImageIcon(PATH_ICONS + "circulo.png"));
    private final JButton buttonBalde = new JButton(new ImageIcon(PATH_ICONS + "balde.png"));
    private final JButton buttonQuadradoB = new JButton("QuadradoB");
    private final JButton buttonTriangulo = new JButton(new ImageIcon(PATH_ICONS + "triangulo.png"));
    private final JButton buttonColor = new JButton();
    
    
    private final JCheckBox checkBoxRotacao = new JCheckBox("Rotação");
    private final JButton buttonPlay = new JButton("Play");
    private final JButton buttonSetKeyFrame = new JButton("Set Keyframe");
    private final JButton buttonSetBlankKeyFrame = new JButton("Blank Keyframe");
    private final JButton buttonSetFrameAnimation = new JButton("Set Animation");
    private final JButton buttonRemoveAnimation = new JButton("Remove Animation");
    private final JButton buttonStopAnimation = new JButton("Stop Animation");
    public JButton botaoSelecionadoAnteriormente = null;
    private int inicioAnimacao, fimAnimacao;
    private int setandoInicioEFim = 1;
    private boolean settingAnimationStartFrame = false;
    private boolean removingAnimation = false;
    private boolean baldeActivo = false;
    private Timeline timeline;
    
    private int actualFrameIndex = 0;
    private PainelDeDesenho painelDeDesenho;

    
    public JanelaPrincipal() {
        standardConfiguration();
        inicializarBarraDeFerramentasDeDesenho();
        inicializarBarraDeFerramentasDeFuncionalidades();
        inicializarTimeline();
        inicializarPainelDeDesenho();
        
        super.add(painelDeDesenho);
        super.add(barraFerramentasDeDesenho);
        super.add(barraFerramentasDeFuncionalidades);
        
        
        OuvidorDeOpcoesEscolhidas escutaDeClickEmBotoes = new OuvidorDeOpcoesEscolhidas();
        buttonMao.addActionListener(escutaDeClickEmBotoes);
        buttonLapis.addActionListener(escutaDeClickEmBotoes);
        buttonColor.addActionListener(escutaDeClickEmBotoes);
        buttonQuadrado.addActionListener(escutaDeClickEmBotoes);
        buttonQuadradoB.addActionListener(escutaDeClickEmBotoes);
        buttonBalde.addActionListener(escutaDeClickEmBotoes);
        buttonCirculo.addActionListener(escutaDeClickEmBotoes);
        buttonLine.addActionListener(escutaDeClickEmBotoes);
        buttonTriangulo.addActionListener(escutaDeClickEmBotoes);
        buttonApagador.addActionListener(escutaDeClickEmBotoes);
        checkBoxRotacao.addActionListener(escutaDeClickEmBotoes);
    }
    
    public void standardConfiguration() {
        super.setTitle("hsalF");
        super.setLayout(null);
        super.setBackground(Color.decode("#3c3c3c"));
    }
    
    public void inicializarTimeline() {
        timeline = new Timeline();
        JScrollPane scrollPane = new JScrollPane(timeline);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 460, TIMELINE_WIDTH, TIMELINE_HEIGTH);
        scrollPane.setBackground(Color.BLACK);
        super.add(scrollPane);
    }
    
    public void inicializarPainelDeDesenho() {
        final int PAINEL_DE_DESENHO_WIDTH = 850;
        painelDeDesenho = new PainelDeDesenho();
        painelDeDesenho.setBackground(Color.WHITE);
        painelDeDesenho.setBounds((int)(WIDTH_JANELA / 2) - (int)(PAINEL_DE_DESENHO_WIDTH / 2), 40, 850, 400);
    }
    
    private void inicializarBarraDeFerramentasDeFuncionalidades() {
        barraFerramentasDeFuncionalidades.setBackground(Color.WHITE);
        barraFerramentasDeFuncionalidades.add(checkBoxRotacao);
        barraFerramentasDeFuncionalidades.add(buttonSetKeyFrame);
        barraFerramentasDeFuncionalidades.add(buttonSetBlankKeyFrame);
        barraFerramentasDeFuncionalidades.add(buttonSetFrameAnimation);
        barraFerramentasDeFuncionalidades.add(buttonRemoveAnimation);
        barraFerramentasDeFuncionalidades.add(buttonPlay);
        barraFerramentasDeFuncionalidades.add(buttonStopAnimation);
        barraFerramentasDeFuncionalidades.setOrientation(1);
        barraFerramentasDeFuncionalidades.setBounds(WIDTH_JANELA - 150, 0, 150, 450);
        
        
        buttonSetFrameAnimation.addActionListener((ActionEvent e) -> {
            settingAnimationStartFrame = true;
        });
        
        buttonRemoveAnimation.addActionListener((ActionEvent e) -> {
            removingAnimation = true;
        });
        
        buttonStopAnimation.addActionListener((ActionEvent e) -> {
            painelDeDesenho.running = false;
            System.out.println("Animação parou ");
        });
        
        buttonSetKeyFrame.addActionListener((ActionEvent e) -> {
            try {
                Component component = timeline.getComponent(actualFrameIndex);
                JButton botaoKeyFrame = (Frame) component;
                
                botaoKeyFrame.setBackground(new Color(255, 0, 102));
                Frame auxFrame = (Frame) component;
                
                auxFrame.setKeyFrame(true);
                timeline.getListaDeFrames().get(actualFrameIndex).setKeyFrame(true);
                for(FiguraGeometrica figura : timeline.getListaDeFrames().get(actualFrameIndex).getListaDeDesenhosNesteFrame()) {
                    figura.setCorFiguraFrame(actualFrameIndex, figura.getCorFigura());
                    figura.setNovoPonto(actualFrameIndex, new Point(figura.getxPos(), figura.getyPos()));
                }
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
                
                auxButton.setMaximumSize(new Dimension(150, 35));
                auxButton.setMaximumSize(new Dimension(150, 35));
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
        barraFerramentasDeDesenho.add(buttonMao);
        barraFerramentasDeDesenho.add(buttonLapis);
        barraFerramentasDeDesenho.add(buttonQuadrado);
        barraFerramentasDeDesenho.add(buttonQuadradoB);
        barraFerramentasDeDesenho.add(buttonCirculo);
        barraFerramentasDeDesenho.add(buttonLine);
        barraFerramentasDeDesenho.add(buttonTriangulo);
        barraFerramentasDeDesenho.add(buttonApagador);
        barraFerramentasDeDesenho.add(buttonBalde);
        barraFerramentasDeDesenho.add(buttonColor);
        
        barraFerramentasDeDesenho.setOrientation(1);
        barraFerramentasDeDesenho.setBounds(0, 0, 150, 450);
        
        for (Object elemento : barraFerramentasDeDesenho.getComponents()) {
            if (elemento instanceof JButton) {
                final JButton auxButton = (JButton) elemento;
                auxButton.setMaximumSize(new Dimension(40, 40));
                auxButton.setMaximumSize(new Dimension(40, 40));
                auxButton.setBackground(Color.WHITE);
            }
        }
        buttonCirculo.setBackground(new Color(0, 174, 255));
        botaoSelecionadoAnteriormente = buttonCirculo;
    }
    
    
    
    public class OuvidorDeOpcoesEscolhidas implements ActionListener {
        public int forma;
        public boolean maoSelecionada = false;
        
        @Override
        public void actionPerformed(ActionEvent e) { 
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
               baldeActivo = false;
               forma = -1;
            } else if (e.getSource() == buttonBalde) {
                baldeActivo = !baldeActivo;
                maoSelecionada = false;
                forma = -1;
            }
            else if (e.getSource() == buttonLapis) {
                forma = 0;
            } else if (e.getSource() == buttonColor) {
                Color color = JColorChooser.showDialog(null, "Seleciona a cor", Color.RED);
                corDeDesenho = color != null ? color : Color.BLACK;
                
                buttonColor.setBackground(corDeDesenho);
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
                maoSelecionada = false;
                baldeActivo = false;
                corDeDesenho = new Color(255, 255, 255);
            }
            
            if (forma != -1) {
                maoSelecionada = false;
            }
            
            buttonColor.setBackground(corDeDesenho);
            
            if (baldeActivo) {
                buttonBalde.setBackground(new Color(0, 174, 255));
            } else {
                buttonBalde.setBackground(new Color(255, 255, 255));
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
                auxButton.setContentAreaFilled(true);
                auxButton.setAlignmentY(TOP_ALIGNMENT);
                auxButton.setFont(new Font("Arial", Font.PLAIN, 10));
                auxButton.id = i;
                auxButton.setText("" + i);
                
                
                if (i % 5 == 0) {
                    auxButton.setBackground(Color.decode("#41abc3"));
                } else {
                    auxButton.setBackground(new Color(255, 255, 255));
                }
                
                auxButton.addActionListener((ActionEvent e) -> {
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
                               buildMoveAnimation();
                               setandoInicioEFim = 1;
                            }
                        }
                    } else if (removingAnimation && auxFrame.isKeyFrame()) {
                        if (setandoInicioEFim == 1) {
                            inicioAnimacao = auxFrame.getID();
                            auxFrame.setInicioAnimation(auxFrame.getID());
                            setandoInicioEFim++;
                        } else {
                            if (inicioAnimacao != auxFrame.getID()) {
                               fimAnimacao = auxButton.getID();
                               auxButton.setFimAnimation(auxButton.getID());
                               removingAnimation = false;
                               removeMoveAnimation();
                               setandoInicioEFim = 1;
                            }
                        }
                    }
                    painelDeDesenho.repaint();
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
            
            for (Frame listaDeFrame : listaDeFrames) {
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
        
        private void buildMoveAnimation() {
            if (settingAnimationStartFrame == false) {
                final int startIndexFrame = inicioAnimacao;
                final int endIndexFrame = fimAnimacao;
                int actualIndexFrame;
                
                Frame startFrame = (Frame) timeline.listaDeFrames.get(startIndexFrame);
                Frame endFrame = (Frame) timeline.listaDeFrames.get(endIndexFrame);
                
                
                final int quantFormasGeometricas = endFrame.listaDeDesenhosNesteFrame.size() >= 
                        startFrame.listaDeDesenhosNesteFrame.size() ? startFrame.listaDeDesenhosNesteFrame.size() : 
                        endFrame.listaDeDesenhosNesteFrame.size();
                
                final ArrayList<FiguraGeometrica> listaDeFiguras = endFrame.listaDeDesenhosNesteFrame.size() >= 
                        startFrame.listaDeDesenhosNesteFrame.size() ?
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
                        final int posicaoFinalX = endFrame.listaDeDesenhosNesteFrame.get(figuraActualIndex).getPosXFrame(endIndexFrame);
                        final int posicaoFinalY = endFrame.listaDeDesenhosNesteFrame.get(figuraActualIndex).getPosYFrame(endIndexFrame);
                        
                        final Color cor = endFrame.listaDeDesenhosNesteFrame.get(figuraActualIndex).getCorFiguraFrame(endIndexFrame);
                        final int redFinal = cor.getRed();
                        final int greenFinal = cor.getGreen();
                        final int blueFinal = cor.getBlue();
                        
                        final int red = (percentagemDeDeslocamento * redFinal) / 100;
                        final int green = (percentagemDeDeslocamento * greenFinal) / 100;
                        final int blue = (percentagemDeDeslocamento * blueFinal) / 100;
                        
                        System.out.println("Ne color: " + new Color(red, green, blue));
                        final int novoPontoX = (percentagemDeDeslocamento * posicaoFinalX) / 100;
                        final int novoPontoY = (percentagemDeDeslocamento * posicaoFinalY) / 100;
                        
                        figuraActual.setNovoPonto(actualIndexFrame, new Point(novoPontoX, novoPontoY));
                        figuraActual.setCorFiguraFrame(actualIndexFrame, new Color(red, green, blue));
                        
                        frameActual.listaDeDesenhosNesteFrame.remove(figuraActualIndex);
                        frameActual.listaDeDesenhosNesteFrame.add(figuraActualIndex, figuraActual);
                    }
                    
                    representacaoVisualFrame.setEnabled(false);
                    representacaoVisualFrame.setBackground(new Color(30, 30, 30));
                }
            }
        }
        
        private void removeMoveAnimation() {
            if (removingAnimation == false) {
                final int startIndexFrame = inicioAnimacao;
                final int endIndexFrame = fimAnimacao;
                int actualIndexFrame;
                
                Frame startFrame = (Frame) timeline.listaDeFrames.get(startIndexFrame);
                Frame endFrame = (Frame) timeline.listaDeFrames.get(endIndexFrame);
                
                
                final int quantFormasGeometricas = endFrame.listaDeDesenhosNesteFrame.size() >= 
                        startFrame.listaDeDesenhosNesteFrame.size() ? startFrame.listaDeDesenhosNesteFrame.size() : 
                        endFrame.listaDeDesenhosNesteFrame.size();
                
                final ArrayList<FiguraGeometrica> listaDeFiguras = endFrame.listaDeDesenhosNesteFrame.size() >= 
                        startFrame.listaDeDesenhosNesteFrame.size() ?
                        startFrame.listaDeDesenhosNesteFrame : endFrame.listaDeDesenhosNesteFrame;
                
                
                for (actualIndexFrame = inicioAnimacao + 1; actualIndexFrame < endIndexFrame; actualIndexFrame++) {
                    final Frame frameActual = (Frame) timeline.getComponent(actualIndexFrame);
                    final JButton representacaoVisualFrame = (JButton) timeline.getComponent(actualIndexFrame);
                    
                    for (int figuraActualIndex = 0; figuraActualIndex < quantFormasGeometricas; figuraActualIndex++) {
                        if (frameActual.listaDeDesenhosNesteFrame.size() <= 0) {
                            frameActual.setListaDeDesenhosNesteFrame(listaDeFiguras);
                        }
                        
                        final FiguraGeometrica figuraActual = frameActual.listaDeDesenhosNesteFrame.get(figuraActualIndex);
                        
                        figuraActual.setNovoPonto(actualIndexFrame, 
                                new Point(startFrame.listaDeDesenhosNesteFrame.get(figuraActualIndex).getPosXFrame(startIndexFrame),
                                        startFrame.listaDeDesenhosNesteFrame.get(figuraActualIndex).getPosYFrame(startIndexFrame)));
                        
                        frameActual.listaDeDesenhosNesteFrame.remove(figuraActualIndex);
                        frameActual.listaDeDesenhosNesteFrame.add(figuraActualIndex, figuraActual);
                    }
                    
                    representacaoVisualFrame.setEnabled(true);
                    if (actualIndexFrame % 5 == 0) {
                        representacaoVisualFrame.setBackground(Color.decode("#41abc3"));
                    } else {
                        representacaoVisualFrame.setBackground(new Color(255, 255, 255));
                    }
                }
            }
        }
    }
   
    
    public class PainelDeDesenho extends JPanel implements MouseMotionListener, MouseListener, Runnable{
        int posXInicialMouse, posYInicialMouse, posXFinalMouse, posYFinalMouse;
        int formaParaSerDesenhada = 3;
        int sizeBorracha = 30;
        boolean redesenharTodosObjectos = false;
        Color corDoDesenho;
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
        Thread thread;
        ArrayList<Frame> listaDeFrames;
        ArrayList<FiguraGeometrica> apagadorLista = new ArrayList<>();
        private boolean running = false;
        private boolean maoSelecionada = false;
        FiguraGeometrica figuraASerArrastada;
        FiguraGeometrica novaFigura;
        Graphics2D g2d;
        
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
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g2d = (Graphics2D) g;
            AffineTransform afine = g2d.getTransform();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            timeline
                .listaDeFrames
                .get(actualFrameIndex % timeline.getListaDeFrames().size())
                .listaDeDesenhosNesteFrame.forEach((objecto) -> {
                    AffineTransform affineAux = g2d.getTransform();
                    desenharObjecto(objecto);
                    g2d.setTransform(affineAux);
                });
            
            apagadorLista.forEach((objecto) -> {
                AffineTransform affineAux = g2d.getTransform();
                desenharObjecto(objecto);
                g2d.setTransform(affineAux);
            });

            desenharNovaFigura();
            g2d.setTransform(afine);
        }
        
        public void desenharNovaFigura() {
            AffineTransform afine = g2d.getTransform();
            
            if (formaParaSerDesenhada == 4) {
               desenharObjecto(new FiguraGeometrica(posXInicialMouse, posYInicialMouse, 
                       posXFinalMouse, posYFinalMouse, corDeDesenho, formaParaSerDesenhada, actualFrameIndex));
            } else {
                int x, y, largura, altura;


                if (posXFinalMouse < posXInicialMouse) {
                   x = posXFinalMouse;
                   largura = posXInicialMouse - posXFinalMouse;
                } else {
                   x = posXInicialMouse;
                   largura = posXFinalMouse - posXInicialMouse;
                }

                if (posYFinalMouse < posYInicialMouse) {
                   y = posYFinalMouse;
                   altura = posYInicialMouse - posYFinalMouse;
                } else {
                   y = posYInicialMouse;
                   altura = posYFinalMouse - posYInicialMouse;
                }

                desenharObjecto(new FiguraGeometrica(x, y, largura, altura, 
                        corDeDesenho, formaParaSerDesenhada, actualFrameIndex));
            }
            g2d.setTransform(afine);
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
           if (maoSelecionada && baldeActivo == false) {
               if (figuraASerArrastada != null) {
                   if (figuraASerArrastada.getForma() != 4) {
                       figuraASerArrastada.setNovoPonto(actualFrameIndex, new Point(e.getPoint().x, e.getPoint().y));
                   } else {
                       int largura = figuraASerArrastada.getLargura() - figuraASerArrastada.getxPos();
                       int altura = figuraASerArrastada.getAltura() - figuraASerArrastada.getyPos();
                       
                       figuraASerArrastada.setNovoPonto(actualFrameIndex, new Point(e.getPoint().x, e.getPoint().y));
                       figuraASerArrastada.setLargura(figuraASerArrastada.getxPos() + largura);
                       figuraASerArrastada.setAltura(figuraASerArrastada.getyPos() + altura);
                   }
               }
           } else {
               if (formaParaSerDesenhada != 6) {
                   posXFinalMouse = e.getX();
                   posYFinalMouse = e.getY();
               } else if (formaParaSerDesenhada == 6) {
                   apagadorLista.add(new FiguraGeometrica(e.getX(), e.getY(),
                            sizeBorracha, sizeBorracha, new Color(255, 255, 255), formaParaSerDesenhada, actualFrameIndex));
               }
           }
           repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
                    
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (formaParaSerDesenhada != 6) {
                if (baldeActivo) {
                    FiguraGeometrica figuraGeometricaASerRecolorida;
                    Point pontoClicadoPeloMouse = e.getPoint();


                    figuraGeometricaASerRecolorida = timeline.getListaDeFrames()
                                                      .get(actualFrameIndex)
                                                      .getFiguraPosicao(pontoClicadoPeloMouse, actualFrameIndex);

                    if (figuraGeometricaASerRecolorida != null) {
                        figuraASerArrastada = figuraGeometricaASerRecolorida;
                        figuraGeometricaASerRecolorida.setCorFigura(corDeDesenho);
                        
                        if (timeline.getListaDeFrames().get(actualFrameIndex).isKeyFrame()) {
                            if (figuraASerArrastada.coresObjectoNosFrames.containsKey(actualFrameIndex)) {
                                figuraASerArrastada.coresObjectoNosFrames.replace(actualFrameIndex, corDeDesenho);
                            } else {
                               figuraASerArrastada.coresObjectoNosFrames.put(actualFrameIndex, corDeDesenho);
                            }
                        }
                        repaint();
                    } else {
                        figuraASerArrastada = null;
                        System.out.println("Nenhum elemento selecionado");
                    }  
                } else if (!maoSelecionada && baldeActivo == false) {
                    posXInicialMouse = e.getX();
                    posYInicialMouse = e.getY();
                } else {
                    FiguraGeometrica figuraGeometricaASerMovimentada = null;
                    Point pontoClicadoPeloMouse = e.getPoint();

                    figuraGeometricaASerMovimentada = timeline.getListaDeFrames()
                                                      .get(actualFrameIndex)
                                                      .getFiguraPosicao(pontoClicadoPeloMouse, actualFrameIndex);
                    if (figuraGeometricaASerMovimentada != null) {
                        figuraASerArrastada = figuraGeometricaASerMovimentada;
                    } else {
                        figuraASerArrastada = null;
                        System.out.println("Nenhum elemento selecionado");
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (formaParaSerDesenhada != 6) {
                if (!maoSelecionada && baldeActivo == false) {
                    posXFinalMouse = e.getX();
                    posYFinalMouse = e.getY();

                    int posXInicialDesenho = posXInicialMouse;
                    int posYInicialDesenho = posYInicialMouse;
                    int x, y, largura, altura;

                    if (posXFinalMouse < posXInicialMouse) {
                        x = posXFinalMouse;
                        largura = posXInicialMouse - posXFinalMouse;
                    } else {
                        x = posXInicialMouse;
                        largura = posXFinalMouse - posXInicialMouse;
                    }

                    if (posYFinalMouse < posYInicialMouse) {
                        y = posYFinalMouse;
                        altura = posYInicialMouse - posYFinalMouse;
                    } else {
                        y = posYInicialMouse;
                        altura = posYFinalMouse - posYInicialMouse;
                    }

                    if (formaParaSerDesenhada == 4) {
                        novaFigura = new FiguraGeometrica(x, y,
                                posXFinalMouse, posYFinalMouse,
                                corDoDesenho, formaParaSerDesenhada, actualFrameIndex);
                    } else {
                        novaFigura = new FiguraGeometrica(posXInicialDesenho, posYInicialDesenho,
                                largura, altura,
                                corDoDesenho, formaParaSerDesenhada, actualFrameIndex);
                    }
                    novaFigura.setNovoPonto(actualFrameIndex, new Point(x, y));
                    novaFigura.setCorFiguraFrame(actualFrameIndex, corDoDesenho);
                    
                    inserirNovaFigura(novaFigura);

                    timeline.listaDeFrames
                            .get(actualFrameIndex % timeline.getListaDeFrames().size())
                            .listaDeDesenhosNesteFrame.add(novaFigura);
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

        @SuppressWarnings("empty-statement")
        private void desenharObjecto(FiguraGeometrica objecto) {
            
            int x = objecto.getPosXFrame(actualFrameIndex);
            int y = objecto.getPosYFrame(actualFrameIndex);
            
            g2d.setColor(objecto.getCorFiguraFrame(actualFrameIndex));
            
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
                            objecto.getLargura(), objecto.getAltura());
                    break;
                case 5:
                    g2d.fillRect(x, y, 
                            objecto.getLargura(), objecto.getAltura());
                    break;
                case 6:
                    g2d.fillOval(x, y, 10, 10);
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
                    
                    if (actualFrameIndex >= 239) {
                        actualFrameIndex = 0;
                        thread.interrupt();
                        running = false;
                        thread.interrupt();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("");
            }
        }

        private void startAnimation() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
}
