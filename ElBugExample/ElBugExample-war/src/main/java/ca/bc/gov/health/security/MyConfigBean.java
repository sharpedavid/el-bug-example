package ca.bc.gov.health.security;

import javax.inject.Named;

@Named
public class MyConfigBean {
    
    private String myvalue = "mouse8";

    public String getMyvalue() {
        return myvalue;
    }
    
}
    