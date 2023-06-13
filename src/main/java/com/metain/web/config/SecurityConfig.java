package com.metain.web.config;

import com.metain.web.service.HrService;
import com.metain.web.service.HrServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration//이 클래스를 통해 bean 등록이나 각종 설정을 하겠다는 표시
@EnableWebSecurity// Spring Security 설정할 클래스라고 정의
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private HrServiceImpl hrServiceImpl;


    @Bean //실제 인증을 한 이후에 인증이 완료되면 Authentication객체를 반환을 위한 bean등록
    public DaoAuthenticationProvider authenticationProvider(HrServiceImpl hrServiceImpl) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(hrServiceImpl);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }


    //HTTP 관련 보안 설정 **가장 중요
    //URL 별 권한 설정, 로그인, 세션 등등 HTTP 보안 관련 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();// //일반 사용자에 대해 Session을 저장하지 않으므로 csrf을 disable 처리함.
                //인증, 인가가 필요한 URL 지정
        http
                .authorizeRequests()
                //접속 허락
                .antMatchers("/loginEmp").permitAll()
                //해당 URL에 진입하기 위해서 Authentication(인증, 로그인)이 필요함
                .antMatchers("/", "/index").authenticated()
                .antMatchers("/mypage/**").hasAnyAuthority("ROLE_SW", "ROLE_DR", "ROLE_GJ", "ROLE_CJ", "ROLE_HR", "ROLE_ADMIN")
                .antMatchers("/certification/**").hasAnyAuthority("ROLE_SW", "ROLE_DR", "ROLE_GJ", "ROLE_CJ", "ROLE_HR", "ROLE_ADMIN")
                .antMatchers("/vacation/**").hasAnyAuthority("ROLE_SW", "ROLE_DR", "ROLE_GJ", "ROLE_CJ", "ROLE_HR", "ROLE_ADMIN")
                .antMatchers("/vacation/vacation-applyform").hasAnyAuthority("ROLE_SW", "ROLE_DR", "ROLE_GJ", "ROLE_CJ", "ROLE_HR", "ROLE_ADMIN")
                .antMatchers("/vacation/vacation-afterapply").hasAnyAuthority("ROLE_SW", "ROLE_DR", "ROLE_GJ", "ROLE_CJ", "ROLE_HR", "ROLE_ADMIN")
                .antMatchers("/vacation/vacation-req-list/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/vacation/request-vacation/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/hr/**").hasAnyAuthority("ROLE_HR")
                .anyRequest().permitAll();
        http
                .formLogin()
                .usernameParameter("empSabun")
                .passwordParameter("empPwd")
                .loginPage("/loginEmp")
                .defaultSuccessUrl("/index")
                .failureUrl("/loginEmp")
                .permitAll(); // 로그인 페이지에는 모두 접근 가능하도록 설정

                //로그아웃
        http
                .logout()
                .logoutUrl("/logout") // 로그아웃 URL 설정
                .logoutSuccessUrl("/loginEmp") // 로그아웃 성공 시 이동할 페이지 설정
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");


                //접근이 거부된 경우 이동할 페이지 설정
        http
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) ->
                        // 페이지 이동
                        response.sendRedirect("/index"));


//        http
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/admin")
//                .invalidateHttpSession(true);
//                .and()
//                .logout()
//                .logoutUrl("/security-login/logout")
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    //실제 인증을 진행할 Provider
    //DB로부터 id, pwd가 맞는지, 해당  유저가 어떤 권한을 갖는지 체크
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(hrServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(authenticationProvider(hrServiceImpl));
//        auth.authenticationProvider(user)

    }


    //이미지,자바스크립트,css 디렉토리 보안 설정
    //파일을 접근 가능하게 처리하는 소스 입력
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }
}


