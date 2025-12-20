package com.example.democrud.Download;

import com.example.democrud.Entity.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Helper {

    public static String[] HEADERS = {
        "id",
        "phone",
        "salary",
        "address",
        "job_title",
        "name"
    };

    public static String SHEET_NAME="Employee_data";

    public static ByteArrayInputStream dataToExcel(List<Employee> list) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try{
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            Row row = sheet.createRow(0);

            for(int i=0; i<HEADERS.length; i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            int rowIndex = 1;
            for (Employee c: list) {

                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;

                dataRow.createCell(0).setCellValue(c.getId());
                dataRow.createCell(1).setCellValue(c.getPhone());
                dataRow.createCell(2).setCellValue(c.getSalary());
                dataRow.createCell(3).setCellValue(c.getAddress());
                dataRow.createCell(4).setCellValue(c.getJobTitle());
                dataRow.createCell(5).setCellValue(c.getName());
            }

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Some error occurred ...");
            return null;
        }
        finally {
            workbook.close();
            out.close();
        }
    }
}
