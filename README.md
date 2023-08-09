# Getting Started with Spring Boot and PostgreSQL

This guide will walk you through setting up your environment to run a Spring Boot application with PostgreSQL.

### Install Java and JDK

```bash
sudo apt update
sudo apt install openjdk-11-jdk
```
### Install Apache Maven

```bash
sudo apt update
sudo apt install maven
```

### Install PostgreSQL
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
```

### Create a PostgreSQL Database
```bash
psql -U postgres
CREATE DATABASE chadiss;
\q
```

### Clone and Run Spring Boot Application
```bash
git clone https://github.com/N-Pacis/CHADISS-Backend.git
cd <project_directory>
mvn spring-boot:run
```

## OR
### Install intellij IDEA and Use it to Run springboot application
