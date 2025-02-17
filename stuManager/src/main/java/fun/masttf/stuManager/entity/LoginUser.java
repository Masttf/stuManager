package fun.masttf.stuManager.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginUser implements UserDetails {
    private String username;
    private String password;
    private List<String> permissions;
    private List<GrantedAuthority> authorities;
    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert roles to GrantedAuthority
        if(this.authorities != null)return this.authorities;
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // If you want to implement account expiration logic, modify here
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // If you want to implement account lock logic, modify here
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // If you want to implement credentials expiration logic, modify here
    }

    @Override
    public boolean isEnabled() {
        return true; // Assuming `active` field indicates if user is enabled.
    }
}
