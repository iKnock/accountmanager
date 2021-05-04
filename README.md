# Accountmanager API



> This is a ReadMe template to help save you time and effort.

---

## Description

Accountmanager api is an wrapper which perform three main operations based on the Fabrick API on the cash account of user. The Operations are Get Balance of an account, List of transactions and Transfer.

#### Technologies

- Spring Boot 
- Redis 
- Mongo DB
- Maven

---

## Architecture

<p align="center">
  <img src="https://github.com/iKnock/accountmanager/blob/main/arch-dig.png" />
</p>

---

## How To Use

#### Installation

- Install and Start MongoDb
- Install and Start Redis
- Install Maven
- Build and run account manager

#### API Reference

## Section One
One of the services of the Account Manager API is to setup the API for further usage. Using this Section one can add API information (operations), Update existing API information, Search existing API Information and remove API Information In order to use the API correctly, its necessary to add the three API information into the configuration of the AccountManager API. Using the below endpoint information and the subsequent json body, its possible to configure the three operations to use throught the Account Manager API.

1. Add API Information/Configuration

Request: 

> GET: /api/fabrick/info/v1.0/add

```html
Request Body:

- For account-balance use the following json body
{
    "operationName": "account-balance",
    "domain": "https://sandbox.platfr.io",
    "endpoint": "/api/gbs/banking/v4.0/accounts/{accountId}/balance",
    "method": "GET",
    "uriParam": [
        "accountId"
    ],
    "uriQuery": [
    ],
    "httpHeader": [
        {
            "headerName": "Auth-Schema",
            "headerValue": "Put auth schema here",
            "mandatory": true
        },
        {
            "headerName": "Api-Key",
            "headerValue": "Put api key here",
            "mandatory": true
        }
    ]
}

- or For list-transactions use the following json body

{
    "operationName": "list-transactions",
    "domain": "https://sandbox.platfr.io",
    "endpoint": "/api/gbs/banking/v4.0/accounts/{accountId}/transactions",
    "method": "GET",
    "uriParam": [
        "accountId"
    ],
    "uriQuery": [
        "fromAccountingDate",
        "toAccountingDate"
    ],
    "httpHeader": [
        {
            "headerName": "Auth-Schema",
            "headerValue": "Put auth schema here",
            "isHeaderMandatory": true
        },
        {
            "headerName": "Api-Key",
            "headerValue": "Put api key here",
            "isHeaderMandatory": true
        }
    ]
}

- Or For transfer use the following json body

{
   "operationName":"transfer",
   "domain":"https://sandbox.platfr.io",
   "endpoint":"/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers",
   "method":"POST",
   "uriParam":[
      "accountId"
   ],
   "uriQuery":[
      
   ],
   "httpHeader":[
      {
         "headerName":"Auth-Schema",
         "headerValue":"Put auth schema here",
         "mandatory":true
      },
      {
         "headerName":"Api-Key",
         "headerValue":"Put api key here",
         "mandatory":true
      },
      {
         "headerName":"X-Time-Zone",
         "headerValue":"Europe/Rome",
         "mandatory":true
      }
   ]
}
```

## Section Two

1. Read Balance

>GET: /api/fabrick/account/v1.0/balance/{accountId}?operationName=account-balance

```html
Request:
    Uri Param: accountId
    URI Variables: operationName=account-balance
```

2. List Transaction

>GET: api/fabrick/account/v1.0/transaction/{accountId}?operationName=list-transactions&fromAccountingDate=2019-01-01&toAccountingDate=2019-04-01

```html
Request: 
    URI Param: accountId
    URI Variables: operationName=list-transaction,fromAccountingDate=2019-11-11, 
    toAccountingDate=2019-12-11
```

3. Transfer

>POST: /api/fabrick/account/v1.0/transfer/{accountId}?operationName=transfer
```html
Request: 
    URI Param: accountId
    URI Variables: operationName=transfer
    Request Body:
    
```