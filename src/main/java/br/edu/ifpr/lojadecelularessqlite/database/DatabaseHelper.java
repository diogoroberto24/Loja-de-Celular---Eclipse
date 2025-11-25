package br.edu.ifpr.lojadecelularessqlite.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.edu.ifpr.lojadecelularessqlite.models.Aparelho;
import br.edu.ifpr.lojadecelularessqlite.models.Compra;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:loja_celulares.db";
    
    private static final String TABELA_APARELHOS = "aparelhos";
    private static final String TABELA_COMPRAS = "compras";
    
    private Connection connection;
    
    public DatabaseHelper() {
        criarTabelas();
    }
    
    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void criarTabelas() {
        String sqlAparelhos = "CREATE TABLE IF NOT EXISTS " + TABELA_APARELHOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "modelo TEXT NOT NULL, " +
                "marca TEXT NOT NULL, " +
                "preco REAL NOT NULL, " +
                "estoque INTEGER NOT NULL" +
                ");";
                
        String sqlCompras = "CREATE TABLE IF NOT EXISTS " + TABELA_COMPRAS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_aparelho INTEGER NOT NULL, " +
                "cliente TEXT NOT NULL, " +
                "quantidade INTEGER NOT NULL, " +
                "data_compra TEXT NOT NULL, " +
                "FOREIGN KEY(id_aparelho) REFERENCES " + TABELA_APARELHOS + "(id)" +
                ");";
        
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(sqlAparelhos);
            stmt.execute(sqlCompras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Métodos para Aparelhos
    public long inserirAparelho(Aparelho aparelho) {
        String sql = "INSERT INTO " + TABELA_APARELHOS + " (modelo, marca, preco, estoque) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, aparelho.getModelo());
            pstmt.setString(2, aparelho.getMarca());
            pstmt.setDouble(3, aparelho.getPreco());
            pstmt.setInt(4, aparelho.getEstoque());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public List<Aparelho> listarAparelhos() {
        List<Aparelho> aparelhos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_APARELHOS + " ORDER BY id DESC";
        
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Aparelho aparelho = new Aparelho();
                aparelho.setId(rs.getInt("id"));
                aparelho.setModelo(rs.getString("modelo"));
                aparelho.setMarca(rs.getString("marca"));
                aparelho.setPreco(rs.getDouble("preco"));
                aparelho.setEstoque(rs.getInt("estoque"));
                aparelhos.add(aparelho);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return aparelhos;
    }
    
    public Aparelho buscarAparelho(int id) {
        String sql = "SELECT * FROM " + TABELA_APARELHOS + " WHERE id = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Aparelho aparelho = new Aparelho();
                    aparelho.setId(rs.getInt("id"));
                    aparelho.setModelo(rs.getString("modelo"));
                    aparelho.setMarca(rs.getString("marca"));
                    aparelho.setPreco(rs.getDouble("preco"));
                    aparelho.setEstoque(rs.getInt("estoque"));
                    return aparelho;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean atualizarAparelho(Aparelho aparelho) {
        String sql = "UPDATE " + TABELA_APARELHOS + " SET modelo = ?, marca = ?, preco = ?, estoque = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, aparelho.getModelo());
            pstmt.setString(2, aparelho.getMarca());
            pstmt.setDouble(3, aparelho.getPreco());
            pstmt.setInt(4, aparelho.getEstoque());
            pstmt.setInt(5, aparelho.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean excluirAparelho(int id) {
        String sql = "DELETE FROM " + TABELA_APARELHOS + " WHERE id = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean atualizarEstoque(int idAparelho, int novoEstoque) {
        String sql = "UPDATE " + TABELA_APARELHOS + " SET estoque = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, novoEstoque);
            pstmt.setInt(2, idAparelho);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Métodos para Compras
    public long inserirCompra(Compra compra) {
        String sql = "INSERT INTO " + TABELA_COMPRAS + " (id_aparelho, cliente, quantidade, data_compra) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, compra.getIdAparelho());
            pstmt.setString(2, compra.getCliente());
            pstmt.setInt(3, compra.getQuantidade());
            pstmt.setString(4, compra.getData());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public List<Compra> listarCompras() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.*, a.modelo, a.marca FROM " + TABELA_COMPRAS + " c " +
                    "JOIN " + TABELA_APARELHOS + " a ON c.id_aparelho = a.id " +
                    "ORDER BY c.id DESC";
        
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Compra compra = new Compra();
                compra.setId(rs.getInt("id"));
                compra.setIdAparelho(rs.getInt("id_aparelho"));
                compra.setCliente(rs.getString("cliente"));
                compra.setQuantidade(rs.getInt("quantidade"));
                compra.setData(rs.getString("data_compra"));
                compras.add(compra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return compras;
    }
    
    public boolean excluirCompra(int idCompra) {
        String sql = "DELETE FROM " + TABELA_COMPRAS + " WHERE id = ?";
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idCompra);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}