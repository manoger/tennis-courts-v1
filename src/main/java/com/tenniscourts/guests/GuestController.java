package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/guest")
@AllArgsConstructor
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @PostMapping
    @ApiOperation(value = "Create a guest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "created"),
            @ApiResponse(code = 400, message = "An unexpected error occurred")
    })
    public ResponseEntity<Void> createGuest(@RequestBody CreateGuestDTO createGuestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(createGuestDTO).getId())).build();
    }

    @PatchMapping
    @ApiOperation(value = "Update a guest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 400, message = "An unexpected error occurred")
    })
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @DeleteMapping("/{guestId}")
    @ApiOperation(value = "Delete a guest")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "no content"),
            @ApiResponse(code = 400, message = "An unexpected error occurred")
    })
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "List all guests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 400, message = "An unexpected error occurred")
    })
    public ResponseEntity<List<GuestDTO>> listGuests(@RequestParam(name= "name",required = false) String name) {
        if(name == null)
            return ResponseEntity.ok(guestService.listGuests());
        else
            return ResponseEntity.ok(guestService.findGuestByNameContainingIgnoreCase(name));
    }

    @GetMapping("/{guestId}")
    @ApiOperation(value = "Find a guest by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 400, message = "An unexpected error occurred")
    })
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }
}
