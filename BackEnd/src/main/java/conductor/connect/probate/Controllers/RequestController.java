package conductor.connect.probate.Controllers;

import conductor.connect.probate.DTO.RequestDTO;
import conductor.connect.probate.Services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    public RequestService requestService;

    @GetMapping("/{id}")
    public RequestDTO getById(@PathVariable int id) {
        return requestService.findById(id);
    }
}
