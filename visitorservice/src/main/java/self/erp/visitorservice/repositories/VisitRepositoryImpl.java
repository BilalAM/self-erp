package self.erp.visitorservice.repositories;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service public abstract class VisitRepositoryImpl implements VisitRepository {

        @PersistenceContext(unitName = "Schema_Visitors") private EntityManager emManager;

        public int getLastVisitID() {
                Query lastIdQuery = emManager.createNamedQuery("VISITORS.getLastVisitID");
                int lastId = lastIdQuery.getFirstResult();
                return lastId;
        }
}
