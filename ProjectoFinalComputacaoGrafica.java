package projectofinalcomputacaografica;

import javax.swing.JFrame;

/**
* @author ed
*/
public class ProjectoFinalComputacaoGrafica {
    public static void main(String[] args) {
        JanelaPrincipal janelaPrincipal = new JanelaPrincipal();
        
        janelaPrincipal.setSize(1200, 600);
        janelaPrincipal.setVisible(true);
        janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaPrincipal.setResizable(false);
        janelaPrincipal.setLocationRelativeTo(null);
        
        
    }
}
