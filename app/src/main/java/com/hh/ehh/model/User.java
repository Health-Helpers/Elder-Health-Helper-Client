package com.hh.ehh.model;


import android.graphics.drawable.Drawable;

public class User {
    protected String id;
    protected String name;
    protected String surname;
    protected String birthDate;
    protected String phone;
    protected String address;
    protected String language;
    protected String imagePath = null;
    protected String idDoc;

    public User(UserBuilder builder) {
        id = builder.id;
        name = builder.name;
        surname = builder.surname;
        birthDate = builder.birthDate;
        phone = builder.phone;
        address = builder.address;
        language = builder.language;
        idDoc = builder.idDoc;
    }

    public User(User user) {
        id = user.id;
        name = user.name;
        surname = user.surname;
        birthDate = user.birthDate;
        phone = user.phone;
        address = user.address;
        language = user.language;
        idDoc = user.idDoc;
    }

    public User() {

    }

    public boolean hasNullValues() {
        return  id == null ||
                surname == null ||
                birthDate == null ||
                phone == null ||
                address == null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Drawable getImageAsDrawable() {
        return Drawable.createFromPath(imagePath);
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (birthDate != null ? !birthDate.equals(user.birthDate) : user.birthDate != null)
            return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (language != null ? !language.equals(user.language) : user.language != null)
            return false;
        if (imagePath != null ? !imagePath.equals(user.imagePath) : user.imagePath != null)
            return false;
        return !(idDoc != null ? !idDoc.equals(user.idDoc) : user.idDoc != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (idDoc != null ? idDoc.hashCode() : 0);
        return result;
    }

    public static class UserBuilder {
        private String id;
        private String name;
        private String surname;
        private String birthDate;
        private String phone;
        private String address;
        private String language;
        private String idDoc;

        public UserBuilder() {
        }

        public UserBuilder(String id) {
            this.id = id;
        }

        public UserBuilder setId(String did) {
            this.id = did;
            return this;
        }


        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder setBirthdate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public UserBuilder setIdDoc(String idDoc) {
            this.idDoc = idDoc;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
