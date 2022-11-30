package io.diana.calculaterate.service;

import io.diana.calculaterate.service.dto.InRoutDTO;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParserExcelService {
    private final Logger log = LoggerFactory.getLogger(ParserExcelService.class);

    public List<InRoutDTO> prepareRoutsFromFile(MultipartFile file) {
        List<InRoutDTO> inRoutDTOS = new ArrayList<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFRow row = xssfSheet.getRow(0);
            for (int i = 1; i < xssfSheet.getLastRowNum() + 1; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i);
                String stationFromCode = "";
                String stationToCode = "";
                String cargoCode = "";
                String cargoVolume = "";

                for (int j = 0; j < 7; j++) {
                    String cellHeader = row.getCell(j).getStringCellValue().trim();
                    XSSFCell cell = xssfRow.getCell(j);
                    if (cellHeader.equals("Код ст. отпр."))
                        stationFromCode = cell.getStringCellValue();
                    if (cellHeader.equals("Код ст. назн."))
                        stationToCode = cell.getStringCellValue();
                    if (cellHeader.equals("Код груза"))
                        cargoCode = cell.getStringCellValue();
                    if (cellHeader.equals("Объем"))
                        cargoVolume = cell.getStringCellValue();
                }
                inRoutDTOS.add(new InRoutDTO(stationFromCode, stationToCode, cargoCode, Long.parseLong(cargoVolume)));
            }
        } catch (IOException e) {
            log.error("Ошибка загруки файла - {}", e.getMessage());
        } catch (OLE2NotOfficeXmlFileException e1) {
            log.error("Некорректный формат файла заявок, необходим формат xlsx");
        }
        return inRoutDTOS;
    }
}
