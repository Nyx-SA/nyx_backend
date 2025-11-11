package com.github.grupo_s.nyx_app.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> getAll() {
        return eventoRepository.findAll();
    }

    public Evento getById(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    public Evento saveEvento(Evento evento) {
        // Validación: si ya existe un evento con ese nombre en ese establecimiento, retorna null
        if (eventoRepository.existsByNombreAndEstablecimiento(evento.getNombre(), evento.getEstablecimiento())) {
            return null;
        }
        return eventoRepository.save(evento);
    }

    public Evento updateEvento(Long id, Evento evento) {
        // Verifica que el evento exista antes de actualizar
        if (!eventoRepository.existsById(id)) {
            return null;
        }
        evento.setIdEvento(id); // Asegura que se actualice el evento correcto
        return eventoRepository.save(evento);
    }

    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }
}