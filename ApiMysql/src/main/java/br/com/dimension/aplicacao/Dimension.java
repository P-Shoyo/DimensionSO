package br.com.dimension.aplicacao;

import br.com.bandtec.componentes.Componentes;
import br.com.bandtec.dimension2.Login;
import br.com.dimension.dao.DimensionDAO;
import br.com.dimension.maquina.Maquina;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dimension {

    public static void main(String[] args) {

//        Login login = new Login();
//        login.setVisible(true);
        Maquina maquina = new Maquina();
        Timer timer = new Timer();

        final long intervalo = (1000 * 5);

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                try {
                    maquina.memoria(2);
                } catch (IOException ex) {
                    Logger.getLogger(Dimension.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    maquina.placaVideo(2);
                } catch (IOException ex) {
                    Logger.getLogger(Dimension.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    maquina.processador(2);
                } catch (IOException ex) {
                    Logger.getLogger(Dimension.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    maquina.disco(2);
                } catch (IOException ex) {
                    Logger.getLogger(Dimension.class.getName()).log(Level.SEVERE, null, ex);
                }
//                                    try {
//                                        maquina.sistema(componentes.getFkMaquina());
//                                    } catch (IOException ex) {
//                                        Logger.getLogger(Dimension.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                    try {
//                                        maquina.processos(componentes.getFkMaquina());
//
////                System.exit(0); //To change body of generated methods, choose Tools | Templates.
//                                    } catch (IOException ex) {
//                                        Logger.getLogger(Dimension.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
            }
        };
        timer.scheduleAtFixedRate(tarefa, 0, intervalo);
    }
}

