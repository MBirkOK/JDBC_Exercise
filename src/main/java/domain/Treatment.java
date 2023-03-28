package domain;

import domain.employment.SeniorOfficer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_exercise_treatment")
public class Treatment {
    @Id
    private int id;
    @Column(name = "treatment")
    private String treatment;

    @OneToOne
    private SeniorOfficer responsible;
    @Column(name = "patient_id")
    @OneToOne
    private Patient patient;

    @Column(name = "med_id")
    @OneToOne
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

    protected Treatment() {
        //for JPA
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
