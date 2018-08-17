package rest.service;


import javax.ejb.Stateless;

@Stateless
public class AuthenticationFilter {


    public Boolean validateToken(String token) {
        return false;
    }
}