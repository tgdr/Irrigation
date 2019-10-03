package lty.buu.irrigation.adapter;

import lty.buu.irrigation.bean.Bean;

public class CardData {

    String image_url,name;
    String description;
    String score;

    public boolean isIspan() {
        return ispan;
    }

    public void setIspan(boolean ispan) {
        this.ispan = ispan;
    }

    boolean ispan;
//    public CardData(String image_url, String name, String description, String score, String tv_num) {
//        this.image_url = image_url;
//        this.name = name;
//        this.description = description;
//        this.score = score;
//        this.tv_num = tv_num;
//    }


    public CardData(String image_url, String name, String description, String score, String tv_num, boolean ispan) {
        this.image_url = image_url;
        this.name = name;
        this.description = description;
        this.score = score;
        this.ispan = ispan;
        this.tv_num = tv_num;
    }

    @Override
    public String toString() {
        return "CardData{" +
                "image_url='" + image_url + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", score=" + score +
                ", tv_num='" + tv_num + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String tv_num;



    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTv_num() {
        return tv_num;
    }

    public void setTv_num(String tv_num) {
        this.tv_num = tv_num;
    }
}
