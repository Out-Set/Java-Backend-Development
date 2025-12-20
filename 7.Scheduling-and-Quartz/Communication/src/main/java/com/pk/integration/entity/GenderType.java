package com.pk.integration.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;

@Entity
@DynamicUpdate
@DynamicInsert
public class GenderType extends GenericParameter {
   public static final String INTERNET = "Internet";
   public static final String INTRANET = "Intranet";
   public static final String BOTH = "Both";

   public String toString() {
      return this.getCode();
   }
}
