package fr.efrei.domain;

public class Single extends Room{
    private String bedType;

    public Single(Builder builder){}

    public static class Builder{
        private int roomNumber;
        private double pricePerNight;
        private String bedType;

        public Builder setRoomNumber(int roomNumber){
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder setPricePerNight(double pricePerNight){
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder setBedType(String bedType){
            this.bedType = bedType;
            return this;
        }

        public Builder copy(Single single){
            this.roomNumber = single.roomNumber;
            this.pricePerNight = single.pricePerNight;
            this.bedType = single.bedType;
            return this;
        }

        public Single build(){
            return new Single(this);
        }
    }
}
