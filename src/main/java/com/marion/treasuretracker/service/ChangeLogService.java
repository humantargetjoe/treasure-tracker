package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.ChangeLog;
import com.marion.treasuretracker.model.Container;
import com.marion.treasuretracker.model.Item;
import com.marion.treasuretracker.model.ReasonType;
import com.marion.treasuretracker.repository.ChangeLogRepository;
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

    void recordUpdateItem(String description, Item item) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.UPDATED);
        changeLog.setDescription(description);
        changeLog.setItem(item);

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

    void recordUpdateContainer(String description, Container container) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.UPDATED);
        changeLog.setDescription(description);
        changeLog.setContainer(container);

        changeLogRepository.save(changeLog);
    }

    void recordMoveItemContainer(String description, Item item, Container container) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.MOVED);
        changeLog.setDescription(description);
        changeLog.setItem(item);
        changeLog.setContainer(container);

        changeLogRepository.save(changeLog);
    }
}
