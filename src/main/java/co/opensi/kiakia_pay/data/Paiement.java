package co.opensi.kiakia_pay.data;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.opensi.kiakia_pay.core.GSM;


public class Paiement  {

    private long _id;
    private String montant;
    private String numClient;
    private int provider;
    private String compactdate, extendeddate;

    private Date date;

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }

    /**
     * Modele de transaction
     *
     * @param _id       de la transaction
     * @param montant   de la transaction
     * @param numClient - numero de telephone du client
     * @param date      de la transaction
     */
    public Paiement(long _id, String montant, String numClient, Date date) {
        this._id = _id;
        int m = Double.valueOf(montant).intValue();
        this.montant = String.valueOf(m);
        this.numClient = numClient;
        this.date = date;
        this.provider = GSM.getOperator(numClient);
        setExtendedStringDate(date);
        setCompactStringDate(date);
    }

    public Paiement() {
    }

    public String getExtendedStringDate() {
        SimpleDateFormat extendedFormat = new SimpleDateFormat("HH:mm:ss");
        return extendedFormat.format(date);

    }

    public String getCompatStringDate() {
        SimpleDateFormat extendedFormat = new SimpleDateFormat("yyyy-MM-dd");
        return extendedFormat.format(date);

    }

    private void setExtendedStringDate(Date date) {
        SimpleDateFormat extendedFormat = new SimpleDateFormat("HH:mm:ss");
        extendeddate = extendedFormat.format(date);
    }

    private void setCompactStringDate(Date date) {
        SimpleDateFormat extendedFormat = new SimpleDateFormat("yyyy-MM-dd");
        compactdate = extendedFormat.format(date);
    }

    private String getCompactDate(Date date) {
        SimpleDateFormat compatFormat = new SimpleDateFormat("yyyy-MM-dd");
        String compactdate = compatFormat.format(date);
        return compactdate;
    }

    /*public List<Paiement> getMMOPaiments() {
        return find(Paiement.class, "compactdate = ? and provider = ?", getCompactDate(new Date()), String.valueOf(GSM.IS_MTN_PHONE));
    }

    public List<Paiement> getFLOOZPaiments() {
        return find(Paiement.class, "compactdate = ? and provider = ?", getCompactDate(new Date()), String.valueOf(GSM.IS_MOOV_PHONE));
    }*/
    public String getNumClient() {

        if (!numClient.contains("+")) {
            if (numClient.startsWith("00229"))
                numClient = numClient.replace("00229", "+229");
            else if (numClient.startsWith("229"))
                numClient = new StringBuilder("+").append(numClient).toString();
            else
                numClient = new StringBuilder("+229").append(numClient).toString();
        }
        Log.e("num client", numClient);
        return numClient.trim();
    }

    public void setNumClient(String numClient) {
        this.numClient = numClient;
    }

    public String getMontant() {
        return montant.trim();
    }

    public void setMontant(String montant) {
        int m = Double.valueOf(montant).intValue();
        this.montant = String.valueOf(m);
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        setCompactStringDate(date);
        setExtendedStringDate(date);
    }


    @Override
    public String toString() {
        return "****num client " + getNumClient() + "****montant " + getMontant();
    }

    @Override
    public boolean equals(Object obj) {
        Paiement paiement;
        try {
            paiement = (Paiement)obj;
            return this.getNumClient().equalsIgnoreCase(((Paiement) obj).getNumClient())
                    && this.getMontant().equalsIgnoreCase(((Paiement) obj).getMontant());
        }catch (ClassCastException e){
            return super.equals(obj);
        }
    }
}
