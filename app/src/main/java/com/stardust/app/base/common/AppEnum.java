package com.stardust.app.base.common;

/**
 * Created by bestw on 2017/9/20.
 */

public class AppEnum {

    public enum Sex{
        MALE(0, "男"), FEMALE(1,"女");
        private int code = 0;
        private String name = "";
        private Sex(int code, String name){
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public int getCode(String name) {
            for (Sex sex: Sex.values()) {
                if (sex.getName().equals(name)) {
                    return sex.getCode();
                }
            }
            return 0;
        }

        public String getName() {
            return name;
        }

        public String getName(int code) {
            for (Sex sex: Sex.values()) {
                if (sex.getCode() == code) {
                    return sex.getName();
                }
            }
            return MALE.getName();
        }
        public void setCode(int code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public enum MediaType{
        IMAGE(0, "图片"), AUDIO(1,"音频"), VIDEO(2,"视频"), OTHER(3,"其他");
        private int code = 0;
        private String name = "";
        private MediaType(int code, String name){
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public int getCode(String name) {
            for (MediaType item: MediaType.values()) {
                if (item.getName().equals(name)) {
                    return item.getCode();
                }
            }
            return IMAGE.getCode();
        }

        public String getName() {
            return name;
        }

        public String getName(int code) {
            for (MediaType item: MediaType.values()) {
                if (item.getCode() == code) {
                    return item.getName();
                }
            }
            return IMAGE.getName();
        }
        public void setCode(int code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
