package org.booking.core.service.reservation;

import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceBean implements ReservationService {


    private ReservationRepository reservationRepository;

    public ReservationServiceBean(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existingUser = reservationRepository.findById(id).get();
// TODO: 02.12.2023 update 
        return reservationRepository.save(existingUser);
    }


    @Override
    public boolean delete(Long id) {
        try {
            reservationRepository.findById(id).get();
            reservationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Reservation getById(Long userId) {
        return reservationRepository.findById(userId).get();
    }

    @Override
    public List<Reservation> getAllUsers() {
        return reservationRepository.findAll();
    }
}
