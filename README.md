# Origin Payment Service

Provides functionality to save payment

#### Request

The following is a sample JSON request:

```JSON
{
    "referenceNumber": <<reference number>>,
    "bsb": <<bsb>>,
    "accountNumber": <<account number>>,
    "amount": <<amount>>
}
```

#### Response
Status : 201 Created

```JSON
{
    "message": "success"
}
```


#### Error
Error object representing error response sent to the API call

```JSON
{
    "code": <<error code>>,
    "message": <<error message>>,
    "timestamp": <<timestamp>>
}
```

<details>
  <summary>400 Bad Request: Invalid BSB</summary>

```JSON
{
    "code": "102",
    "message": "BSB number is invalid! ",
    "timestamp": "2022-02-07T14:33:30.110527"
}
```
</details>

<details>
  <summary>400 Bad Request: Invalid reference number</summary>
  
```JSON
{
    "code": "102",
    "message": "Reference number format is invalid!",
    "timestamp": "2022-02-07T14:39:07.894066"
}
```
</details>

<details>
  <summary>400 Bad Request: Invalid account number</summary>
  
```JSON
{
    "code": "102",
    "message": "Account number is invalid!",
    "timestamp": "2022-02-07T14:39:40.086030"
}
```
</details>


<details>
  <summary>400 Bad Request: amount < $00.01</summary>
  
```JSON
{
    "code": "102",
    "message": "Amount value cannot be less than $0.01! ",
    "timestamp": "2022-02-07T14:40:31.634341"
}
```
</details>

<details>
  <summary>400 Bad Request: amount > $2000.00</summary>
  
```JSON
{
    "code": "102",
    "message": "Amount cannot be more than $2000.00!",
    "timestamp": "2022-02-07T14:41:11.862112"
}
```
</details>

<details>
  <summary>403 Forbidden: accessing outside of allowed time</summary>
  
```JSON
{
    "code": "104",
    "message": "Payments are only allowed between 09:00 and 17:00, Monday to Thursday, or between 08:00 and 18:00 on Fridays.",
    "timestamp": "2022-02-07T14:44:46.298106"
}
```
</details>


<details>
  <summary>500 Internal Server Error: any other exception</summary>
  
```JSON
{
    "code": "103",
    "message": null,
    "timestamp": "2022-02-07T15:12:29.841901"
}
```
</details>


### Prerequisite
- maven
- Java 11

### Installation
Run the following command:

> mvn clean install

### Usage
Run the following command to start the application
> mvn spring-boot:run

Using [Postman](https://www.postman.com) and access the application using the url [http://localhost:8080/payment](http://localhost:8080/payment). <br>
Select the method as POST <br>
Select body as raw and JSON <br>
Following is a valid request:

```JSON
{
    "referenceNumber": "12345",
    "bsb": 123457,
    "accountNumber": 123456,
    "amount": "1900.01"
}
```

## Author

👤 **Peppa Pig**

- Github: [@r-peppa-pig](https://github.com/r-peppa-pig)

## License

This project is [MIT](https://github.com/kefranabg/readme-md-generator/blob/master/LICENSE) licensed.
