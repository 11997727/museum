package entity;

/**
 * Created by teacher ZHANG on 2020/2/19
 */
public class Museum {
    private Integer museumId;
    private String museumName;
    private String museumInfo;
    private String museumDesc;
    private Integer capacity;
    private String image;
    private Integer orderNum; //预约数
    private Integer dayOff; //休息日

    public Integer getMuseumId() {
        return museumId;
    }

    public void setMuseumId(Integer museumId) {
        this.museumId = museumId;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getMuseumInfo() {
        return museumInfo;
    }

    public void setMuseumInfo(String museumInfo) {
        this.museumInfo = museumInfo;
    }

    public String getMuseumDesc() {
        return museumDesc;
    }

    public void setMuseumDesc(String museumDesc) {
        this.museumDesc = museumDesc;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDayOff() {
        return dayOff;
    }

    public void setDayOff(Integer dayOff) {
        this.dayOff = dayOff;
    }
}
