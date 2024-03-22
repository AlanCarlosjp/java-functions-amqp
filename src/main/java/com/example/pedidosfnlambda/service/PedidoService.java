package com.example.pedidosfnlambda.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PedidoService {

    private List<PedidoRec> pedidosRecList = new ArrayList<>();
    private AtomicInteger id = new AtomicInteger(0);


    public List<PedidoRec> findAll() {
        return pedidosRecList;
    }

    public void create(String nome) {
        pedidosRecList.add(new PedidoRec(id.getAndIncrement(), nome));
    }


    public record PedidoRec(int id, String nome) {

    }
}
