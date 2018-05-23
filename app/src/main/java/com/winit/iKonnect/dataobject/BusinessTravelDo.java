package com.winit.iKonnect.dataobject;

/**
 * Created by Ojha.Sandeep on 10/31/2017.
 */

public class BusinessTravelDo extends FormRequestDO {

    public String Destinations = "";
    public String PurposeofTravel = "";
    public String StartDate = "";
    public String EndDate = "";
    public String ContactNumberduringBusinessTravel = "";
    public String TicketRequired = "";
    public String Sector = "";
    public String VisaRequired = "";
    public String HotelAccommodation = "";
    public String CostDebitto = "";
    public String AnyOtherRequirements = "";

    public String getDestinations() {
        return Destinations;
    }

    public void setDestinations(String destinations) {
        Destinations = destinations;
    }

    public String getPurposeofTravel() {
        return PurposeofTravel;
    }

    public void setPurposeofTravel(String purposeofTravel) {
        PurposeofTravel = purposeofTravel;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getContactNumberduringBusinessTravel() {
        return ContactNumberduringBusinessTravel;
    }

    public void setContactNumberduringBusinessTravel(String contactNumberduringBusinessTravel) {
        ContactNumberduringBusinessTravel = contactNumberduringBusinessTravel;
    }

    public String getTicketRequired() {
        return TicketRequired;
    }

    public void setTicketRequired(String ticketRequired) {
        TicketRequired = ticketRequired;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getVisaRequired() {
        return VisaRequired;
    }

    public void setVisaRequired(String visaRequired) {
        VisaRequired = visaRequired;
    }

    public String getHotelAccommodation() {
        return HotelAccommodation;
    }

    public void setHotelAccommodation(String hotelAccommodation) {
        HotelAccommodation = hotelAccommodation;
    }

    public String getCostDebitto() {
        return CostDebitto;
    }

    public void setCostDebitto(String costDebitto) {
        CostDebitto = costDebitto;
    }

    public String getAnyOtherRequirements() {
        return AnyOtherRequirements;
    }

    public void setAnyOtherRequirements(String anyOtherRequirements) {
        AnyOtherRequirements = anyOtherRequirements;
    }
}
