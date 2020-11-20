package com.javaproject.harang.entity.user.customer;

import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.security.AuthorityType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

    @OneToMany(mappedBy = "scoreTargetId", cascade = CascadeType.ALL)
    List<Score> scores = new ArrayList<>();

    public double getAverageScore() {
        return BigDecimal.valueOf(scores.stream().mapToInt(Score::getScore).average().orElse(0))
                .setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    public AuthorityType getType() {
        return AuthorityType.USER;
    }

    public Customer updateFileName(String fileName) {
        this.imagePath = fileName;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(userId, customer.userId) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(age, customer.age) &&
                Objects.equals(phoneNumber, customer.phoneNumber) &&
                Objects.equals(imagePath, customer.imagePath) &&
                Objects.equals(intro, customer.intro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, password, name, age, phoneNumber, imagePath, intro);
    }
}
