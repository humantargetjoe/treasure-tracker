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

    void recordAcquiredItem(String description, Item item) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.ACQUIRED);
        changeLog.setDescription(description);
        changeLog.setItem(item);

        changeLogRepository.save(changeLog);
    }

    void recordUpdateItem(Item current, Item updated) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.UPDATED);
        changeLog.setDescription(createUpdateDescription(current, updated));
        changeLog.setItem(updated);

        changeLogRepository.save(changeLog);
    }

    void recordAcquiredContainer(String description, Container container) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.ACQUIRED);
        changeLog.setDescription(description);
        changeLog.setContainer(container);

        changeLogRepository.save(changeLog);
    }

    void recordUpdateContainer(Container current, Container updated) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.UPDATED);
        changeLog.setDescription(createUpdateDescription(current, updated));
        changeLog.setContainer(updated);

        changeLogRepository.save(changeLog);
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

    private static String createMoveDescription(Item current, Item updated) {
        return String.format("Moved '%s' from '%s' to '%s'",
                current.getName(),
                current.getContainer() != null ? current.getContainer().getName() : "none",
                updated.getContainer() != null ? updated.getContainer().getName() : "none");
    }


    private static String createUpdateDescription(Item current, Item updated) {
        StringBuilder result = new StringBuilder();
        if (!StringUtils.equals(current.getName(), updated.getName())) {
            result.append(String.format("name:  '%s' -> '%s'; ", current.getName(), updated.getName()));
        }
        if (!StringUtils.equals(current.getDescription(), updated.getDescription())) {
            result.append(String.format("description:  '%s' -> '%s'; ", current.getDescription(), updated.getDescription()));
        }
        if (!StringUtils.equals(current.getSource(), updated.getSource())) {
            result.append(String.format("source:  '%s' -> '%s'; ", current.getSource(), updated.getSource()));
        }
        if (!current.getAmount().equals(updated.getAmount())) {
            result.append(String.format("amount:  '%s' -> '%s'; ", current.getAmount(), updated.getAmount()));
        }

        if (!current.getItemType().equals(updated.getItemType())) {
            result.append(String.format("type:  '%s' -> '%s'; ", current.getItemType(), updated.getItemType()));
        }
        if (!current.getItemSubType().equals(updated.getItemSubType())) {
            result.append(String.format("subtype:  '%s' -> '%s'; ", current.getItemSubType(), updated.getItemSubType()));
        }

        if (current.getContainer() != updated.getContainer()) {
            result.append(createMoveDescription(current, updated));
        }

        return result.toString();
    }

    private static String createUpdateDescription(Container current, Container updated) {
        StringBuilder result = new StringBuilder();
        if (!StringUtils.equals(current.getName(), updated.getName())) {
            result.append(String.format("'%s' to '%s'; ", current.getName(), updated.getName()));
        }
        if (!StringUtils.equals(current.getDescription(), updated.getDescription())) {
            result.append(String.format("'%s'' to '%s'; ", current.getDescription(), updated.getDescription()));
        }

        return result.toString();
    }
}
