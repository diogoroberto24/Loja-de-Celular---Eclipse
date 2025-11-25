package br.edu.ifpr.lojadecelularessqlite.gui;

import br.edu.ifpr.lojadecelularessqlite.database.DatabaseHelper;
import br.edu.ifpr.lojadecelularessqlite.models.Aparelho;
import br.edu.ifpr.lojadecelularessqlite.models.Compra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ComprasFrame extends JFrame {
    private DatabaseHelper dbHelper;
    private JTable tableCompras;
    private DefaultTableModel tableModel;
    private JButton btnNova, btnExcluir, btnVoltar;

    public ComprasFrame() {
        super("Gerenciar Compras");
        this.dbHelper = new DatabaseHelper();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        carregarCompras();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        // Criar tabela
        String[] colunas = {"ID", "Cliente", "Aparelho", "Quantidade", "Data"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableCompras = new JTable(tableModel);
        tableCompras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableCompras.getTableHeader().setReorderingAllowed(false);
        
        // Criar botões
        btnNova = new JButton("Registrar Compra");
        btnExcluir = new JButton("Excluir");
        btnVoltar = new JButton("Voltar ao Menu");
        
        // Dimensões
        Dimension buttonSize = new Dimension(150, 30);
        btnNova.setPreferredSize(buttonSize);
        btnExcluir.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Painel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel lblTitulo = new JLabel("GERENCIAR COMPRAS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        
        // Painel central com tabela
        JScrollPane scrollPane = new JScrollPane(tableCompras);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Compras"));
        
        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotoes.add(btnNova);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(Box.createHorizontalStrut(50));
        panelBotoes.add(btnVoltar);
        
        // Adicionar componentes
        add(panelTitulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        btnNova.addActionListener(e -> abrirFormCompra());
        
        btnExcluir.addActionListener(e -> {
            int selectedRow = tableCompras.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String cliente = (String) tableModel.getValueAt(selectedRow, 1);
                
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Confirma a exclusão da compra do cliente: " + cliente + "?", 
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (dbHelper.excluirCompra(id)) {
                        JOptionPane.showMessageDialog(this, "Compra excluída com sucesso!");
                        carregarCompras();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir compra!", 
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma compra para excluir!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarCompras() {
        tableModel.setRowCount(0);
        List<Compra> compras = dbHelper.listarCompras();
        
        for (Compra compra : compras) {
            // Buscar informações do aparelho
            Aparelho aparelho = dbHelper.buscarAparelho(compra.getIdAparelho());
            String descricaoAparelho = aparelho != null ? 
                aparelho.getModelo() + " - " + aparelho.getMarca() : 
                "Aparelho não encontrado";
            
            Object[] row = {
                compra.getId(),
                compra.getCliente(),
                descricaoAparelho,
                compra.getQuantidade(),
                compra.getData()
            };
            tableModel.addRow(row);
        }
    }

    private void abrirFormCompra() {
        FormCompraFrame formFrame = new FormCompraFrame(-1, this);
        formFrame.setVisible(true);
        
        // Atualizar lista quando o form for fechado
        formFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarCompras();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComprasFrame());
    }
}