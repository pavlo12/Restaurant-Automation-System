package com.mainacad.myproject.entities;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "order_id")
    private List<OrderedDish> orderedDishes = new ArrayList();

    @ManyToOne
    @JoinColumn(name = "table_id")
    private com.mainacad.myproject.entities.Table tableOrdered;

    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeBefore;
    private String status;

    private int countPerson;

    @OneToOne
    @JoinColumn(name = "order_id")
    private User customer;


    public int getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(int countPerson) {
        this.countPerson = countPerson;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public LocalDateTime getDateTimeBefore() {
        return dateTimeBefore;
    }

    public void setDateTimeBefore(LocalDateTime dateTimeBefore) {
        this.dateTimeBefore = dateTimeBefore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<OrderedDish> getOrderedDishes() {
        return orderedDishes;
    }

    public void addOrderedDishes(OrderedDish orderedDish) {
        this.orderedDishes.add(orderedDish);
    }

    public com.mainacad.myproject.entities.Table getTableOrdered() {
        return tableOrdered;
    }

    public void setTableOrdered(com.mainacad.myproject.entities.Table tableOrdered) {
        this.tableOrdered = tableOrdered;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedDishes=" + orderedDishes +
                ", tableOrdered=" + tableOrdered +
                ", dateTimeFrom=" + dateTimeFrom +
                ", dateTimeBefore=" + dateTimeBefore +
                ", status='" + status + '\'' +
                ", countPerson=" + countPerson +
                ", customer=" + customer +
                '}';
    }
}