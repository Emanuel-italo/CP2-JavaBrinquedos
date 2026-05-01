# 🧸 CP2 - API REST Brinquedos Infantis

> Projeto desenvolvido para a disciplina de **Java Advanced** — FIAP 2026
> Checkpoint 2 — API REST com Spring Boot + Oracle Database
> Professor: Dr. Marcel Stefan Wagner

---

## 👥 Integrantes

| Nome | RM |
1- RM563811 Paulo Henrique Alves Estalise
2- RM561337 Emanuel Italo
3- RM562012 Gabriel Bebe

---

## 📋 Descrição

API REST completa para gerenciamento de **brinquedos infantis** (0 a 14 anos).
Desenvolvida com **Spring Boot**, **JPA/Hibernate** e **Oracle Database**, seguindo o padrão CRUD e boas práticas de desenvolvimento com DTOs e Bean Validation.

O programa recebe requisições HTTP via Postman/Insomnia no endpoint `/brinquedos`, consulta/persiste os dados na tabela `TDS_TB_BRINQUEDO` do banco Oracle FIAP, e retorna os resultados em formato JSON.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão |
|-----------|--------|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Maven | 3.9+ |
| Oracle Database | 19c+ |
| ojdbc11 | Runtime |
| Spring Data JPA | 3.2.5 |
| Bean Validation | Jakarta |
| Spring Web | 3.2.5 |

---

## 📁 Estrutura do Projeto

```
src/
└── main/
    ├── java/fiap/com/br/cp2/
    │   ├── Cp2Application.java              ← Classe principal (@SpringBootApplication)
    │   ├── controller/
    │   │   └── BrinquedoController.java     ← Endpoints REST (GET, POST, PUT, DELETE)
    │   ├── service/
    │   │   └── BrinquedoService.java        ← Regras de negócio + mapeamento DTO ↔ Entity
    │   ├── repository/
    │   │   └── BrinquedoRepository.java     ← Acesso ao banco Oracle (Spring Data JPA)
    │   ├── entity/
    │   │   └── Brinquedo.java               ← Entidade JPA mapeada para TDS_TB_BRINQUEDO
    │   ├── dto/
    │   │   └── BrinquedoDTO.java            ← DTO com Bean Validation (@NotBlank, @Min, etc.)
    │   └── exception/
    │       ├── BrinquedoNotFoundException.java  ← Exceção customizada (HTTP 404)
    │       └── GlobalExceptionHandler.java      ← Handler global (@RestControllerAdvice)
    └── resources/
        ├── application.properties           ← Configurações Oracle + JPA
        └── script.sql                       ← DDL + DML para Oracle SQL Developer
```

---

## ⚙️ Configuração do Banco de Dados Oracle

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=SEU_RM_AQUI
spring.datasource.password=SUA_SENHA_AQUI
```

> 💡 Substitua `SEU_RM_AQUI` pelo seu RM e `SUA_SENHA_AQUI` pela sua senha FIAP.

---

## 🗄️ Script SQL

Execute o script `src/main/resources/script.sql` no **Oracle SQL Developer** antes de rodar a aplicação:

```sql
CREATE SEQUENCE SEQ_BRINQUEDOS START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE TDS_TB_BRINQUEDO (
    ID_BRINQUEDO     NUMBER(10)    NOT NULL,
    NM_BRINQUEDO     VARCHAR2(100) NOT NULL,
    TP_BRINQUEDO     VARCHAR2(50)  NOT NULL,
    NR_CLASSIFICACAO NUMBER(2)     NOT NULL,
    DS_TAMANHO       VARCHAR2(20)  NOT NULL,
    VL_PRECO         NUMBER(10,2)  NOT NULL,
    CONSTRAINT PK_BRINQUEDO     PRIMARY KEY (ID_BRINQUEDO),
    CONSTRAINT CK_CLASSIFICACAO CHECK (NR_CLASSIFICACAO BETWEEN 0 AND 14),
    CONSTRAINT CK_PRECO         CHECK (VL_PRECO > 0)
);
```

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- Java 17 instalado
- Maven 3.9+
- Oracle SQL Developer configurado e acessível
- IntelliJ IDEA (recomendado)
- Postman ou Insomnia para testar os endpoints

### Passos

**1. Clone o repositório**
```bash
git clone https://github.com/gugomesx10/CP2-Java
cd CP2-Java
```

**2. Configure o banco de dados**
Edite `application.properties` com suas credenciais Oracle FIAP.

**3. Execute o script SQL**
Abra o Oracle SQL Developer e execute o arquivo `script.sql`.

**4. Rode a aplicação**
```bash
mvn spring-boot:run
```
Ou pela IDE: clique com o botão direito em `Cp2Application.java` → **Run**.

**5. A API estará disponível em:**
```
http://localhost:8080/brinquedos
```

---

## 📡 Endpoints da API

### Base URL: `http://localhost:8080`

