package io.swagger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class ApiKey {
    @Id
    private String key;

    public ApiKey(String key) {
        this.key = key;
    }
}

