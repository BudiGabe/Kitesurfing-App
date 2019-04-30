package data.model;

public class Spot {
    private String name;
    private String country;
    private String id;
    private boolean isFavorite;


    public Spot(String name, String country, String id, boolean isFavorite) {
        this.name = name;
        this.country = country;
        this.id = id;
        this.isFavorite = isFavorite;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
