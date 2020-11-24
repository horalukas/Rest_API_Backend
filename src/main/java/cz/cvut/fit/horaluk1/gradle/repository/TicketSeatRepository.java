package cz.cvut.fit.horaluk1.gradle.repository;

import cz.cvut.fit.horaluk1.gradle.entity.TicketSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketSeatRepository  extends JpaRepository<TicketSeat, Long> {
}
