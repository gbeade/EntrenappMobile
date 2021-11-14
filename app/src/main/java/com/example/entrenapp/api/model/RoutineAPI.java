package com.example.entrenapp.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;



public class RoutineAPI {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("date")
    @Expose
    private Long date;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("metadata")
    @Expose
    private RoutineMetadata metadata;
    @SerializedName("user")
    @Expose
    private UserRoutine user;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public Long getDate() {
        return date;
    }


    public void setDate(Long date) {
        this.date = date;
    }


    public Integer getScore() {
        return score;
    }


    public void setScore(Integer score) {
        this.score = score;
    }


    public Boolean getIsPublic() {
        return isPublic;
    }


    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }


    public String getDifficulty() {
        return difficulty;
    }


    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    public RoutineMetadata getMetadata() {
        return metadata;
    }


    public void setMetadata(RoutineMetadata metadata) {
        this.metadata = metadata;
    }


    public UserRoutine getUser() {
        return user;
    }


    public void setUser(UserRoutine user) {
        this.user = user;
    }




    public class UserRoutine {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("avatarUrl")
        @Expose
        private String avatarUrl;
        @SerializedName("date")
        @Expose
        private Long date;
        @SerializedName("lastActivity")
        @Expose
        private Long lastActivity;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public Long getDate() {
            return date;
        }

        public void setDate(Long date) {
            this.date = date;
        }

        public Long getLastActivity() {
            return lastActivity;
        }

        public void setLastActivity(Long lastActivity) {
            this.lastActivity = lastActivity;
        }

    }


    public class RoutineMetadata {

        @SerializedName("sport")
        @Expose
        private String sport;
        @SerializedName("duracion")
        @Expose
        private Integer duracion;
        @SerializedName("equipacion")
        @Expose
        private Boolean equipacion;


        public String getSport() {
            return sport;
        }


        public void setSport(String sport) {
            this.sport = sport;
        }


        public Integer getDuracion() {
            return duracion;
        }


        public void setDuracion(Integer duracion) {
            this.duracion = duracion;
        }


        public Boolean getEquipacion() {
            return equipacion;
        }


        public void setEquipacion(Boolean equipacion) {
            this.equipacion = equipacion;
        }
    }



    }