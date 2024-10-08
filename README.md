# Weather Monitor

## Описание

Weather Monitor — это система для сбора и обработки данных о погоде. Она состоит из двух частей:
1. **Сенсор** — приложение, которое регистрируется на сервере и отправляет данные о погоде.
2. **Сервер** — RESTful API, который принимает данные от сенсора, сохраняет их в базу данных и предоставляет клиентам актуальные данные о погоде.

Проект реализован с использованием **Java**, **Spring Boot**, и базы данных **PostgreSQL**. Для генерации документации API используется **Swagger**.

## Технологии

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL** или **MySQL**
- **Swagger/OpenAPI** для документации API
- **Maven** для управления зависимостями

## Установка и запуск

### Шаги для запуска проекта:

1. **Клонирование репозитория**
    ```bash
    git clone <URL репозитория>
    cd weather-monitor
    ```

2. **Настройка базы данных**
    - Убедитесь, что у вас установлен **PostgreSQL** или **MySQL**.
    - Создайте базу данных:
      ```sql
      CREATE DATABASE weather_monitor;
      ```

3. **Настройка параметров подключения к базе данных**
    - Откройте файл `src/main/resources/application.properties` и измените настройки для подключения к базе данных:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/weather_monitor
      spring.datasource.username=<Ваш пользователь БД>
      spring.datasource.password=<Ваш пароль БД>
      spring.datasource.driver-class-name=org.postgresql.Driver

      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
      ```

4. **Сборка и запуск проекта**
    - Выполните следующие команды для сборки и запуска проекта:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

5. **Доступ к API и документации**
    - API будет доступно по адресу:
      ```
      http://localhost:8080
      ```
    - Swagger UI (документация API) будет доступна по адресу:
      ```
      http://localhost:8080/swagger-ui.html
      ```

## API Эндпоинты

### 1. **Регистрация сенсора**
- **URL:** `POST /sensors/registration`
- **Описание:** Регистрирует новый сенсор и возвращает уникальный ключ для дальнейшей передачи данных.
- **Пример запроса:**
    ```json
    {
      "name": "Sensor name"
    }
    ```
- **Пример ответа:**
    ```json
    {
      "key": "8bcb5ffa-ff4d-4214-a727-bb01ab90ceaa"
    }
    ```

### 2. **Отправка данных о погоде**
- **URL:** `POST /sensors/{key}/measurements`
- **Описание:** Отправляет данные о погоде от зарегистрированного сенсора.
- **Пример запроса:**
    ```json
    {
      "value": 24.5,
      "raining": false
    }
    ```

### 3. **Получение всех активных сенсоров**
- **URL:** `GET /sensors`
- **Описание:** Возвращает список всех активных сенсоров.

### 4. **Получение последних 20 измерений сенсора**
- **URL:** `GET /sensors/{key}/measurements`
- **Описание:** Возвращает 20 последних измерений для указанного сенсора.

### 5. **Получение актуальных измерений от всех сенсоров**
- **URL:** `GET /sensors/measurements`
- **Описание:** Возвращает актуальные измерения, сделанные в последние 60 секунд.

## Структура проекта

- `src/main/java/com/weathermonitor/weather_monitor/`
    - `controller/` — содержит контроллеры для обработки HTTP-запросов.
    - `entity/` — содержит сущности базы данных.
    - `repository/` — содержит репозитории для работы с базой данных.
    - `service/` — содержит бизнес-логику приложения.
    - `config/` — содержит конфигурацию Swagger для документации API.

- `src/main/resources/`
    - `application.properties` — настройки для базы данных и приложения.

## Тестирование

Для запуска тестов выполните следующую команду:

```bash
mvn test