package com.dev.pc.services;

import com.dev.pc.models.Caja;
import com.dev.pc.models.PagosServicio;
import com.dev.pc.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CajaService implements DAOService<Caja> {

    @Autowired
    private CajaRepository repository;

    @Override
    public Caja registrar(Caja p) throws Exception {
        return repository.save(p);
    }

    public Caja persistirMonto(List<PagosServicio> pagos, Caja caja) throws Exception {

        Double  monto = 0.00;
        for (PagosServicio pg : pagos) {
            monto += pg.getMontopagado();
        }
        caja.setTotal(monto);
//        cajaService.registrar(caja);
        return repository.save(caja);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Caja obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Caja> listar() throws Exception {
        return repository.findAll();
    }

    public List<Caja> listar(Date apertura) throws Exception {
        return repository.findByFapertura(apertura);
    }

    public List<Caja> listarAsc() throws Exception {
        return repository.findByOrderByIdcajaDesc();
    }

    public Caja obtenerUlimo() throws Exception {
        return repository.findTopByOrderByIdcajaDesc();
    }
}
