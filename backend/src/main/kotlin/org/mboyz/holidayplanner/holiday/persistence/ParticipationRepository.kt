package org.mboyz.holidayplanner.holiday.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
@Transactional
interface ParticipationRepository: JpaRepository<ParticipationEntity, Long>