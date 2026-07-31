# Orçamento API

API REST em Spring Boot para gerenciar orçamentos (cliente + produtos), com
armazenamento em memória e geração de PDF a partir de um template HTML
estático (Thymeleaf + Flying Saucer/iText).

## Stack

- Java 17
- Spring Boot 3.3.4 (Web, Thymeleaf, Validation)
- Flying Saucer (`flying-saucer-pdf-itext5`) para converter HTML em PDF
- Armazenamento em memória (`ConcurrentHashMap`) — **nenhum banco de dados**

## Como rodar

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Endpoints

### 1. Criar orçamento
`POST /orcamentos`

```bash
curl -X POST http://localhost:8080/orcamentos \
  -H "Content-Type: application/json" \
  -d '{
    "nomeCliente": "Empresa Alfa Ltda",
    "cpfCnpj": "12.345.678/0001-90",
    "dataValidade": "2026-08-30",
    "produtos": [
      { "nome": "Consultoria", "descricao": "Consultoria técnica - 10h", "quantidade": 10, "valorUnitario": 150.00 },
      { "nome": "Licença de Software", "descricao": "Licença anual", "quantidade": 1, "valorUnitario": 1200.00 }
    ]
  }'
```

Resposta (`201 Created`): o orçamento criado, incluindo o `id` gerado.

### 2. Buscar orçamento por ID
`GET /orcamentos/{id}`

```bash
curl http://localhost:8080/orcamentos/{id}
```

### 3. Listar todos os orçamentos
`GET /orcamentos`

```bash
curl http://localhost:8080/orcamentos
```

### 4. Gerar e baixar o PDF
`GET /orcamentos/{id}/pdf`

```bash
curl -OJ http://localhost:8080/orcamentos/{id}/pdf
```

Retorna `Content-Type: application/pdf` e
`Content-Disposition: attachment; filename=orcamento-{id}.pdf`.

## Estrutura do projeto

```
src/main/java/com/example/orcamento
├── OrcamentoApiApplication.java      # classe main
├── model/
│   ├── Orcamento.java                 # cliente + validade + produtos
│   └── Produto.java                   # nome, descricao, quantidade, valorUnitario
├── repository/
│   └── OrcamentoRepository.java       # armazenamento em memória (Map)
├── service/
│   ├── OrcamentoService.java          # regras de negócio (criar, buscar, listar)
│   └── PdfGeneratorService.java       # renderiza o template e gera o PDF
├── controller/
│   └── OrcamentoController.java       # endpoints REST
└── exception/
    ├── OrcamentoNaoEncontradoException.java
    └── GlobalExceptionHandler.java    # tratamento de erros (404 / 400 / 500)

src/main/resources/
├── templates/orcamento-template.html  # template XHTML usado para gerar o PDF
└── application.properties
```

## Observações importantes

- **Sem banco de dados**: os dados existem apenas em memória e são perdidos a
  cada reinício da aplicação. Para persistência real, troque
  `OrcamentoRepository` por um repositório Spring Data (JPA, Mongo etc.).
- **Template em XHTML**: o Flying Saucer exige XML bem formado (todas as tags
  fechadas). Por isso o `PdfGeneratorService` usa um `TemplateEngine`
  Thymeleaf configurado em `TemplateMode.XHTML`, separado do engine padrão do
  Spring MVC.
- **CSS suportado**: o Flying Saucer entende um subconjunto de CSS 2.1 (não
  suporta flexbox/grid). O template usa `<table>` para o layout por esse
  motivo.
- **Validação**: campos obrigatórios (`nomeCliente`, `cpfCnpj`,
  `dataValidade`, `produtos`) são validados via Bean Validation; erros
  retornam `400 Bad Request` com detalhes.
