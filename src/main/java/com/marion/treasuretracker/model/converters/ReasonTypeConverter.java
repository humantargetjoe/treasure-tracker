package com.marion.treasuretracker.model.converters;

import com.marion.treasuretracker.model.ItemType;
import com.marion.treasuretracker.model.ReasonType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ReasonTypeConverter implements AttributeConverter<ReasonType, String> {
    @Override
    public String convertToDatabaseColumn(ReasonType attribute) {
        if (attribute == null)
            return null;

        return attribute.toString();
    }

    @Override
    public ReasonType convertToEntityAttribute(String dbData) {
        return ReasonType.valueOf(dbData);
    }
}
