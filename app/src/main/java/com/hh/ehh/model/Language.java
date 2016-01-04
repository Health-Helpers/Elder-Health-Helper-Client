package com.hh.ehh.model;

/**
 * Created by mpifa on 19/12/15.
 */
public class Language {
    private String id;
    private String code;
    private String name;

    public Language(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "LanguageTable{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        if (id != null ? !id.equals(language.id) : language.id != null) return false;
        if (code != null ? !code.equals(language.code) : language.code != null) return false;
        return !(name != null ? !name.equals(language.name) : language.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
