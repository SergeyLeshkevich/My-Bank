
В ходе разработки консольного приложения для Clever-Bank следующий technological stack:
•	Серверные технологии:
       o Язык программирования:Java 17
       o Система автоматической сборки: Gradle 8.2
       o  База данных: PostgreSQL 15.2-2
       o JDBC
       o Lombok
       o Servlets

Были продемонстрированы следующие умения:
•	Строить модели (выделять классы, интерфейсы, их связи), разделять функционал между ними.

Был реализован следующий функционал:
•	Запуск приложения используя сервер приложений (Инструкция внизу).
•       Реализованы операции пополнения и снятия средств со счета;
•       Реализована возможность перевода средств другому клиенту Clever-Bank и
        клиенту другого банка. При всех операциях используется один объект транзакции.
        Обеспечена безопастность путем запрашивания логина и пароля(Пароли храняться в БД в зашифрованном виде).
        Реалиованны следующие проверки:
          1. Проверка логина на уникалность при регистрации нового клиента;
          2. При проведении операций по счету проверяется наличие всех счетов, соотвествие логина и пароля пользователя;
          3. При проведении банковских операций по снятию денег и переводе проверяется баланс на счете.
        Реализован функционал обмена транзакцией между банками, проверки и прерывания, с откатом всех изменений по счету, с указанием соответствующего статуса операции. 
•       Реализован функционал проверки по расписанию (раз в полминуты) соответствия даты на сегодня с последней датой месяца и в случае соответствия проходит операция по 
        начислению процентов (1% - значение подставляется из конфигурационного файла) на остаток счета. Проверка начисления процентов реализованна асинхронно.
        Реализованы CRUD операции для всех сущностей(входные и выходные данные внизу).
•       Значения хранятся в конфигурационном файле - .yml
•       После каждой операции в соответствии с приложением 1 сформируется чек (чеки хранятся в ресурсах в папке check в фотматах .txt и .pdf).
•       В ходе проектирования применялись шаблоны проектирования MVC и Builder(передоставлен Lombok).
•       В ресурсах в папке javaDoc хранится соответствующая документация.
•       Разработан функционал формирования выписки по транзакциям пользователя за месяц, год или весь период обслуживания клиента в форматах PDF и TXT(Servlet) в 
        соответствии с приложением 2.
•       Реализован рассчет и сохранение информацию о количестве потраченных и полученных средств за определенный период времени, используя SQL(хранится в ресурсах 
        в папке statement-money в формате .pdf) в соответствии с приложением 3 и вызывается с помощью Servlet.
•       Все таблицы в базе данных соответствуют третьей нормальной формой(3 НФ).
•       Реализовано сквозное логирование в файл с использованием Log4j 2 для методов сервиса.


Инструкция по запуску:
Чтобы запустить собранный WAR-файл(src/main/resources/Clever-Bank-1.0-SNAPSHOT.war):

1. Скачайте и установите apache-tomcat-10.1.13 с официального сайта.
   На компьютере должна быть установлена БД  PostgreSQL 15.2-2. Необходимо импортировать файл БД: check_db.sql (путь к файлу src/main/resources/copy_db.sql)

2. Разверните скачанный архив сервера Tomcat в папку на вашем компьютере.

3. Перейдите в папку Tomcat и найдите папку "webapps".

4. Скопируйте ваш WAR-файл в папку "webapps".

5. Запустите сервер Tomcat, запустив файл "startup.bat" (для Windows) или "startup.sh" (для Linux / macOS) в папке "bin" вашего установленного Tomcat.
   Важно отметить, что эти инструкции предполагают, что Tomcat настроен по умолчанию на порт 8080. Если вы изменили порт для своей установки Tomcat,
   замените "8080" в URL соответствующим портом.

6. После запуска сервера откройте веб-браузер и введите следующиe URL: 

1. Запуск класса Main
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/run_app

2. Получение счета:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/getAccount?number=64646546
{
  "id": 178,
  "number": "64646546",
  "User": {
    "id": 9,
    "login": "user9",
    "lastname": "Perry",
    "firstname": "Maurice"
  },
  "Bank": {
    "id": 4,
    "name": "Gama-Bank"
  },
  "balance": 10100.0,
  "openDate": {
    "ldt": "2011-дек.-18 02:33:50"
  }
}
3. Обновление баланса
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/upAccount?balance=100&number=06546513
status 200
4.Добавление баланса в БД
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/addAccount?id_user=1&id_bank=2&balance=100&number=3334
status 200
5.Удаление счета
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/deleteAccount?id=207
status 200
////////////
6. Получение банка:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/getBank?id=1
{
  "id": 1,
  "name": "Clever-Bank"
}

