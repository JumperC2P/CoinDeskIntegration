# CoinDeskIntegration

## Introduction

This is a sample script for coin desk integration.
It uses a h2 databse in memory as it is just a sample project as well and don't want to store data into a real file for permenant storage.
Under `src/main/resourses` you can find the `database.sql` file which is used to create table and insert some data into the database when the application starts.
In the projects, there are 2 sets of APIs: Currency and Exchange Rate. We will explain more below.

### Postman
You can download or fork the postman collection for testing.

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/3849413-7e6a3428-2a6f-4112-8271-8786b6c2177d?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D3849413-7e6a3428-2a6f-4112-8271-8786b6c2177d%26entityType%3Dcollection%26workspaceId%3D687ec9db-07ef-4bb3-abd8-61276e5f7d16)

![image](https://github.com/user-attachments/assets/a653fc82-0a0b-4767-993e-ca6458eb22d9)

### Currency
It contains a CURD operation for table currency. The table has 3 records when application starts, which are USD, EUR, GBP. Then you will be able to operate the table via these APIs.
The APIs are:
- **GET** /currency: Get all the currency records.
- **POST** /currency: Create a new currency record.
- **PUT** /currency: Update a currency record by short name.
- **DELETE** /currency: Delete a currency record by short name.

### Exchange Rate

#### Get Exchange Rate from Coin Desk:

- **GET** /exchange/coindesk:  

    The API returns all original information of bitcoin price index, fetching from Coin Desk.

- **GET** /exchange/coindesk?fiatCurrency=USD: 

    The API returns the original information of the bitcoin price index of fiatCurrency in query string from Coin Desk. If the fiatCurrency is not supported by Coin Desk, you will receive `21` as code in response.

#### Get Exchange Rate:

- **GET** /exchange/coindesk/transform: 

  The API returns custom information of the rate of bitcoin to fiat, including Mandarin currency name. The rate information is also from Coin Desk.

- **GET** /exchange/coindesk/transform?fiatCurrency=USD:

  The API returns custom information of the rate of bitcoin to fiatCurrency, including Mandarin currency name. If the fiatCurrency is not supported by Coin Desk, you will receive `21` as code in response.

### Unit Test

In unit test, we cover the test for all the APIs in Currency and Exchange Rate and also Coin Desk API call.

For the Coin Desk API call (`CoinDeskClientTest.java`), we call Coin Desk API via Feign to get the response. We check whether the response is successful and the response contains at least 3 information of currencies, such as USD, GBP and EUR.

For the Currency API (`CurrencyControllerTest.java`), we cover the test for all the APIs in Currency. We check whether the response is successful and also test for the negative case, such as the currency is not found or currency duplicated.

For the Exchange Rate API (`ExchangeRateControllerTest.java`), we cover the test for all the APIs in Exchange Rate. We check whether the response is successful and also test for the negative case, such as the fiat currency is not supported by Coin Desk.

### How to run the project

You can run the project by running the main class `Application.java`.
Or you can run the project with docker. 
```shell
docker-compose up -d
```
The application will start at port 8080. You can access the APIs via `http://localhost:8080`.


