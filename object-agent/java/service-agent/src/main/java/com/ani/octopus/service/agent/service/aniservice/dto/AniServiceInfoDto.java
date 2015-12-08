package com.ani.octopus.service.agent.service.aniservice.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhaoyu on 15-11-4.
 */
public class AniServiceInfoDto implements Serializable {
    private static final long serialVersionUID = 7605800320163634441L;

    @NotNull
    @Size(max = 150)
    public String serviceServerUrl;
    @NotNull
    @Size(max = 150)
    public String logoPath;
    @NotNull
    public Set<LanguageEnum> languageSet;
    @NotNull
    public Set<String> tagSet;
    public Double price;
    @NotNull
    public Date onShelf;
    @NotNull
    @Size(max = 255)
    public String description;

    public AniServiceInfoDto() {
    }

    public AniServiceInfoDto(String serviceServerUrl, String logoPath, Set<LanguageEnum> languageSet,
                             Set<String> tagSet, Double price, Date onShelf, String description) {
        this.serviceServerUrl = serviceServerUrl;
        this.logoPath = logoPath;
        this.languageSet = languageSet;
        this.tagSet = tagSet;
        this.price = price;
        this.onShelf = onShelf;
        this.description = description;
    }

    public static Set<String> toStringSet(Set<LanguageEnum> languageSet) {
        if (languageSet == null) {
            return null;
        }
        Set<String> set = new HashSet<>();
        for (LanguageEnum languageEnum : languageSet) {
            set.add(languageEnum.toString());
        }
        return set;
    }

    public static Set<LanguageEnum> toEnumSet(Set<String> languageSet) {
        if (languageSet == null) {
            return null;
        }
        Set<LanguageEnum> enumSet = new HashSet<>();
        for (String language : languageSet) {
            enumSet.add(getEnum(LanguageEnum.class, language));
        }
        return enumSet;
    }

    public static LanguageEnum getEnum(Class<LanguageEnum> enumClass , String enumName) {
        EnumSet<LanguageEnum> languageEnumEnumSet = EnumSet.allOf(enumClass);
        for (LanguageEnum languageEnum : languageEnumEnumSet) {
            if (languageEnum.getName().equals(enumName)) {
                return languageEnum;
            }
        }
        return null;
    }
}
