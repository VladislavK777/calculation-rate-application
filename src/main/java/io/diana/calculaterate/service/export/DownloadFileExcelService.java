package io.diana.calculaterate.service.export;

import io.diana.calculaterate.service.dto.ExportModelDTO;
import io.diana.calculaterate.service.dto.OutRoutDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static io.diana.calculaterate.service.export.CellStyleCommon.*;

@Component
public class DownloadFileExcelService {
    public ByteArrayResource downloadFileExcel(List<ExportModelDTO> exportModelDTOList) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = DownloadFileExcelService.class.getResourceAsStream("/" + "calculation.xlsx");
        BufferedInputStream fis = new BufferedInputStream(inputStream);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowStartHead = 0;
        int numberTable = 1;
        for (ExportModelDTO exportModelDTO : exportModelDTOList) {
            // Строка финиша шапки
            int rowFinishHead = rowStartHead + 3;
            for (int i = rowStartHead; i <= rowFinishHead; i++) {
                XSSFRow rowHead = sheet.createRow(i);
                Cell head0 = rowHead.createCell(0);
                head0.setCellValue("№п/п");
                head0.setCellStyle(cellStyleHead(workbook));

                Cell head1 = rowHead.createCell(1);
                head1.setCellValue("Станция отправления");
                head1.setCellStyle(cellStyleHead(workbook));

                Cell head2 = rowHead.createCell(2);
                head2.setCellValue("Дорога отпр.");
                head2.setCellStyle(cellStyleHead(workbook));
                sheet.setColumnWidth(2, 2194);

                Cell head3 = rowHead.createCell(3);
                head3.setCellValue("Станция назначения");
                head3.setCellStyle(cellStyleHead(workbook));

                Cell head4 = rowHead.createCell(4);
                head4.setCellValue("Дорога назн.");
                head4.setCellStyle(cellStyleHead(workbook));
                sheet.setColumnWidth(4, 2194);

                Cell head5 = rowHead.createCell(5);
                head5.setCellValue("Наименование груза");
                head5.setCellStyle(cellStyleHead(workbook));

                Cell head6 = rowHead.createCell(6);
                head6.setCellValue("Расст., км");
                head6.setCellStyle(cellStyleHead(workbook));

                Cell head7 = rowHead.createCell(7);
                head7.setCellValue("Время в пути, сут");
                head7.setCellStyle(cellStyleHead(workbook));

                Cell head8 = rowHead.createCell(8);
                head8.setCellValue("Погр. / выгр.");
                head8.setCellStyle(cellStyleHead(workbook));

                Cell head9 = rowHead.createCell(9);
                head9.setCellValue("Оборот, сут.");
                head9.setCellStyle(cellStyleHead(workbook));

                Cell head10 = rowHead.createCell(10);
                head10.setCellValue("ВО");
                head10.setCellStyle(cellStyleHead(workbook));

                Cell head11 = rowHead.createCell(11);
                if (i == rowFinishHead) {
                    head11.setCellValue("руб/ваг.");
                } else {
                    head11.setCellValue("ДОХОД");
                }
                head11.setCellStyle(cellStyleHeadBottom(workbook));

                Cell head12 = rowHead.createCell(12);
                if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                    head12.setCellValue("Тариф в собств. вагонах");
                } else if (i == rowFinishHead) {
                    head12.setCellValue("руб/ваг.");
                } else {
                    head12.setCellValue("РАСХОД");
                }
                head12.setCellStyle(cellStyleHeadBottom(workbook));

                Cell head13 = rowHead.createCell(13);
                if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                    head13.setCellValue("За нахождение в пути");
                } else if (i == rowFinishHead) {
                    head13.setCellValue("руб/ваг.");
                } else {
                    head13.setCellValue("ПРИБЫЛЬ");
                }
                head13.setCellStyle(cellStyleHeadBottom(workbook));

                Cell head14 = rowHead.createCell(14);
                if (i == rowFinishHead - 1 || i == rowFinishHead - 2) {
                    head14.setCellValue("В сутки");
                } else if (i == rowFinishHead) {
                    head14.setCellValue("руб/ваг/сут.");
                } else {
                    head14.setCellValue("ПРИБЫЛЬ");
                }
                head14.setCellStyle(cellStyleHeadRight(workbook));

