# Список необходимых проверок

## Описание

В данной директории представлены **Expect-скрипты**, покрывающие весь функционал клиента,  
а также присутствует bash-скрипт `run_tests.sh` для запуска тестов через консоль (без использования TestNG).

## Структура проекта

```
.
├── run_tests.sh                # Скрипт для запуска тестов без TestNG
├── preparing_test.sh           # Вспомогательный скрипт для запуска Expect-скриптов
├── test_*.exp                  # Expect-скрипты разделенные по негативным и позитивным сценариям
├── invalid_domain_list.exp     # Вспомогательный список не валидных доменных имен
├── invalid_IP_list.exp         # Вспомогательный список не валидных IP-адресов
└── utils.exp                   # Вспомогательный скрипт для работы Expect-скриптов
```

## Позитивные сценарии

1. **Изменение конфигурации приложения**  
   После изменения параметров конфига по одному и всех сразу, параметры сохраняются и корректно отображаются.

2. **Вывод списка текущих пар "домен-IP" (без проверки на заведомо существующий адрес)**  
   Клиент должен вывести хотя бы одну пару, либо в случае если файл пуст — соответствующее сообщение.

3. **Вывод списка текущих пар "домен-IP" (проверка на заведомо существующий адрес)**  
   Клиент должен вывести пару, которая однозначно должна присутствовать в списке.

4. **Поиск по IP**  
   Клиент должен вывести доменное имя, соответствующее заданному IP-адресу.

5. **Поиск по домену**  
   Клиент должен вывести IP-адрес, соответствующий заданному домену.

6. **Добавление новой пары "домен - IP"**  
   После ввода доменного имени и IP-адреса клиент должен вывести сообщение об успешном добавлении новой пары.

7. **Удаление пары по IP**  
   После ввода IP-адреса клиент должен вывести сообщение об успешном удалении пары.

8. **Удаление пары по домену**  
   После ввода домена клиент должен вывести сообщение об успешном удалении пары.

## Негативные сценарии

1. **Изменение конфигурации приложения**  
   При изменении IP-адреса и порта хоста проверяется валидность данных элементов.

2. **Поиск по IP**  
   При вводе IP-адреса проверяется его валидность. Если IP-адреса не существует в списке, то клиент должен вывести соответствующее сообщение.

3. **Поиск по домену**  
   При вводе домена проверяется его валидность. Если домена не существует в списке, то клиент должен вывести соответствующее сообщение.

4. **Добавление новой пары "домен - IP"**  
   При вводе домена и IP-адреса проверяется их валидность.  
   Если домен или IP-адрес уже существует в списке, то клиент должен отменить добавление и вывести соответствующее сообщение.

5. **Удаление пары по IP/домену**  
   При вводе домена и IP-адреса проверяется их валидность.  
   Если домен или IP-адрес не существует в списке, то клиент должен вывести соответствующее сообщение.

***

Для запуска тестов см. [инструкцию](https://github.com/OwLRUS/infotecs_internship/tree/main/java/test).

## Контакты

Для вопросов или обратной связи вы можете связаться с автором репозитория через [telegram](https://t.me/OwLRU) (@OwLRU).

---

> **Infotecs** — ведущий российский разработчик решений в сфере информационной безопасности.
