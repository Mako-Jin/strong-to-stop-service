package com.smk.cpp.sts.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.smk.cpp.sts.base.constant.FileConstants;
import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.model.AuthorityModel;
import com.smk.cpp.sts.base.model.UserDetailsModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:33
 */
public class JwtTokenUtils {
    
    /**
     * @Description: 生成Access_token
     * @Author: Jin-LiangBo
     * @Date: 2021/8/20 15:46
     * @param userDetails
     * @return java.lang.String
     */
    public static String createAccessToken(UserDetailsModel userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(IConstants.USERNAME, userDetails.getUsername());
        claims.put(IConstants.AUTHORIZES, userDetails.getAuthorities());
        return createJwtToken(userDetails.getUserId(), claims, 
                IConstants.JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS, 
                IConstants.ACCESS_TOKEN);
    }

    /**
     * @Description: 生成refresh_token
     * @Author: Jin-LiangBo
     * @Date: 2021/8/20 15:47
     * @param userDetails
     * @return java.lang.String
     */
    public static String createRefreshToken(UserDetailsModel userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(IConstants.USERNAME, userDetails.getUsername());
        return createJwtToken(userDetails.getUserId(), claims, 
                IConstants.JWT_REFRESH_TOKEN_VALIDITY_IN_SECONDS , 
                IConstants.REFRESH_TOKEN);
    }

    /**
     * @Description: web login create token
     * @Author: Jin-LiangBo
     * @Date: 2021/8/12 18:53
     * @param userId
     * @return java.lang.String
     */
    public static String createJwtToken(String userId, Map<String, Object> claims, 
                Long expiration, String subject) {
        String privateKey = EncryptUtil.RSAUtil.getPrivateKey(FileConstants.PRIVATE_KEY_PATH);
        RSAPrivateKey rsaPrivateKey = EncryptUtil.loadPrivateKeyByStr(privateKey);
        return Jwts.builder()
                .setClaims(claims)
                // jti
                .setId(userId)
                // 发布者
                .setIssuer(IConstants.COMPANY_ORGANIZATION_NAME)
                // 发布时间
                .setIssuedAt(new Date())
                // 生效时间
                .setNotBefore(new Date())
                // 听众，即接受者
                .setAudience(userId)
                // 主题
                .setSubject(subject)
                // 签名过期时间
                .setExpiration(generateExpiration(expiration)) 
                .signWith(SignatureAlgorithm.RS384, rsaPrivateKey)
                .compact();
    }

    /**
     * @Description: 计算token过期时间
     * @Author: Jin-LiangBo
     * @Date: 2021/8/16 19:52
     * @param expiration
     * @return java.util.Date
     */
    private static Date generateExpiration (Long expiration) {
        if (expiration == null || expiration <= 0) {
            expiration = IConstants.DEFAULT_JWT_TOKEN_VALIDITY_IN_SECONDS;
        }
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * @Description: 从token中获取用户信息
     * @Author: Jin-LiangBo
     * @Date: 2021/8/16 19:53
     * @param token
     * @return null
     */
    public static UserDetailsModel getUserFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        String json = JSONObject.toJSONString(claims.get(IConstants.AUTHORIZES, ArrayList.class));
        UserDetailsModel userDetails = new UserDetailsModel(
            claims.getAudience(),
            String.valueOf(claims.get(IConstants.USERNAME)),
            JSONObject.parseObject(json, new TypeReference<Set<AuthorityModel>>() {
        })
        );
        return userDetails;
    }
    
    /**
     * @Description: 从token中获取用户名
     * @Author: Jin-LiangBo
     * @Date: 2021/8/16 19:53
     * @param token
     * @return null
     */
    public static String getUsernameFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return String.valueOf(claims.get(IConstants.USERNAME));
    }

    public static String getAudienceFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return String.valueOf(claims.getAudience());
    }
    
    public static Boolean isTokenExpired(String token) throws SignatureException {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis() 
                - IConstants.PRE_EXPIRED_TIME * 1000));
    }

    public static Date getExpirationDateFromToken(String token) throws SignatureException {
        final Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
    
    private static Claims getClaimsFromToken (String token) throws SignatureException {
        try {
            String publicKey = EncryptUtil.RSAUtil.getPublicKey(FileConstants.PUBLIC_KEY_PATH);
            RSAPublicKey rsaPublicKey = EncryptUtil.loadPublicKeyByStr(publicKey);
            return Jwts.parser().setSigningKey(rsaPublicKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException exception) {
            return exception.getClaims();
        }
    }
    

//     private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//         return (lastPasswordReset != null && created.before(lastPasswordReset));
//     }
//
//     // Device用户检测当前用户的设备，用不到的话可以删掉（使用这个需要添加相应的依赖）
// //    private String generateAudience(Device device) {
// //        String audience = AUDIENCE_UNKNOWN;
// //        if (device.isNormal()) {
// //            audience = AUDIENCE_WEB;
// //        } else if (device.isTablet()) {
// //            audience = AUDIENCE_TABLET;
// //        } else if (device.isMobile()) {
// //            audience = AUDIENCE_MOBILE;
// //        }
// //        return audience;
// //    }
//
//     private Boolean ignoreTokenExpiration(String token) {
//         String audience = getAudienceFromToken(token);
//         return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
//     }
//
//     public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//         final Date created = getCreatedDateFromToken(token);
//         return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
//                 && (!isTokenExpired(token) || ignoreTokenExpiration(token));
//     }
//
//     public String refreshToken(String token) {
//         String refreshedToken;
//         try {
//             final Claims claims = getClaimsFromToken(token);
//             claims.put(CLAIM_KEY_CREATED, new Date());
//             refreshedToken = generateToken(claims);
//         } catch (Exception e) {
//             refreshedToken = null;
//         }
//         return refreshedToken;
//     }
//
//     //TODO,验证当前的token是否有效
//     public Boolean validateToken(String token, UserDetails userDetails) {
//         JwtUser user = (JwtUser) userDetails;
//         final String username = getUsernameFromToken(token);
//         final Date created = getCreatedDateFromToken(token);
//         return (username.equals(user.getUsername())&& !isTokenExpired(token));
//     }

}
