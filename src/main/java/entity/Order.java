package entity;

import entity.Museum;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by teacher ZHANG on 2020/2/19
 */
public class Order {
    private Integer orderId;
    private Museum museum;
    private String phoneNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}
