package com.koreamall.dto.user;


import com.koreamall.dto.FileDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class UserDTO  implements UserDetails, OAuth2User {
    @NotBlank
    @Length(min = 4, max = 10)
//    @Pattern(regexp = "^(?=.*[a-z][A-Z])(?=.*[0-9])$")
    private String id;
    private String ci;
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "(010|011|017|018)-[0-9]{3,4}-[0-9]{4}")
    private String phone;
    private String name;
    private String nickName;
    private FileDTO profileImg;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority("READ") );
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
