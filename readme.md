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
I would have to change the method arguments to be nullables, which is undesirable:
```kotlin
	fun buildAuthorizeUrl(req: HttpServletRequest?, redirectUri: String?): String {
		return auth0.buildAuthorizeUrl(req, redirectUri).build()
	}
```

TBC: Solution / Workarounds

related reading: https://medium.com/elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791

## Auth0 & Testing
#### Mocking
When authenticating your requests with Auth0 in Java, it is convenient to use the Auth0 AuthenticationController.

One of the methods you are going to be using for this, returns an AuthorizeUrl instance, which you will be redirecting your requests to.

```kotlin
	@RequestMapping(value = LOGIN, method = arrayOf(RequestMethod.GET))
	fun login(req: HttpServletRequest): String {
		val redirectUri = req.scheme + "://" + req.serverName + ":" + req.serverPort + CALLBACK
		val authorizeUrl = auth0.buildAuthorizeUrl(req, redirectUri).build()
		return "redirect:" + authorizeUrl
	}
```
However, this class' constructor and its builder are package private; preventing it from being instantiated
outside of the auth0 library, and effectively preventing the controller to be mocked, as it is not possible
to specify a return value for the buildAuthorizeUrl method.

As a workaround, I ended up creating a component to encapsulate the AuthenticationController, with methods I can mock:
```kotlin
@Component
class Auth0Wrapper(@Autowired val auth0: AuthenticationController) {
	fun handle(req: HttpServletRequest): Tokens {
		return auth0.handle(req)
	}

	fun buildAuthorizeUrl(req: HttpServletRequest, redirectUri: String): String {
		return auth0.buildAuthorizeUrl(req, redirectUri).build()
	}
}
```