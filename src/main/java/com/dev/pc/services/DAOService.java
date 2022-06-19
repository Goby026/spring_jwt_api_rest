package com.dev.pc.services;


import java.util.List;

public interface DAOService <Param> {
    public Param registrar(Param p) throws Exception;

    public void eliminar(Long id) throws Exception;

    public Param obtener(Long id) throws Exception;

    public List<Param> listar() throws Exception;
}
