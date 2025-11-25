package br.edu.ifpr.lojadecelularessqlite.gui;

import br.edu.ifpr.lojadecelularessqlite.database.DatabaseHelper;
import br.edu.ifpr.lojadecelularessqlite.models.Aparelho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class FormAparelhoFrame extends JFrame {
    private DatabaseHelper dbHelper;
    private int idAparelho;
    private AparelhosFrame parentFrame;
    
    private JTextField txtModelo, txtMarca, txtPreco, txtEstoque;
    private JButton btnSalvar, btnExcluir, btnCancelar;

    public FormAparelhoFrame(int idAparelho, AparelhosFrame parentFrame) {
        super(idAparelho == -1 ? "Novo Aparelho" : "Editar Aparelho");
        this.dbHelper = new DatabaseHelper();
        this.idAparelho = idAparelho;
        this.parentFrame = parentFrame;
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        if (idAparelho != -1) {
            carregarAparelho();
        }
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
    }

    private void initComponents() {
        // Labels
        JLabel lblModelo = new JLabel("Modelo:");
        JLabel lblMarca = new JLabel("Marca:");
        JLabel lblPreco = new JLabel("Preço:");
        JLabel lblEstoque = new JLabel("Estoque:");
        
        // Aumentar fonte dos labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        lblModelo.setFont(labelFont);
        lblMarca.setFont(labelFont);
        lblPreco.setFont(labelFont);
        lblEstoque.setFont(labelFont);
        
        // Campos de texto
        txtModelo = new JTextField(20);
        txtMarca = new JTextField(20);
        txtPreco = new JTextField(20);
        txtEstoque = new JTextField(20);
        
        // Aumentar fonte dos campos
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        txtModelo.setFont(fieldFont);
        txtMarca.setFont(fieldFont);
        txtPreco.setFont(fieldFont);
        txtEstoque.setFont(fieldFont);
        
        // Botões
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnCancelar = new JButton("Cancelar");
        
        // Aumentar tamanho dos botões
        Dimension buttonSize = new Dimension(120, 40);
        btnSalvar.setPreferredSize(buttonSize);
        btnExcluir.setPreferredSize(buttonSize);
        btnCancelar.setPreferredSize(buttonSize);
        
        // Aumentar fonte dos botões
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        btnSalvar.setFont(buttonFont);
        btnExcluir.setFont(buttonFont);
        btnCancelar.setFont(buttonFont);
        
        // Configurar botão excluir
        btnExcluir.setEnabled(idAparelho != -1);
        
        // Dimensões
        Dimension labelSize = new Dimension(100, 35);
        lblModelo.setPreferredSize(labelSize);
        lblMarca.setPreferredSize(labelSize);
        lblPreco.setPreferredSize(labelSize);
        lblEstoque.setPreferredSize(labelSize);
        
        Dimension fieldSize = new Dimension(400, 35);
        txtModelo.setPreferredSize(fieldSize);
        txtMarca.setPreferredSize(fieldSize);
        txtPreco.setPreferredSize(fieldSize);
        txtEstoque.setPreferredSize(fieldSize);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Painel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel lblTitulo = new JLabel(idAparelho == -1 ? "NOVO APARELHO" : "EDITAR APARELHO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        
        // Painel de campos
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Configurar constraints para expansão horizontal
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Linha 1 - Modelo
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.0;
        panelCampos.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // Permitir expansão horizontal
        panelCampos.add(txtModelo, gbc);
        
        // Linha 2 - Marca
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        panelCampos.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(txtMarca, gbc);
        
        // Linha 3 - Preço
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        panelCampos.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(txtPreco, gbc);
        
        // Linha 4 - Estoque
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.0;
        panelCampos.add(new JLabel("Estoque:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(txtEstoque, gbc);
        
        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnCancelar);
        
        // Adicionar componentes
        add(panelTitulo, BorderLayout.NORTH);
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        btnSalvar.addActionListener(e -> salvarAparelho());
        btnExcluir.addActionListener(e -> excluirAparelho());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarAparelho() {
        Aparelho aparelho = dbHelper.buscarAparelho(idAparelho);
        if (aparelho != null) {
            txtModelo.setText(aparelho.getModelo());
            txtMarca.setText(aparelho.getMarca());
            txtPreco.setText(String.valueOf(aparelho.getPreco()));
            txtEstoque.setText(String.valueOf(aparelho.getEstoque()));
        }
    }

    private void salvarAparelho() {
        String modelo = txtModelo.getText().trim();
        String marca = txtMarca.getText().trim();
        String precoStr = txtPreco.getText().trim();
        String estoqueStr = txtEstoque.getText().trim();
        
        if (modelo.isEmpty() || marca.isEmpty() || precoStr.isEmpty() || estoqueStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        double preco;
        int estoque;
        try {
            preco = Double.parseDouble(precoStr);
            estoque = Integer.parseInt(estoqueStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço ou estoque inválidos!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Aparelho aparelho = new Aparelho();
        aparelho.setModelo(modelo);
        aparelho.setMarca(marca);
        aparelho.setPreco(preco);
        aparelho.setEstoque(estoque);
        
        boolean sucesso;
        String mensagem;
        
        if (idAparelho == -1) {
            long novoId = dbHelper.inserirAparelho(aparelho);
            sucesso = novoId != -1;
            mensagem = sucesso ? "Aparelho cadastrado com sucesso!" : "Erro ao cadastrar aparelho!";
        } else {
            aparelho.setId(idAparelho);
            sucesso = dbHelper.atualizarAparelho(aparelho);
            mensagem = sucesso ? "Aparelho atualizado com sucesso!" : "Erro ao atualizar aparelho!";
        }
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, mensagem);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirAparelho() {
        if (idAparelho == -1) return;
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Confirma a exclusão deste aparelho?", 
            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dbHelper.excluirAparelho(idAparelho)) {
                JOptionPane.showMessageDialog(this, "Aparelho excluído com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir aparelho!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}