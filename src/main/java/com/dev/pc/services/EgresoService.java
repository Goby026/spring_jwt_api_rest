package com.dev.pc.services;

import com.dev.pc.models.Caja;
import com.dev.pc.models.Egreso;
import com.dev.pc.repository.CajaRepository;
import com.dev.pc.repository.EgresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EgresoService implements DAOService<Egreso> {

    @Autowired
    private EgresoRepository repository;

    @Autowired
    private CajaRepository cajaRepository;

    @Override
    public Egreso registrar(Egreso p) throws Exception {
        double montoegresos = 0;

        Egreso egreso = repository.save(p);

        List<Egreso> egresos = repository.findByCajaIdcaja(p.getCaja().getIdcaja());

        for (Egreso e: egresos) {
            montoegresos = montoegresos + e.getImporte();
        }

//        actualizar campo [totalegresos] de la caja
        Caja updateCaja = p.getCaja();
        updateCaja.setTotalegresos(montoegresos);

        cajaRepository.save(updateCaja);

        return egreso;
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Egreso obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Egreso> listar() throws Exception {
        return repository.findAll();
    }

    public List<Egreso> listarPorCaja(long idcaja) throws Exception {
        return repository.findByCajaIdcaja(idcaja);
    }
}
