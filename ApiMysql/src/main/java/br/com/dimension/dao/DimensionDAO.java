package br.com.dimension.dao;

import br.com.bandtec.componentes.Componentes;
import br.com.bandtec.dimension2.Login;
import br.com.dimension.conexao.DimensionConexao;
import br.com.dimension.insercao.Insercao;
import br.com.dimension.insercao.Log;
import br.com.dimension.usuario.Usuario;
import com.github.britooo.looca.api.core.Looca;
import com.sun.jdi.IntegerValue;
import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mensageria.AppSlack;


public class DimensionDAO {
    
    public void updateComponente(Componentes componente) throws IOException{
    String sql = "UPDATE componentes SET processador = ?, memoriaRam = ?, placaVideo = ?, "
            + "so = ? WHERE idComponente = ?";
    Connection conn = null;
    PreparedStatement pstm = null;
    Log log = new Log();
    try{
        conn = DimensionConexao.createConnectionToSQL();
        System.out.println("Componentes atualizados com sucesso!");
        pstm = (PreparedStatement) conn.prepareStatement(sql);
        
        pstm.setString(1, componente.getProcessadorComponente());
        pstm.setString(2, componente.getMemoriaRam());
        pstm.setString(3, componente.getPlacaVideo());
        pstm.setString(4, componente.getSo());
        pstm.setInt(5, componente.getIdComponente());
        
        pstm.execute();
    }
    catch (Exception e){
        System.out.println("Erro ao atualizar componente!");
        log.criarLog("_atualizarComp","Alto", e);
        e.printStackTrace();
    }
    finally{
        try{
            if(pstm!=null){
                pstm.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    }
    
    public List<Usuario> pegarUsuario() throws IOException{
        String sql = "Select * from funcionario;";
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        Log log = new Log();
        
        try{
            conn = DimensionConexao.createConnectionToSQL();
            System.out.println("Select feito com sucesso!");
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            resultSet = pstm.executeQuery();
            System.out.println("Select feito com sucesso!");
            while(resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getInt("idFuncionario"));
                usuario.setUsuario(resultSet.getString("loginFuncionario"));
                usuario.setSenha(resultSet.getString("senhaFuncionario"));

                usuarios.add(usuario);
            }
        }
        
        catch (Exception e){
            System.out.println("Erro no Select!");
            log.criarLog("_BD", "Alto", e);
            e.printStackTrace();
            
        }finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return usuarios;
        
    }
    
    public List<Componentes> pegarComponente(Integer idFuncionario, String email, String senha) throws IOException{
        String sql = "select * from maquina inner join funcionario on "
                + "idFuncionario = fkFuncionario where idFuncionario = ?";
        List<Componentes> componentes = new ArrayList<Componentes>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        Log log = new Log();
        
        try{
            conn = DimensionConexao.createConnectionToSQL();
            System.out.println("Select feito com sucesso!");
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setInt(1, idFuncionario);
            resultSet = pstm.executeQuery();
            System.out.println("Select feito com sucesso!");
           
            if (resultSet.next()) {
            Componentes componente = new Componentes();
            componente.setFkMaquina(resultSet.getInt("idMaquina"));
            componentes.add(componente);            
            
            }
            else{
                Looca looca = new Looca();
                                Componentes componenteNovo = new Componentes();
                                componenteNovo.setProcessadorComponente(looca.getProcessador().getNome().substring(10));
                                componenteNovo.setFkMaquina(idFuncionario);
                                componenteNovo.setMemoriaRam(String.valueOf(looca.getMemoria().getTotal()));
                                componenteNovo.setPlacaVideo("GTX 1080");
                                componenteNovo.setSo(looca.getSistema().getSistemaOperacional());
                                componentes.add(componenteNovo);
        DimensionDAO dimensionDAO = new DimensionDAO();
            AppSlack appSlack = new AppSlack();

        try {
            dimensionDAO.inserirBdMaquina(componenteNovo);
            Login login = new Login();
            login.autenticarDados(email, senha);

        } catch (Exception a) {
            System.out.println("Erro ao inserir!");
            log.criarLog("_CapturaDados","Alto", a);
        }
            }
            }
        
        catch (Exception e){
            e.printStackTrace();
            
        }finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return componentes;
    }

    public void inserirBD (Insercao insercao) throws IOException{
        String sql = "INSERT INTO registro(nomeComponente, data,dadosColetados,fkMaquina) VALUES (?, ?, ?, ?) ";
        Connection conn = null;
        PreparedStatement pstm = null;
        Log log = new Log();
        try{
            conn = DimensionConexao.createConnectionToSQL();
            System.out.println("Select feito com sucesso!");
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, insercao.getNomeComponente());
            pstm.setString(2, insercao.getData());
            pstm.setInt(3, (int) Math.round(insercao.getDadosColetados()));
            pstm.setInt(4, insercao.getFkMaquina());
            pstm.execute();

        } catch (Exception e) {
            System.out.println("Erro no Select!");
            log.criarLog("_BD", "Alto", e);
            e.printStackTrace();
        }
        finally {
            try{
                if (pstm!=null){
                    pstm.close();
                }
                if (conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void inserirBdUsuario (Usuario usuario) throws IOException{
        String sql = "INSERT INTO funcionario(nomeFuncionario, sobrenomeFuncionario,loginFuncionario, senhaFuncionario, cpfFuncionario) VALUES (?, ?, ?, ?, ?) ";
        Connection conn = null;
        PreparedStatement pstm = null;
        Log log = new Log();
        try{
            conn = DimensionConexao.createConnectionToSQL();
            System.out.println("Select feito com sucesso!");
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, usuario.getNomeUsuario());
            pstm.setString(2, usuario.getSobrenomeUsuario());
            pstm.setString(3, usuario.getUsuario());
            pstm.setString(4, usuario.getSenha());
            pstm.setString(5, usuario.getCpfUsuario());
            pstm.execute();

        } catch (Exception e) {
            System.out.println("Erro no Select!");
            log.criarLog("_BD", "Alto", e);
            e.printStackTrace();
        }
        finally {
            try{
                if (pstm!=null){
                    pstm.close();
                }
                if (conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }

    public void inserirBdMaquina (Componentes componentes) throws IOException{
        String sql = "INSERT INTO maquina(processador, memoriaRam, placaVideo, so, fkFuncionario) VALUES (?, ?, ?, ?, ?) ";
        Connection conn = null;
        PreparedStatement pstm = null;
        Log log = new Log();
        try{
            conn = DimensionConexao.createConnectionToSQL();
            System.out.println("Select feito com sucesso!");
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, componentes.getProcessadorComponente());
            pstm.setString(2, componentes.getMemoriaRam());
            pstm.setString(3, componentes.getPlacaVideo());
            pstm.setString(4, componentes.getSo());
            pstm.setInt(5, componentes.getFkMaquina());
            pstm.execute();

        } catch (Exception e) {
            System.out.println("Erro no Select!");
            log.criarLog("_BD", "Alto", e);
            e.printStackTrace();
        }
        finally {
            try{
                if (pstm!=null){
                    pstm.close();
                }
                if (conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    
    public void inserirMysqlBD (Insercao insercao) throws IOException{
        String sql = "INSERT INTO registro(nomeComponente, data, dadosColetados) VALUES (?, ?, ?) ";
        Connection mysql = null;
        PreparedStatement psmysql = null;
        Log log = new Log();
        try{
            
            mysql = DimensionConexao.createConnectionToMySQL();
            System.out.println("Select feito com sucesso!");
            psmysql = (PreparedStatement) mysql.prepareStatement(sql);
            psmysql.setString(1, insercao.getNomeComponente());
            psmysql.setString(2, insercao.getData());
            psmysql.setDouble(3, insercao.getDadosColetados());
            psmysql.execute();

        } catch (Exception e) {
            System.out.println("Erro no Select!");
            log.criarLog("_BD", "Alto", e);
            e.printStackTrace();
        }
        finally {
            try{
                if (psmysql!=null){
                    psmysql.close();
                }               
                if (mysql!=null){
                    mysql.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
