package com.marion.treasuretracker.model.converters;

import com.marion.treasuretracker.model.ItemSubType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemSubTypeConverter implements AttributeConverter<ItemSubType, String> {
    @Override
    public String convertToDatabaseColumn(ItemSubType attribute) {
        if (attribute == null)
            return null;

        return attribute.toString();
    }

    @Override
    public ItemSubType convertToEntityAttribute(String dbData) {
        return ItemSubType.valueOf(dbData);
    }
}
