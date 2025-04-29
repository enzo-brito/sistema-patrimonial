
---

# 📘 Controle de Bens

[![Status](https://img.shields.io/badge/status-finalizado-green.svg)]()  
[![License](https://img.shields.io/badge/license-MIT-blue.svg)]()  
[![Java](https://img.shields.io/badge/Java-8+-red.svg)]()  
[![JSF](https://img.shields.io/badge/JSF-PrimeFaces-blueviolet.svg)]()  
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12+-blue.svg)]()  
[![GlassFish](https://img.shields.io/badge/GlassFish-5%2B-yellowgreen.svg)]()

> **Controle de Bens** é um sistema de controle patrimonial utilizando **JSF**, **PrimeFaces** e **PostgreSQL**, com cálculos automáticos de depreciação e deploy no servidor **GlassFish**. O sistema permite o cadastro, listagem e cálculo de depreciação de bens com base na sua vida útil.

---
## 📁 Estrutura do Projeto

```
controlebens/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/com/foxinline/controlebens/
│       │       ├── bean/              # ManagedBeans JSF
│       │       ├── dao/               # Acesso ao banco de dados
│       │       ├── model/             # Entidades JPA
│       │       ├── service/           # Lógica de negócio
│       │       └── util/              # Utilitários (JPA, Conversores)
│       └── resources/
│           └── META-INF/
│               └── persistence.xml    # Configuração JPA
├── WebContent/
│   ├── pages/                         # Telas JSF com PrimeFaces
│   ├── resources/                     │   └── WEB-INF/
│       ├── web.xml                    # Configuração JSF
│       └── faces-config.xml          # Configuração adicional JSF
└── pom.xml
```

---

## ⚙️ Requisitos

- Java 8+
- Apache Maven (opcional)
- GlassFish 5+
- PostgreSQL 12+
- PrimeFaces 8.0+
- JSF 2.3

---

## 🔧 Configuração e Execução

### 1. Banco de Dados (PostgreSQL)

Crie o banco:

```sql
CREATE DATABASE controlebens;
```

### 2. Configurar `persistence.xml`

Arquivo: `src/main/resources/META-INF/persistence.xml`

```xml
<persistence-unit name="controleBensPU" transaction-type="RESOURCE_LOCAL">
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
    <class>br.com.foxinline.controlebens.model.Bem</class>
    <class>br.com.foxinline.controlebens.model.TipoProduto</class>

    <properties>
        <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver"/>
        <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/controlebens"/>
        <property name="javax.persistence.jdbc.user" value="postgres"/>
        <property name="javax.persistence.jdbc.password" value="sua_senha_aqui"/>

        <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
        <property name="hibernate.hbm2ddl.auto" value="update"/>
        <property name="hibernate.show_sql" value="true"/>
        <property name="hibernate.format_sql" value="true"/>
    </properties>
</persistence-unit>
```

### 3. Importar Projeto na IDE

- Eclipse ou IntelliJ
- Caso use Maven: "Importar projeto Maven existente"

### 4. Deploy no GlassFish

- Copie o `.war` para `glassfish/domains/domain1/autodeploy/`
- Ou use `http://localhost:4848` para fazer o deploy

### 5. Acessar Aplicação

```
http://localhost:8080/controlebens
```

---

## 🖥️ Funcionalidades

- Cadastro e edição de **Bens**
- Cadastro e edição de **Tipos de Produtos**
- Listagem com filtros
- Cálculo automático de depreciação
- Conversores JSF personalizados
- Layout com PrimeFaces

---

## 🧮 Cálculo de Depreciação

Implementado em `DepreciacaoService.java`:

```java
public class DepreciacaoService {

    public BigDecimal calcularDepreciacaoAnual(Bem bem) {
        if (bem.getPrecoCompra() == null || bem.getValorResidual() == null || bem.getVidaUtil() == null || bem.getVidaUtil() == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal diferenca = bem.getPrecoCompra().subtract(bem.getValorResidual());
        return diferenca.divide(BigDecimal.valueOf(bem.getVidaUtil()), 2, RoundingMode.HALF_UP);
    }
}
```

### Fórmula:

```
Depreciação Anual = (Preço de Compra - Valor Residual) / Vida Útil
```

---

## 🛠️ Tecnologias Usadas

- **Java 8**
- **JSF 2.3**
- **PrimeFaces 8**
- **PostgreSQL**
- **Hibernate / JPA**
- **GlassFish 5**

---

## 🧑‍💻 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir *issues* ou enviar *pull requests*.

---

## 📜 Licença

Distribuído sob a licença **MIT**.

---

## 📫 Contato

- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin&style=for-the-badge)](https://www.linkedin.com/in/enzo-brito-b85471284)
- [![Gmail](https://img.shields.io/badge/Email-Contact-red?logo=gmail&style=for-the-badge)](mailto:enzoj820@gmail.com)

Feito com ❤️ e muito café!
