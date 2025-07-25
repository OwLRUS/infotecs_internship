# Инструкция по сборке и запуску тестов

## Описание

В данной директории реализованы тесты на основе фреймворка **TestNG** и **Expect-скриптов**.  
Список тестов с позитивными и негативными сценариями представлен в [/scripts](https://github.com/OwLRUS/infotecs_internship/tree/main/java/test/scripts).

## Структура проекта

```
.
├── scripts/                      # Директория с Expect-скриптами
├── src/main/java/org/example/    # Исходные файлы тестов
├── tests-1.0.jar                 # Исполняемый jar-файл тестов
├── dependency-reduced-pom.xml    # Файл конфигурации Maven
├── pom.xml                       # Файл конфигурации Maven
└── test.xml                      # Файл конфигурации TestNG
```

## Сборка

Сборка выполняется из папки проекта тестов командой:

```
mvn clean package
```

**ПРИМЕЧАНИЕ:** Для сборки приложения требуются:
- JDK версии 8 и выше  
- Maven  
- Expect

## Запуск

Вариант 1: запуск собранного `.jar`-файла:

```
java -jar ./*путь к файлу*/tests-1.0.jar
```

**ПРИМЕЧАНИЕ:** Для запуска требуются:
- JRE версии 8 и выше  
- Expect

Вариант 2: запуск средствами Maven:

```
mvn clean test
```

**ПРИМЕЧАНИЕ:** Для запуска требуются:
- JRE версии 8 и выше  
- Maven  
- Expect

## Проверка установленных компонентов

Проверить наличие и версии JRE, JDK, Maven и Expect можно следующими командами:

```
java -version  
javac -version  
mvn -v  
expect -v
```

## Результат выполнения

Результатом работы будет вывод пройденных тестов:

```
[INFO] -------------------------------------------------------  
[INFO]  T E S T S  
[INFO] -------------------------------------------------------  
[INFO] Running TestSuite  
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.564 s -- in TestSuite  
[INFO]  
[INFO] Results:  
[INFO]  
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0  
```

## Контакты

Для вопросов или обратной связи вы можете связаться с автором репозитория через [telegram](https://t.me/OwLRU) (@OwLRU).

---

> **Infotecs** — ведущий разработчик решений в области информационной безопасности.

