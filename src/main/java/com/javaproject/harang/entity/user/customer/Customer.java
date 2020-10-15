package com.javaproject.harang.entity.user.customer;

import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.security.AuthorityType;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String userId;

    private String password;

    private String name;

    private Integer age;

    private Integer phoneNumber;

    private String imagePath;

    private String intro;

    public AuthorityType getType() {
        return AuthorityType.USER;
    }

}
