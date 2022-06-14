package com.example.springstartherespringdata.ch_14_15.service;

import com.example.springstartherespringdata.ch_14_15.model.Account;
import com.example.springstartherespringdata.ch_14_15.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService {
    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferMoney(long idSender, long idReceiver, BigDecimal amount){
        Account sender = accountRepository.findById(idSender).orElseThrow(
                () -> new AccountNotFoundException());
        Account receiver = accountRepository.findById(idReceiver).orElseThrow(
                () -> new AccountNotFoundException());
        BigDecimal senderNewAmount = sender.getAmount().subtract(amount);
        BigDecimal receiverNewAmount = receiver.getAmount().add(amount);
        /*use custom realisation*/
        accountRepository.changeAmount(idSender, senderNewAmount);
        accountRepository.changeAmount(idReceiver, receiverNewAmount);
    }

    /*Use Spring realisation*/
    public Iterable<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    /*use custom realisation*/
    public List<Account> findAccountsByName(String name){
        return accountRepository.findAccountsByName(name);
    }
}
