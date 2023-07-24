package com.anhkhoa.repository;

import com.anhkhoa.dataexport.excel.ExcelExport;

public class ExcelExportRepository {
    public ExcelExport getExcel() {
        try {
            return new ExcelExport();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
