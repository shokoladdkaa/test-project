# Test REST project

# Description
Stack: Spring Boot, Java 11
Application has one endpoint which fully covered by tests (Class 88%, Lines 96%).

# Application requirements:
1. Request and response must be in json format.
2. Request, response and date must be logged into separate file.
3. Project must be built in war.
4. If id not equals 1, the response must be null. Else if id equals 1, the response must be like this:
```json
{
    "id": 1,
    "fio": "Test Testov"
}
```
5. Must be just one http POST request.

# Application test using:

### Case #1: Get user with request where id = 1

#### Request
```bash
curl --location --request POST 'localhost:8081/api/v1/user' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1
}'
```
#### Response
```json
{
    "id": 1,
    "fio": "Test Testov"
}
```

### Case #2: Get user with request where id != 1

#### Request
```bash
curl --location --request POST 'localhost:8081/api/v1/user' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 2
}'
```
#### Request
Get empty response
```json
```
