package com.demo.assignment.repository;

import com.demo.assignment.entity.LichChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LichChieuRepository extends JpaRepository<LichChieu, Integer> {
    List<LichChieu> findByPhim_MaNhom(String maNhom);
    @Query("SELECT COUNT(lc) > 0 " +
            "FROM LichChieu lc " +
            "WHERE lc.rap.maRap = :maRap " +
            "AND lc.startTime < :newEndTime " +
            "AND lc.endTime > :newStartTime")
    boolean existsOverlappingSchedules(@Param("maRap") Integer maRap,
                                       @Param("newStartTime") LocalDateTime newStartTime,
                                       @Param("newEndTime") LocalDateTime newEndTime);
}
