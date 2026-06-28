package br.ufscar.pooa.cinema_api.features.snacks.dto;

public class SnackResponseDTO {
    private int id;
    private String name;
    private String description;
    private float price;
    private String category;

    public SnackResponseDTO(int id, String name, String description, float price, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public float getPrice() { return price; }
    public String getCategory() { return category; }
}
