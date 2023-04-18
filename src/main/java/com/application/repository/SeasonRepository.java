package com.application.repository;

import com.application.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * {@link Season} related implementation of Spring {@link JpaRepository}.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
public interface SeasonRepository extends JpaRepository<Season, UUID> {

    @Query("select c from Season c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%'))")
    List<Season> search(@Param("searchTerm") String searchTerm);

}