                if (i == rowFinishHead) {
                    Cell head15 = rowHead.createCell(15);
                    head15.setCellValue(exportModelDTO.getCargoVolume());
                    head15.setCellStyle(cellStyleHeadVolume(workbook));
                }
            }

            for (int i = 0; i < 11; i++) {
                sheet.addMergedRegion(new CellRangeAddress(rowStartHead, rowFinishHead, i, i));
            }
            sheet.addMergedRegion(new CellRangeAddress(rowStartHead, rowFinishHead - 1, 11, 11));
            sheet.addMergedRegion(new CellRangeAddress(rowStartHead, rowStartHead, 13, 14));
            for (int i = 12; i < 15; i++) {
                sheet.addMergedRegion(new CellRangeAddress(rowStartHead + 1, rowFinishHead - 1, i, i));
            }

            // Строка первого рейса
            int rowFirstRoute = rowFinishHead + 1;
            // Номер первой ячейки данных
            int firstNumberCell = rowFirstRoute + 1;
            boolean isMarker = false;

            for (OutRoutDTO route : exportModelDTO.getRout()) {
                int num = rowFirstRoute + 1;
                XSSFRow row = sheet.createRow(rowFirstRoute);
                Cell number = row.createCell(0);
                if (!isMarker && route.isRequestRout()) {
                    number.setCellValue(numberTable);
                    isMarker = true;
                } else {
                    number.setCellValue("");
                }
                number.setCellStyle(cellStyleField(workbook));

                Cell stationFrom = row.createCell(1);
                stationFrom.setCellValue(route.getStationFromName());
                if (route.isRequestRout())
                    stationFrom.setCellStyle(cellStyleFieldNeedCalc(workbook));
                else
                    stationFrom.setCellStyle(cellStyleField(workbook));

                Cell roadFrom = row.createCell(2);
                roadFrom.setCellValue(route.getRoadFromNameShort());
                if (route.isRequestRout())
                    roadFrom.setCellStyle(cellStyleFieldNeedCalc(workbook));
                else
                    roadFrom.setCellStyle(cellStyleField(workbook));

                Cell stationTo = row.createCell(3);
                stationTo.setCellValue(route.getStationToName());
                if (route.isRequestRout())
                    stationTo.setCellStyle(cellStyleFieldNeedCalc(workbook));
                else
                    stationTo.setCellStyle(cellStyleField(workbook));

                Cell roadTo = row.createCell(4);
                roadTo.setCellValue(route.getRoadToNameShort());
                if (route.isRequestRout())
                    roadTo.setCellStyle(cellStyleFieldNeedCalc(workbook));
                else
                    roadTo.setCellStyle(cellStyleField(workbook));

                Cell cargo = row.createCell(5);
                cargo.setCellValue(route.getCargoName());
                cargo.setCellStyle(cellStyleFieldCargo(workbook));

                Cell distance = row.createCell(6);
                if (route.getDistance() > 0) {
                    distance.setCellValue(route.getDistance());
                    distance.setCellStyle(cellStyleFieldFormatDistance(workbook));
                }

                Cell countDays = row.createCell(7);
                if (route.getTravelTime() > 0) {
                    countDays.setCellValue(route.getTravelTime());
                    countDays.setCellStyle(cellStyleField(workbook));
                }

                Cell countDaysLoadUnload = row.createCell(8);
                countDaysLoadUnload.setCellValue(route.getLoadUnload());
                countDaysLoadUnload.setCellStyle(cellStyleField(workbook));

                Cell fullCountDays = row.createCell(9);
                fullCountDays.setCellFormula("SUM(H" + num + ":I" + num + ")");
                fullCountDays.setCellStyle(cellStyleField(workbook));

                Cell call9 = row.createCell(10);
                call9.setCellValue("поваг");
                call9.setCellStyle(cellStyleField(workbook));
                sheet.autoSizeColumn(10);

                Cell rate = row.createCell(11);
                if (route.getRate() > 0)
                    rate.setCellValue(route.getRate());
                if (route.isRequestRout())
                    rate.setCellStyle(cellStyleFieldNeedCalc(workbook));
                else
                    rate.setCellStyle(cellStyleFieldFormat(workbook));


                Cell tariff = row.createCell(12);
                if (route.getTariff() == 0) {
                    tariff.setCellStyle(cellStyleFieldNull(workbook));
                } else {
                    tariff.setCellValue(route.getTariff());
                    tariff.setCellStyle(cellStyleFieldFormat(workbook));
                }

                Cell rateTariff = row.createCell(13);
                rateTariff.setCellFormula("L" + num + "-M" + num);
                rateTariff.setCellStyle(cellStyleFieldFormat(workbook));
                sheet.setColumnWidth(13, 3182);

                Cell cell13 = row.createCell(14);
                cell13.setCellValue("");
                cell13.setCellStyle(cellStyleFieldRightBold(workbook));

                rowFirstRoute++;
            }

            // Номер последней ячейки данных
            int lastNumberCell = rowFirstRoute;
            int totalYieldNum = rowFirstRoute + 1;
            XSSFRow row = sheet.createRow(rowFirstRoute);

            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(cellStyleFieldTotal(workbook));

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(cellStyleFieldTotal(workbook));

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(cellStyleFieldTotal(workbook));

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(cellStyleFieldTotal(workbook));

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(cellStyleFieldTotal(workbook));

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(cellStyleFieldTotal(workbook));

            sheet.addMergedRegion(new CellRangeAddress(rowFirstRoute, rowFirstRoute, 0, 5));

            // Строка итоговых расчетов
            Cell totalDistance = row.createCell(6);
            totalDistance.setCellFormula("SUM(G" + firstNumberCell + ":G" + lastNumberCell + ")");
            totalDistance.setCellStyle(cellStyleFieldTotalFormatDistance(workbook));

            Cell totalCountDays = row.createCell(7);
            totalCountDays.setCellFormula("SUM(H" + firstNumberCell + ":H" + lastNumberCell + ")");
            totalCountDays.setCellStyle(cellStyleFieldTotal(workbook));

            Cell totalCountLoadUnloadDays = row.createCell(8);
            totalCountLoadUnloadDays.setCellFormula("SUM(I" + firstNumberCell + ":I" + lastNumberCell + ")");
            totalCountLoadUnloadDays.setCellStyle(cellStyleFieldTotal(workbook));

            Cell totalFullCountDays = row.createCell(9);
            totalFullCountDays.setCellFormula("SUM(J" + firstNumberCell + ":J" + lastNumberCell + ")");
            totalFullCountDays.setCellStyle(cellStyleFieldTotal(workbook));

            Cell cell10 = row.createCell(10);
            cell10.setCellStyle(cellStyleFieldTotal(workbook));

            Cell cell11 = row.createCell(11);
            cell11.setCellStyle(cellStyleFieldTotal(workbook));

            Cell cell12 = row.createCell(12);
            cell12.setCellStyle(cellStyleFieldTotal(workbook));

            Cell totalRateTariff = row.createCell(13);
            totalRateTariff.setCellFormula("SUM(N" + firstNumberCell + ":N" + lastNumberCell + ")");
            totalRateTariff.setCellStyle(cellStyleFieldTotalFormat(workbook));

            Cell yield = row.createCell(14);
            yield.setCellFormula("N" + totalYieldNum + "/J" + totalYieldNum);
            yield.setCellStyle(cellStyleFieldTotalRight(workbook));
            sheet.autoSizeColumn(14);

            Cell cell15 = row.createCell(15);
            cell15.setCellValue(exportModelDTO.getProfit());

            Cell cell16 = row.createCell(16);
            cell16.setCellFormula("P" + totalYieldNum + "-O" + totalYieldNum);

            Cell cell17 = row.createCell(17);
            cell17.setCellFormula("Q" + totalYieldNum + "*J" + totalYieldNum);

            rowStartHead = lastNumberCell + 1;
            numberTable++;
        }
        workbook.write(byteArrayOutputStream);
        workbook.close();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }

    public ByteArrayResource downloadTemplateGroupFileExcel() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = DownloadFileExcelService.class.getResourceAsStream("/" + "template_group.xlsx");
        BufferedInputStream fis = new BufferedInputStream(inputStream);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        workbook.write(byteArrayOutputStream);
        workbook.close();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }
}
