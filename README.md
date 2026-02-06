# EncurteMe - API Encurtadora de URLs

API desenvolvida em Java com Spring Boot utilizando Arquitetura Hexagonal para encurtamento de URLs.

## 🚀 Como Rodar Localmente

### Pré-requisitos
* Java 21 ou superior
* Maven (ou utilizar o `./mvnw` incluso)
* PostgreSQL

### 1. Configuração do Ambiente
Crie um arquivo `.env` na raiz do projeto (o projeto já ignora este arquivo no Git) com as seguintes variáveis:

```env
DB_URL=jdbc:postgresql://localhost:5432/nome_do_seu_banco
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
BASE_URL=http://localhost:8080
```

### 2. Compilar o Projeto
Para gerar o arquivo `.jar` ignorando os testes (devido à necessidade de banco de dados ativo):
```bash
./mvnw clean package -DskipTests
```

### 3. Executar a Aplicação
Você pode rodar diretamente via Maven:
```bash
./mvnw spring-boot:run
```
Ou executando o JAR gerado:
```bash
java -jar target/EncurteMe-0.0.1-SNAPSHOT.jar
```

## 📍 Endpoints Principais

* **POST** `/api/v1/shortener`: Encurta uma URL original.
* **GET** `/api/v1/{codigo}`: Redireciona para a URL original.
* **GET** `/api/v1/list`: Lista todas as URLs (paginado).
* **DELETE** `/api/v1`: Remove todos os registros.
* **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`

## 🏗️ Arquitetura
O projeto segue os princípios da **Arquitetura Hexagonal (Ports and Adapters)**, garantindo que a regra de negócio seja independente de frameworks e bancos de dados.

## ✒️ Autor
* **Luis Ricardo Laranjeira Vieira**
* **GitHub**: [KingDanone](https://github.com/KingDanone)
* **Email**: lricardolv10@gmail.com

## 📄 Licença
Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.
