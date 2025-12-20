package com.spring.scheduler.DynamicCron.dto;

import lombok.Data;

@Data
public class Method {
    public void oneTo10(){
        for(int i=1; i<=10; i++){
            System.out.println("i :: "+i);
        }
    }

    public int sumOfNNum(int num){
        int sum=0;
        for(int i=1; i<=num; i++){
            sum+=i;
        }
        return sum;
    }
}
