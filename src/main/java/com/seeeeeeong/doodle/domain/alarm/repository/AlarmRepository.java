package com.seeeeeeong.doodle.domain.alarm.repository;

import com.seeeeeeong.doodle.domain.alarm.domain.Alarm;
import com.seeeeeeong.doodle.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmQueryRepository {


}
