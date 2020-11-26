package cz.cvut.fit.horaluk1.gradle.repository;

import cz.cvut.fit.horaluk1.gradle.entity.Screening;
import cz.cvut.fit.horaluk1.gradle.entity.TicketSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketSeatRepository  extends JpaRepository<TicketSeat, Long> {

    List<TicketSeat> findAllByOwnerEmail(String email);

    List<TicketSeat> findAllByScreeningId(int id);
}
