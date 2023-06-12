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


//    @Bean //회원가입시 비번 암호화에 필요한 bean 등록
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

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
                .antMatchers("/mypage/**").hasAnyAuthority("ROLE_SW")
//                .antMatchers("/vacation/**").hasAnyAuthority("ROLE_SW", "ROLE_ADMIN")
//                .antMatchers("/hr/**").authenticated()
//                .antMatchers("/certification/**").authenticated()
                //해당 URL에 진입하기 위해서 Authorization(인가, ex)권한이 ADMIN인 유저만 진입 가능)이 필요함
//                .antMatchers("/security-login/admin/**").hasAuthority(UserRole.ADMIN.name())
                .anyRequest().permitAll();
        http
                .formLogin()
                .usernameParameter("empSabun")
                .passwordParameter("empPwd")
                .loginPage("/loginEmp")
                .defaultSuccessUrl("/index")
//                .loginProcessingUrl("/index")
                .failureUrl("/loginEmp")
                .permitAll(); // 로그인 페이지에는 모두 접근 가능하도록 설정
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


