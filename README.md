# Loja de Celulares - Java Desktop

Aplicação desktop Java para gerenciamento de loja de celulares, convertida do Android Studio para Eclipse.

## 🚀 Tecnologias Utilizadas

- **Java SE 8+**
- **Swing** - Interface gráfica
- **SQLite JDBC** - Banco de dados local
- **Eclipse IDE** - Ambiente de desenvolvimento

## 📋 Funcionalidades

### 📱 Gerenciamento de Aparelhos
- ✅ Cadastrar novos aparelhos (modelo, marca, preço, estoque)
- ✅ Editar informações de aparelhos existentes
- ✅ Excluir aparelhos
- ✅ Visualizar lista de todos os aparelhos

### 🛒 Gerenciamento de Compras
- ✅ Registrar novas compras
- ✅ Selecionar aparelho e quantidade
- ✅ Digitar nome do cliente
- ✅ Controle automático de estoque
- ✅ Excluir compras registradas

### 💾 Banco de Dados
- ✅ SQLite local
- ✅ Controle de estoque automático
- ✅ Integridade referencial

## 🛠️ Configuração do Projeto

### 1. Download do SQLite JDBC
Baixe o driver SQLite JDBC e coloque na pasta `lib/`:
- Arquivo: `sqlite-jdbc-3.36.0.3.jar`
- Download: https://github.com/xerial/sqlite-jdbc/releases

### 2. Importar no Eclipse
1. Abra o Eclipse IDE
2. File → Import → Existing Projects into Workspace
3. Selecione a pasta do projeto
4. Clique em Finish

### 3. Configurar Build Path
1. Clique com botão direito no projeto
2. Build Path → Configure Build Path
3. Libraries → Add JARs
4. Selecione `lib/sqlite-jdbc-3.36.0.3.jar`
5. Aplique e feche

## 🎯 Como Executar

### Opção 1: Via Eclipse
1. Localize a classe `LojaCelularesApp.java`
2. Clique com botão direito → Run As → Java Application

### Opção 2: Via Linha de Comando
```bash
# Compilar
javac -cp lib/sqlite-jdbc-3.36.0.3.jar -d bin src/main/java/br/edu/ifpr/lojadecelularessqlite/*.java src/main/java/br/edu/ifpr/lojadecelularessqlite/*/*.java

# Executar
java -cp bin:lib/sqlite-jdbc-3.36.0.3.jar br.edu.ifpr.lojadecelularessqlite.LojaCelularesApp
```

## 📁 Estrutura do Projeto

```
LojaCelularEclipseProject/
├── src/main/java/br/edu/ifpr/lojadecelularessqlite/
│   ├── LojaCelularesApp.java          # Classe principal
│   ├── models/
│   │   ├── Aparelho.java              # Modelo de aparelho
│   │   └── Compra.java                # Modelo de compra
│   ├── database/
│   │   └── DatabaseHelper.java        # Gerenciamento do banco
│   └── gui/
│       ├── AparelhosFrame.java        # Lista de aparelhos
│       ├── ComprasFrame.java          # Lista de compras
│       ├── FormAparelhoFrame.java     # Formulário de aparelho
│       └── FormCompraFrame.java       # Formulário de compra
├── lib/
│   └── sqlite-jdbc-3.36.0.3.jar      # Driver SQLite
├── .project                            # Configuração Eclipse
├── .classpath                          # Configuração Eclipse
└── README.md                          # Este arquivo
```

## 🔧 Arquivo de Banco de Dados

O banco de dados SQLite será criado automaticamente no diretório raiz do projeto:
- **Arquivo:** `loja_celulares.db`
- **Tabelas:** `aparelhos` e `compras`

## 🎨 Interface Gráfica

A aplicação possui interface gráfica intuitiva com:
- Tela inicial com acesso aos módulos
- Tabelas para listagem de dados
- Formulários modais para cadastro/edição
- Cores e layout profissional
- Responsividade adequada

## ⚠️ Notas Importantes

- O estoque é controlado automaticamente nas compras
- Não é possível vender mais do que o estoque disponível
- A aplicação usa SQLite local (não requer servidor)
- Compatível com Java 8 ou superior

## 🐛 Solução de Problemas

### Erro: "Class not found: org.sqlite.JDBC"
- Verifique se o arquivo `sqlite-jdbc-3.36.0.3.jar` está na pasta `lib/`
- Confirme que foi adicionado ao build path do Eclipse

### Erro: "Could not initialize class"
- Certifique-se de ter Java 8+ instalado
- Verifique as configurações do projeto no Eclipse

## 📞 Suporte

Para questões relacionadas ao projeto, verifique:
1. As configurações do build path
2. A versão do Java instalada
3. A presença do driver SQLite JDBC
4. Os logs de erro no console do Eclipse