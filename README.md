# Quarkus Integration S3 with LocalStack

Este projeto demonstra a integração do **Quarkus** com **Amazon S3**, utilizando o **LocalStack** para simulação de serviços AWS localmente.

> ⚠️ **Pré-requisitos**
>
> - Docker instalado. ([Instalação oficial](https://docs.docker.com/engine/install/))
> - AWS CLI instalado. ([Instalação oficial](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html))

---

## 1. Configuração do ambiente

Defina as variáveis de ambiente para que o AWS CLI consiga se autenticar com o LocalStack.

**Windows (PowerShell):**

```powershell
$env:AWS_ACCESS_KEY_ID="test"
$env:AWS_SECRET_ACCESS_KEY="test"
$env:AWS_DEFAULT_REGION="us-east-1"

```

**Linux (bash):**
```Linux
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1

```

**Criar bucket:**
```
aws --endpoint-url=http://localhost:4566 s3 mb s3://my-s3

```

**Verificar se foi o bucket foi criado:**
```
aws --endpoint-url=http://localhost:4566 s3 ls
```

**Executando docker compose:**
````
docker compose up
````

**Executando projeto:**
Projeto está configurado para executar na porta 9090
````
mvn quarkus:dev
````

**Swagger:**
````
http://localhost:9090/q/dev-ui/quarkus-smallrye-openapi/swagger-ui
````

**pgAdmin:**
````
http://localhost:15434/browser/
````

# Problemas ainda não resolvidos: 
## Incompatibilidade SDK/CLI x LocalStack

Ao integrar S3 com **LocalStack**, alguns comportamentos podem gerar erros inesperados devido a incompatibilidades:

### 1. AWS SDK v2 (Java)

- Por padrão, o **SDK AWS v2** envia headers de **checksum e trailers** (ex: `x-amz-checksum-crc32`, `x-amz-trailer`) para otimizar uploads.
- LocalStack (versões 2.x e algumas 3.x) **não suporta esses headers**.
- Isso pode gerar erros como:
- Value for x-amz-checksum-crc32 header is invalid

### 2. AWS CLI moderno (v2)

- O **AWS CLI v2** também envia automaticamente trailers e faz validação de checksum.
- Ao tentar `aws s3 cp` para o LocalStack 2.x, mesmo arquivos pequenos podem falhar com:
- InvalidRequest: The value specified in the x-amz-trailer header is not supported


