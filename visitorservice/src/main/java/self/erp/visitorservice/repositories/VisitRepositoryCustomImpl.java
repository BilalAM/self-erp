package self.erp.visitorservice.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class VisitRepositoryCustomImpl implements VisitRepositoryCustom {

    @PersistenceContext
    private EntityManager emManager;

    @Override
    public int getlastVisitID() {
        TypedQuery<Integer> lastIdQuery = emManager.createNamedQuery("VISITORS.getLastVisitID", Integer.class);
        return lastIdQuery.getSingleResult();
    }
}
