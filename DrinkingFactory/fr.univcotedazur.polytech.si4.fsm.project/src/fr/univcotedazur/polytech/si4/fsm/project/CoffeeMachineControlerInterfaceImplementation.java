package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.basiccoffeecontroller.IBasicCoffeeControllerStatemachine.SCInterfaceListener;

public class CoffeeMachineControlerInterfaceImplementation implements SCInterfaceListener {

    DrinkFactoryMachine theDfm;
    public CoffeeMachineControlerInterfaceImplementation(DrinkFactoryMachine dfm) {
        theDfm = dfm;
    }
    
    @Override
    public void onPayRaised() {
        theDfm.raisePaid();
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
        int change = theDfm.money - theDfm.finalChoice.price;
    	theDfm.lblChange.setText("Change : " + change);
        theDfm.timerChange();
    }

    @Override
    public void onCheckAmountRaised() {
        if (theDfm.enoughMoney()) {
            theDfm.raiseAmountVerified();
        }
    }

    @Override
    public void onCancelRaised() {
    }

    @Override
    public void onStartRecipeRaised() {
        String choiceDescription = "<html>Commande en préparation :<br>";

        switch (theDfm.size) {
            case 0:
                choiceDescription += "Short ";
                break;
            case 1:
                choiceDescription += "Medium ";
                break;
            case 2:
                choiceDescription += "Large ";
                break;
            default:
                break;
        }

        System.out.println("lets go le café lets go");
        choiceDescription += theDfm.choice;

        theDfm.messagesToUser.setText(choiceDescription);
        theDfm.makeDrink();
    }

    @Override
    public void onCleanRaised() {
    	theDfm.messagesToUser.setText("<html>Commande terminée,<br>Nettoyage ...");
    	theDfm.clean();
    }

    @Override
    public void onRestartRaised() {
    	theDfm.choice = theDfm.NONE;
    	theDfm.money = 0;
    	theDfm.recipeStarted = false;
    	theDfm.messagesToUser.setText("Veuillez faire votre choix");
    }

	@Override
	public void onTimesupRaised() {
		System.out.println("commande annulée");
		theDfm.raiseCanceled();
		
	}
}
