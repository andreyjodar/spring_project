package com.github.andreyjodar.backend.features.user.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.andreyjodar.backend.core.model.BaseEntity;
import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.bid.model.Bid;
import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.feedback.model.Feedback;
import com.github.andreyjodar.backend.features.role.model.Role;
import com.github.andreyjodar.backend.features.role.model.RoleType;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
 
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "validity_code", length = 6)
    @JsonIgnore
    private String validityCode;

    @Column(name = "expiration_date")
    @JsonIgnore
    private LocalDateTime expirationDate;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "recipient")
    private List<Feedback> receivedFeedbacks;

    @OneToMany(mappedBy = "author")
    private List<Feedback> writtenFeedbacks;

    @OneToMany(mappedBy = "bidder")
    private List<Bid> bids;

    @OneToMany(mappedBy = "author")
    private List<Category> categories;

    @OneToMany(mappedBy = "auctioneer")
    private List<Auction> auctions;

    @JsonIgnore
    public Boolean isAdmin() {
        return roles.stream().anyMatch(role -> role.getType() == RoleType.ADMIN);
    }

    @JsonIgnore
    public Boolean isSeller() {
        return roles.stream().anyMatch(role -> role.getType() == RoleType.SELLER);
    }

    @JsonIgnore
    public Boolean isBuyer() {
        return roles.stream().anyMatch(role -> role.getType() == RoleType.BUYER);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getType().name()))
            .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return active;
    }
}
