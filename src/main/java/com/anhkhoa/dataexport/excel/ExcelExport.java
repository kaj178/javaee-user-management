package com.anhkhoa.dataexport.excel;

import com.anhkhoa.dataexport.httpclient.UserAPI;
import com.anhkhoa.model.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExport {
    private static String[] columns = {"ID", "Name", "Gender", "Status"};
    private List<User> userList;
    private String defaultDownloadFolder;
    private String savePath;

    public ExcelExport() throws IOException, InterruptedException {
        this.userList = getUserAPI();
        defaultDownloadFolder = System.getProperty("user.home") + "\\Downloads\\";
        savePath = defaultDownloadFolder + "user.xlsx";
        writeExcel(userList, savePath);
    }

    private static void writeExcel(List<User> list, String path) throws IOException {
        // Create a workbook
        Workbook workBook = new XSSFWorkbook(); // XSSF for generating '.xlsx' files
        // Create a sheet
        Sheet sheet =  workBook.createSheet("User");

        // Set font and color for header's columns
        Font headerFont = workBook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        CellStyle headerCellStyle = workBook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create header row
        Row headerRow = sheet.createRow(0);
        // Create cells for header row
        for (int index = 0; index < columns.length; index++) {
            Cell headerCell = headerRow.createCell(index);
            headerCell.setCellValue(columns[index]);
            headerCell.setCellStyle(headerCellStyle);
        }

        // Set main data
        int rowIndex = 1;
        for (User user : list) {
            Row row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getGender());
            row.createCell(3).setCellValue(user.getStatus());
            rowIndex++;
        }

        // Write the output to a .xlsx file
        FileOutputStream exporter = new FileOutputStream(path);
        workBook.write(exporter);

        // Closing all
        exporter.close();
        workBook.close();
    }

    private static List<User> getUserAPI() throws IOException, InterruptedException {
        String endPoint = "http://localhost:8080/demorest/api/users/v1";
        return new UserAPI().getMethod(endPoint);
    }

//    public static void main(String[] args) {
//        try {
//            ExcelExport exporter = new ExcelExport();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
