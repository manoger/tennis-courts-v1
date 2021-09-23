package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO createGuest(CreateGuestDTO createGuestDTO) {
        GuestDTO guestDTO = GuestDTO.builder().name(createGuestDTO.getName()).build();
        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        guestRepository.findById(guestDTO.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found to be updated.");
        });
        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }

    public void deleteGuest(Long guestId) {
        var guest = guestRepository.findById(guestId);
        guestRepository.delete(guest.orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found to be deleted.");
        }));
    }

    public List<GuestDTO> listGuests() {
        return guestMapper.map(guestRepository.findAll());
    }

    public GuestDTO findGuestById(Long id) {
        return guestMapper.map(guestRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        }));
    }

    public List<GuestDTO> findGuestByNameContainingIgnoreCase(String name) {
        return guestMapper.map(guestRepository.findByNameContainingIgnoreCase(name));
    }
}
