package com.univ.gest.repository;

import com.univ.gest.domain.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    // ✅ pour dashboard teacher
    List<SessionEntity> findByTeacher_IdOrderByDayOfWeekAscStartTimeAsc(Long teacherId);

    // ✅ pour dashboard student (par group)
    List<SessionEntity> findByGroup_IdOrderByDayOfWeekAscStartTimeAsc(Long groupId);

    // Overlap condition: existing.start < newEnd AND newStart < existing.end
    @Query("""
        SELECT s FROM SessionEntity s
        WHERE s.dayOfWeek = :day
          AND s.room.id = :roomId
          AND (:excludeId IS NULL OR s.id <> :excludeId)
          AND s.startTime < :endTime
          AND :startTime < s.endTime
    """)
    List<SessionEntity> findRoomConflicts(@Param("day") int day,
                                         @Param("roomId") Long roomId,
                                         @Param("startTime") LocalTime startTime,
                                         @Param("endTime") LocalTime endTime,
                                         @Param("excludeId") Long excludeId);

    @Query("""
        SELECT s FROM SessionEntity s
        WHERE s.dayOfWeek = :day
          AND s.teacher.id = :teacherId
          AND (:excludeId IS NULL OR s.id <> :excludeId)
          AND s.startTime < :endTime
          AND :startTime < s.endTime
    """)
    List<SessionEntity> findTeacherConflicts(@Param("day") int day,
                                            @Param("teacherId") Long teacherId,
                                            @Param("startTime") LocalTime startTime,
                                            @Param("endTime") LocalTime endTime,
                                            @Param("excludeId") Long excludeId);

    @Query("""
        SELECT s FROM SessionEntity s
        WHERE s.dayOfWeek = :day
          AND s.group.id = :groupId
          AND (:excludeId IS NULL OR s.id <> :excludeId)
          AND s.startTime < :endTime
          AND :startTime < s.endTime
    """)
    List<SessionEntity> findGroupConflicts(@Param("day") int day,
                                          @Param("groupId") Long groupId,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime,
                                          @Param("excludeId") Long excludeId);
}
