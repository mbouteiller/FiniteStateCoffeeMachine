package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.basiccoffeecontroller.IBasicCoffeeControllerStatemachine.SCInterfaceListener;

public class CoffeeMachineControlerInterfaceImplementation implements SCInterfaceListener {

    DrinkFactoryMachine theDfm;
    public CoffeeMachineControlerInterfaceImplementation(DrinkFactoryMachine dfm) {
        theDfm = dfm;
    }
    
    @Override
    public void onPayRaised() {
    }

    @Override
    public void onRefundRaised() {
    	String message="<html>Commande annulée<br>";
    	if (theDfm.money!=0) {
    		message+="Récuperez vos : <br> "+theDfm.money+" centimes";
    	}
    	theDfm.messagesToUser.setText(message);
    }

    @Override
    public void onGiveChangeRaised() {
    	System.out.println("cahnge");
    	theDfm.messagesToUser.setText("fini");
    }

    @Override
    public void onCheckAmountRaised() {

    }

    @Override
    public void onCancelRaised() {
    	
    }

    @Override
    public void onStartRecipeRaised() {
        System.out.println("lets go le café lets go");
        theDfm.messagesToUser.setText("Commande en préparation");
        theDfm.makeDrink();
    }

    @Override
    public void onCleanRaised() {
    	theDfm.messagesToUser.setText("Commande terminée, nettoyage");
    	theDfm.clean();
    }

    @Override
    public void onRestartRaised() {
    	theDfm.choice=Products.NONE;
    	theDfm.money=0;
    	theDfm.messagesToUser.setText("Veuillez faire votre choix");
    }

	@Override
	public void onTimesupRaised() {
		System.out.println("commande annulée");
		theDfm.raiseCancel();
		
	}
}
