package fr.univcotedazur.polytech.si4.fsm.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import fr.univcotedazur.polytech.si4.fsm.project.basiccoffeecontroller.IBasicCoffeeControllerStatemachine.SCInterfaceListener;
import fr.univcotedazur.polytech.si4.fsm.project.user.Info;

public class CoffeeMachineControlerInterfaceImplementation implements SCInterfaceListener {

    DrinkFactoryMachine theDfm;
    public CoffeeMachineControlerInterfaceImplementation(DrinkFactoryMachine dfm) {
        theDfm = dfm;
    }


    @Override
    public void onUpdateOptionsRaised() {
        if (!theDfm.recipeStarted) {
            theDfm.hideOptions();

            if (!theDfm.choice.name.equals("soup")) {
                theDfm.updateSugarSlider();
            }
            else {
                theDfm.updateEpiceSlider();
            }

            theDfm.updateOptions();
        }
    }

    @Override
    public void onRefundRaised() {
    	theDfm.messagesToUser.setText("Abandon de la commande recuperez votre monnaie");
        theDfm.lblChange.setText(String.valueOf(theDfm.money));
        theDfm.reset();
    }

    @Override
    public void onGiveChangeRaised() {
        theDfm.lblChange.setText(String.valueOf(theDfm.change));
    }

    @Override
    public void onTimesupRaised() {
        theDfm.messagesToUser.setText("Commande annulée pour cause d'inactivité");
        theDfm.lblChange.setText(String.valueOf(theDfm.money));
        theDfm.reset();
    }

    @Override
    public void onRestartRaised() {
        theDfm.reset();
        theDfm.progressBar.setValue(0);
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

        theDfm.change = theDfm.computeChange();
        theDfm.hideOptions();
    }

    @Override
    public void onWaitTakeOrderRaised() {
        theDfm.recipeStarted = false;
        theDfm.messagesToUser.setText("<html>Please retrieve<br>your drink<br>Cleaning ...");
    }

    @Override
    public void onCloseDoorRaised() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./picts/closed.jpg"));
        } catch (IOException ee) {
            ee.printStackTrace();
        }
        theDfm.labelForPictures.setIcon(new ImageIcon(myPicture));
    }

    @Override
    public void onOpenDoorRaised() {
        if (theDfm.hasOwnCup()) {
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
            } catch (IOException ee) {
                ee.printStackTrace();
            }
            theDfm.labelForPictures.setIcon(new ImageIcon(myPicture));
        }
        else {
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("./picts/gobeletPolluant.jpg"));
            } catch (IOException ee) {
                ee.printStackTrace();
            }
            theDfm.labelForPictures.setIcon(new ImageIcon(myPicture));
        }
    }

    @Override
    public void onStartRecipeRaised() {
        String choiceDescription = "<html>Preparing order :<br>";

        choiceDescription += getSizeString();
        choiceDescription += theDfm.finalChoice;
        if (!theDfm.hasOwnCup()) {
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("./picts/gobeletPolluant.jpg"));
            } catch (IOException ee) {
                ee.printStackTrace();
            }
            theDfm.labelForPictures.setIcon(new ImageIcon(myPicture));
        }
        System.out.println("lets go le " + theDfm.finalChoice + " lets go");

        if (theDfm.nfcFree) {
            Info currentInfo = theDfm.nfcMap.get(theDfm.nfcInput.getText().hashCode());
            int priceWithDiscount = theDfm.computePrice() - currentInfo.getSum()/10; ;

            if (priceWithDiscount <= 0) {
                choiceDescription += "<br> Price = 0€";
            }
            else {
                choiceDescription += "<br> Price = " + (float)priceWithDiscount/100 + "€";
            }

            currentInfo.setSum(0);
            currentInfo.setCount(0);
            theDfm.nfcFree = false;
        }
        else {
            choiceDescription += "<br> Price = " + (float)theDfm.computePrice()/100 + "€";
        }


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

	@Override
	public void onAdd10Raised() {
		theDfm.progressBar.setValue(theDfm.progressBar.getValue()+10);
	}

	@Override
	public void onAdd15Raised() {
		theDfm.progressBar.setValue(theDfm.progressBar.getValue()+15);
	}

	@Override
	public void onAdd20Raised() {
		theDfm.progressBar.setValue(theDfm.progressBar.getValue()+20);
	}
}
