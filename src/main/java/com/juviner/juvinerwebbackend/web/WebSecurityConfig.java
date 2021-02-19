package com.juviner.juvinerwebbackend.web;

//@EnableWebSecurity
//@Configuration
public class WebSecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
    /*@Autowired
    private CustomAuthenticationProvider ap;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/user/self").authenticated()
            .antMatchers("/api/threads").authenticated()
            .antMatchers("/api/user/**").authenticated()
            .antMatchers("/**").permitAll()
            .and()
            .formLogin().loginPage("/login").permitAll();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ap);
    }
    */
}
