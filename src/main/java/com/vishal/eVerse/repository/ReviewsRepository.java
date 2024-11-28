package com.vishal.eVerse.repository;

import com.vishal.eVerse.models.reviews.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Review, Integer> {

    @Query(value = """
      select r from Review r inner join User u\s
      on r.user.id = u.id\s
      where u.id = :id\s
      """)
    List<Review> findAllReviewsByUserId(Integer id);

    @Query(value = """
      select r from Review r inner join EvStations s\s
      on r.station.id = s.id\s
      where s.id = :id\s
      """)
    List<Review> findAllReviewsByStationId(Integer id);
}
