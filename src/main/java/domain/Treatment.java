package domain;

import domain.employment.SeniorOfficer;

public class Treatment {
    private int id;
    private String treatment;
    private SeniorOfficer responsible;
    private Patient patient;

    public Treatment(int id, String treatment, SeniorOfficer responsible, Patient patient) {
        this.id = id;
        this.treatment = treatment;
        this.responsible = responsible;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public String getTreatment() {
        return treatment;
    }

    public SeniorOfficer getResponsible() {
        return responsible;
    }

    public Patient getPatient() {
        return patient;
    }
}
