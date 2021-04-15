package com.blend.jetpackstudy.databinding.other;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;

public class ObservableGoods {

    private ObservableField<String> name;

    private ObservableField<String> details;

    private ObservableFloat price;

    public ObservableGoods(String name, String details, float price) {
        this.name = new ObservableField<>(name);
        this.details = new ObservableField<>(details);
        this.price = new ObservableFloat(price);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<String> getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public ObservableFloat getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }
}
