package com.metain.web.config;

import com.metain.web.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration//이 클래스를 통해 bean 등록이나 각종 설정을 하겠다는 표시
@EnableWebSecurity// Spring Security 설정할 클래스라고 정의
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//

    @Autowired
    public SecuritySuccessHandler authenticationSuccessHandler;

    @Autowired
    private SecurityFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    //HTTP 관련 보안 설정 **가장 중요
    //URL 별 권한 설정, 로그인, 세션 등등 HTTP 보안 관련 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

//        http
//                .csrf().disable();// //일반 사용자에 대해 Session을 저장하지 않으므로 csrf을 disable 처리함.
                //인증, 인가가 필요한 URL 지정

        http
                .authorizeRequests()
                //접속 허락
                .antMatchers("/loginEmp").permitAll()
                //해당 URL에 진입하기 위해서 Authentication(인증, 로그인)이 필요함
                .antMatchers("/", "/index").hasAnyAuthority("ACTIVE", "RETIREE")
//                .antMatchers("/mypage/**").access("hasAuthority('ADMIN') and hasAuthority('ACTIVE')")

                .antMatchers("/mypage/update-mypage", "/mypage/alarm").hasAnyAuthority("ACTIVE")
                .antMatchers("/mypage/my-cert-list", "/mypage/my-cert-list/*").hasAnyAuthority("ACTIVE", "RETIREE")
                .antMatchers("/mypage/my-vac-list", "/mypage/my-vac-list/*").hasAnyAuthority("ACTIVE")

                .antMatchers("/certification/emp-cert-apply", "/certification/emp-cert-show").hasAnyAuthority("ACTIVE")
                .antMatchers("/certification/exper-cert-apply", "/certification/exper-cert-show").hasAnyAuthority("ACTIVE")
                .antMatchers("/certification/retire-cert-apply", "/certification/retire-cert-show").hasAnyAuthority("RETIREE")

//                .antMatchers("/vacation/vacation-list").access("hasAuthority('ADMIN') and hasAuthority('ACTIVE')")
//                .antMatchers("/vacation/vacation-detail").access("hasAuthority('ADMIN') and hasAuthority('ACTIVE')")
                .antMatchers("/vacation/vacation-list").hasAnyAuthority("ACTIVE")
                .antMatchers("/vacation/vacation-detail").hasAnyAuthority("ACTIVE")
                .antMatchers("/vacation/vacation-req-list").hasAnyAuthority("ACTIVE")
                .antMatchers("/hr/emp-list").hasAnyAuthority("ACTIVE")
                .antMatchers("/hr/emp-update", "/hr/insert-new-emp", "/hr/new-emp-list").hasAnyAuthority("ACTIVE")

                .antMatchers("/vacation/vacation-applyform").hasAnyAuthority("ACTIVE")
                .antMatchers("/vacation/vacation-afterapply").hasAnyAuthority("ACTIVE")
//                .antMatchers("/vacation/vacation-req-list").access("hasAuthority('ADMIN') and hasAuthority('ACTIVE')")
                .antMatchers("/vacation/request-vacation").hasAnyAuthority("ACTIVE")
                .antMatchers("/vacation/request-vacation/**").hasAnyAuthority("ACTIVE")


//                .antMatchers("/hr/emp-list").access("hasAnyAuthority('HR', 'ADMIN', 'DEPUTY') and hasAuthority('ACTIVE')")
//                .antMatchers("/hr/emp-update", "/hr/insert-new-emp", "/hr/new-emp-list").access("hasAuthority('HR') and hasAuthority('ACTIVE')")
                .anyRequest().permitAll();

        http
                .formLogin()
//                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .loginPage("/loginEmp")
                .usernameParameter("empSabun")
                .passwordParameter("empPwd")
                .defaultSuccessUrl("/index")
//                .failureUrl("/loginEmp")
                .permitAll(); // 로그인 페이지에는 모두 접근 가능하도록 설정

                //로그아웃
        http
                .logout()
                .logoutUrl("/logout") // 로그아웃 URL 설정 (= form action url)
                .logoutSuccessUrl("/loginEmp") // 로그아웃 성공 시 이동할 페이지 설정
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

                //접근이 거부된 경우 이동할 페이지  설 정
        http
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) ->
                        // 페이지 이동
                        response.sendRedirect("/error/access-denied"));

        http
                .sessionManagement()
                .maximumSessions(1) //같은 아이디로 1명만 로그인
                .maxSessionsPreventsLogin(true) //false :신규 로그인은 허용, 기존 사용자는 세션 아웃  true: 이미 로그인한 세션이있으면 로그인 불가
                .expiredUrl("/loginEmp"); //세션 아웃되면 이동할 url

    }

    //실제 인증을 진행할 Provider
    //DB로부터 id, pwd가 맞는지, 해당  유저가 어떤 권한을 갖는지 체크
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(hrServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(customAuthenticationProvider);
//        auth.authenticationProvider(user)

    }


    //이미지,자바스크립트,css 디렉토리 보안 설정
    //파일을 접근 가능하게 처리하는 소스 입력
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/**/favicon.ico");
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SecuritySuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SecurityFailureHandler();
    }


}


