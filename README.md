
# Financial API

Este trabalho é realizado no ambito da cadeira de computação em Núvem

Arquitetura de micro serviços:

![Arquitecture](https://user-images.githubusercontent.com/36867483/111453481-87f06200-870b-11eb-924d-295b8fc510cf.png)

Foram criados 5 containers , 2 persistentes para as bases de dados e 3 transientes que são os microserviços

# Correr a API




# Fazer chamadas Http

Usando um software como por exemplo o Postman é possivel fazer pedidos http fácilmente.

Primeiro deve-se fazer um registo de um novo utilizador, para isso faça:

Pedido: POST |
url: localhost:8080/api/register |
body:
{
    "username":"user@user.com",
    "password":"123456789",
    "firstName":"User",
    "lastName":"User"
}

Depois de ter criado um utilizador temos de fazer o login

Pedido: POST |
url: localhost:8080/api/login |
body:
{
    "username":"user@user.com",
    "password":"123456789",
}

Devemos ter recebido um JWT token para authenticar o nosso utilizador,
apartir daqui devemos usar sempre este token Bearer nos nosso próprio pedidos

Resposta ao login:
{
    "Acess token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZW5vaXRhQGdtYWlsLmNvbSIsImV4cCI6MTYxNjg0MTU3Nywicm9sIjpbIlJPTEVfQ0xJRU5UIl19.eJ0SMs6lyWuURHk
    6P6w_v96R41FEw9R_VDEc8gqcWllHmUw7QdqOYb7RLtty5mDPxWIXL_P-nbIiVrhKdAQWIA"
}

Agora podemos ver quantas chamadas o nosso utilizador tem disponiveis:

Pedido: GET |
url: localhost:8080/api/user/remainingCalls |
authorization: {token}

Como acabámos de criar o utilizador temos 0 calls ao sistema, devemos então comprar mais calls para chamar serviços

Pedido: GET|
url: localhost:8080/api/user/buy |
authorization: {token} |
body:
{
  "cardNumber": "1234 4654 1231 5463",
  "cvc": 123,
  "validDate": "02/25",
  "amount": 100,
  "currency": "EUR"
}
Podemos agora fazer um pedido a um serviço:


Pedido: GET |
url: localhost:8081/api/stock/top |
authorization: {token}


