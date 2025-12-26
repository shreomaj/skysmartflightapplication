import com.ai.skysmart.dto.TicketDto;
import com.ai.skysmart.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // 1️⃣ Get ALL tickets for a PNR
    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<List<TicketDto>> getTicketsByPnr(@PathVariable String pnr) {
        return ResponseEntity.ok(ticketService.getTicketsByPnr(pnr));
    }

    // 2️⃣ Get single ticket details
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    // 3️⃣ Update seat number
    @PutMapping("/{ticketId}/seat")
    public ResponseEntity<TicketDto> updateSeat(
            @PathVariable Long ticketId,
            @RequestParam Integer newSeat) {

        return ResponseEntity.ok(ticketService.changeSeat(ticketId, newSeat));
    }

    // 4️⃣ Download individual boarding pass PDF
    @GetMapping("/{ticketId}/boarding-pass")
    public ResponseEntity<byte[]> downloadBoardingPass(@PathVariable Long ticketId) {
        byte[] pdf = ticketService.generateBoardingPass(ticketId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=boarding-pass-" + ticketId + ".pdf")
                .body(pdf);
    }

    // 5️⃣ Download full booking ticket PDF (contains all passengers)
    @GetMapping("/booking/{bookingId}/ticket-pdf")
    public ResponseEntity<byte[]> downloadBookingTicket(@PathVariable String bookingId) {
        byte[] pdf = ticketService.generateBookingPdf(bookingId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=booking-ticket-" + bookingId + ".pdf")
                .body(pdf);
    }
}
