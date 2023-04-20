package com.dev.pc.services;

import com.dev.pc.models.PagosServiciosDeta;
import com.dev.pc.repository.PagosServiciosDetaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PagosServiciosDetaService implements DAOService<PagosServiciosDeta> {

    @Autowired
    private PagosServiciosDetaRespository detaRepo;

    @Override
    public PagosServiciosDeta registrar(PagosServiciosDeta p) throws Exception {
        return detaRepo.save(p);
    }

    public List<PagosServiciosDeta> registrar(List<PagosServiciosDeta> detas) throws Exception {
        return detaRepo.saveAll(detas);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        detaRepo.deleteById(id);
    }

    @Override
    public PagosServiciosDeta obtener(Long id) throws Exception {
        return detaRepo.findById(id).get();
    }

    @Override
    public List<PagosServiciosDeta> listar() throws Exception {
        return detaRepo.findAll();
    }

    public List<PagosServiciosDeta> listar(Long idCliente) throws Exception {
        return detaRepo.findByClienteIdclientes(idCliente);
    }

    public List<PagosServiciosDeta> listarPorClienteAnio(Long idcliente, int anio) throws Exception{
        return detaRepo.findByClienteIdclientesAndIdanno(idcliente, anio);
    }

    public List<PagosServiciosDeta> listarPorPago(Long idpago) throws Exception {
        return detaRepo.findByPagosServicioId(idpago);
    }

//    listar por rango de fechas
    public List<PagosServiciosDeta> listarPorFechas(Date desde, Date hasta) throws Exception {
        return detaRepo.findByCreatedAtBetween(desde, hasta);
    }
}
