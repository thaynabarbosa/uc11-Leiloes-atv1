/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES(?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.execute();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar o cadastro!");
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT id, nome, valor, status FROM produtos";

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            listagem = new ArrayList<>();

            while (resultset.next()) {
                ProdutosDTO prod = new ProdutosDTO();

                prod.setId(resultset.getInt("id"));
                prod.setNome(resultset.getString("nome"));
                prod.setValor(resultset.getInt("valor"));
                prod.setStatus(resultset.getString("status"));

                listagem.add(prod);
            }
            return listagem;
        } catch (Exception e) {
            return null;
        }

    }

    public void venderProduto(String id) {
        conn = new conectaDAO().connectDB();
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, id);
            prep.execute();
            JOptionPane.showMessageDialog(null, "Produto Vendido!");
        } catch (Exception e) {
            System.out.println("Erro ao editar status do produto: " + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido';";
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            listagem = new ArrayList<>();

            while (resultset.next()) {
                ProdutosDTO prod = new ProdutosDTO();

                prod.setId(resultset.getInt("id"));
                prod.setNome(resultset.getString("nome"));
                prod.setValor(resultset.getInt("valor"));
                prod.setStatus(resultset.getString("status"));

                listagem.add(prod);
            }
            return listagem;
        } catch (Exception e) {
            return null;
        }
    }
}
