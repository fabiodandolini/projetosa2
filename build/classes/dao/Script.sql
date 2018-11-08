drop database if exists dbSa2;

create database dbSa2;

use dbSa2;

CREATE TABLE coordenador (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255),
    curso VARCHAR(255),
    senha VARCHAR(255),
    PRIMARY KEY(id));

CREATE TABLE turma (
    idturma INT NOT NULL AUTO_INCREMENT,
    turma VARCHAR(255),
    curso VARCHAR(255),
    coordenador VARCHAR(255),
    PRIMARY KEY(idturma));

CREATE TABLE aluno (
  idaluno INT NOT NULL AUTO_INCREMENT,
  aluno VARCHAR(256) ,
  dataNascimento DATE ,
  matricula VARCHAR(255),
  curso VARCHAR(255),
  coordenador VARCHAR(255),
  ativo TINYINT ,
  idturma INT NOT NULL,
  PRIMARY KEY (idaluno),
  CONSTRAINT FOREIGN KEY (idturma) REFERENCES turma(idturma));
  
  CREATE TABLE autorizacao (
    id INT NOT NULL AUTO_INCREMENT,
    idaluno INT NOT NULL,
    curso VARCHAR(255),
    criacao DATE,
    saida DATETIME,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (idaluno) REFERENCES aluno(idaluno));