| Método | Endpoint | Descrição | Status de Resposta |
|--------|----------|-----------|-------------------|
| GET | `/brinquedos` | Lista todos os brinquedos | 200 OK |
| GET | `/brinquedos/{id}` | Busca brinquedo por ID | 200 OK / 404 Not Found |
| POST | `/brinquedos` | Cria novo brinquedo | 201 Created / 400 Bad Request |
| PUT | `/brinquedos/{id}` | Atualiza brinquedo existente | 200 OK / 404 Not Found |
| DELETE | `/brinquedos/{id}` | Exclui brinquedo pelo ID | 204 No Content / 404 Not Found |

---

## 🧪 Exemplos JSON para Postman / Insomnia

### ✅ POST — Criar Brinquedo
**URL:** `POST http://localhost:8080/brinquedos`
**Headers:** `Content-Type: application/json`

```json
{
  "nome": "Hot Wheels",
  "tipo": "Carrinho",
  "classificacao": 10,
  "tamanho": "Pequeno",
  "preco": 29.90
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "Hot Wheels",
  "tipo": "Carrinho",
  "classificacao": 10,
  "tamanho": "Pequeno",
  "preco": 29.90
}
```

---

### ✅ GET — Listar Todos
**URL:** `GET http://localhost:8080/brinquedos`

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "Hot Wheels",
    "tipo": "Carrinho",
    "classificacao": 10,
    "tamanho": "Pequeno",
    "preco": 29.90
  },
  {
    "id": 2,
    "nome": "LEGO Classic",
    "tipo": "Blocos de Montar",
    "classificacao": 6,
    "tamanho": "Grande",
    "preco": 199.90
  }
]
```

---

### ✅ GET — Buscar por ID
**URL:** `GET http://localhost:8080/brinquedos/1`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "Hot Wheels",
  "tipo": "Carrinho",
  "classificacao": 10,
  "tamanho": "Pequeno",
  "preco": 29.90
}
```

**Resposta (404 Not Found):**
```json
{
  "timestamp": "2026-04-28T10:30:00",
  "status": 404,
  "erro": "Não Encontrado",
  "mensagem": "Brinquedo não encontrado com ID: 99"
}
```

---

### ✅ PUT — Atualizar Brinquedo
**URL:** `PUT http://localhost:8080/brinquedos/1`
**Headers:** `Content-Type: application/json`

```json
{
  "nome": "Hot Wheels Edição Especial",
  "tipo": "Carrinho",
  "classificacao": 8,
  "tamanho": "Pequeno",
  "preco": 49.90
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "Hot Wheels Edição Especial",
  "tipo": "Carrinho",
  "classificacao": 8,
  "tamanho": "Pequeno",
  "preco": 49.90
}
```

---

### ✅ DELETE — Excluir Brinquedo
**URL:** `DELETE http://localhost:8080/brinquedos/1`

**Resposta (204 No Content):** *(sem corpo)*

---

### ❌ Erro de Validação (400 Bad Request)

Ao enviar dados inválidos no POST ou PUT:

```json
{
  "timestamp": "2026-04-28T10:30:00",
  "status": 400,
  "erro": "Dados Inválidos",
  "mensagem": "Verifique os campos obrigatórios.",
  "campos": {
    "nome": "O nome do brinquedo é obrigatório.",
    "classificacao": "A classificação máxima é de 14 anos (infantil)."
  }
}
```

---

## ✅ Validações (Bean Validation — Jakarta)

| Campo | Anotações | Regras |
|-------|-----------|--------|
| `nome` | `@NotBlank` `@Size` | Obrigatório, entre 2 e 100 caracteres |
| `tipo` | `@NotBlank` `@Size` | Obrigatório, entre 2 e 50 caracteres |
| `classificacao` | `@NotNull` `@Min` `@Max` | Obrigatório, entre 0 e 14 anos |
| `tamanho` | `@NotBlank` `@Size` | Obrigatório, entre 2 e 20 caracteres |
| `preco` | `@NotNull` `@DecimalMin` | Obrigatório, maior que R$ 0,01 |

---

## 🔄 Arquitetura da Aplicação

```
Postman / Insomnia
      |
      | HTTP (JSON) — localhost:8080
      |
  BrinquedoController (@RestController)
      |
  BrinquedoService (@Service)
      |   — regras de negócio
      |   — conversão DTO ↔ Entity
      |
  BrinquedoRepository (@Repository / JpaRepository)
      |
      | JPA / Hibernate
      |
  Oracle Database (FIAP)
  Tabela: TDS_TB_BRINQUEDO
```

---

## 📝 Licença

Projeto acadêmico — FIAP 2026. Todos os direitos reservados.
