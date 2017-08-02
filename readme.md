## Introduction
This project is for me to experiment with Kotlin+Spring and share my learnings along the way.

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
Mockito matchers such as any() return Java types, and are not compatible with Kotlin nonNullable types. In order to make the following work:

```kotlin
given(auth0Mock.buildAuthorizeUrl(any(), any())).willReturn("someUrl")
```
I would have to change the method arguments to be nullables, 
which defeats the purpose of having non-nullables in the first place:
```kotlin
fun buildAuthorizeUrl(req: HttpServletRequest?, redirectUri: String?): String {
	return auth0.buildAuthorizeUrl(req, redirectUri).build()
}
```

A google search led me to this [article](https://medium.com/elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791)
by Elye. I followed the advice and introduced a helper function, which wraps Mockito's any(), and returns
a Kotlin generic. This allows it to match against Kotlin nonNullable method parameters:
```kotlin
fun <T> any(): T {
    Mockito.any<T>()
    return null as T
}
```

## Auth0 & Testing
#### Mocking the AuthenticationController
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
The functionality of these methods is simple enough, that I can live with them not being tested.