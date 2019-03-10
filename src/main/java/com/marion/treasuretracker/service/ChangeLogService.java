package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.ChangeLog;
import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.ReasonType;
import com.marion.treasuretracker.repository.ChangeLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChangeLogService {
    private static Log log = LogFactory.getLog(ChangeLogService.class);

    @Autowired
    ChangeLogRepository changeLogRepository;

    @Autowired
    EntityManager entityManager;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<ChangeLog> queryChangeLog(String query) {
        Query nativeQuery = entityManager.createNativeQuery(query, ChangeLog.class);
        List<ChangeLog> entries = nativeQuery.getResultList();

        try {
            for (ChangeLog entry : entries) {
                log.info(objectMapper.writeValueAsString(entry));
            }
        } catch (JsonProcessingException jpEx) {
            log.error(jpEx.getMessage(), jpEx);
        }

        return entries;
    }

    public List<ChangeLog> listChangeLog() {
        String query = "SELECT * FROM changelog";
        return queryChangeLog(query);
    }

    void recordAcquiredItem(Item item) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.ACQUIRED);
        changeLog.setDescription(createAcquisitionDescription(item));
        changeLog.setItem(item);

        changeLogRepository.save(changeLog);
    }

    void recordSoldItem(Item item, Integer amount, Float value) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.SOLD);
        changeLog.setDescription(createSoldDescription(item, amount, value));
        changeLog.setItem(item);

        changeLogRepository.save(changeLog);
    }

    void recordUpdateItem(Item current, Item updated) {
        List<String> changes = createUpdateDescriptions(current, updated);
        for (String description : changes) {
            ChangeLog changeLog = new ChangeLog();
            changeLog.setTimestamp(new Date());
            changeLog.setReason(ReasonType.UPDATED);
            changeLog.setDescription(description);
            changeLog.setItem(updated);
            changeLog.setContainer(updated.getContainer());

            changeLogRepository.save(changeLog);
        }
    }

    void recordAcquiredContainer(Container container) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.ACQUIRED);
        changeLog.setDescription(createAcquisitionDescription(container));
        changeLog.setContainer(container);

        changeLogRepository.save(changeLog);
    }

    void recordUpdateContainer(Container current, Container updated) {
        List<String> changes = createUpdateDescriptions(current, updated);
        for (String description : changes) {
            ChangeLog changeLog = new ChangeLog();
            changeLog.setTimestamp(new Date());
            changeLog.setReason(ReasonType.UPDATED);
            changeLog.setDescription(description);
            changeLog.setContainer(updated);

            changeLogRepository.save(changeLog);
        }
    }

    void recordMoveItemContainer(String description, Item item) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.MOVED);
        changeLog.setDescription(description);
        changeLog.setItem(item);
        changeLog.setContainer(item.getContainer());

        changeLogRepository.save(changeLog);
    }

    private static String createAcquisitionDescription(Item item) {
        return String.format("Acquired '%s' from '%s'",
                item.getName(),
                StringUtils.isNotBlank(item.getSource()) ? item.getSource() : "unspecified");
    }

    private static String createAcquisitionDescription(Container item) {
        return String.format("Acquired '%s'",
                item.getName());
    }

    private static String createMoveDescription(Item current, Item updated) {
        return String.format("Moved '%s' from '%s' to '%s'",
                current.getName(),
                current.getContainer() != null ? current.getContainer().getName() : "none",
                updated.getContainer() != null ? updated.getContainer().getName() : "none");
    }

    private static List<String> createUpdateDescriptions(Item current, Item updated) {
        List<String> result = new ArrayList<>();
        if (!StringUtils.equals(current.getName(), updated.getName())) {
            result.add(String.format("name from '%s' to '%s'; ", current.getName(), updated.getName()));
        }
        if (!StringUtils.equals(current.getDescription(), updated.getDescription())) {
            result.add(String.format("description from '%s' to '%s'; ", current.getDescription(), updated.getDescription()));
        }
        if (!StringUtils.equals(current.getSource(), updated.getSource())) {
            result.add(String.format("source from '%s' to '%s'; ", current.getSource(), updated.getSource()));
        }
        if (!current.getAmount().equals(updated.getAmount())) {
            result.add(String.format("amount from '%s' to '%s'; ", current.getAmount(), updated.getAmount()));
        }

        if (!current.getItemType().equals(updated.getItemType())) {
            result.add(String.format("type from '%s' to '%s'; ", current.getItemType(), updated.getItemType()));
        }
        if (!current.getItemSubType().equals(updated.getItemSubType())) {
            result.add(String.format("subtype from '%s' to '%s'; ", current.getItemSubType(), updated.getItemSubType()));
        }
        if (!current.getValue().equals(updated.getValue())) {
            result.add(String.format("value from '%s' to '%s'; ", current.getValue(), updated.getValue()));
        }

        if (current.getContainer() != updated.getContainer()) {
            result.add(createMoveDescription(current, updated));
        }

        return result;
    }

    private static List<String> createUpdateDescriptions(Container current, Container updated) {
        List<String> result = new ArrayList<>();
        if (!StringUtils.equals(current.getName(), updated.getName())) {
            result.add(String.format("'%s' to '%s'; ", current.getName(), updated.getName()));
        }
        if (!StringUtils.equals(current.getDescription(), updated.getDescription())) {
            result.add(String.format("'%s'' to '%s'; ", current.getDescription(), updated.getDescription()));
        }

        return result;
    }

    private static String createSoldDescription(Item item, Integer amount, Float value){
        return String.format("Sold %d of '%s' for %s gp", amount, item.getName(), value);
    }
}
