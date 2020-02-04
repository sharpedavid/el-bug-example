# el-bug-example
Demonstrates that EL doesn't always work when used in the OpenIdAuthenticationDefinition in an EAR file.

Steps to reproduce:

1. Run `mvn package`.
2. Deploy EAR file to Payara Server 5.192.
3. Open http://localhost:8080/FMDB/ in a web browser.

On deploy or browser access, one of two things will happen.

Bug:

```
  ElBugExample-ear2558790153234021915 was successfully deployed in 4,054 milliseconds.|#]
  JASPIC: http msg authentication fail
javax.el.PropertyNotFoundException: ELResolver cannot handle a null base Object with identifier 'myConfigBean'
	at com.sun.el.lang.ELSupport.throwUnhandled(ELSupport.java:68)
	at com.sun.el.parser.AstIdentifier.getValue(AstIdentifier.java:126)
	at com.sun.el.parser.AstValue.getBase(AstValue.java:150)
	at com.sun.el.parser.AstValue.getValue(AstValue.java:199)
  ```
  
  "Good" behaviour:
  
  ```
    ElBugExample-ear was successfully deployed in 1,492 milliseconds.|#]
  JASPIC: http msg authentication fail
javax.ws.rs.ProcessingException: URI is not absolute
	at org.glassfish.jersey.client.ClientRuntime.invoke(ClientRuntime.java:263)
	at org.glassfish.jersey.client.JerseyInvocation.lambda$invoke$0(JerseyInvocation.java:729)
	at org.glassfish.jersey.internal.Errors.process(Errors.java:292)
	at org.glassfish.jersey.internal.Errors.process(Errors.java:274)
  ```
  
  The code under test is:
  
  ```java
  package ca.bc.gov.health.security;
import fish.payara.security.annotations.OpenIdAuthenticationDefinition;

@OpenIdAuthenticationDefinition(
       providerURI = "#{myConfigBean.myvalue}",
       clientId = "ElBugExample",
       clientSecret = "someSecret",
       redirectURI = "#{myConfigBean.myvalue}",
       scope = { "openid" }

)
public class KeycloakSecurityBean {
    
}
```

When the test fails, we get "ELResolver cannot handle a null base Object with identifier 'myConfigBean'" because the `providerURI = "#{myConfigBean.myvalue}"` expression doesn't work. When the test "passes" we get a "URI is not absolute" because I've just put a dummy value in the URI. I don't know why it works sometimes and fails others. 

To get it to pass or fail, try redeploying the application, or restarting the server, or recompiling the application -- I'm really not sure what triggers the failure.
