package fr.efrei.domain;

public class Double extends Room{
    private String bedType;

    public Double(Builder builder){}

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

        public Builder copy(Double doubleRoom){
            this.roomNumber = doubleRoom.roomNumber;
            this.pricePerNight = doubleRoom.pricePerNight;
            this.bedType = doubleRoom.bedType;
            return this;
        }

        public Double build(){
            return new Double(this);
        }
    }
}
