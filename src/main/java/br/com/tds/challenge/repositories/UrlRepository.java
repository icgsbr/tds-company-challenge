package br.com.tds.challenge.repositories;

import br.com.tds.challenge.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * API repository class. Responsible for database manipulations
 */
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    //region SELECTS
    @Query(value = "select url from Url url where url.shortUrl = :shortUrl")
    Optional<Url> findByShortUrl(@Param(value = "shortUrl") String shortUrl);
    //endregion

    //region UPDATES
    @Modifying
    @Transactional
    @Query(value = "update Url url set url.shortUrl = :shortUrl where url.id = :id")
    void updateShortUrl(@Param(value = "id") Long id, @Param(value = "shortUrl") String shortUrl);

    @Modifying
    @Transactional
    @Query(value = "update Url url set url.numberOfAccesses = (url.numberOfAccesses + 1) where url.id = :id")
    void updateNumberOfAccesses(@Param(value = "id") Long id);

    @Modifying
    @Transactional
    @Query(value = "update Url url set url.lastAccessDate = :lastAccess where url.id = :id")
    void updateLastAccessDate(@Param(value = "id") Long id, @Param(value = "lastAccess") LocalDateTime lastAccess);

    @Modifying
    @Transactional
    @Query(value = "update Url url set url.dailyAccessAverage = :average where url.id = :id")
    void updateDailyAccessAverage(@Param(value = "id") Long id, @Param(value = "average") Double average);
    //endregion
}
