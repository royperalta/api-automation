#language:en
Feature: Automation APIS

  @GET_API
  Scenario: GET
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And config query params
      | key  | value |
      | page | 2     |
    When execute API
      | method | url                         |
      | GET    | https://reqres.in/api/users |
    Then check status code "200"

  @GET_API_LIST
  Scenario: LIST <RESOURCE>
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And config query params
      | key  | value |
      | page | 2     |
    When execute API
      | method | url                           |
      | GET    | https://reqres.in/api/unknown |
    Then check status code "200"

  @POST_API
  Scenario: CREATE
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And configure the body request: "body.json"
      | key  | value    |
      | name | morpheus |
      | job  | leader   |
    When execute API
      | method | url                          |
      | POST   | https://reqres.in/api/users/ |
    Then check status code "201"

  @UPDATE_PUT
  Scenario: UPDATE PUT
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And configure the body request: "body.json"
      | key  | value         |
      | name | morpheus      |
      | job  | zion resident |
    And config paths variables
      | key | value |
      | id  | 2     |
    When execute API
      | method | url                              |
      | PUT    | https://reqres.in/api/users/{id} |
    Then check status code "200"

  @UPDATE_PATCH
  Scenario: UPDATE PATCH
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And configure the body request: "body.json"
      | key  | value         |
      | name | morpheus      |
      | job  | zion resident |
    And config paths variables
      | key | value |
      | id  | 2     |
    When execute API
      | method | url                              |
      | PUT    | https://reqres.in/api/users/{id} |
    Then check status code "200"


  @POST_REGISTER_SUCCESSFULL
  Scenario: REGISTER SUCCESSFULL
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And configure the body request: "register.json"
      | key      | value              |
      | email    | eve.holt@reqres.in |
      | password | pistol             |
    When execute API
      | method | url                            |
      | POST   | https://reqres.in/api/register |
    Then check status code "200"


  @POST_REGISTER_UNSUCCESSFULL
  Scenario: REGISTER UNSUCCESSFULL
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And configure the body request: "register-nook.json"
      | key   | value              |
      | email | eve.holt@reqres.in |
    When execute API
      | method | url                            |
      | POST   | https://reqres.in/api/register |
    Then check status code "400"


  @POST_LOGIN_SUCCESSFULL
  Scenario:LOGIN_SUCCESSFULL
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And configure the body request: "login.json"
      | key      | value              |
      | email    | eve.holt@reqres.in |
      | password | cityslicka         |
    When execute API
      | method | url                         |
      | POST   | https://reqres.in/api/login |
    Then check status code "200"

  @POST_LOGIN_UNSUCCESSFULL
  Scenario: LOGIN UNSUCCESSFULL
    Given config the header
      | headers      | value            |
      | Content-Type | application/json |
    And configure the body request: "login-nook.json"
      | key      | value              |
      | email    | eve.holt@reqres.in |
    When execute API
      | method | url                         |
      | POST   | https://reqres.in/api/login |
    Then check status code "400"