package com.juviner.juvinerwebbackend.web;

//@Component
public class CustomAuthenticationProvider /*implements AuthenticationProvider*/ {
    /*@Autowired
    private ClientRegistrationRepository clientRegistrations;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2AccessTokenResponseClient<OAuth2PasswordGrantRequest> accessTokenResponseClient = new DefaultPasswordTokenResponseClient();
        
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        OAuth2PasswordGrantRequest request = new OAuth2PasswordGrantRequest(clientRegistrations.findByRegistrationId("juviner"), name, password);
        OAuth2AccessTokenResponse response = accessTokenResponseClient.getTokenResponse(request);
        OAuth2AccessToken token = response.getAccessToken();
        OAuth2RefreshToken refreshToken = response.getRefreshToken();
        
        OAuth2User user = new OAuth2User() {
            @Override
            public Map<String, Object> getAttributes() {
                return null;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return authorities;
            }

            @Override
            public String getName() {
                return authentication.getName();
            }
        };
        
        OAuth2AuthorizedClient aClient = new OAuth2AuthorizedClient(clientRegistrations.findByRegistrationId("juviner"), authentication.getName(), token, refreshToken);
        
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new AbstractAuthenticationToken(authorities) {
            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return authentication.getName();
            }
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }*/
}
