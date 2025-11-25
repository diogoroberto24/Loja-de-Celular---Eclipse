@echo off
echo Compilando o projeto com Java 8...
javac --release 8 -cp "lib/sqlite-jdbc-3.36.0.3.jar;bin" -d bin src\main\java\br\edu\ifpr\lojadecelularessqlite\models\*.java src\main\java\br\edu\ifpr\lojadecelularessqlite\database\*.java src\main\java\br\edu\ifpr\lojadecelularessqlite\gui\*.java src\main\java\br\edu\ifpr\lojadecelularessqlite\LojaCelularesApp.java

echo Executando a aplicacao...
java -cp "lib/sqlite-jdbc-3.36.0.3.jar;bin" br.edu.ifpr.lojadecelularessqlite.LojaCelularesApp
pause