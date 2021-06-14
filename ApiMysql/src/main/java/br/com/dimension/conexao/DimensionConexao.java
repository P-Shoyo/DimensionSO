package br.com.dimension.conexao;
import br.com.dimension.insercao.Log;
import java.sql.*;

public class DimensionConexao {
        private static final String url = "jdbc:sqlserver://dimension.database.windows.net:1433;database=Dimension;user=dimension@dimension;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        private static final String username = "dimension";
        private static final String password = "#Gfgrupo10";
        
        public static Connection createConnectionToSQL() throws Exception {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conectado ao Banco de dados Azure");

            return connection;

    }
        
        public static Connection createConnectionToMySQL() throws Exception {
            Log log = new Log();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                String url = "jdbc:mysql://172.17.0.1:3306/dimensionBD";
                Connection con = DriverManager.getConnection(url, "root", "urubu100");
                System.out.println("Conectado ao Banco de dados MySQL");
                return con;
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e);
                System.out.println("Erro ao conectar ao MySQL!");
                log.criarLog("_ConexaoMYSQL","Alto", e);
            }      
            return null;
        }

    public static void main(String[] args) throws Exception {
        Connection con= createConnectionToSQL();
        
        DimensionConexao conn = new DimensionConexao();
        Connection connection = conn.createConnectionToMySQL();
        Log log = new Log();

        if (con!=null){
            try {
                System.out.println("Conectado com sucesso");
                con.close();
            } catch (Exception e) {
                System.out.println("Erro ao conectar na Azure!");
                log.criarLog("_ConexaoAzure","Alto", e);
            }
        }
        
        if (connection!=null){
            try {
                System.out.println("Conectado com sucesso ao MySQL");
                con.close();
            } catch (Exception e) {
                System.out.println("Erro ao conectar ao MySQL!");
                log.criarLog("_ConexaoMYSQL","Alto", e);
            }
        }
        
    }
}
