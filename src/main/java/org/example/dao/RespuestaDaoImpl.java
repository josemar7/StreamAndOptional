package org.example.dao;

import org.example.model.Poliza;
import org.example.model.Producto;
import org.example.model.Respuesta;
import org.example.model.Servicio;

import java.util.ArrayList;
import java.util.List;

public class RespuestaDaoImpl implements RespuestaDao {

    private static RespuestaDaoImpl instance;
    private Respuesta respuesta;

    private RespuestaDaoImpl() {
        Servicio servicio = new Servicio().builder()
                .id(1)
                .nombre("Servicio 1")
                .unidades(null)
                .build();
        Servicio servicio2 = new Servicio().builder()
                .id(2)
                .nombre("Servicio 2")
                .unidades(3)
                .build();
        Servicio servicio3 = new Servicio().builder()
                .id(3)
                .nombre("Servicio 3")
                .unidades(5)
                .build();
        List<Servicio> servicios = new ArrayList<>();
        servicios.add(servicio);
        servicios.add(servicio2);
        servicios.add(servicio3);
        Producto producto1 = new Producto().builder()
                .id(1)
                .nombre("Producto 1")
                .servicios(servicios)
                .build();
        Producto producto2 = new Producto().builder()
                .id(2)
                .nombre("Producto 2")
                .build();
        List<Producto> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);
        Poliza poliza1 = new Poliza().builder()
                .id(1)
                .nombre("Poliza 1")
                .productos(productos)
                .build();
        Poliza poliza2 = new Poliza().builder()
                .id(2)
                .nombre("Poliza 2")
                .build();
        List<Poliza> polizas = new ArrayList<>();
        polizas.add(poliza1);
        polizas.add(poliza2);
        respuesta = new Respuesta().builder()
                .codigo("RES")
                .polizas(polizas)
                .build();
    }

    public static RespuestaDaoImpl getInstance() {
        synchronized (RespuestaDaoImpl.class) {
            if (instance == null)
                instance = new RespuestaDaoImpl();
        }
        return instance;
    }

    @Override
    public Respuesta getRespuesta() {
        return respuesta;
    }
}
