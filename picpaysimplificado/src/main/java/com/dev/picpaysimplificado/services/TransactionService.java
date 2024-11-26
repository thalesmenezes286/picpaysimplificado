package com.dev.picpaysimplificado.services;

import com.dev.picpaysimplificado.api.ApiResponse;
import com.dev.picpaysimplificado.dto.TransactionDTO;
import com.dev.picpaysimplificado.entities.Transaction;
import com.dev.picpaysimplificado.entities.User;
import com.dev.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;


@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;
    private RestTemplate restTemplate;

    public void createTransaction(TransactionDTO transactionDTO) throws Exception{
        User sender = this.userService.findById(transactionDTO.senderId());
        User receiver = this.userService.findById(transactionDTO.receiverId());

        userService.validateTransaction(sender, transactionDTO.value());

        if(!this.isAuthorized()){
            throw new Exception("Transação não autorizaeda");
        }

        var newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.repository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);
    }

//    public boolean authorizeTransaction(User sender, BigDecimal value){
//        ResponseEntity<Map> autorization = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
//
//        if(autorization.getStatusCode() == HttpStatus.Ok && autorization.getBody().get("sucess"))
//    }

    public boolean isAuthorized(){
        String url = "https://util.devi.toos/api/v2/authorize";

        //Faz a reiquisicao GET  e mapeia a resposta para ApiResponse
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

        if(response != null && "success".equalsIgnoreCase(response.getStatus()) &&  response.getData() != null){
            return response.getData().isAuthorization();
        }

        return false;
    }
}
