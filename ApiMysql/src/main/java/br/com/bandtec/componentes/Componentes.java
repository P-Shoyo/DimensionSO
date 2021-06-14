/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bandtec.componentes;

/**
 *
 * @author thall
 */
public class Componentes {
    private Integer idComponente;
    private String processadorComponente;
    private String memoriaRam;
    private String so;
    private String placaVideo;

    public Componentes() {
        this.idComponente = idComponente;
        this.processadorComponente = processadorComponente;
        this.memoriaRam = memoriaRam;
        this.so = so;
        this.placaVideo = placaVideo;
    }

    
    
    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getProcessadorComponente() {
        return processadorComponente;
    }

    public void setProcessadorComponente(String processadorComponente) {
        this.processadorComponente = processadorComponente;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getPlacaVideo() {
        return placaVideo;
    }

    public void setPlacaVideo(String placaVideo) {
        this.placaVideo = placaVideo;
    }
    
}
