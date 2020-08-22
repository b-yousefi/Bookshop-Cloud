package b_yousefi.bookshop.usersws.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by: b.yousefi
 * Date: 5/10/2020
 */
@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    private static final long serialVersionUID = -8878137178165650086L;
    @Id
    private String id;

    @Pattern(regexp = "[a-zA-Z0-9]+([_ -]?[a-zA-Z0-9]){5,40}$", message = "Username doesn't follow the acceptable pattern")
    @NotBlank(message = "Username cannot be blank")
    @Indexed(name = "UK_USER_username", direction = IndexDirection.ASCENDING, unique = true)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @Column(updatable = false)
    @Builder.Default
    private String role = "ROLE_USER";

    private String pictureId;

    @Pattern(regexp = "^\\d{12}$", message = "Phone number is not acceptable")
    @Indexed(name = "UK_USER_phoneNumber", direction = IndexDirection.ASCENDING, unique = true)
    private String phoneNumber;

    @Email(message = "Email address has the wrong pattern")
    @Indexed(name = "UK_USER_email", direction = IndexDirection.ASCENDING, unique = true)
    private String email;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
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

    public boolean isAdmin() {
        return getRole().equals("ROLE_ADMIN");
    }
}
