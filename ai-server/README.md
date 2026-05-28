# AI Psychological Assistant Backend

This is the Spring Boot backend for the Vue frontend in `../ai-vue`.

## Run Locally

1. Import the database script:

   ```bash
   mysql -u root -p < ../database/init.sql
   ```

2. Edit `src/main/resources/application.yml` and set your MySQL username/password.

3. Start the backend:

   ```bash
   mvn spring-boot:run
   ```

4. Change the Vue proxy in `ai-vue/vite.config.js` when you are ready to use this backend:

   ```js
   target: 'http://localhost:8080'
   ```

## First Java Concepts To Review

- `class`: a Java type, such as `SysUser`.
- `field`: data inside a class, such as `username`.
- `method`: behavior inside a class, such as `login`.
- `annotation`: metadata starting with `@`, such as `@RestController`.
- `interface`: a contract. MyBatis-Plus mapper interfaces provide database CRUD methods.
- `DTO`: request data object, such as `LoginRequest`.
- `Entity`: database table mapping object, such as `KnowledgeArticle`.
- `Controller`: HTTP API entrypoint.
- `Mapper`: database access layer.

## Current API Coverage

- `POST /user/login`
- `POST /user/register`
- `POST /user/logout`
- `GET /knowledge/category/tree`
- `GET /knowledge/article/page`
- `POST /knowledge/article`
- `GET /knowledge/article/{id}`
- `PUT /knowledge/article/{id}/status`
- `DELETE /knowledge/article/{id}`
- `POST /file/upload`
- `GET /psychological-chat/sessions`
- `GET /psychological-chat/sessions/{sessionId}/messages`
- `GET /emotion-diary/admin/page`
- `DELETE /emotion-diary/admin/{id}`

## Development Note

The current login password logic is intentionally simple for early local development. Demo users from `database/init.sql` can log in with `admin123` or `123456`. Before real deployment, replace this with BCrypt password hashing.
