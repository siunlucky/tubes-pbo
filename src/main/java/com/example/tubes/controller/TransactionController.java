package com.example.tubes.controller;

import com.example.tubes.dto.StatisticResponse;
import com.example.tubes.dto.TransactionByCategoryDTO;
import com.example.tubes.dto.TransactionByTypeDTO;
import com.example.tubes.exception.NotFoundException;
import com.example.tubes.exception.ResourceNotFoundException;
import com.example.tubes.model.Expense;
import com.example.tubes.model.Income;
import com.example.tubes.model.Transaction;
import com.example.tubes.model.Wallet;
import com.example.tubes.utils.ApiResponse;
import com.example.tubes.services.TransactionService;
import com.example.tubes.services.UserService;
import com.example.tubes.services.WalletService;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/wallets/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final WalletService walletService;

    public TransactionController(
        TransactionService transactionService,
        WalletService walletService
    ) {
        this.transactionService = transactionService;
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactions(@RequestParam Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        List<Transaction> transactions = transactionService.getAllTransaction(wallet);
        return ResponseEntity.ok(ApiResponse.success(transactions, "All transactions retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> getTransactionById(@PathVariable Long id, @RequestParam Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        Transaction transaction = transactionService.getTransactionById(id, wallet);

        return ResponseEntity.ok(ApiResponse.success(transaction, "Transaction retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> deleteTransactionById(@PathVariable Long id, @RequestParam Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        transactionService.deleteTransactionById(id, wallet);

        return ResponseEntity.ok(ApiResponse.success(null, "Transaction deleted successfully"));
    }

    @GetMapping("/income")
    public ResponseEntity<ApiResponse<List<Income>>> getAllIncome(@RequestParam Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        List<Income> incomes = transactionService.getAllIncome(wallet);

        return ResponseEntity.ok(ApiResponse.success(incomes, "All incomes retrieved successfully"));
    }

    @GetMapping("/expense")
    public ResponseEntity<ApiResponse<List<Expense>>> getAllExpense(@RequestParam Long walletId) {
        Wallet wallet = walletService.getWalletById(walletId);
        List<Expense> expenses = transactionService.getAllExpense(wallet);

        return ResponseEntity.ok(ApiResponse.success(expenses, "All expenses retrieved successfully"));
    }

    @PostMapping("/income")
    public ResponseEntity<ApiResponse<Income>> createIncome(@RequestBody Income income) {
        Wallet wallet = walletService.getWalletById(income.getWallet().getId());
        income.setWallet(wallet);
        Income saved = transactionService.saveIncome(income);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(saved, "Income created successfully"));
    }

    @PostMapping("/expense")
    public ResponseEntity<ApiResponse<Expense>> createExpense(@RequestBody Expense expense) {
        Wallet wallet = walletService.getWalletById(expense.getWallet().getId());
        expense.setWallet(wallet);
        Expense saved = transactionService.saveExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(saved, "Expense created successfully"));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<StatisticResponse>> getBarChartData(
            @RequestParam Long walletId,
            @RequestParam Boolean isYear) {

        Wallet wallet = walletService.getWalletById(walletId);

        Double totalIncome;
        Double totalExpense;
        List<Object[]> rawCategoryData;

        if (Boolean.FALSE.equals(isYear)) {
            totalIncome = transactionService.getTotalCurrentMonthIncome(wallet);
            totalExpense = transactionService.getTotalCurrentMonthExpense(wallet);

            rawCategoryData = transactionService.getTotalCategoryCurrentMonth(wallet);
        } else {
            totalIncome = transactionService.getTotalCurrentYearIncome(wallet);
            totalExpense = transactionService.getTotalCurrentYearExpense(wallet);

            rawCategoryData = transactionService.getTotalCategoryCurrentYear(wallet);
        }

        
        List<TransactionByCategoryDTO> allTransactionByCategory = rawCategoryData.stream()
            .map(row -> new TransactionByCategoryDTO(
                (String) row[0],
                ((Long) row[1]).intValue()
            ))
            .toList();

        List<TransactionByTypeDTO> allTransactionByType = Arrays.asList(
                new TransactionByTypeDTO("income", totalIncome),
                new TransactionByTypeDTO("outcome", totalExpense)
        );

        StatisticResponse statisticResponse = new StatisticResponse();
        statisticResponse.setAllTransactionByType(allTransactionByType);
        statisticResponse.setAllTransactionByCategory(allTransactionByCategory);
        statisticResponse.setWalletId(wallet.getId());
        statisticResponse.setTotalTransaction(totalIncome + totalExpense);
        statisticResponse.setTotalIncome(totalIncome);
        statisticResponse.setTotalOutcome(totalExpense);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(statisticResponse, "Data for statistic retrieved successfully"));
    }
}
