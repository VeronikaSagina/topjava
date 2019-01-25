####   Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей с использованием <a href="https://zeroturnaround.com/rebellabs/java-tools-and-technologies-landscape-2016/" target="_blank">наиболее популярных инструментов и технологий Java</a>: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API
-  Основное внимание будет уделяться способам решения многочисленных проблем разработки в Spring/JPA, а также структурному (красивому и надежному) java кодированию и архитектуре приложения.
-  Каждая итерация проекта в закрепляется домашним заданием по реализации схожей функциональности. Следующее занятие начинается с разбора домашних заданий.
-  Большое внимание уделяется тестированию кода: в проекте 88 JUnit тестов.
-  Несмотря на относительно небольшой размер, приложение разрабатывается с нуля как большой проект (например мы используем кэш 2-го уровня Hibernate, настраиваем Jenkins для работы с ленивой загрузкой
Hibernate.
            Разбираются архитектурные паттерны: слои приложения и как правильно разбивать логику по слоям, когда нужно применять Data Transfer Object.
            Т.е на выходе получается не учебный проект, а хорошо маштабируемый шаблон для большого проекта на всех пройденных технологиях.
-   Большое внимание уделяется деталям: популяция базы, использование транзакционности, тесты сервисов и REST
            контроллеров, насторойка EntityManagerFactory,
            выбор реализации пула коннектов. Особое внимание уделяется работе с базой: через Spring JDBC, Spring ORM и
            Spring Data Jpa.
-   Используются самые востребованные на сегодняшний момент фреймворки: Maven, Spring Security 5
            вместе с Spring Security Test, наиболее удобный для работы с базой проект Spring Data Jpa, библиотека логирования logback, реализующая SLF4J, повсеместно используемый Bootstrap и jQuery.

#### <a href="http://calories-man.herokuapp.com" target=_blank>Менеджер калорий</a>

## План проекта
### Архитектура проекта. Персистентность.
-  Системы управления версиями
-  Java 8: Lambda, Stream API
-  Обзор используемых в проекте технологий и инструментов.
-  Maven
-  WAR. Веб-контейнер Tomcat. Сервлеты.
-  Логирование.
-  Обзор стандартных библиотек. Apache Commons, Guava
-  Слои приложения. Создание каркаса приложения.
-  Spring Framework. Spring Context.
-  Тестирование через JUnit.
-  Spring Test
-  Базы данных. PostgreSQL. Обзор NoSQL и Java persistence solution без ORM.
-  Скрипты инициализации базы. Spring Jdbc Template.
-  ORM. Hibernate. JPA.
-  Поддержка HSQLDB
-  Транзакции
-  Профили Maven и Spring
-  Пул коннектов
-  Spring Data JPA
-  Кэш Hibernate

### Разработка WEB
-  Spring кэш
-  Spring Web
-  JSP, JSTL, i18n
-  Tomcat maven plugin. JNDI
-  Spring Web MVC
-  Spring Internationalization
-  Тестирование Spring MVC
-  REST контроллеры
-  Тестирование REST контроллеров. Jackson.
-  jackson-datatype-hibernate. Тестирование через матчеры.
-  Тестирование через SoapUi. UTF-8
-  WebJars.
-  Bootstrap. jQuery datatables.
-  AJAX. jQuery. Notifications.
-  Spring Security
-  Spring Binding/Validation
-  Работа с datatables через Ajax.
-  Spring Security Test
-  Encoding password
-  CSRF (добавление в проект защиты от межсайтовой подделки запроса)
-  form-login. Spring Security Taglib
-  Handler interceptor
-  Spring Exception Handling
-  Смена локали
-  Фильтрация JSON через @JsonView
-  Защита от XSS (Cross Site Scripting)
-  Деплой в Heroku