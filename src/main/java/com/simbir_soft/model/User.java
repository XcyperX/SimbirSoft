package com.simbir_soft.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean ban;

    @Column(name = "end_ban_date")
    private LocalDateTime endBanDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_room", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "room_id")})
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(String login) {
        this.login = login;
    }

    public User(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public String getUserName() {
        return getLogin();
    }

    public String getUserRole() {
        for (Permission nameRole : role.getPermissions()) {
            if (nameRole.equals(Permission.ADMIN) ||
                    nameRole.equals(Permission.MODERATOR) ||
                    nameRole.equals(Permission.USER)) {
                return nameRole.getPermission();
            }
        }
        return "None";
    }

    public Boolean isAdmin() {
        return role.getPermissions().contains(Permission.ADMIN);
    }

    public Boolean isModerator() {
        return role.getPermissions().contains(Permission.MODERATOR);
    }

    public Boolean isUser() {
        return role.getPermissions().contains(Permission.USER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthority();
    }

    @Override
    public String getUsername() {
        return login;
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
