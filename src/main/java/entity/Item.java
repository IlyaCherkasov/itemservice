package entity;

import dto.ItemDTO;

public class Item {
    private Long id;
    private String name;
    private long price;
    private long amount;
    private long reservedAmount;

    public Item() {}

    public Item(ItemDTO itemDTO) {
        this.id = itemDTO.getId();
        this.name = itemDTO.getName();
        this.price = itemDTO.getPrice();
        this.amount = itemDTO.getAmount();
        this.reservedAmount = itemDTO.getReservedAmount();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getReservedAmount() {
        return this.reservedAmount;
    }

    public void setReservedAmount(long reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public void changeAmount(long amount) {
        this.amount += amount;
    }

    public void changeReservedAmount(long reservedAmount) {
        this.reservedAmount += reservedAmount;
    }
}