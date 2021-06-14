package br.com.dimension.maquina;
import br.com.dimension.dao.DimensionDAO;
import br.com.dimension.insercao.Insercao;
import br.com.dimension.insercao.Log;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.processos.ProcessosGroup;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.components.Gpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import com.sun.jdi.DoubleValue;
import java.io.IOException;

import java.util.Date;
import java.util.List;
import mensageria.ConexaoSlack;

public class Maquina {
    
    ConexaoSlack conexao = new ConexaoSlack();
    
    public void placaVideo() throws IOException{
        Insercao insercao = new Insercao();
        Components componenteGpu = JSensors.get.components();
        List<Gpu> gpus = componenteGpu.gpus;  
        for (final Gpu gpu : gpus) {
            if (gpu.sensors != null) {
              //Print temperatures
              List<Temperature> temps = gpu.sensors.temperatures;
              for (final Temperature temp : temps) {
                   insercao.setDadosColetados(temp.value);
              };
            insercao.setNomeComponente("Placa de video");
            insercao.setData(new Date());
            DimensionDAO dimensaoDAO = new DimensionDAO();
            Log log = new Log();
            try {
                dimensaoDAO.inserirBD(insercao);
                System.out.println("Inserido com Sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao inserir!");
                log.criarLog("_CapturaDados","Alto", e);
            }
        
    }}}

    public  void memoria() throws IOException{
        Insercao insercao = new Insercao();
        Looca looca= new Looca();
        insercao.setNomeComponente("Memoria RAM");
        insercao.setDadosColetados(((100*Double.valueOf(looca.getMemoria().getEmUso()/1000000000))/Double.valueOf((looca.getMemoria().getTotal()/1000000000))));
        insercao.setData(new Date());
        DimensionDAO dimensionDAO = new DimensionDAO();
        Log log = new Log();
        
        try {
            dimensionDAO.inserirBD(insercao);
            dimensionDAO.inserirMysqlBD(insercao);
            System.out.println("Inserido com Sucesso!");
            
        } catch (Exception e) {
            System.out.println("Erro ao inserir!");
            log.criarLog("_CapturaDados","Alto", e);
        }
        
    }
    
    public  void processador() throws IOException{
        Insercao insercao = new Insercao();
        Looca looca= new Looca();
        insercao.setNomeComponente("Processador");
        insercao.setDadosColetados((100*looca.getProcessador().getUso())/((looca.getProcessador().getFrequencia()/1000000000)*looca.getProcessador().getNumeroCpusLogicas()));
        insercao.setData(new Date());
        DimensionDAO dimensionDAO = new DimensionDAO();
        Log log = new Log();
        
        try {
            dimensionDAO.inserirBD(insercao);
            dimensionDAO.inserirMysqlBD(insercao);
            System.out.println("Inserido com Sucesso!");
            
        } catch (Exception e) {
            System.out.println("Erro ao inserir!");
            log.criarLog("_CapturaDados","Alto", e);
            
        }
    }
    
    public void sistema() throws IOException{
        Insercao insercao = new Insercao();
        Looca looca= new Looca();
        insercao.setNomeComponente("SO");
        insercao.setDadosColetados(Double.valueOf(looca.getSistema().getTempoDeAtividade()/86400));
        insercao.setData(new Date());
        DimensionDAO dimensionDAO = new DimensionDAO();
        Log log = new Log();
        
        try {
            dimensionDAO.inserirBD(insercao);
            dimensionDAO.inserirMysqlBD(insercao);
            System.out.println("Inserido com Sucesso!");
            
        } catch (Exception e) {
            System.out.println("Erro ao inserir!");
            log.criarLog("_CapturaDados","Alto", e);
        }
    }
    
    public void processos() throws IOException{
        Insercao insercao = new Insercao();
        Looca looca= new Looca();
        DimensionDAO dimensionDAO = new DimensionDAO();
        ProcessosGroup grupoDeProcessos = looca.getGrupoDeProcessos();
        List<Processo> processos = grupoDeProcessos.getProcessos();
        for (Processo processo: processos){
            if (processo.getUsoMemoria()>1 || processo.getUsoCpu()>1){
                insercao.setDadosColetados((100*processo.getUsoCpu())/4);
                insercao.setNomeComponente(String.valueOf(processo.getNome()));
                insercao.setData(new Date());
                Log log = new Log();
        
                try {
                    dimensionDAO.inserirBD(insercao);
                    dimensionDAO.inserirMysqlBD(insercao);
                    System.out.println("Inserido com Sucesso!");

                } catch (Exception e) {
                    System.out.println("Erro ao inserir!");
                    log.criarLog("_CapturaDados","Alto", e);
                }
            }

                insercao.setDadosColetados((100*processo.getUsoMemoria())/16);
                insercao.setNomeComponente(String.valueOf(processo.getNome()));
                insercao.setData(new Date());
                Log log = new Log();
        
                try {
                    dimensionDAO.inserirBD(insercao);
                    dimensionDAO.inserirMysqlBD(insercao);
                    System.out.println("Inserido com Sucesso!");

                } catch (Exception e) {
                    System.out.println("Erro ao inserir!");
                    log.criarLog("_CapturaDados","Alto", e);
                }            
            }
            for (Processo processo : processos) {
                if (processo.getUsoCpu() > 95.0) {
                    conexao.mensagem(processo);
                } else if (processo.getUsoMemoria() > 85.0) {
                       conexao.mensagem(processo); 
                    }
        }

    }
}

