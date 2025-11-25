package br.edu.ifpr.lojadecelularessqlite.gui;

import br.edu.ifpr.lojadecelularessqlite.database.DatabaseHelper;
import br.edu.ifpr.lojadecelularessqlite.models.Aparelho;
import br.edu.ifpr.lojadecelularessqlite.models.Compra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FormCompraFrame extends JFrame {
    private DatabaseHelper dbHelper;
    private ComprasFrame parentFrame;
    
    private JComboBox<String> comboAparelho;
    private JTextField txtCliente, txtQuantidade, txtData;
    private JButton btnSalvar, btnCancelar;
    
    private List<Aparelho> listaAparelhos;

    public FormCompraFrame(int idCompra, ComprasFrame parentFrame) {
        super(idCompra == -1 ? "Nova Compra" : "Editar Compra");
        this.dbHelper = new DatabaseHelper();
        this.parentFrame = parentFrame;
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        carregarAparelhos();
        
        // Definir data atual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtData.setText(sdf.format(new Date()));
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(parentFrame);
        setResizable(false);
    }

    private void initComponents() {
        // Labels
        JLabel lblAparelho = new JLabel("Aparelho:");
        JLabel lblCliente = new JLabel("Cliente:");
        JLabel lblQuantidade = new JLabel("Quantidade:");
        JLabel lblData = new JLabel("Data:");
        
        // Aumentar fonte dos labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        lblAparelho.setFont(labelFont);
        lblCliente.setFont(labelFont);
        lblQuantidade.setFont(labelFont);
        lblData.setFont(labelFont);
        
        // Campos
        comboAparelho = new JComboBox<>();
        txtCliente = new JTextField(20);
        txtQuantidade = new JTextField(20);
        txtData = new JTextField(20);
        txtData.setEditable(false);
        
        // Aumentar fonte dos campos
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        txtCliente.setFont(fieldFont);
        txtQuantidade.setFont(fieldFont);
        txtData.setFont(fieldFont);
        comboAparelho.setFont(fieldFont);
        
        // Botões
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        
        // Aumentar tamanho dos botões
        Dimension buttonSize = new Dimension(120, 40);
        btnSalvar.setPreferredSize(buttonSize);
        btnCancelar.setPreferredSize(buttonSize);
        
        // Aumentar fonte dos botões
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        btnSalvar.setFont(buttonFont);
        btnCancelar.setFont(buttonFont);
        
        // Dimensões
        Dimension labelSize = new Dimension(120, 35);
        lblAparelho.setPreferredSize(labelSize);
        lblCliente.setPreferredSize(labelSize);
        lblQuantidade.setPreferredSize(labelSize);
        lblData.setPreferredSize(labelSize);
        
        Dimension fieldSize = new Dimension(350, 35);
        comboAparelho.setPreferredSize(fieldSize);
        txtCliente.setPreferredSize(fieldSize);
        txtQuantidade.setPreferredSize(fieldSize);
        txtData.setPreferredSize(fieldSize);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Painel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel lblTitulo = new JLabel("REGISTRAR COMPRA");
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
        
        // Linha 1 - Aparelho
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.0;
        panelCampos.add(new JLabel("Aparelho:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // Permitir expansão horizontal
        panelCampos.add(comboAparelho, gbc);
        
        // Linha 2 - Cliente
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        panelCampos.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(txtCliente, gbc);
        
        // Linha 3 - Quantidade
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        panelCampos.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(txtQuantidade, gbc);
        
        // Linha 4 - Data
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.0;
        panelCampos.add(new JLabel("Data:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelCampos.add(txtData, gbc);
        
        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCancelar);
        
        // Adicionar componentes
        add(panelTitulo, BorderLayout.NORTH);
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        btnSalvar.addActionListener(e -> salvarCompra());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarAparelhos() {
        comboAparelho.removeAllItems();
        listaAparelhos = dbHelper.listarAparelhos();
        
        for (Aparelho aparelho : listaAparelhos) {
            String item = aparelho.getModelo() + " - " + aparelho.getMarca() + 
                         " (Estoque: " + aparelho.getEstoque() + ")";
            comboAparelho.addItem(item);
        }
        
        if (!listaAparelhos.isEmpty()) {
            comboAparelho.setSelectedIndex(0);
        }
    }

    private void salvarCompra() {
        if (comboAparelho.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aparelho!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String cliente = txtCliente.getText().trim();
        String quantidadeStr = txtQuantidade.getText().trim();
        String data = txtData.getText().trim();
        
        if (cliente.isEmpty() || quantidadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar estoque
        int selectedIndex = comboAparelho.getSelectedIndex();
        Aparelho aparelhoSelecionado = listaAparelhos.get(selectedIndex);
        
        if (quantidade > aparelhoSelecionado.getEstoque()) {
            JOptionPane.showMessageDialog(this, 
                "Estoque insuficiente!\nEstoque disponível: " + aparelhoSelecionado.getEstoque(), 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Criar compra
        Compra compra = new Compra();
        compra.setIdAparelho(aparelhoSelecionado.getId());
        compra.setCliente(cliente);
        compra.setQuantidade(quantidade);
        compra.setData(data);
        
        // Inserir compra e atualizar estoque
        long novoId = dbHelper.inserirCompra(compra);
        if (novoId != -1) {
            // Atualizar estoque
            int novoEstoque = aparelhoSelecionado.getEstoque() - quantidade;
            if (dbHelper.atualizarEstoque(aparelhoSelecionado.getId(), novoEstoque)) {
                JOptionPane.showMessageDialog(this, "Compra registrada com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar estoque!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar compra!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}