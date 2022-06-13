package com.example.springstartherespringdata.controller;

import com.example.springstartherespringdata.model.Account;
import com.example.springstartherespringdata.service.TransferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
    curl http://localhost:8080/accounts

    curl http://localhost:8080/transfer -H "Content-Type:application/json"
    -d "{\"senderAccountId\":1, \"receiverAccountId\":2, \"amount\":100}

    curl http://localhost:8080/accounts?name=Jane+Down
 */
@RestController
public class AccountController {
    private final TransferService transferService;

    public AccountController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestBody TransferRequest request) {
        transferService.transferMoney(request.getSenderAccountId(),
                request.getReceiverAccountId(), request.getAmount());
    }

    @GetMapping("/accounts")
    public Iterable<Account> getAllAccounts(@RequestParam(required = false) String name) {
        if (name == null) {
            return transferService.getAllAccounts();
        } else {
            return transferService.findAccountsByName(name);
        }
    }
}
