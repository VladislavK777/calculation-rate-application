package io.diana.calculaterate.service.export;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.awt.*;

public class CellStyleCommon {
    private static XSSFFont getFont(XSSFWorkbook xssfWorkbook) {
        XSSFFont xssfFont = xssfWorkbook.createFont();
        xssfFont.setFontName("Times New Roman");
        xssfFont.setFontHeight(12.0);
        return xssfFont;
    }

    public static short formatWith00(XSSFWorkbook xssfWorkbook) {
        XSSFDataFormat format = xssfWorkbook.createDataFormat();
        return format.getFormat("#,##0.00");
    }

    public static short formatWithOut00(XSSFWorkbook xssfWorkbook) {
        XSSFDataFormat format = xssfWorkbook.createDataFormat();
        return format.getFormat("#,##0");
    }

    public static XSSFCellStyle cellStyleField(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleField = xssfWorkbook.createCellStyle();
        cellStyleField.setFont(getFont(xssfWorkbook));
        cellStyleField.setBorderBottom(BorderStyle.DOTTED);
        cellStyleField.setBorderTop(BorderStyle.DOTTED);
        cellStyleField.setBorderRight(BorderStyle.DOTTED);
        cellStyleField.setBorderLeft(BorderStyle.DOTTED);
        cellStyleField.setAlignment(HorizontalAlignment.CENTER);
        cellStyleField.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyleField;
    }

    public static XSSFCellStyle cellStyleFieldFormat(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldFormat = xssfWorkbook.createCellStyle();
        cellStyleFieldFormat.setFont(getFont(xssfWorkbook));
        cellStyleFieldFormat.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldFormat.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldFormat.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldFormat.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldFormat.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldFormat.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldFormat.setWrapText(true);
        cellStyleFieldFormat.setDataFormat(formatWith00(xssfWorkbook));
        return cellStyleFieldFormat;
    }

    public static XSSFCellStyle cellStyleFieldFormatDistance(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldFormatDistance = xssfWorkbook.createCellStyle();
        cellStyleFieldFormatDistance.setFont(getFont(xssfWorkbook));
        cellStyleFieldFormatDistance.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldFormatDistance.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldFormatDistance.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldFormatDistance.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldFormatDistance.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldFormatDistance.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldFormatDistance.setWrapText(true);
        cellStyleFieldFormatDistance.setDataFormat(formatWithOut00(xssfWorkbook));
        return cellStyleFieldFormatDistance;
    }

    public static XSSFCellStyle cellStyleHead(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleHead = xssfWorkbook.createCellStyle();
        cellStyleHead.setFont(getFont(xssfWorkbook));
        cellStyleHead.setBorderRight(BorderStyle.DOTTED);
        cellStyleHead.setBorderLeft(BorderStyle.DOTTED);
        cellStyleHead.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHead.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHead.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleHead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHead.setWrapText(true);
        return cellStyleHead;
    }

    public static XSSFCellStyle cellStyleHeadBottom(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleHeadBottom = xssfWorkbook.createCellStyle();
        cellStyleHeadBottom.setFont(getFont(xssfWorkbook));
        cellStyleHeadBottom.setBorderRight(BorderStyle.DOTTED);
        cellStyleHeadBottom.setBorderBottom(BorderStyle.DOTTED);
        cellStyleHeadBottom.setBorderLeft(BorderStyle.DOTTED);
        cellStyleHeadBottom.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHeadBottom.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHeadBottom.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleHeadBottom.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeadBottom.setWrapText(true);
        return cellStyleHeadBottom;
    }

    public static XSSFCellStyle cellStyleHeadRight(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleHeadRight = xssfWorkbook.createCellStyle();
        cellStyleHeadRight.setFont(getFont(xssfWorkbook));
        cellStyleHeadRight.setBorderRight(BorderStyle.MEDIUM);
        cellStyleHeadRight.setBorderBottom(BorderStyle.DOTTED);
        cellStyleHeadRight.setBorderLeft(BorderStyle.DOTTED);
        cellStyleHeadRight.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHeadRight.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHeadRight.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleHeadRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeadRight.setWrapText(true);
        return cellStyleHeadRight;
    }

