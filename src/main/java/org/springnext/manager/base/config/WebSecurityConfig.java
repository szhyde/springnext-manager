package org.springnext.manager.base.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springnext.manager.base.security.ValidateCodeFilter;
import org.springnext.manager.base.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ValidateCodeFilter validateCodeFilter;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/assets/**");
        web.ignoring().antMatchers("/code");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class).authorizeRequests()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //指定登录页是"/login"
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")//设置登录成功后默认跳转
                .permitAll()
                .and()
                //开始iframer
                .headers().frameOptions().disable()
                .and()
                .logout()
                .logoutUrl("/logout")
                //设置CSRF功能跳过logout，可以让登出用get方式访问
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) 
                .logoutSuccessUrl("/login")//设置退出登录后的默认url是
                .permitAll();
        
      //所以监控不用权限
//    	http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
//        .anyRequest().permitAll();
        
        //h2的控制台跳过csrf验证
        http.csrf().ignoringAntMatchers("/h2-console/**");

    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth
            .userDetailsService(customUserDetailsService())
            .passwordEncoder(passwordEncoder());

    }
    
    /**
     * 自定义UserDetailsService，从数据库中读取用户信息
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
    	//使用官方默认的BCryptPasswordEncoder加密
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 自定义UserDetailsService，从数据库中读取用户信息
     * @return
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }

}