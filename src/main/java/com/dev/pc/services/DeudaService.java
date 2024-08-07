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

    private final ClienteRepository clienteRepository;
    private final DeudaRepository repository;
    private final DeudaDescripcionRepository deudaDescripcionRepository;

    public DeudaService(ClienteRepository clienteRepository, DeudaRepository repository, DeudaDescripcionRepository deudaDescripcionRepository) {
        this.clienteRepository = clienteRepository;
        this.repository = repository;
        this.deudaDescripcionRepository = deudaDescripcionRepository;
    }


    @Override
    public Deuda registrar(Deuda p) throws Exception {
        return this.repository.save(p);
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

        return this.repository.saveAll(newDeudas);
    }

//    actualizar estado de deudas pagadas
    public List<Deuda> registrar(List<Deuda> deudas) throws Exception {
        return this.repository.saveAll(deudas);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    @Override
    public Deuda obtener(Long id) throws Exception {
        return this.repository.findById(id).get();
    }

    @Override
    public List<Deuda> listar() throws Exception {
        return this.repository.findAll();
    }

    public List<Deuda> listar(Long id) throws Exception {
        return this.repository.findByClienteIdclientes(id);
    }

    public List<Deuda> listarPorZonaAnnio(Long idzona, Date inicio, Date fin) throws Exception {
        return this.repository.findByClienteZonaIdtbzonasAndPeriodoBetween(idzona, inicio, fin);
    }

    public List<Deuda> listarPorAnnio(int year) throws Exception {
        return this.repository.findAllByYear(year);
    }

    public List<Deuda> listarPorClienteAnnio(Long idcliente, Date inicio, Date fin) throws Exception {
        return this.repository.findByClienteIdclientesAndPeriodoBetween(idcliente, inicio, fin);
    }

    public List<Deuda> listarPorZona(Long idzona) throws Exception {
        return this.repository.findByClienteZonaIdtbzonas(idzona);
    }

    public void generarDeudaAnnio() throws Exception {
        //1:indicar numero de mes de corte
        //2:obtener todos los pagos del mes de corte
        //3:generar deuda para todos los que no aparecen en el paso 2
        Meses[] meses = Meses.values(); //enum de meses
        List<Cliente> clientes = this.clienteRepository.findAll();
        //DeudaDescripcion deudaDescripcion = deudaDescripcionRepository.findById(1L).get();

        for (Meses mes: meses) { //12
//                repository.save(deuda);
            for (Cliente cli: clientes) { //850
//            Deuda deuda = Deuda.builder()
//                    .cliente(cli)
//                    .codigo("GEN-"+"2014")
//                    .deudaDescripcion(deudaDescripcion)
//                    .deudaEstado(null)
//                    .periodo(null)
//                    .total(cli.getCosto().getServicio().getTarifarios().indexOf(cli))
//                    .build();
//            Deuda deuda = new Deuda();
//            deuda.setCliente(cli);
//            deuda.setCodigo("GEN-"+2024);
//            deuda.setDeudaDescripcion(deudaDescripcion);
//            deuda.setEstado(1);
//            deuda.setPeriodo(new Date());
//            deuda.setTotal(10L);

                logger.info(cli.getNombres());
            }
        }


    }

    public List<Deuda> listarPorPeriodos(Date inicio, Date fin) throws Exception {
        return this.repository.findByPeriodoBetween(inicio, fin);
    }
}
