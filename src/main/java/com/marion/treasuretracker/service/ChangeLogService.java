package com.marion.treasuretracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marion.treasuretracker.model.*;
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
import java.util.Objects;

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
        changeLog.setContainer(item.getContainer());

        changeLogRepository.save(changeLog);
    }

    void recordLootedItem(Item item) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.LOOTED);
        changeLog.setDescription(createLootedDescription(item));
        changeLog.setItem(item);
        changeLog.setContainer(item.getContainer());

        changeLogRepository.save(changeLog);
    }

    void recordPurchasedItem(Item item, int amount, ItemType itemType, ItemSubType itemSubType) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.PURCHASED);
        changeLog.setDescription(createPurchasedDescription(item, amount, itemType, itemSubType));
        changeLog.setItem(item);
        changeLog.setContainer(item.getContainer());

        changeLogRepository.save(changeLog);
    }

    void recordSoldItem(Item item, Integer amount, Float value) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.SOLD);
        changeLog.setDescription(createSoldDescription(item, amount, value));
        changeLog.setItem(item);
        changeLog.setContainer(item.getContainer());

        changeLogRepository.save(changeLog);
    }

    void recordSpentItem(Item item, Integer amount, String description) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.SPENT);
        changeLog.setDescription(createSpentDescription(item, amount, description));
        changeLog.setItem(item);
        changeLog.setContainer(item.getContainer());

        changeLogRepository.save(changeLog);
    }

    void recordExchangeCoins(Item item, ItemSubType itemSubType, int amount) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.UPDATED);
        changeLog.setDescription(createExchangeDescription(amount, itemSubType, item.getAmount(), item.getItemSubType()));
        changeLog.setItem(item);
        changeLog.setContainer(item.getContainer());

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

    void recordMoveItemContainer(Item item) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setTimestamp(new Date());
        changeLog.setReason(ReasonType.MOVED);
        changeLog.setDescription(createMoveDescription(item));
        changeLog.setItem(item);
        changeLog.setContainer(item.getContainer());

        changeLogRepository.save(changeLog);
    }

    private static String createAcquisitionDescription(Item item) {
        return String.format("Acquired '%s' from '%s'",
                item.getName(),
                StringUtils.isNotBlank(item.getSource()) ? item.getSource() : "unspecified");
    }

    private static String createLootedDescription(Item item) {
        return String.format("Looted '%s' from '%s'",
                item.getName(),
                StringUtils.isNotBlank(item.getSource()) ? item.getSource() : "unspecified");
    }

    private static String createPurchasedDescription(Item item, int amount, ItemType itemType, ItemSubType itemSubType) {
        return String.format("Purchased '%s' from '%s' for %d %s %s",
                item.getName(),
                StringUtils.isNotBlank(item.getSource()) ? item.getSource() : "unspecified",
                amount,
                itemType,
                itemSubType);
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
        if (!Objects.equals(current.getAmount(),updated.getAmount())) {
            result.add(String.format("amount from '%s' to '%s'; ", current.getAmount(), updated.getAmount()));
        }

        if (!Objects.equals(current.getItemType(),updated.getItemType())) {
            result.add(String.format("type from '%s' to '%s'; ", current.getItemType(), updated.getItemType()));
        }
        if (!Objects.equals(current.getItemSubType(),updated.getItemSubType())) {
            result.add(String.format("subtype from '%s' to '%s'; ", current.getItemSubType(), updated.getItemSubType()));
        }
        if (!Objects.equals(current.getValue(), updated.getValue())) {
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

    private static String createSpentDescription(Item item, Integer amount, String description){
        return String.format("Spent %d %s for %s", amount, item.getItemSubType(), description);
    }

    private static String createExchangeDescription(int amount1, ItemSubType itemSubType1, int amount2, ItemSubType itemSubType2) {
        return String.format("Exchanged %s %s for %s %s", amount1, itemSubType1, amount2, itemSubType2);
    }

    private static String createMoveDescription(Item item) {
        return String.format("Moved %d %s to %s", item.getAmount(), item.getName(), item.getContainer().getName());
    }
}
