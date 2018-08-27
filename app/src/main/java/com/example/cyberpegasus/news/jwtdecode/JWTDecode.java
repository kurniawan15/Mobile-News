package com.example.cyberpegasus.news.jwtdecode;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.cyberpegasus.news.tokenmanager.TokenManager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JWTDecode {
    TokenManager tokenManager;

    public void JWTDecode (){
        HashMap<String, String> user = tokenManager.getDetailLogin();
        String username = user.get(TokenManager.KEY_USER_NAME);
        String jwttoken = user.get(TokenManager.KEY_JWT_TOKEN);

        JWT jwt = new JWT(jwttoken);

        String issuer = jwt.getIssuer();
        //Returns the Issuer value or null if it's not defined.

        String subject = jwt.getSubject();
        //Returns the Subject value or null if it's not defined.

        List<String> audience = jwt.getAudience();
        //Returns the Audience value or an empty list if it's not defined.

        Date expiresAt = jwt.getExpiresAt();
        //Returns the Expiration Time value or null if it's not defined.

        Date notBefore = jwt.getNotBefore();
        //Returns the Not Before value or null if it's not defined.

        Date issuedAt = jwt.getIssuedAt();
        //Returns the Issued At value or null if it's not defined.

        String id = jwt.getId();
        //Returns the JWT ID value or null if it's not defined.

        Claim claim = jwt.getClaim(""+username);
        /*  Additional Claims defined in the token can be obtained by calling getClaim and passing the Claim name.
            If the claim can't be found, a BaseClaim will be returned. BaseClaim will return null on every method call except
            for the asList and asArray.*/




    }



}
