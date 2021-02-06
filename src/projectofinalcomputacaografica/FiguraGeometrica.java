package projectofinalcomputacaografica;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @Autor ed
 * Free Use - Livre_Uso
 */
public class FiguraGeometrica extends JPanel implements Runnable, MouseListener {
    HashMap<Integer, Point> posicoesObjectoNosFrames = new HashMap<>(); 
    Point localizacaoObjecto;
    int altura, largura;
    int xPosFinal, yPosFinal;
    int frameDeCriacao;
    Color corFigura;
    int forma = 0;
    float grauRotacao;
    boolean rotacionando = false;
    Thread thread;
    
    public FiguraGeometrica(int xPos, int yPos, int altura, int largura, Color corFigura, int forma, int frameDeCriacao) {
        this.altura = altura;
        this.largura = largura;
        this.corFigura = corFigura;
        this.forma = forma;
        this.frameDeCriacao = frameDeCriacao;
        this.localizacaoObjecto = new Point(xPos, yPos);
        this.posicoesObjectoNosFrames.put(frameDeCriacao, localizacaoObjecto);
    }

    public int getxPos() {
        return (int) this.localizacaoObjecto.getX();
    }

    public void setxPos(int xPos) {
        this.localizacaoObjecto.x = xPos;
    }

    public int getyPos() {
        return (int) this.localizacaoObjecto.getY();
    }

    public void setyPos(int yPos) {
        this.localizacaoObjecto.y = yPos;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public Color getCorFigura() {
        return corFigura;
    }

    public void setCorFigura(Color corFigura) {
        this.corFigura = corFigura;
    }

    public int getForma() {
        return forma;
    }

    public void setForma(int forma) {
        this.forma = forma;
    }
    
    public int getCenterX(){
        return (this.getxPos() + (int)(this.getLargura() / 2));
    }
    
    public int getCenterY(){
        return (this.getyPos() + (int)(this.getAltura() / 2));
    }

    public float getGrauRotacao() {
        return grauRotacao;
    }

    public void setGrauRotacao(int grauRotacao) {
        this.grauRotacao = grauRotacao;
    }

    public boolean isRotacionando() {
        return rotacionando;
    }

    public void setRotacionando(boolean rotacionando) {
        if (rotacionando) {
            thread = new Thread(this);
            thread.start();
        }
        this.rotacionando = rotacionando;
    }
    
    public Point getPontosFigura() {
        return this.posicoesObjectoNosFrames.get(this.frameDeCriacao);
    }

    public HashMap<Integer, Point> getPosicoesObjectoNosFrames() {
        return posicoesObjectoNosFrames;
    }

    public void setPosicoesObjectoNosFrames(HashMap<Integer, Point> posicoesObjectoNosFrames) {
        this.posicoesObjectoNosFrames = posicoesObjectoNosFrames;
    }

    public Point getLocalizacaoObjecto() {
        return localizacaoObjecto;
    }

    public void setLocalizacaoObjecto(Point localizacaoObjecto) {
        this.localizacaoObjecto = localizacaoObjecto;
    }
    
    public void setNovoPonto(int frameIndex, Point novoPonto) {
        if (posicoesObjectoNosFrames.containsKey(frameIndex)) {
            posicoesObjectoNosFrames.replace(frameIndex, novoPonto);
        } else {
            posicoesObjectoNosFrames.put(frameIndex, novoPonto);
        }
    }
    
    

    @Override
    public void run() {
        try {
            while(true) {
                repaint();
                grauRotacao += 0.1;
                Thread.sleep(1000/24);
            }
        } catch (Exception e) {
            System.out.println("Ferrou  ");
        }
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Rotacionando: " + this.forma);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
