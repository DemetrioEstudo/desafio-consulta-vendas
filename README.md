# Desafio Consulta Vendas

## 📋 Sobre o Projeto

Este projeto é um **exercício prático** desenvolvido durante a formação DevSuperior. Trata-se de uma API REST para consulta e relatórios de vendas.

### Funcionalidades Implementadas

**1. Relatório de Vendas**
- **[IN]** O usuário informa, opcionalmente, data inicial, data final e um trecho do nome do vendedor.
- **[OUT]** O sistema informa uma listagem paginada contendo id, data, quantia vendida e nome do vendedor, das vendas que se enquadrem nos dados informados.

**2. Sumário de Vendas por Vendedor**
- **[IN]** O usuário informa, opcionalmente, data inicial e data final.
- **[OUT]** O sistema informa uma listagem contendo nome do vendedor e soma de vendas deste vendedor no período informado.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 2.7.3**
- **Spring Data JPA**
- **Spring Web**
- **H2 Database** (banco de dados em memória)
- **Maven** (gerenciador de dependências)

## 💾 Modelo de Dados

O sistema utiliza um banco de dados relacional H2 com as seguintes tabelas:

### Tabela: tb_seller (Vendedor)
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | Long | Chave primária |
| name | String | Nome do vendedor |
| email | String | E-mail do vendedor |
| phone | String | Telefone do vendedor |

### Tabela: tb_sales (Vendas)
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | Long | Chave primária |
| seller_id | Long | Chave estrangeira (vendedor) |
| visited | Integer | Número de visitas |
| deals | Integer | Número de negócios fechados |
| amount | Double | Valor da venda |
| date | LocalDate | Data da venda |

**Relacionamento:** Uma venda (Sale) pertence a um vendedor (Seller). Um vendedor pode ter várias vendas (relacionamento 1:N).

## 📦 Estrutura do Projeto

```
src/main/java/com/devsuperior/dsmeta/
├── controllers/     # Controladores REST
├── dto/            # Data Transfer Objects
├── entities/       # Entidades JPA
├── repositories/   # Repositórios Spring Data
└── services/       # Camada de serviços
```

## 🔧 Como Executar

1. Clone o repositório
2. Execute o projeto usando Maven:
```bash
./mvnw spring-boot:run
```
3. A API estará disponível em `http://localhost:8080`

## 📌 Endpoints

### GET /sales/{id}
Busca uma venda por ID.

### GET /sales/report
Relatório de vendas com filtros opcionais:
- `name`: Nome do vendedor (opcional)
- `minDate`: Data inicial (opcional, formato: YYYY-MM-DD)
- `maxDate`: Data final (opcional, formato: YYYY-MM-DD)
- Suporta paginação

### GET /sales/summary
Sumário de vendas por vendedor com filtros opcionais:
- `minDate`: Data inicial (opcional, formato: YYYY-MM-DD)
- `maxDate`: Data final (opcional, formato: YYYY-MM-DD)
- Suporta paginação

## 👨‍💻 Autor

Desenvolvido como exercício do curso de Formação DevSuperior.
Por Flávio Antonio Demétrio

