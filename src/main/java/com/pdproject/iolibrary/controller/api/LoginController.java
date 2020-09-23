package com.pdproject.iolibrary.controller.api;

import com.pdproject.iolibrary.dto.UserDTO;
import com.pdproject.iolibrary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/login-process")
@AllArgsConstructor
public class LoginController {

    private UserService userService;

    @GetMapping("/success")
    public ResponseEntity<UserDTO> loginSuccess(Authentication authentication){
        UserDTO result = null;
        try {
            result = userService.findByEmail(((User) authentication.getPrincipal()).getUsername());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/oauth-success")
    public ResponseEntity<UserDTO> loginOAuthSuccess(OAuth2AuthenticationToken token){
        UserDTO result = null;
        try {
            OAuth2User oAuth2User = token.getPrincipal();
            UserDTO dto = new UserDTO();
            dto.setName(oAuth2User.getAttribute("name"));
            dto.setEmail(oAuth2User.getAttribute("email"));
            dto.setAvatar(oAuth2User.getAttribute("picture"));
            result = userService.insert(dto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/fail")
    public ResponseEntity loginFail(){
        return ResponseEntity.badRequest().build();
    }
}
