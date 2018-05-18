package safety.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "locations", schema = "womansafety", catalog = "")
public class LocationsEntity {
    private int idPlace;
    private String placeName;
    private String description;
    private String phone;
    private double latitude;
    private double longitude;

    @Id
    @Column(name = "id_place")
    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    @Basic
    @Column(name = "name_of_place")
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Basic
    @Column(name = "place_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "lat")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "lng")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LocationsEntity that = (LocationsEntity) object;
        return idPlace == that.idPlace &&
                latitude == that.latitude &&
                longitude == that.longitude &&
                Objects.equals(placeName, that.placeName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlace, placeName, description, phone, latitude, longitude);
    }
}