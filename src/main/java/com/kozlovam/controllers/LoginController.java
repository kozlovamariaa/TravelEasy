package com.kozlovam.controllers;

//import com.kozlovam.models.Token;
import com.kozlovam.models.Token;
import com.kozlovam.models.User;
import com.kozlovam.security.JWTAuthorizationFilter;
import com.kozlovam.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Value("${SECRET}")
    private String secret;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    private Token token = new Token();

    public LoginController(){
    }

    @PostMapping
    public String login(@RequestParam("userlogin") String email, @RequestParam("userpassword") String password) throws UnsupportedEncodingException {
        User selectedUser = userService.loadUserByUserLogin(email);
        if(selectedUser != null){
            if(passwordEncoder.matches(password, selectedUser.getUserpassword())){
                token.setToken(jwtAuthorizationFilter.getJWTToken(email));
                token.setUser(selectedUser);
                userService.saveUserToken(token);
                return token.getToken();
            }
        }
        return "error, try again";
    }
}
