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

        choiceDescription += getTemperatureString();
        choiceDescription += getSizeString();
        choiceDescription += theDfm.finalChoice;
        choiceDescription += "<br> with " + theDfm.nbSugar + " pieces of sugar";

        System.out.println("lets go le " + theDfm.finalChoice + " lets go");

        theDfm.messagesToUser.setText(choiceDescription);
        theDfm.makeDrink();
    }

    private String getSizeString() {
        String size = "";
        switch (theDfm.size) {
            case 0:
                size += "short ";
                break;
            case 1:
                size += "medium ";
                break;
            case 2:
                size += "large ";
                break;
            default:
                break;
        }
        return size;
    }

    private String getTemperatureString() {
        String temperature = "";
        switch (theDfm.temperature) {
            case 0:
                temperature += "Ambient ";
                break;
            case 1:
                temperature += "Gentle ";
                break;
            case 2:
                temperature += "Hot ";
                break;
            case 3:
                temperature += "Very hot ";
                break;
            default:
                break;
        }
        return temperature;
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
