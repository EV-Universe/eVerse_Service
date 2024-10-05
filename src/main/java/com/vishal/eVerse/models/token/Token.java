package com.vishal.eVerse.models.token;

import com.vishal.eVerse.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue
    private  Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    public TokenType  tokenType = TokenType.BEARER;

    private boolean expired;
    private boolean revoked;

    // This is a many-to-one relationship between the Token and User entities.
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
