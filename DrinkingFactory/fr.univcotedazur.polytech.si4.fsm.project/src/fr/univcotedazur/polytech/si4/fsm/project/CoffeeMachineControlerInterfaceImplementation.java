package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import fr.univcotedazur.polytech.si4.fsm.project.basiccoffeecontroller.IBasicCoffeeControllerStatemachine.SCInterfaceListener;

public class CoffeeMachineControlerInterfaceImplementation implements SCInterfaceListener {

    DrinkFactoryMachine theDfm;
    public CoffeeMachineControlerInterfaceImplementation(DrinkFactoryMachine dfm) {
        theDfm = dfm;
    }

    @Override
    public void onRefundRaised() {
    	theDfm.messagesToUser.setText("Abandon de la commande recuperez votre monnaie");
        theDfm.reset();
    }

    @Override
    public void onGiveChangeRaised() {
        if (theDfm.ownCup) {
            theDfm.lblChange.setText(String.valueOf(theDfm.change + 10));
        }
        else {
            theDfm.lblChange.setText(String.valueOf(theDfm.change));
        }
    }

    @Override
    public void onOrderVerifiedRaised() {
        theDfm.recipeStarted = true;
        theDfm.finalChoice = theDfm.choice;
        theDfm.choice = theDfm.NONE;
        theDfm.size = theDfm.sizeSlider.getValue();
        theDfm.temperature = theDfm.temperatureSlider.getValue();
        theDfm.nbSugar = theDfm.sugarSlider.getValue();
        theDfm.updateSliders();
        System.out.println(theDfm.ownCup);

        if (theDfm.ownCup) {
            theDfm.change += (theDfm.money - theDfm.finalChoice.price) + 10;
        }
        else {
            theDfm.change += (theDfm.money - theDfm.finalChoice.price);
        }
    }

    @Override
    public void onRestartRaised() {
        System.out.println("restart");
        theDfm.recipeStarted = false;
        theDfm.reset();
    }

    @Override
    public void onWaitTakeOrderRaised() {
        System.out.println("You can retrieve your drink");
        theDfm.messagesToUser.setText("<html>Please retrieve<br>your drink");
    }

    @Override
    public void onStartRecipeRaised() {
        String choiceDescription = "<html>Preparing order :<br>";

        choiceDescription += getTemperatureString();
        choiceDescription += getSizeString();
        choiceDescription += theDfm.finalChoice;
        choiceDescription += "<br> with " + theDfm.nbSugar + " pieces of sugar";
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/gobeletPolluant.jpg"));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		theDfm.labelForPictures.setIcon(new ImageIcon(myPicture));

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
	public void onTimesupRaised() {
		System.out.println("Commande annulée pour cause d'inactivité");
		theDfm.reset();
	}
}
