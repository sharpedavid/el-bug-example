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
