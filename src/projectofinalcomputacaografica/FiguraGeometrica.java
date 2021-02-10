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
public class FiguraGeometrica {
    HashMap<Integer, Point> posicoesObjectoNosFrames = new HashMap<>();
    HashMap<Integer, Color> coresObjectoNosFrames = new HashMap<>();
    Point localizacaoObjecto;
    int altura, largura;
    int xPosFinal, yPosFinal;
    int frameDeCriacao;
    Color corFigura;
    int forma = 0;
    float grauRotacao;
    boolean rotacionando = false;
    Thread thread;
    
    public FiguraGeometrica(int xPos, int yPos, int largura, int altura, Color corFigura, int forma, int frameDeCriacao) {
        this.altura = altura;
        this.largura = largura;
        this.corFigura = corFigura;
        this.forma = forma;
        this.frameDeCriacao = frameDeCriacao;
        this.localizacaoObjecto = new Point(xPos, yPos);
        this.posicoesObjectoNosFrames.put(frameDeCriacao, localizacaoObjecto);
        this.coresObjectoNosFrames.put(frameDeCriacao, corFigura);
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

    
    public int getPosXFrame(int frame) {
        return this.posicoesObjectoNosFrames.get(frame) != null ? 
                    (int)this.posicoesObjectoNosFrames.get(frame).getX() : this.getxPos();
    }
    
    public int getPosYFrame(int frame) {
        return this.posicoesObjectoNosFrames.get(frame) != null ? 
                    (int)this.posicoesObjectoNosFrames.get(frame).getY() : this.getyPos();
    }
    
    public void setCorFiguraFrame(int frame, Color cor) {
        if (posicoesObjectoNosFrames.containsKey(frame)) {
            coresObjectoNosFrames.replace(frame, cor);
        } else {
            coresObjectoNosFrames.put(frame, cor);
        }
    }
    
    public Color getCorFiguraFrame(int frame) {
        return this.coresObjectoNosFrames.get(frame) != null ?
               this.coresObjectoNosFrames.get(frame) : this.getCorFigura();
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
        
}
