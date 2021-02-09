package io.github.onetwostory.salon.entity;

import java.math.BigDecimal;

public class ServiceOption extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static class Builder {
        private ServiceOption serviceOption;

        public Builder() {
            serviceOption = new ServiceOption();
        }

        public Builder name(String name) {
            serviceOption.setName(name);
            return this;
        }

        public Builder description(String description) {
            serviceOption.setDescription(description);
            return this;
        }

        public Builder price(BigDecimal price) {
            serviceOption.setPrice(price);
            return this;
        }

        public Builder id(Integer id) {
            serviceOption.setIdentifier(id);
            return this;
        }

        public ServiceOption build() {
            return serviceOption;
        }

    }
}
