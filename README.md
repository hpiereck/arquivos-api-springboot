# 📁 API de Manipulação de Arquivos (Spring Boot + Docker)

API para manipulação de arquivos (CRUD), desenvolvida com **Spring Boot** e **containerizada com Docker**. O projeto também utiliza o **Flyway** para versionamento do banco de dados.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Flyway
- PostgreSQL / Docker

---

## 🧾 Funcionalidades

- ✅ Upload de arquivos
- 📥 Download de arquivos
- 🗑️ Exclusão de arquivos
- 📃 Listagem de arquivos

---

## 🐳 Banco de Dados (Docker)

Este projeto utiliza o **PostgreSQL** rodando em um container Docker.

Antes de iniciar a aplicação Spring Boot, é necessário subir o banco de dados:

```bash
docker-compose up -d
