package co.opensi.kiakia_pay.core;

/**
 * Created by ALI SHADAÏ (Software Craftman) on 19/06/2017.
 */

public interface PayCallback
{
    /**
     * apellé par le système lorsque la procédure de demande
     * de paiement a démarré
     */
    void onStart();

    /**
     * Cette methode est appellé automatiquement lorsque
     * le paiement est terminée
     * @param isSucces true if success false if not
     */
    void onFinish(boolean isSucces);

    /**
     * Cette methode est automatiquement appellé lorsque
     * lorsque la requette prends trop de temps a s'executer
     * et donc la requette est automatiquement interrompu
     */
    void onTimeOut();
}
