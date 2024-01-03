package com.omer.spring.batch.config;

import com.omer.spring.batch.entity.AbcAnalysisCloud;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * This class demonstrates how we can implement a row mapper that maps
 * a row found from an Excel document into a {@code StudentDTO} object. If
 * the Excel document has no header, we have to use this method for transforming
 * the input data into {@code StudentDTO} objects.
 *
 * @author Petri Kainulainen
 */
@Component
public class AbcAnalysisExcelRowMapper implements RowMapper<AbcAnalysisCloud> {

    @Override
    public AbcAnalysisCloud mapRow(RowSet rowSet) throws Exception {
        if (rowSet == null || rowSet.getCurrentRow() == null) {
            // Handle the case where either rowSet or getCurrentRow() is null
            // You might want to log a warning or return null, depending on your use case
            return null;
        }

        AbcAnalysisCloud abc = new AbcAnalysisCloud();

        String materialNO = getStringValue(rowSet.getCurrentRow(), 0);
        String plant = getStringValue(rowSet.getCurrentRow(), 1);
        String baseUom = getStringValue(rowSet.getCurrentRow(), 2);
        String unitRate = getStringValue(rowSet.getCurrentRow(), 3);
        String currency = getStringValue(rowSet.getCurrentRow(), 4);
        String quantity = getStringValue(rowSet.getCurrentRow(), 5);
        String materialType = getStringValue(rowSet.getCurrentRow(), 6);
        String materialGroup = getStringValue(rowSet.getCurrentRow(), 7);
        String purchasingGroup = getStringValue(rowSet.getCurrentRow(), 8);
        String location = getStringValue(rowSet.getCurrentRow(), 9);
        String unspsc = getStringValue(rowSet.getCurrentRow(), 10);
        String industry = getStringValue(rowSet.getCurrentRow(), 11);

        abc.setAuditId(generateRandomString(5));
        abc.setMaterialNo(materialNO);
        abc.setPlant(plant);
        abc.setBaseUom(baseUom);
        abc.setUnitRate(unitRate);
        abc.setCurrency(currency);
        abc.setQuantityConsumed(quantity);
        abc.setMaterialType(materialType);
        abc.setMaterialGroup(materialGroup);
        abc.setPurchasingGroup(purchasingGroup);
        abc.setStorageLocation(location);
        abc.setUnspsc(unspsc);
        abc.setIndustry(industry);

        return abc;
    }

    private String getStringValue(Object[] row, int index) {
        return (row != null && index >= 0 && index < row.length) ? String.valueOf(row[index]) : null;

}


    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }


}
