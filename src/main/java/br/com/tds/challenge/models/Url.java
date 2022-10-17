package br.com.tds.challenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Url entity class that will be saved on database
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "url")
public class Url {
    //region ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String longUrl;

    private String shortUrl;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    private Integer numberOfAccesses = 0;

    private Double dailyAccessAverage = 0.0;

    private LocalDateTime lastAccessDate;
    //endregion

    //region CONSTRUCTORS
    public Url(String longUrl) {
        this.creationDate = LocalDateTime.now();
        this.longUrl = longUrl;
    }
    //endregion
}
