# 📁 API de Manipulação de Arquivos (Spring Boot + Docker)

API para manipulação de arquivos (CRUD), desenvolvida com **Spring Boot** e **containerizada com Docker**.  
O projeto utiliza **Flyway** para versionamento do banco de dados e foi desenvolvido no **IntelliJ IDEA**.  
Os testes da API foram realizados com o **Postman**.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Flyway (controle de versão do banco de dados)
- PostgreSQL / Docker
- IntelliJ IDEA (ambiente de desenvolvimento)
- Postman (testes de API)

---

## 🧾 Funcionalidades

- ✅ Upload de arquivos
- 📥 Download de arquivos
- 🗑️ Exclusão de arquivos
- 📃 Listagem de arquivos

---

## 📌 Endpoints da API

| Método   |        Endpoint             |        Descrição         |
|----------|-----------------------------|--------------------------|
| **POST**   | `/api/arquivos`           | Upload de um arquivo     |
| **GET**    | `/api/arquivos`           | Lista todos os arquivos  |
| **GET**    | `/api/arquivos/{id}`      | Download de um arquivo   |
| **DELETE** | `/api/arquivos/{id}`      | Remove um arquivo        |

---

## 🐳 Banco de Dados (Docker)

Este projeto utiliza o **PostgreSQL** rodando em um container Docker.

Antes de iniciar a aplicação Spring Boot, é necessário subir o banco de dados:

```bash
docker-compose up -d
