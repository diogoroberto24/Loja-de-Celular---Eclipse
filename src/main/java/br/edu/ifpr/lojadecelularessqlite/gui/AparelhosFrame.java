package br.edu.ifpr.lojadecelularessqlite.gui;

import br.edu.ifpr.lojadecelularessqlite.database.DatabaseHelper;
import br.edu.ifpr.lojadecelularessqlite.models.Aparelho;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AparelhosFrame extends JFrame {
    private DatabaseHelper dbHelper;
    private JTable tableAparelhos;
    private DefaultTableModel tableModel;
    private JButton btnNovo, btnEditar, btnExcluir, btnVoltar;

    public AparelhosFrame() {
        super("Gerenciar Aparelhos");
        this.dbHelper = new DatabaseHelper();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        carregarAparelhos();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        // Criar tabela
        String[] colunas = {"ID", "Modelo", "Marca", "Preço", "Estoque"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableAparelhos = new JTable(tableModel);
        tableAparelhos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAparelhos.getTableHeader().setReorderingAllowed(false);
        
        // Criar botões
        btnNovo = new JButton("Novo Aparelho");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnVoltar = new JButton("Voltar ao Menu");
        
        // Estilizar botões
        Dimension buttonSize = new Dimension(150, 30);
        btnNovo.setPreferredSize(buttonSize);
        btnEditar.setPreferredSize(buttonSize);
        btnExcluir.setPreferredSize(buttonSize);
        btnVoltar.setPreferredSize(buttonSize);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Painel superior com título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel lblTitulo = new JLabel("GERENCIAR APARELHOS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        
        // Painel central com tabela
        JScrollPane scrollPane = new JScrollPane(tableAparelhos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Aparelhos"));
        
        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotoes.add(btnNovo);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(Box.createHorizontalStrut(50));
        panelBotoes.add(btnVoltar);
        
        // Adicionar componentes ao frame
        add(panelTitulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        btnNovo.addActionListener(e -> abrirFormAparelho());
        
        btnEditar.addActionListener(e -> {
            int selectedRow = tableAparelhos.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                abrirFormAparelho(id);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um aparelho para editar!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnExcluir.addActionListener(e -> {
            int selectedRow = tableAparelhos.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String modelo = (String) tableModel.getValueAt(selectedRow, 1);
                
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Confirma a exclusão do aparelho: " + modelo + "?", 
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (dbHelper.excluirAparelho(id)) {
                        JOptionPane.showMessageDialog(this, "Aparelho excluído com sucesso!");
                        carregarAparelhos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir aparelho!", 
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um aparelho para excluir!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnVoltar.addActionListener(e -> dispose());
        
        // Duplo clique na tabela para editar
        tableAparelhos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selectedRow = tableAparelhos.getSelectedRow();
                    if (selectedRow >= 0) {
                        int id = (int) tableModel.getValueAt(selectedRow, 0);
                        abrirFormAparelho(id);
                    }
                }
            }
        });
    }

    private void carregarAparelhos() {
        tableModel.setRowCount(0);
        List<Aparelho> aparelhos = dbHelper.listarAparelhos();
        
        for (Aparelho aparelho : aparelhos) {
            Object[] row = {
                aparelho.getId(),
                aparelho.getModelo(),
                aparelho.getMarca(),
                String.format("R$ %.2f", aparelho.getPreco()),
                aparelho.getEstoque()
            };
            tableModel.addRow(row);
        }
    }

    private void abrirFormAparelho() {
        abrirFormAparelho(-1);
    }

    private void abrirFormAparelho(int id) {
        FormAparelhoFrame formFrame = new FormAparelhoFrame(id, this);
        formFrame.setVisible(true);
        
        // Atualizar lista quando o form for fechado
        formFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                carregarAparelhos();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AparelhosFrame());
    }
}