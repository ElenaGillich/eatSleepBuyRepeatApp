package org.example.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AppUserRepository appUserRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        AppUser appUser = appUserRepository.findById(oAuth2User.getName())
                .orElseGet(()-> createAppUser(oAuth2User));

        return oAuth2User;
    }

    private AppUser createAppUser(OAuth2User oAuth2User){
        AppUser newUser = AppUser.builder()
                .id(oAuth2User.getName())
                .userName(oAuth2User.getAttribute("login"))
                .role("USER")
                .build();

        appUserRepository.save(newUser);
        return newUser;
    }
}
