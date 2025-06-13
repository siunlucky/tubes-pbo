package com.example.tubes.model;
import jakarta.servlet.http.HttpServletResponse;

public interface Exportable {
    /**
     * @param id
     * @param isYear
     * @param response
     */
    void exportTransactionExcel(Long id, Boolean isYear, HttpServletResponse response);
}