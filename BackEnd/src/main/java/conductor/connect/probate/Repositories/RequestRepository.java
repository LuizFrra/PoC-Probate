package conductor.connect.probate.Repositories;

import conductor.connect.probate.Models.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Integer> {
}
