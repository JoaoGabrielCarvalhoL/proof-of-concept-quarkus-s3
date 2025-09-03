# Quarkus Integration S3 with LocalStack

Este projeto demonstra a integração do **Quarkus** com **Amazon S3**, utilizando o **LocalStack** para simulação de serviços AWS localmente.

> ⚠️ **Pré-requisitos**
>
> - Docker instalado. ([Instalação oficial](https://docs.docker.com/engine/install/))
> - AWS CLI instalado. ([Instalação oficial](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html))
> - Local Stack. ([Instalação_oficial](https://docs.localstack.cloud/aws/getting-started/installation/))
---

## 1. Configuração do ambiente

Configurar credentials aws

**aws configure:**
```
aws configure --profile localstack
```
- Preencher com informações fake.

**Criar bucket:**
```
aws --endpoint http://localhost:4566 --profile localstack s3 mb s3://joaocruz

```

**Verificar se foi o bucket foi criado:**
```
aws --endpoint http://localhost:4566 --profile localstack s3 ls
```

**Executando docker compose:**
````
docker compose up
````
- Observação: Na raiz do projeto consta um docker-compose.yaml referente ao postgres e ao pgAdmin. É necessário que esses serviços estejam funcionando para que a aplicação consiga inicializar. 
- Dentro do diretório localstack, consta o docker-compose-yaml referente ao localstack. No meu caso, inicializei via docker compose. Caso tenha instalado na sua máquina, não é necessário inicializar. Contudo, garanta que o serviço esteja funcionando. 
- Se optar subir através do docker compose, certifique-se de subir manualmente. 
- Por padrão, o docker executará o docker-compose contido na raiz do projeto.

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
