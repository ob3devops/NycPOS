package com.openbravo.pos.customers;

import com.openbravo.format.Formats;
import com.openbravo.pos.util.RoundUtils;
import java.util.Date;

public class CustomerInfoExt extends CustomerInfo {

    protected String taxcustomerid;
    protected String notes;
    protected boolean visible;
    protected String card;
    protected Double maxdebt;
    protected Date curdate;
    protected Double curdebt;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String phone;
    protected String phone2;
    protected String fax;
    protected String address;
    protected String address2;
    protected String postal;
    protected String city;
    protected String region;
    protected String country;
    protected String image;

    public CustomerInfoExt(String id) {
        super(id);
    }

    /**
     *
     * @return customer's tax category
     */
    public String getTaxCustCategoryID() {
        return taxcustomerid;
    }
    public void setTaxCustomerID(String taxcustomerid) {
        this.taxcustomerid = taxcustomerid;
    }

    /**
     *
     * @return notes string
     */
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     *
     * @return Is visible Y/N? boolean
     */
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     *
     * @return customer's hashed member/loyalty card string
     */
    public String getCard() {
        return card;
    }
    public void setCard(String card) {
        this.card = card;
    }

    /**
     *
     * @return customer's maximum allowed debt value
     */
    public Double getMaxdebt() {
        return maxdebt;
    }
    public void setMaxdebt(Double maxdebt) {
        this.maxdebt = maxdebt;
    }
    public String printMaxDebt() {
        return Formats.CURRENCY.formatValue(RoundUtils.getValue(getMaxdebt()));
    }

    /**
     *
     * @return customer's last ticket transaction date
     */
    public Date getCurdate() {
        return curdate;
    }
    public void setCurdate(Date curdate) {
        this.curdate = curdate;
    }
    public String printCurDate() {
        return Formats.DATE.formatValue(getCurdate());
    }

    /**
     *
     * @return customer's current value of account
     */
    public Double getCurdebt() {
        return curdebt;
    }
    public void setCurdebt(Double curdebt) {
        this.curdebt = curdebt;
    }
    public String printCurDebt() {
        return Formats.CURRENCY.formatValue(RoundUtils.getValue(getCurdebt()));
    }


    public void updateCurDebt(Double amount, Date d) {

        curdebt = curdebt == null ? amount : curdebt + amount;
// JG Aug 2014
        curdate = (new Date());

        if (RoundUtils.compare(curdebt, 0.0) > 0) {
            if (curdate == null) {
                // new date
                curdate = d;
            }
        } else if (RoundUtils.compare(curdebt, 0.0) == 0) {
            curdebt = null;
            curdate = null;
        } else { // < 0
            curdate = null;
        }

    }

    /**
     *
     * @return customer's firstname string
     */
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return customer's lastname string
     */
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     *
     * @return customer's email string
     */
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return customer's Primary telephone string
     */
    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return customer's Secondary telephone string
     */
    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    /**
     *
     * @return customer's fax number string
     */
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     *
     * @return customer's address line 1 string
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return customer's address line 2 string
     */
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     *
     * @return customer's postal/zip code string
     */
    @Override
    public String getPostal() {
        return postal;
    }

    @Override
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     *
     * @return customer's address city string
     */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return customer's address region/state/county string
     */
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    /**
     *
     * @return customer's address country string
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return customer's photograph / image
     */
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
