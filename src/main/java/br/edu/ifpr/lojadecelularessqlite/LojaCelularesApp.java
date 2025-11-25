package br.edu.ifpr.lojadecelularessqlite;

import br.edu.ifpr.lojadecelularessqlite.gui.AparelhosFrame;
import br.edu.ifpr.lojadecelularessqlite.gui.ComprasFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LojaCelularesApp extends JFrame {
    private JButton btnAparelhos, btnCompras;

    public LojaCelularesApp() {
        super("Loja de Celulares");
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Criar botões
        btnAparelhos = new JButton("Gerenciar Aparelhos");
        btnCompras = new JButton("Gerenciar Compras");
        
        // Estilizar botões
        Dimension buttonSize = new Dimension(250, 60);
        btnAparelhos.setPreferredSize(buttonSize);
        btnCompras.setPreferredSize(buttonSize);
        
        btnAparelhos.setFont(new Font("Arial", Font.BOLD, 16));
        btnCompras.setFont(new Font("Arial", Font.BOLD, 16));
        
        btnAparelhos.setBackground(new Color(70, 130, 180));
        btnAparelhos.setForeground(Color.WHITE);
        btnCompras.setBackground(new Color(70, 130, 180));
        btnCompras.setForeground(Color.WHITE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Painel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel lblTitulo = new JLabel("LOJA DE CELULARES");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        
        // Painel central com botões
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(btnAparelhos, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelCentral.add(btnCompras, gbc);
        
        // Adicionar componentes ao frame
        add(panelTitulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        btnAparelhos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirAparelhos();
            }
        });
        
        btnCompras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCompras();
            }
        });
    }

    private void abrirAparelhos() {
        SwingUtilities.invokeLater(() -> {
            AparelhosFrame frame = new AparelhosFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
    }

    private void abrirCompras() {
        SwingUtilities.invokeLater(() -> {
            ComprasFrame frame = new ComprasFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LojaCelularesApp());
    }
}