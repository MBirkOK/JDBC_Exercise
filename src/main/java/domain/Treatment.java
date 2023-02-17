package domain;

import domain.employment.SeniorOfficer;

public class Treatment {
    private int id;
    private String treatment;
    private SeniorOfficer responsible;
    private Patient patient;

    private Medication medication;

    public Treatment(int id, String treatment, SeniorOfficer responsible, Patient patient) {
        this.id = id;
        this.treatment = treatment;
        this.responsible = responsible;
        this.patient = patient;
    }

    public Treatment(int id, String treatment, SeniorOfficer responsible, Patient patient, Medication medication) {
        this.id = id;
        this.treatment = treatment;
        this.responsible = responsible;
        this.patient = patient;
        this.medication = medication;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
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
