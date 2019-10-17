package dao;

import dto.ItemDTO;
import entity.Item;
import service.SessionFactoryService;
import org.hibernate.Session;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

public class ItemDAO {
    private SessionFactoryService sfService = new SessionFactoryService();
    private Logger logger = Logger.getLogger(ItemDAO.class);

    public List<Item> getItems() {
        Session session = null;
        List<Item> items = null;
        try {
            session = sfService.getOpenedSession();
            session.beginTransaction();
            CriteriaQuery<Item> query = session.getCriteriaBuilder().createQuery(Item.class);
            query.from(Item.class);
            items = session.createQuery(query).getResultList();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            logger.error(e);
        }
        finally {
            sfService.closeSession(session);
        }
        return items;
    }

    public Item getItemById(long id) {
        Session session = null;
        Item item = null;
        try {
            session = sfService.getOpenedSession();
            session.beginTransaction();
            CriteriaQuery<Item> query = session.getCriteriaBuilder().createQuery(Item.class);
            Root<Item> root = query.from(Item.class);
            query.select(root).where(root.get("id").in(id));
            List<Item> results = session.createQuery(query).getResultList();
            item = results.isEmpty() ? null : results.get(0);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            sfService.closeSession(session);
        }
        return item;
    }

    public void save(Item obj) {
        Session session = null;
        try {
            session = sfService.getOpenedSession();
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e)
        } finally {
            sfService.closeSession(session);
        }
    }
}
