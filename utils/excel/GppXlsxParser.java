/**
 *
 */
package com.metaship.edu.utils.excel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author None
 */
public class GppXlsxParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(GppXlsxParser.class);
    private static GppXlsxParser instance;
    private XSSFFormulaEvaluator evaluator;

    private GppXlsxParser() {

    }

    public static GppXlsxParser getInst() {
        if (instance == null) {
            instance = new GppXlsxParser();
        }
        return instance;
    }

    public <T> List<T> parseConfig(Class<T> clazz) throws Exception {
        if (clazz == null) {
            throw new IllegalArgumentException("config object con't be null!");
        }
        System.err.println(String.format("尝试读取Excel映射对象[%s]", clazz));
        GppXlsxObject gxoClass = clazz.getAnnotation(GppXlsxObject.class);
        String filepath = gxoClass.filepath();
        String sheetName = gxoClass.sheet();
        if (filepath.length() == 0 || sheetName.length() == 0) {
            throw new Exception(String.format("%s GppXlsxObject 配置有误！", clazz));
        }
        File file = new File(filepath);
        if (!file.exists() || file.isDirectory()) {
            throw new Exception(String.format("config file [%s] not found.", file.getAbsolutePath()));
        }
        InputStream is = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(is);
        evaluator = wb.getCreationHelper().createFormulaEvaluator();
        XSSFSheet sheet = wb.getSheet(sheetName);
        if (sheet == null) {
            is.close();
            throw new Exception(String.format("config file [%s] sheet [%s] not found.", filepath, sheetName));
        }
        List<T> ret = new ArrayList<T>();
        int lastRowNum = sheet.getLastRowNum();
        Map<String, XSSFCell> headers = new HashMap<String, XSSFCell>();
        XSSFRow row = sheet.getRow(0);
        short lastCellNum = row.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            XSSFCell cell = row.getCell(i);
            headers.put(cell.getStringCellValue(), cell);
        }
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 1; i <= lastRowNum; i++) {
            T bean = clazz.newInstance();
            XSSFRow dataRow = sheet.getRow(i);
            for (Field field : fields) {
                GppXlsxColumn column = field.getAnnotation(GppXlsxColumn.class);
                String name = column.name();
                XSSFCell hCell = headers.get(name);
                if (hCell == null) {
                    continue;
                }
                XSSFCell cell = dataRow.getCell(hCell.getColumnIndex());
                if (cell == null) {
                    continue;
                }
                ValueType valueType = column.valueType();
                Object cellValue = getCellValue(cell, valueType);
                if (cellValue == null) {
                    continue;
                }
                PropertyUtils.setProperty(bean, field.getName(), cellValue);
            }
            ret.add(bean);
        }
        is.close();
        System.err.println(String.format("成功读取Excel映射对象[%s]", clazz));
        return ret;
    }

    public <T> List<T> parseConfigByParameter(Class<T> clazz, String filepath, String sheetName) throws Exception {
        if (clazz == null) {
            throw new IllegalArgumentException("config object con't be null!");
        }
        System.err.println(String.format("尝试读取Excel映射对象[%s]", clazz));
        GppXlsxObject gxoClass = clazz.getAnnotation(GppXlsxObject.class);
       /* String filepath = gxoClass.filepath();
        String sheetName = gxoClass.sheet();*/
        if (filepath.length() == 0 || sheetName.length() == 0) {
            LOGGER.error(String.format("%s GppXlsxObject 配置有误！", clazz));
            throw new Exception(String.format("%s GppXlsxObject 配置有误！", clazz));
        }
        File file = new File(filepath);
        if (!file.exists() || file.isDirectory()) {
            LOGGER.error(String.format("config file [%s] not found.", file.getAbsolutePath()));
            throw new Exception(String.format("config file [%s] not found.", file.getAbsolutePath()));
        }
        InputStream is = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(is);
        evaluator = wb.getCreationHelper().createFormulaEvaluator();
        XSSFSheet sheet = wb.getSheet(sheetName);
        if (sheet == null) {
            is.close();
            LOGGER.error(String.format("config file [%s] sheet [%s] not found.", filepath, sheetName));
            throw new Exception(String.format("config file [%s] sheet [%s] not found.", filepath, sheetName));
        }
        List<T> ret = new ArrayList<T>();
        int lastRowNum = sheet.getLastRowNum();
        Map<String, XSSFCell> headers = new HashMap<String, XSSFCell>();
        XSSFRow row = sheet.getRow(0);
        short lastCellNum = row.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            XSSFCell cell = row.getCell(i);
            headers.put(cell.getStringCellValue(), cell);
        }
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 1; i <= lastRowNum; i++) {
            T bean = clazz.newInstance();
            XSSFRow dataRow = sheet.getRow(i);
            for (Field field : fields) {
                if (field.getAnnotation(GppXlsxColumn.class) == null) {
                    continue;
                }
                GppXlsxColumn column = field.getAnnotation(GppXlsxColumn.class);
                String name = column.name();
                XSSFCell hCell = headers.get(name);
                if (hCell == null) {
                    continue;
                }
                XSSFCell cell = dataRow.getCell(hCell.getColumnIndex());
                if (cell == null) {
                    continue;
                }
                ValueType valueType = column.valueType();
                Object cellValue = getCellValue(cell, valueType);
                if (cellValue == null) {
                    continue;
                }
                PropertyUtils.setProperty(bean, field.getName(), cellValue);
            }
            ret.add(bean);
        }
        is.close();
        System.err.println(String.format("成功读取Excel映射对象[%s]", clazz));
        return ret;
    }

    private Object getCellValue(XSSFCell cell, ValueType valueType) {
        CellValue cellValue = evaluator.evaluate(cell);
        if (cellValue == null) {
            return null;
        }
        switch (valueType) {
            case TYPE_BYTE:
                return (byte) (cellValue.getNumberValue());
            case TYPE_SHORT:
                return (short) (cellValue.getNumberValue());
            case TYPE_OBJECT:
                return (Object) cellValue.getNumberValue();
            case TYPE_INT:
                return (int) (cellValue.getNumberValue());
            case TYPE_LONG:
                return (long) (cellValue.getNumberValue());
            case TYPE_FLOAT:
                return (float) (cellValue.getNumberValue());
            case TYPE_DOUBLE:
                return (double) (cellValue.getNumberValue());
            case TYPE_STRING:
                /**
                 * 改良：当excel中的column为数字，但是在object中对应的该column的类型为string的时候会输出null，故而对其进行改良
                 * */
                return cellValue.getStringValue() == null ? ((long) (cellValue.getNumberValue()) + "") : cellValue.getStringValue();
            case TYPE_BOOLEAN:
                return cellValue.getBooleanValue();
            case TYPE_INT_LIST:
                List<Integer> list = new ArrayList<Integer>();
                try {
                    String string = cellValue.getStringValue();
                    if (string != null) {
                        String[] split = string.split(",");
                        for (String s : split) {
                            list.add(Integer.parseInt(s));
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println(String.format("单元格内容配置有误[%s]", cell.getRawValue()));
                    e.printStackTrace();
                }
                return list;
        }
        return null;
    }
}
