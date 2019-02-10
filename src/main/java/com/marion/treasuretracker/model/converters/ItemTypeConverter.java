package com.marion.treasuretracker.model.converters;

import com.marion.treasuretracker.model.ItemType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ItemTypeConverter implements AttributeConverter<ItemType, String> {
    @Override
    public String convertToDatabaseColumn(ItemType attribute) {
        if (attribute == null)
            return null;

        return attribute.toString();
    }

    @Override
    public ItemType convertToEntityAttribute(String dbData) {
        return ItemType.valueOf(dbData);
    }
}
