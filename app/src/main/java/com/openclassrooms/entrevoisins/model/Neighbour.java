package com.openclassrooms.entrevoisins.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Model object representing a Neighbour
 *
 * As an educational project, we don't use any persistence library
 * As an educational project, we show both Java serializable and Parcelable implementations
 *
 * Keep in mind that Parcelable is faster than Serializable, but it is also more verbose
 */
public class Neighbour implements Serializable, Parcelable {

    /** Identifier */
    private long id;

    /** Full name */
    private String name;

    /** Avatar */
    private final String avatarUrl;

    /** Address */
    private final String address;

    /** Phone number */
    private final String phoneNumber;

    /** About me */
    private final String aboutMe;

    /** Favorite */
    private boolean favorite;

    /**
     * Constructor
     * @param id : id
     * @param name : name
     * @param avatarUrl : avatar
     */
    public Neighbour(long id, String name, String avatarUrl, String address,
                     String phoneNumber, String aboutMe) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.aboutMe = aboutMe;
    }

    /**
     * Get the ID of the neighbour
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set the ID of the neighbour
     * @param id : id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the name of the neighbour
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the neighbour
     * @param name : name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the avatar URL of the neighbour
     * @return avatar
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Get the address of the neighbour
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the phone number of the neighbour
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Get the about me description of the neighbour
     * @return about me description
     */
    public String getAboutMe() {
        return aboutMe;
    }

    /**
     * Tell if that neighbour is favorite
     * @return true if favorite, false otherwise
     */
    public Boolean isFavorite() { return favorite; }

    /**
     * Tell if the neighbour is favorite
     * @param favorite : true if favorite, false otherwise
     */
    public void setIsFavorite(Boolean favorite) { this.favorite = favorite; }

    /**
     * Two neighbours are considered equal if they have exactly the same id
     * @param o : the object to compare
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neighbour neighbour = (Neighbour) o;
        return Objects.equals(id, neighbour.id);
    }

    /**
     *
     * Not strictly necessary, but we show a example with parcelable bellow
     * Note that Intellij have generated this code for us
     *
     */

    protected Neighbour(Parcel in) {
        id = in.readLong();
        name = in.readString();
        avatarUrl = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        aboutMe = in.readString();
        favorite = in.readByte() != 0;
    }

    public static final Creator<Neighbour> CREATOR = new Creator<Neighbour>() {
        @Override
        public Neighbour createFromParcel(Parcel in) {
            return new Neighbour(in);
        }

        @Override
        public Neighbour[] newArray(int size) {
            return new Neighbour[size];
        }
    };


    /**
     * Parcelable serialization example
     * @param parcel : parcel
     * @param i : flags
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(avatarUrl);
        parcel.writeString(address);
        parcel.writeString(phoneNumber);
        parcel.writeString(aboutMe);
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }

    /**
     * Neighbours have the same hashcode if they have the same id
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Parcelable serialization example
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

}