7. Обновление названия банка:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/updateBank?name=Test2&id=6
status 200
8.Добавление банка в БД
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/addAccount?id_user=1&id_bank=2&balance=100&number=3334
status 200
9.Удаление банка:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/deleteBank?id=6
status 200
/////////////
6. Получение транзакции:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/getTransaction?number=3527
{
  "id": 3584,
  "dataOperation": {
    "ldt": "2023-сент.-03 14:39:22"
  },
  "senderAccount": {
    "id": 168,
    "number": "46535465",
    "User": {
      "id": 18,
      "login": "user18",
      "lastname": "Newton",
      "firstname": "Emery"
    },
    "Bank": {
      "id": 1,
      "name": "Clever-Bank"
    },
    "balance": 5869.16,
    "openDate": {
      "ldt": "1997-сент.-03 19:35:41"
    }
  },
  "beneficiaryAccount": {
    "id": 0,
    "number": "64674764",
    "User": {
      "id": 0,
      "lastname": "Curtis",
      "firstname": "Melissa"
    },
    "Bank": {
      "id": 3,
      "name": "Beta-Bank"
    },
    "balance": 0.0
  },
  "senderBank": {
    "id": 1,
    "name": "Clever-Bank"
  },
  "beneficiaryBank": {
    "id": 3,
    "name": "Beta-Bank"
  },
  "typeOperation": "TRANSLATION",
  "status": "COMPLETED",
  "amount": -100.0
}
7. Обновление статуса транзакции:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/updateTransaction?id=3527&status=REJECTED
status 200
8.Добавление транзакции в БД:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/addTransaction?numASBank=156465432&numABBank=246865433&typeOperation=transacation&amount=1656&idSBank=1&idBBank=2
status 200
9.Удаление транзакции:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/deleteTransaction?id=3527
status 200
////////////
6. Получение пользователя:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/getUser?login=user1
{
  "id": 1,
  "login": "user1",
  "lastname": "Beverly",
  "firstname": "Owens"
}
7. Обновление фамилии пользователя:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/updateUser?login=test&lastname=newName
status 200
8.Добавление пользователя в БД:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/addUser?login=test2&lastname=lName&firstname=fName&password=1111
status 200
9.Удаление пользователя:
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/deleteUser?id=38
status 200
///////////
10.Получение выписки по счету
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/getMStatement?number=46535465&dateFor=2023-08-01&dateBefore=2023-09-03
                        Money statement         
                      Clever-Bank
Client                        | Emery Newton            
Check                         | 46535465                
Currency                      | BYN                     
Opening date                  | 1997-09-03              
Period                        | 2023-08-01 - 2023-09-03 
Date and time of formation    | 2023-09-03, 17:23:18    
Remainder                     | 5869,16 BYN             
          Receipt          |        Care        
_____________________________________________________
          10750,00         |        -1750,00           

11.Получение выписки по счету за месяц
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/extractMonth?number=46535465&login=user18
                        Extract         
                      Clever-Bank
Client                        | Newton Emery            
Check                         | 46535465                
Currency                      | BYN                     
Opening date                  | 1997-09-03              
Period                        | 2023-08-03 - 2023-09-03
Date and time of formation    | 2023-09-03, 19:43:02    
Remainder                     | 5869,16 BYN             
    Date  |        Notice                | Sum
_____________________________________________________
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|TRANSLATION Curtis Melissa    |-100,00   
2023-09-03|TRANSLATION Curtis Melissa    |-100,00   
2023-09-03|TRANSLATION Curtis Melissa    |-100,00   
2023-09-03|TRANSLATION Curtis Melissa    |-100,00   
2023-09-03|TRANSLATION Curtis Melissa    |-100,00

12.Получение выписки по счету за год
http://localhost:8080/Clever-Bank-1.0-SNAPSHOT/extractYear?number=46535465&login=user18
                        Extract         
                     Clever-Bank
Client                        | Newton Emery            
Check                         | 46535465                
Currency                      | BYN                     
Opening date                  | 1997-09-03              
Period                        | 2022-09-03 - 2023-09-03
Date and time of formation    | 2023-09-03, 19:45:33    
Remainder                     | 5869,16 BYN             
    Date  |        Notice                | Sum
_____________________________________________________
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|WITHDRAWAL Newton Emery       |-150,00   
2023-09-03|TRANSLATION Bruce Noah        |-100,00   
2023-09-03|REFILL Newton Emery           |150,00    
2023-09-03|TRANSLATION Curtis Melissa    |-100,00   
2023-09-03|TRANSLATION Curtis Melissa    |-100,00   
2023-09-03|TRANSLATION Curtis Melissa    |-100,00   
2023-09-03|TRANSLATION Curtis Melissa    |-100,00   
2023-09-03|TRANSLATION Curtis Melissa    |-100,00



