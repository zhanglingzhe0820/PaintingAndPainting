package com.painting.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

class Father {
    private String fatherName;

    public Father() {
    }

    public Father(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
}

class Son extends Father {
    private String sonName;

    public Son() {
    }

    public Son(String sonName) {
        this.sonName = sonName;
    }

    public Son(String fatherName, String sonName) {
        super(fatherName);
        this.sonName = sonName;
    }

    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }
}

public class ReflectionUtilTest {

    @Test
    public void getAllFields() {
        assertEquals("fatherName", ReflectionUtil.getAllFields(Son.class)[0].getName());
    }

    @Test
    public void getAllField() {
        try {
            assertEquals("fatherName", ReflectionUtil.getAllField(Son.class,"fatherName").getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}