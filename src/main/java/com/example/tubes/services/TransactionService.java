package com.example.tubes.services;

import com.example.tubes.exception.NotFoundException;
import com.example.tubes.model.Expense;
import com.example.tubes.model.Income;
import com.example.tubes.model.Transaction;
import com.example.tubes.model.Wallet;
import com.example.tubes.repository.ExpenseRepository;
import com.example.tubes.repository.IncomeRepository;
import com.example.tubes.repository.TransactionRepository;
import com.opencsv.CSVWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public TransactionService(
        TransactionRepository transactionRepository,
        IncomeRepository incomeRepository,
        ExpenseRepository expenseRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<Transaction> getAllTransaction(Wallet wallet) {
        return transactionRepository.findAllByWallet(wallet);
    }

    public Transaction getTransactionById(Long id, Wallet wallet) {
        Optional<Transaction> transactionOptional = transactionRepository.findByIdAndWallet(id, wallet);
        if (transactionOptional.isEmpty()) {
            throw new NotFoundException("Transaction not found with id: " + id);
        }
        Transaction transaction = transactionOptional.get();
        return transaction;
    }

    public List<Income> getAllIncome(Wallet wallet){
        return incomeRepository.findAllByWallet(wallet);
    }

    public List<Expense> getAllExpense(Wallet wallet) {
        return expenseRepository.findAllByWallet(wallet);
    }

    public Income saveIncome(Income income) {
        income.setDate(new Date());
        return incomeRepository.save(income);
    }

    public Expense saveExpense(Expense expense) {
        expense.setDate(new Date());
        return expenseRepository.save(expense);
    }

    public void deleteTransactionById(Long id, Wallet wallet) {
        Transaction transaction = getTransactionById(id, wallet);
        transactionRepository.delete(transaction);
    }

    public Double getTotalCurrentMonthIncome(Wallet wallet) {
        return incomeRepository.sumByCurrentMonthByWallet(wallet);
    }

    public Double getTotalCurrentMonthExpense(Wallet wallet) {
        return expenseRepository.sumByCurrentMonthByWallet(wallet);
    }

    public Double getTotalCurrentYearExpense(Wallet wallet) {
        return expenseRepository.sumByCurrentYearByWallet(wallet);
    }

    public Double getTotalCurrentYearIncome(Wallet wallet) {
        return incomeRepository.sumByCurrentYearByWallet(wallet);
    }

    public List<Object[]> getTotalCategoryCurrentMonth(Wallet wallet) {
        return transactionRepository.sumCategoryCurrentMonthByWallet(wallet);
    }

    public List<Object[]> getTotalCategoryCurrentYear(Wallet wallet) {
        return transactionRepository.sumCategoryCurrentYearByWallet(wallet);
    }

    public List<Object[]> getTotalCategory(Wallet wallet) {
        return transactionRepository.sumCategoryByWallet(wallet);
    }

    public Double getTotalIncome(Wallet wallet) {
        return incomeRepository.sumByWallet(wallet);
    }

    public Double getTotalExpense(Wallet wallet) {
        return expenseRepository.sumByWallet(wallet);
    }

    public void writeToExcel(OutputStream out, Wallet wallet, boolean isYear) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transactions");

        Row header = sheet.createRow(0);
        String[] columns = { "Nama Wallet", "Tanggal", "Amount", "Tipe Transaksi", "Jenis Kategori" };
        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<Transaction> transactions = isYear
                ?  transactionRepository.getAllTransactionCurrentYearByWallet(wallet)
                :  transactionRepository.getAllTransactionCurrentMonthByWallet(wallet);

        int rowIdx = 1;
        for (Transaction t : transactions) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(wallet.getName());
            row.createCell(1).setCellValue(t.getDate().toString());
            row.createCell(2).setCellValue(String.valueOf(t.getAmount()));
            row.createCell(3).setCellValue(t.getTransactionType());
            row.createCell(4).setCellValue("N/A");
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(out);
        workbook.close();
    }
}
