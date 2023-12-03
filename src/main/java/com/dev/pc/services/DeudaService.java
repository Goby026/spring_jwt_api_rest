package com.dev.pc.services;

import com.dev.pc.enums.Meses;
import com.dev.pc.models.Cliente;
import com.dev.pc.models.Deuda;
import com.dev.pc.models.DeudaDescripcion;
import com.dev.pc.repository.ClienteRepository;
import com.dev.pc.repository.DeudaDescripcionRepository;
import com.dev.pc.repository.DeudaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeudaService implements DAOService<Deuda> {

    private final static Logger logger = LoggerFactory.getLogger(DeudaService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DeudaRepository repository;

    @Autowired
    private DeudaDescripcionRepository deudaDescripcionRepository;

    @Override
    public Deuda registrar(Deuda p) throws Exception {
        return repository.save(p);
    }

    public List<Deuda> registrarTodo(List<Deuda> deudas) throws Exception {
        List<Deuda> newDeudas = new ArrayList<>();

        SimpleDateFormat standar = new SimpleDateFormat("yyyy-MM-dd");


        for (Deuda deuda: deudas) {
            String dateStr = standar.format(deuda.getPeriodo());
            Date calDate = standar.parse(dateStr);
            deuda.setPeriodo(calDate);

            newDeudas.add(deuda);
        }

        return repository.saveAll(newDeudas);
    }

//    actualizar estado de deudas pagadas
    public List<Deuda> registrar(List<Deuda> deudas) throws Exception {
        return repository.saveAll(deudas);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public Deuda obtener(Long id) throws Exception {
        return repository.findById(id).get();
    }

    @Override
    public List<Deuda> listar() throws Exception {
        return repository.findAll();
    }

    public List<Deuda> listar(Long id) throws Exception {
        return repository.findByClienteIdclientes(id);
    }

    public List<Deuda> listarPorZonaAnnio(Long idzona, Date inicio, Date fin) throws Exception {
        return repository.findByClienteZonaIdtbzonasAndPeriodoBetween(idzona, inicio, fin);
    }

    public List<Deuda> listarPorClienteAnnio(Long idcliente, Date inicio, Date fin) throws Exception {
        return repository.findByClienteIdclientesAndPeriodoBetween(idcliente, inicio, fin);
    }

    public List<Deuda> listarPorZona(Long idzona) throws Exception {
        return repository.findByClienteZonaIdtbzonas(idzona);
    }

    public void generarDeudaAnnio() throws Exception {
        Meses[] meses = Meses.values(); //enum de meses
        List<Cliente> clientes = clienteRepository.findAll();
        DeudaDescripcion deudaDescripcion = deudaDescripcionRepository.findById(1L).get();



        for (Cliente cli: clientes) { //850
//            Deuda deuda = Deuda.builder()
//                    .cliente(cli)
//                    .codigo("GEN-"+annio)
//                    .deudaDescripcion(deudaDescripcion)
//                    .deudaEstado(null)
//                    .periodo(null)
//                    .total(cli.getCosto().getServicio().getTarifarios().indexOf(cli))
//                    .build();
//            Deuda deuda = new Deuda();
//            deuda.setCliente(cli);
//            deuda.setCodigo("GEN-"+2023);
//            deuda.setDeudaDescripcion(deudaDescripcion);
//            deuda.setEstado(1);
//            deuda.setPeriodo(new Date());
//            deuda.setTotal(10L);
//
//            for (Meses mes: meses) { //12
////                repository.save(deuda);
//                logger.info(deuda.toString());
//            }
            logger.info(cli.toString());
        }
    }

    public List<Deuda> listarPorPeriodos(Date inicio, Date fin) throws Exception {
        return repository.findByPeriodoBetween(inicio, fin);
    }
}
