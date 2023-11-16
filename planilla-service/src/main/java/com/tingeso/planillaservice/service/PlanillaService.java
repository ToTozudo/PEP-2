package com.tingeso.planillaservice.service;

import com.tingeso.planillaservice.entity.Planilla;
import com.tingeso.planillaservice.repository.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanillaService {
    @Autowired
    PlanillaRepository planillaRepository;
    public List<Planilla> getAll() { return planillaRepository.findAll();}
    public Planilla getPlanillaByRut(String rut){return planillaRepository.findByRut(rut);}

    public void guardarPlanilla(Planilla planilla){planillaRepository.save(planilla);}

    public void updatePlanilla(String rut, Planilla planilla) {
        Planilla planillaAntigua = getPlanillaByRut(rut);
        planillaAntigua.setDctoPuntaje(planilla.getDctoPuntaje());
        planillaAntigua.setIntereses(planilla.getIntereses());
    }
}
