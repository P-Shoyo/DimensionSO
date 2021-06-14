package br.com.dimension.insercao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Log {
  public String criarNome(){
  
      DateTimeFormatter dataHora = DateTimeFormatter.ofPattern("yyyyMMdd_hhmm");
      return dataHora.format(LocalDateTime.now());
  }  
  
  public String dataHora(){
   DateTimeFormatter dataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
   return dataHora.format(LocalDateTime.now());
      
  }
  
  public void criarLog(String nome,String categoria, Exception erro) throws IOException{
      
      FileWriter arq = new FileWriter("C:\\Users\\Public\\Documents\\"+criarNome()+nome+".txt");
        PrintWriter logArq = new PrintWriter(arq);
        logArq.write(categoria+"--------"+dataHora()+"\n\n"+erro);
  
       arq.close();
  }      
  
}
