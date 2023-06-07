package com.metain.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    //HTTP 관련 보안 설정 **가장 중요
    //URL 별 권한 설정, 로그인, 세션 등등 HTTP 보안 관련 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //인증, 인가가 필요한 URL 지정
                .authorizeRequests()
                //해당 URL에 진입하기 위해서 Authentication(인증, 로그인)이 필요함
                .antMatchers("/mypage/**").authenticated()
                .antMatchers("/vacation/**").authenticated()
                .antMatchers("/hr/**").authenticated()
                .antMatchers("/certification/**").authenticated()
                //해당 URL에 진입하기 위해서 Authorization(인가, ex)권한이 ADMIN인 유저만 진입 가능)이 필요함
//                .antMatchers("/security-login/admin/**").hasAuthority(UserRole.ADMIN.name())
//                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("empSabun")
                .passwordParameter("empPwd")
                .loginPage("/member/login-form")
                .defaultSuccessUrl("/index")
                .failureUrl("/member/login-form");
//                .and()
//                .logout()
//                .logoutUrl("/security-login/logout")
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    //실제 인증을 진행할 Provider
    //DB로부터 id, pwd가 맞는지, 해당  유저가 어떤 권한을 갖는지 체크
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.authenticationProvider(userRoleProvider);

    }


    //이미지,자바스크립트,css 디렉토리 보안 설정
    //파일을 접근 가능하게 처리하는 소스 입력
    @Override
    public void configure(WebSecurity web) {
    }






}
