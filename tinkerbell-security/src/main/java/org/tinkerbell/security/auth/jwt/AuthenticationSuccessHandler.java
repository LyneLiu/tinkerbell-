package org.tinkerbell.security.auth.jwt;

/**
 * Created by fan.jin on 2016-11-07.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.tinkerbell.security.entity.bo.UserTokenState;
import org.tinkerbell.security.entity.po.AuthUser;
import org.tinkerbell.security.util.TokenUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

	@Value("${app.user_cookie}")
	private String USER_COOKIE;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
    private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication ) throws IOException, ServletException {
		clearAuthenticationAttributes(request);
		User user = (User)authentication.getPrincipal();

		/**
		 **************************************************
		 *
		 *  在login认证成功的情况下，生成JWT的token并将该token更新至数据库，供TokenAuthenticationFilter认证使用！
		 *  比如：jwtTokenService.update(user.getUsername(),gettoken);
		 *
		 **************************************************
		 */

		String jws = tokenUtil.generateJsonWerbToken( user.getUsername() );

        // Create token auth Cookie
        Cookie authCookie = new Cookie( TOKEN_COOKIE, ( jws ) );
		authCookie.setPath( "/" );
		authCookie.setHttpOnly( true );
		authCookie.setMaxAge( EXPIRES_IN );
		// Create flag Cookie
		Cookie userCookie = new Cookie( USER_COOKIE, ( user.getUsername() ) );
		userCookie.setPath( "/" );
		userCookie.setMaxAge( EXPIRES_IN );
		// Add cookie to response
		response.addCookie( authCookie );
		response.addCookie( userCookie );
		// JWT is also in the response
		UserTokenState userTokenState = new UserTokenState(jws, EXPIRES_IN);
		String jwtResponse = objectMapper.writeValueAsString( userTokenState );
		response.setContentType("application/json");
		response.getWriter().write( jwtResponse );
	}
}
