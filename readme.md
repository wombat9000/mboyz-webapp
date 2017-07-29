## Introduction
This project is for me to experiment with Kotlin and Spring.

## Tech & Tools
- Heroku
- TravisCI
- Auth0
- PSQL
	- flyway
- Kotlin(JVM)
	- JUnit/Mockito/Hamcrest
	- Selenium
- SpringBoot
	- Security
	- Data
	- Thymeleaf
	
	
## Mockito ArgumentMatchers and Kotlin nullsafety
Mockito matchers any() and nonNull() don't work with nonNullable Kotlin types. In order to make the following work:

```kotlin
        `when`(auth0Mock.buildAuthorizeUrl(any(), any())).thenReturn("someUrl")

```
I would have to change the accepted method arguments to be nullables, which is undesirable:
```kotlin
      fun buildAuthorizeUrl(req: HttpServletRequest?, redirectUri: String?): String {
          return auth0.buildAuthorizeUrl(req, redirectUri).build()
      }
```

related: https://medium.com/elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791

## Future topics:
- Auth0 package private constructors and mocking
- Integration with Auth0
- KotlinJS and KotlinJVM
