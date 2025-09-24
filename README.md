# 🔗 7ADJT – QHealth Atendimento Service  
Serviço orquestrador do ecossistema **QHealth**, responsável por coordenar todo o fluxo de **novo atendimento** entre os diferentes microserviços, usando mensageria com RabbitMQ.

## 📝 Visão Geral  
O **7ADJT-QHealth-atendimento-service** atua como **núcleo de orquestração** do processo de atendimento.  
Ele recebe solicitações de novo atendimento, processa as informações (paciente, triagem, anamnese, fila) e envia os dados para as filas corretas de cada Unidade Básica de Saúde (UBS).

Graças à comunicação assíncrona via **RabbitMQ**, o sistema garante **escalabilidade**, **baixa latência** e **resiliência**, mesmo em cenários de alto volume de atendimentos.

## 🛠 Funcionalidades Principais  
- 📨 **Escuta de Mensagens**: recebe novos atendimentos pela fila `novo.atendimento.queue`.  
- 🧮 **Processamento de Regras**: cria anamnese, busca paciente, define triagem, escolhe fila e registra atendimento.  
- 🔁 **Encaminhamento para Filas UBS**: envia o atendimento para a fila correta (`atendimento.ubs-1`, `atendimento.ubs-1-preferencial`, `atendimento.ubs-2`, etc.).  
- 🧩 **Integração com Serviços**: comunicação com microserviços de **Paciente**, **Anamnese**, **Triagem** e **UBS** via **FeignClient**.  
- 🧪 **Logs e Observabilidade**: registros detalhados de cada etapa para fácil monitoramento e troubleshooting.

## 🏗 Arquitetura  
O fluxo principal é:
```
1️⃣ Mensagem recebida em 'novo.atendimento.queue'
   ↓
2️⃣ Criação da Anamnese (anamnese-service)
   ↓
3️⃣ Consulta de Paciente (usuarios-service)
   ↓
4️⃣ Definição da Triagem (triagem-service)
   ↓
5️⃣ Escolha da Fila (fila-service)
   ↓
6️⃣ Salvamento do Atendimento (atendimento-service interno)
   ↓
7️⃣ Envio do Atendimento para a fila UBS correta (RabbitMQ)
```
Tudo é orquestrado de forma assíncrona, garantindo alta performance e desacoplamento entre serviços.

## 🚀 Tecnologias Principais  
- **Java Spring Boot** – estrutura base da aplicação.  
- **RabbitMQ** – mensageria para comunicação entre serviços.  
- **Spring Cloud OpenFeign** – integração com outros microserviços.  
- **Spring Data JPA** – persistência de dados.  
- **Swagger/OpenAPI** – documentação interativa de endpoints.  
- **JUnit** – testes automatizados.  
- **Docker** – containerização e fácil deploy.
