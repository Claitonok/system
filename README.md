# 💈 System Barbearia API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/technologies/downloads/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue)](https://www.postgresql.org/)
[![Railway](https://img.shields.io/badge/Deploy-Railway-black)](https://railway.app/)

API robusta desenvolvida para gestão de barbearias, focada em segurança, escalabilidade e comunicação assíncrona. O sistema gerencia agendamentos e autenticação de usuários, servindo como backend para aplicações web modernas.

## 🚀 Tecnologias e Infraestrutura

*   **Linguagem:** Java 21 (v21.0.11) com Spring Boot 3.
*   **Banco de Dados:** PostgreSQL (Hospedado no Railway).
*   **Mensageria:** RabbitMQ para processamento de filas e eventos.
*   **Segurança:** Spring Security com `BCryptPasswordEncoder` para proteção de credenciais.
*   **Frontend:** Next.js/React hospedado na Vercel.

## 🛠️ Arquitetura de Segurança

A aplicação implementa um fluxo de autenticação personalizado via `MyUserDetailsService`:

1.  **Criptografia:** Senhas nunca são salvas em texto puro, utilizando hash BCrypt.
2.  **Controle de Acesso:** Endpoints protegidos por Roles (`ADMIN`, `USER`) via `SecurityFilterChain`.
3.  **Sessão:** Gerenciamento de sessão limitado para garantir a integridade dos acessos.

## 📦 Como Executar o Projeto

### Pré-requisitos
* JDK 21
* Maven 3.x
* Instâncias de PostgreSQL e RabbitMQ (Docker ou Cloud)

### Variáveis de Ambiente Necessárias
Configure as seguintes variáveis no seu ambiente de deploy (Railway) ou no `application.properties`:

## 🌐 Deploy e CI/CD
Backend (Railway): Atualização automática via GitHub Integration. O sistema utiliza Docker para orquestrar o Java 21 e as dependências de banco e mensageria.

Frontend (Vercel): Consome a API utilizando renderização dinâmica (force-dynamic) para garantir dados em tempo real.

**Desenvolvido por Claiton 🚀
