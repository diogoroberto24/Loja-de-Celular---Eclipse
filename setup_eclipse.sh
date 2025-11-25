#!/bin/bash

echo "========================================"
echo "Loja de Celulares - Configuracao Eclipse"
echo "========================================"
echo ""

# Criar pasta lib se nao existir
mkdir -p lib

# Baixar SQLite JDBC
echo "Baixando SQLite JDBC..."
curl -L "https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar" -o "lib/sqlite-jdbc-3.36.0.3.jar"

if [ -f "lib/sqlite-jdbc-3.36.0.3.jar" ]; then
    echo "✅ SQLite JDBC baixado com sucesso!"
else
    echo "❌ Erro ao baixar SQLite JDBC!"
    echo "Por favor, baixe manualmente em: https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar"
    echo "E coloque o arquivo na pasta lib/"
fi

echo ""
echo "========================================"
echo "Configuracao concluida!"
echo "========================================"
echo ""
echo "Proximos passos:"
echo "1. Abra o Eclipse"
echo "2. Importe o projeto (File -> Import -> Existing Projects into Workspace)"
echo "3. Configure o Build Path se necessario"
echo "4. Execute a classe LojaCelularesApp.java"
echo "" 
read -p "Pressione Enter para continuar..."