package app.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


@Table(name = "Accounts")
public class Accounts {

    @Id
    @GeneratedValue
    @Column (name = "AccountNumber", nullable = false, unique = true)
    private int accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", insertable = false, updatable = false)
    private Participants idParticipant;

    @Column (name = "Amount", nullable = false)
    private float amount;

    @Column (name = "Status", nullable = false)
    private boolean status;

    @Column (name = "Deadline", nullable = true)
    private Date deadline;

    @Column (name = "ID_Hiking", nullable = true)
    private int idHiking;

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Participants getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(Participants idParticipants) {
        this.idParticipant = idParticipants;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getIdHiking() {
        return idHiking;
    }

    public void setIdHiking(int idHiking) {
        this.idHiking = idHiking;
    }
}

