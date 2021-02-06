package projectofinalcomputacaografica;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @Autor ed
 * Free Use - Livre_Uso
 */
public class FiguraGeometrica extends JPanel implements Runnable {
    int xPos, yPos, altura, largura;
    Color corFigura;
    int forma = 0;
    float grauRotacao;
    boolean rotacionando = false;
    Thread thread;
    
    public FiguraGeometrica(int xPos, int yPos, int altura, int largura, Color corFigura, int forma) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.altura = altura;
        this.largura = largura;
        this.corFigura = corFigura;
        this.forma = forma;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
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
    
}