    public static XSSFCellStyle cellStyleHeadVolume(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleHeadVolume = xssfWorkbook.createCellStyle();
        XSSFFont xssfFont = getFont(xssfWorkbook);
        xssfFont.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        xssfFont.setBold(true);
        cellStyleHeadVolume.setFont(xssfFont);
        cellStyleHeadVolume.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHeadVolume.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHeadVolume.setFillForegroundColor(new XSSFColor(new Color(255, 255, 153), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleHeadVolume.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeadVolume.setWrapText(true);
        return cellStyleHeadVolume;
    }

    public static XSSFCellStyle cellStyleFieldNull(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldNull = xssfWorkbook.createCellStyle();
        XSSFFont xssfFont = getFont(xssfWorkbook);
        xssfFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        cellStyleFieldNull.setFont(xssfFont);
        cellStyleFieldNull.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldNull.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldNull.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldNull.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldNull.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldNull.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldNull.setWrapText(true);
        return cellStyleFieldNull;
    }

    public static XSSFCellStyle cellStyleFieldRightBold(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldRightBold = xssfWorkbook.createCellStyle();
        XSSFFont xssfFont = getFont(xssfWorkbook);
        xssfFont.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        cellStyleFieldRightBold.setFont(xssfFont);
        cellStyleFieldRightBold.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldRightBold.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldRightBold.setBorderRight(BorderStyle.MEDIUM);
        cellStyleFieldRightBold.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldRightBold.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldRightBold.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldRightBold.setWrapText(true);
        return cellStyleFieldRightBold;
    }

    public static XSSFCellStyle cellStyleFieldCargo(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldCargo = xssfWorkbook.createCellStyle();
        cellStyleFieldCargo.setFont(getFont(xssfWorkbook));
        cellStyleFieldCargo.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldCargo.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldCargo.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldCargo.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldCargo.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyleFieldCargo;
    }

    public static XSSFCellStyle cellStyleFieldNeedCalc(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldNeedCalc = xssfWorkbook.createCellStyle();
        XSSFFont xssfFont = getFont(xssfWorkbook);
        xssfFont.setBold(true);
        cellStyleFieldNeedCalc.setFont(xssfFont);
        cellStyleFieldNeedCalc.setBorderBottom(BorderStyle.DOTTED);
        cellStyleFieldNeedCalc.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldNeedCalc.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldNeedCalc.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldNeedCalc.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldNeedCalc.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldNeedCalc.setFillForegroundColor(new XSSFColor(new Color(214, 214, 214), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleFieldNeedCalc.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldNeedCalc.setDataFormat(formatWith00(xssfWorkbook));
        return cellStyleFieldNeedCalc;
    }

    public static XSSFCellStyle cellStyleFieldTotal(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldTotal = xssfWorkbook.createCellStyle();
        XSSFFont xssfFont = getFont(xssfWorkbook);
        xssfFont.setBold(false);
        cellStyleFieldTotal.setFont(xssfFont);
        cellStyleFieldTotal.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleFieldTotal.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldTotal.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldTotal.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldTotal.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldTotal.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldTotal.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleFieldTotal.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldTotal.setWrapText(true);
        return cellStyleFieldTotal;
    }

    public static XSSFCellStyle cellStyleFieldTotalFormat(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldTotalFormat = xssfWorkbook.createCellStyle();
        cellStyleFieldTotalFormat.setFont(getFont(xssfWorkbook));
        cellStyleFieldTotalFormat.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleFieldTotalFormat.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldTotalFormat.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldTotalFormat.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldTotalFormat.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldTotalFormat.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldTotalFormat.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleFieldTotalFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldTotalFormat.setWrapText(true);
        cellStyleFieldTotalFormat.setDataFormat(formatWith00(xssfWorkbook));
        return cellStyleFieldTotalFormat;
    }

    public static XSSFCellStyle cellStyleFieldTotalFormatDistance(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldTotalFormatDistance = xssfWorkbook.createCellStyle();
        cellStyleFieldTotalFormatDistance.setFont(getFont(xssfWorkbook));
        cellStyleFieldTotalFormatDistance.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleFieldTotalFormatDistance.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldTotalFormatDistance.setBorderRight(BorderStyle.DOTTED);
        cellStyleFieldTotalFormatDistance.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldTotalFormatDistance.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldTotalFormatDistance.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldTotalFormatDistance.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleFieldTotalFormatDistance.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldTotalFormatDistance.setWrapText(true);
        cellStyleFieldTotalFormatDistance.setDataFormat(formatWithOut00(xssfWorkbook));
        return cellStyleFieldTotalFormatDistance;
    }

    public static XSSFCellStyle cellStyleFieldTotalRight(XSSFWorkbook xssfWorkbook) {
        XSSFCellStyle cellStyleFieldTotalRight = xssfWorkbook.createCellStyle();
        cellStyleFieldTotalRight.setFont(getFont(xssfWorkbook));
        cellStyleFieldTotalRight.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleFieldTotalRight.setBorderTop(BorderStyle.DOTTED);
        cellStyleFieldTotalRight.setBorderRight(BorderStyle.MEDIUM);
        cellStyleFieldTotalRight.setBorderLeft(BorderStyle.DOTTED);
        cellStyleFieldTotalRight.setAlignment(HorizontalAlignment.CENTER);
        cellStyleFieldTotalRight.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleFieldTotalRight.setFillForegroundColor(new XSSFColor(new Color(204, 255, 204), xssfWorkbook.getStylesSource().getIndexedColors()));
        cellStyleFieldTotalRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleFieldTotalRight.setWrapText(true);
        cellStyleFieldTotalRight.setDataFormat(formatWith00(xssfWorkbook));
        return cellStyleFieldTotalRight;
    }
}
