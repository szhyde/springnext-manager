package org.springnext.manager.base.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.code.kaptcha.Constants;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
	
	@Autowired
	private MessageSource messageSource;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(StringUtils.equals((request.getContextPath()+"/login"), request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {

                // 1. 进行验证码的校验
            	String verifyCode=request.getParameter("verifycode").toUpperCase();  
                //判断验证码输入是否正确  
                if(!verifyCode.equals(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY))){  
                	// 2. 如果校验不通过，调用SpringSecurity的校验失败处理器
                    //authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateCodeException("验证码错误"));
                    
                	String strUrl = request.getContextPath() + "/login?error";
                    request.getSession().setAttribute("status", 0);
                    String message = messageSource.getMessage("ValidateCode.exception", null, "验证码错误", LocaleContextHolder.getLocale());
                    request.getSession().setAttribute("message", message);
                    request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new ValidateCodeException(message));
                    response.sendRedirect(strUrl);
                    
                    return ;
                }
                
        }
        // 3. 校验通过，就放行
        filterChain.doFilter(request , response);
	}

}
